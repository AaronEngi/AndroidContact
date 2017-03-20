package wang.tyrael.order;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.tyrael.library.excel.CsvFileReader;
import cn.tyrael.library.excel.CsvInputStreamReader;
import cn.tyrael.library.excel.CsvReader;

public class OrderProcessor {
    private final CsvReader csvReader;

    public OrderProcessor(String file) {
        this.csvReader = new CsvFileReader(file);
        this.csvReader.open();
    }

    public OrderProcessor(InputStream file) {
        this.csvReader = new CsvInputStreamReader(file);
        this.csvReader.open();
    }

    public List<OrderData> parse(){
        List<OrderData> l = new ArrayList<OrderData>();
        csvReader.nextLine();
        while(csvReader.hasNextLine()){
            String[] line = csvReader.nextLine();
            OrderData data = new OrderData();
            data.mobile = line[17];//r
            data.name = line[18];//s
            l.add(data);
        }
        return l;
    }
}
