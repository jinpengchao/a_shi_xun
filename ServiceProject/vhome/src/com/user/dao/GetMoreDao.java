package com.user.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dbutil.DBUtil;


public class GetMoreDao {
	private Statement stmt;
	private ResultSet rs0;
	private String phoneInfo=" ";
	private String sex;
	private String imei;
	private String[] all= {"nasp","nasp"};
	public String[] getMore(String phone) {
		try {
			Connection conn = DBUtil.getInstance().getConnection();
			stmt = conn.createStatement();
			// 执行查询
				rs0 = stmt.executeQuery("select sendPhone from tbl_connect where receivePhone ='"+phone+"'");
				while(rs0.next()) {
					phoneInfo = rs0.getString(1);
					rs0 = stmt.executeQuery("select sex,imeiNumber from tbl_parent_userinfo where phone ='"+phoneInfo+"'");
					while(rs0.next()) {
						sex = rs0.getString(1);
						imei = rs0.getString(2);
						if(sex.equals("男")) {
							all[0]=imei;
						}else {
							all[1]=imei;
						}
					}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all;
	}
}
