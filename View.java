package com.zeng.douban;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

public class View {

  public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("豆瓣图书搜索");
		f.setLocation(100, 100);
		f.setSize(510, 500);
		f.setVisible(true);
		f.setLayout(new FlowLayout());
		// 建立输入图书ISBN码项

		f.add(new JLabel("ISBN:"));
		final JTextField id = new JTextField(10);
		f.add(id);
		f.add(new JLabel("(输入10~13位数字)    "));
		JButton butt = new JButton("搜索");
		f.add(butt);
		// f.add(new Label("查询结果：\n"));
		final JTextArea area = new JTextArea(20, 30);
		// f.add(new JScrollPane(area));
		f.add(area);
		// 设置按钮监听
		butt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book book = null;
				BookSet isbnTest = new BookSet();
				String isbn = id.getText();// 获取输入的图书ISBN码
				String bookjson;
				JSONObject jsonobj = null;
				String s;
				try {
					bookjson = isbnTest.fetchBookInfoByXML(isbn);

					if (bookjson != null) {
						jsonobj = isbnTest.stringToJson(bookjson);
						book = isbnTest.setBookData();
					}

					if (book != null)
						s = "名称：" + book.getTitle() + "\n" + "作者："
								+ book.getAuthor() + "\n" + "ISBN:"
								+ book.getIsbn10() + "\n" + "介绍:"
								+ book.getSummary();
					else
						s = "查询不到该书！";
					area.setText(s);

				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		});
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent args) {
				System.exit(0);
			}
		});

	}

}
