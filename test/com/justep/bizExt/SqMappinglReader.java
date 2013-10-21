package com.justep.bizExt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class SqMappinglReader {
	private LinkedHashMap<String, String> sm = new LinkedHashMap<String, String>();

	public SqMappinglReader(String fileName) throws Throwable {
		InputStream in = SqMappinglReader.class.getClassLoader().getResourceAsStream(
				"./" + fileName);
		InputStreamReader ir = new InputStreamReader(in, "utf-8");
		BufferedReader br = new BufferedReader(ir);
		loadSql(br);
		br.close();

	}

	private void loadSql(BufferedReader br) throws Throwable {
		
		StringBuffer sb = new StringBuffer();
		String currentElement = "";
		boolean hasNext = true;

		while (hasNext) {
			String s = br.readLine();
			if (s == null) {
				/**
				 * 文件结束 不循环了
				 */
				hasNext = false;
			} else {
				/**
				 * 遇到/*开头的行的时候判断上面读取的部分是不是一个sql-maping
				 * 
				 */
				if (s.startsWith("/*")) {
					currentElement = (sb.toString() != "" ? sb.toString() : "");
					dealElement(currentElement);
					sb = new StringBuffer();
				} else {
					if (!s.trim().equals("")) {
						sb.append(" ");
					}

				}
				sb.append(s.trim());
				s = s.trim();
			}

		}

	}

	public static void main(String[] args) throws Throwable {
		InputStream in = SqMappinglReader.class.getClassLoader().getResourceAsStream(
				"pamis.sql");
		System.out.println(in);
		SqMappinglReader sr = new SqMappinglReader("pamis.sql");

		for (Iterator<String> it = sr.sm.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			System.out.println("*********************\nkey=" + key + "\nvalue="
					+ sr.sm.get(key) + "\n**************\n");
		}

	}


	

	private void dealElement(String element) {
		
		element = element.trim();
		/**
		 * 如果上面部分有值并且 是以/*开头，不是以*\/结尾 同时中间有结束标记的
		 * 符合是一个sql-maping的部分，加入数组中
		 */
		if (element.startsWith("/*") && element.contains("*/")
				&& !element.endsWith("*/")) {
			String[] part = element.split("\\*/");
			if (part.length == 2) {
				String key = part[0];
				key = key.replace("/*", "").replaceAll("\\*", "").trim();
				sm.put(key, part[1].trim());
			}
		}
	}
}
