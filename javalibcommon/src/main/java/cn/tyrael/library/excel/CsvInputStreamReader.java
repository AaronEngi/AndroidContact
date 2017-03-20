package cn.tyrael.library.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import cn.tyrael.library.log.LogAdapter;

/**
 * Created by wangchao on 2017/3/20.
 */

public class CsvInputStreamReader extends CsvReader{
    private static final String TAG = "CsvInputStreamReader";
    private final InputStream inputStream;

    public CsvInputStreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void open(){
        LogAdapter.d(TAG, "open:");
        scanner = new Scanner(inputStream, "utf-8");
    }
}
