package com.justep.doc;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.justep.loadRunner.Scenario;

public class TestUpload {
	public static void main(String[] args) throws Exception {
		/*for (int i = 0; i < 10; i++) {
			System.out.println("先进行一次单走的测试");
			File f =new File("c:/test.doc");
			AbstractDoc ad =new AbstractDoc();
			ad.upload(false, f);
			System.out.println("开始并发测试");
		}*/
		
		
		new Scenario(1, new Runnable(){
			public void run() {
				File f =new File("c:/testIndex.doc");
				AbstractDoc ad =new AbstractDoc("http://192.168.1.49:8080/DocServer");
				try {
					ad.upload(false, f);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1, 1, TimeUnit.SECONDS);
	}
}
