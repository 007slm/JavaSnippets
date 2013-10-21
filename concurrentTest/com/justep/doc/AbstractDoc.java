package com.justep.doc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.justep.util.Utils;


public class AbstractDoc {
	public AbstractDoc(String host) {
		this.host=host;
	}
	private static String host ="http://192.168.1.49:8080/DocServer";
	
	public void download(Boolean isHttps, OutputStream outputStream, String versionID, String partType,String fileID) throws Exception {
		InputStream inputStream = null;
		versionID = Utils.isEmptyString(versionID) ? "last" : versionID;
		partType = Utils.isEmptyString(partType) ? "content" : partType;
		String url = host + "/repository/file/download/" + fileID + "/" + versionID + "/" + partType ;
				
		;
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		HttpMethod method = new GetMethod(url);
		try {
			client.executeMethod(method);
			inputStream = method.getResponseBodyAsStream();

			int bytesRead;
			byte[] buf = new byte[4 * 1024]; //4K buffer
			while ((bytesRead = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, bytesRead);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
	}

	private String getsFileID() {
		// TODO Auto-generated method stub
		return null;
	}

	public void upload(String host, Part[] parts) throws Exception {
		String url = host+ "/repository/file/cache/upload";
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		MultipartRequestEntity multipartRequestEntity = new MultipartRequestEntity(parts, postMethod.getParams());
		postMethod.setRequestEntity(multipartRequestEntity);
		System.out.println(Thread.currentThread().getName()+"开始发http请求");
		int statusCode = httpClient.executeMethod(postMethod);
		System.out.println(Thread.currentThread().getName()+"http请求结束");
		if (statusCode != HttpStatus.SC_OK) {
			throw new RuntimeException("Failed to send request to DocServer: " + postMethod.getStatusLine());
		} else {
			InputStream is = postMethod.getResponseBodyAsStream();
			SAXReader reader = new SAXReader();
			Document responseDoc = null;
			try {
				responseDoc = reader.read(is);
				postMethod.releaseConnection();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			org.dom4j.Element fe = (org.dom4j.Element) responseDoc.getRootElement().selectSingleNode("//file");
			System.out.println(fe.asXML());
			
		}
	}

	private void upload(String host, InputStream inputStream) throws Exception {
		
		String sDocName = getsDocName();
		InputStreamPartSource bps = new InputStreamPartSource(inputStream, sDocName);
		Part[] parts = { new FilePart(sDocName, bps) };
		upload(host, parts);
	}

	private String getsDocName() {
		return "fileName"+new Random(100);
	}

	private void upload(String host, File file) throws Exception {
		String sDocName = getsDocName();

		Part[] parts = { new FilePart(sDocName, file) };
		upload(host, parts);
	}

	public void upload(boolean isHttps, InputStream inputStream) throws Exception {
		upload(getHost(isHttps), inputStream);
	}

	private String getHost(boolean isHttps) {
		return host;
	}

	public void upload(boolean isHttps, File file) throws Exception {
		upload(getHost(isHttps), file);
	}



	private class InputStreamPartSource implements PartSource {
		private InputStream in = null;
		private String fileName = null;

		public InputStreamPartSource(InputStream in, String fileName) {
			this.in = in;
			this.fileName = fileName;
		}

		public InputStream createInputStream() throws IOException {
			return in;
		}

		public String getFileName() {
			return fileName;
		}

		public long getLength() {
			try {
				return this.in.available();
			} catch (IOException e) {
				e.printStackTrace();
				return -1;
			}
		}
	}

}
