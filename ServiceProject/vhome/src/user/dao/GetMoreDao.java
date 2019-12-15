package transport.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBManage;

public class GetMoreDao {
	private Statement stmt;
	private ResultSet rs0;
	private ResultSet rs1;
	private String myId;
	private String parent0=" ";
	private String parent1=" ";
	private String sex;
	private String imei;
	private String[] all= {" "," "};
	public String[] getMore(String phone) {
		try {
			Connection conn = DBManage.getInstance().getConnection();
			stmt = conn.createStatement();
			// 执行查询
			rs0 = stmt.executeQuery("select id from tbl_child_userinfo where phone ='"+phone+"'");
			while(rs0.next()) {
				myId= rs0.getString(1);
				rs0 = stmt.executeQuery("select connectId from tbl_connect where personId ='"+myId+"'");
				while(rs0.next()) {
					parent0 = rs0.getString(1);
					rs0 = stmt.executeQuery("select sex,imeiNumber from tbl_parent_userinfo where id ='"+parent0+"'");
					while(rs0.next()) {
						sex = rs0.getString(1);
						imei = rs0.getString(2);
					}
					if(sex.equals("男")) {
						all[0]=imei;
					}else {
						all[1]=imei;
					}
					if(rs0.next()!=false) {
					parent1 = rs0.getString(2);
					rs0 = stmt.executeQuery("select sex,imeiNumber where id ='"+parent1+'"');
					while(rs0.next()) {
						sex = rs0.getString(1);
						imei = rs0.getString(2);
					}
					if(sex.equals("男")) {
						all[0]=imei;
					}else {
						all[1]=imei;
					}
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
