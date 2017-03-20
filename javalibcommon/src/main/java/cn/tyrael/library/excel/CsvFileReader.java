package cn.tyrael.library.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cn.tyrael.library.log.LogAdapter;

public class CsvFileReader extends CsvReader {
	private static final String TAG = "CsvFileReader";

	private final String file;

	public CsvFileReader(String file) {
		super();
		this.file = file;
	}

	public void open(){
		LogAdapter.d(CsvFileReader.TAG, "open:" + file);
		try {
			scanner = new Scanner(new File(file), "utf-8");
		} catch (FileNotFoundException e) {
			LogAdapter.w(CsvFileReader.TAG, "FileNotFoundException");
			e.printStackTrace();

		}
	}

}
