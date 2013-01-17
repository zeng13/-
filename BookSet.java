package com.zeng.douban;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookSet {
  String isbnUrl = "http://api.douban.com/book/subject/";
	JSONObject jsonobj;
	Book book;

	/**
	 * 从根据isbn号从豆瓣获取数据
	 * 
	 * @param isbnNo
	 * @return
	 * @throws IOException
	 */
	public String fetchBookInfoByXML(String isbnNo) {
		try {
			String requestUrl = isbnUrl + isbnNo + "?alt=json";
			URL url = new URL(requestUrl);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			br.close();
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	public JSONObject stringToJson(String str) throws JSONException {
		if (str != null) {
			jsonobj = new JSONObject(str);
			return jsonobj;
		} else
			return null;
	}

	// 设置获取的图书信息并返回对象
	public Book setBookData() {
		try {
			JSONObject mesge = jsonobj.getJSONObject("summary");
			book = new Book();
			book.setSummary(in(mesge.getString("$t")));// 设置图书概要信息
			JSONArray bookMessage = jsonobj.getJSONArray("db:attribute");
			JSONObject info;
			// 设置ISBN10
			info = bookMessage.getJSONObject(0);
			book.setIsbn10(in(info.getString("$t")));
			// 设置ISBN13
			info = bookMessage.getJSONObject(1);
			book.setIsbn13(in(info.getString("$t")));
			// 设置书名
			info = bookMessage.getJSONObject(2);
			book.setTitle((info.getString("$t")));
			// 设置图书页数
			info = bookMessage.getJSONObject(3);
			book.setPage(in(info.getString("$t")));
			// 设置作者姓名
			info = bookMessage.getJSONObject(4);
			book.setAuthor(in(info.getString("$t")));
			// 设置图书价格
			info = bookMessage.getJSONObject(5);
			book.setPrice(in(info.getString("$t")));
			// 设置出版社
			info = bookMessage.getJSONObject(6);
			book.setPublisher(in(info.getString("$t")));
			// 设置Paperback
			info = bookMessage.getJSONObject(7);
			book.setBinding(in(info.getString("$t")));
			// 设置出版日期
			info = bookMessage.getJSONObject(8);
			book.setPubdate(in(info.getString("$t")));

			return book;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	// 将过长的字符串截断从新分行
	public String in(String out) {
		int a = 27;
		StringBuffer sb = new StringBuffer(out);
		while (sb.length() > a) {
			sb.insert(a, "\n");
			a = a + 27;
		}
		return sb.toString();
	}

}
