package com.justep.report;

import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.justep.loadRunner.Scenario;

public class TestReport {
	public static void main(String[] args) {
		new Scenario(1, new Runnable(){
			public void run() {
				HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
				HttpMethod method = new PostMethod("http://192.168.1.49:8080/ReportServer/reportservice");
				try {
					client.executeMethod(method);
					String response = method.getResponseBodyAsString();
					System.out.println(response);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					method.releaseConnection();
				}
			}
		}, 100, 1, TimeUnit.SECONDS);
	}
	
}
