package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.ParentUserInfo;
import entity.User;

public class UserDao {
	//注册用户
	public void registerUser(String phone, String password, String registerTime, String id, String wechat, String qq, int type){
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_user values(?,?,?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, phone);
			psmt.setString(2, password);
			psmt.setString(3, registerTime);
			psmt.setString(4, id);
			psmt.setString(5, wechat);
			psmt.setString(6,qq);
			psmt.setInt(7, type);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("注册成功");
			}else
				System.out.println("注册失败");
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//添加用户信息
	public void addUserInfo(String phone, String id, String nikeName, String sex, String area, String headerImg, int type){
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "";
			if(type == 0) {
				sql = "insert into tbl_parent_userinfo values(?,?,?,?,?,?)";
			}else
				sql = "insert into tbl_child_userinfo values(?,?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, phone);
			psmt.setString(2, id);
			psmt.setString(3, nikeName);
			psmt.setString(4, sex);
			psmt.setString(5, area);
			psmt.setString(6,headerImg);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("添加用户信息成功");
			}else
				System.out.println("添加用户信息失败");
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//密码登录
	public User pwdLogin(String phone,String password) {
		DBUtil util = DBUtil.getInstance();
		User user = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_user where phone='"+phone+"'";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs.next()) {
				String pwd = rs.getString("password");
				if (password.equals(pwd)) {// 判断密码是否正确5
					user = new User();
					int type = rs.getInt("type");
					user.setPhone(phone);
					user.setPassword(password);
					user.setType(type);
					System.out.println("登陆成功！");
				} else {
					System.out.println("密码错误！");
				}
			} else {
				System.out.println("用户不存在！");
			}
			rs.close();
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	//判断是否存在用户
	public boolean exist(String phone) {
		DBUtil util = DBUtil.getInstance();
		boolean a = true;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_user where phone='"+phone+"'";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if(rs.next()) {
				a = false;
				System.out.println("用户已经存在！");
			}
			rs.close();
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	//查询用户个人信息
	public ParentUserInfo findUserInfo(String phone,int type) {
		DBUtil util = DBUtil.getInstance();
		ParentUserInfo userInfo = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "";
			if(type==0) {
				System.out.println("type=0");
				sql = "select * from tbl_parent_userinfo where phone='"+phone+"'";
			}else if(type==1) {
				System.out.println("type=1");
				sql = "select * from tbl_child_userinfo where phone='"+phone+"'";
			}
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs.next()) {
				userInfo = new ParentUserInfo();
				userInfo.setPhone(rs.getString("phone"));
				userInfo.setId(rs.getString("id"));
				userInfo.setNikeName(rs.getString("nickName"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setArea(rs.getString("area"));
				userInfo.setAcieve(rs.getString("achieve"));
				userInfo.setPersonalWord(rs.getString("personalWord"));
				userInfo.setHeaderImg(rs.getString("headimg"));
				userInfo.setType(type);
				System.out.println("用户信息存储完毕");
			}else {
				System.out.println("未查到这个人的信息");
			}
			rs.close();
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
}
