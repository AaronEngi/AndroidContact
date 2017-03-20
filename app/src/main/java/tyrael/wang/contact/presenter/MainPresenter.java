package tyrael.wang.contact.presenter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.tyrael.library.log.LogAdapter;
import tyrael.wang.contact.activity.MainActivity;
import wang.tyrael.order.OrderData;
import wang.tyrael.order.OrderProcessor;

/**
 * Created by wangchao on 2017/3/20.
 */

public class MainPresenter {
    private static final String TAG = "MainPresenter";
    private final MainActivity mainActivity;

    public MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void insertContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取assets数据
                InputStream is = null;
                List<OrderData> list = null;
                try {
                    is = mainActivity.getResources().getAssets().open("20150516.csv");
                    list = new OrderProcessor(is).parse();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LogAdapter.d(TAG, "size:" + list.size());
                for (OrderData orderData : list) {
                    ContactData contactData = new ContactData();
                    contactData.name = orderData.name;
                    contactData.mobile = orderData.mobile;
                    insertContact(contactData);
                }
            }
        }).start();

    }

    private void insertContact(ContactData data){
        Context context  = mainActivity;
        ContentValues values = new ContentValues();
        //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);

        //往data表入姓名数据
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        if(TextUtils.isEmpty(data.name)){
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, data.mobile);
        }else{
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, data.name);
        }
        context.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);

        //往data表入电话数据
        values.clear();
        values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, data.mobile);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        context.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);
    }
}
