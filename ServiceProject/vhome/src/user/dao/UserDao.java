package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.ParentUserInfo;
import entity.PostBean;
import entity.SendPerson;
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
				sql = "insert into tbl_parent_userinfo values(?,?,?,?,?,?,?,?,?)";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, phone);
				psmt.setString(2, id);
				psmt.setString(3, nikeName);
				psmt.setString(4, sex);
				psmt.setString(5, area);
				psmt.setInt(6, 0);
				psmt.setInt(7, 0);
				psmt.setString(8,"");
				psmt.setString(9,headerImg);
			}else {
				sql = "insert into tbl_child_userinfo values(?,?,?,?,?,?)";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, phone);
				psmt.setString(2, id);
				psmt.setString(3, nikeName);
				psmt.setString(4, sex);
				psmt.setString(5, area);
				psmt.setString(6,headerImg);
			}
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
	public User pwdLogin(String phone) {
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
				user = new User();
				String registerTime = rs.getString("registerTime");
				user.setRegisterTime(registerTime);
			} else {
				System.out.println("用户不存在！");
			}
			rs.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
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
					String registerTime = rs.getString("registerTime");
					user.setPhone(phone);
					user.setPassword(password);
					user.setType(type);
					user.setRegisterTime(registerTime);
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
	//验证码登录
	public User codeLogin(String phone) {
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
				user = new User();
				int type = rs.getInt("type");
				user.setPhone(phone);
				user.setPassword(pwd);
				user.setType(type);
				System.out.println("登陆成功！");
			} else {
				System.out.println("用户不存在！");
			}
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
		try {
			conn = util.getConnection();
			String sql = "";
			if(type==0) {
				System.out.println("type=0");
				sql = "select * from tbl_parent_userinfo where phone='"+phone+"'";
				psmt = conn.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery();
				if (rs.next()) {
					userInfo = new ParentUserInfo();
					userInfo.setPhone(rs.getString("phone"));
					userInfo.setId(rs.getString("id"));
					userInfo.setNikeName(rs.getString("nickName"));
					userInfo.setSex(rs.getString("sex"));
					userInfo.setArea(rs.getString("area"));
					userInfo.setAcieve(rs.getString("achieve"));
					userInfo.setBirthday(rs.getString("birthday"));
					userInfo.setPersonalWord(rs.getString("personalWord"));
					userInfo.setHeaderImg(rs.getString("headimg"));
					userInfo.setType(type);
					System.out.println("用户信息存储完毕--父母");
				}else {
					System.out.println("未查到这个人的信息--父母");
				}
				rs.close();
			}
			if(type==1) {
				System.out.println("type=1");
				sql = "select * from tbl_child_userinfo where phone='"+phone+"'";
				psmt = conn.prepareStatement(sql);
				ResultSet rs1 = psmt.executeQuery();
				if (rs1.next()) {
					userInfo = new ParentUserInfo();
					userInfo.setPhone(rs1.getString("phone"));
					userInfo.setId(rs1.getString("id"));
					userInfo.setNikeName(rs1.getString("nickName"));
					userInfo.setSex(rs1.getString("sex"));
					userInfo.setArea(rs1.getString("area"));
					userInfo.setHeaderImg(rs1.getString("headerImg"));
					userInfo.setType(type);
					System.out.println("用户信息存储完毕--子女");
				}else {
					System.out.println("未查到这个人的信息--子女");
				}
				rs1.close();
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	/**
	 * 按照id查找个人信息
	 *  @title:findUserInfo
	 * @Description: todo
	 * @throws下午8:27:13
	 * returntype:ParentUserInfo
	 */
	public ParentUserInfo findUserInfo(String id) {
		DBUtil util = DBUtil.getInstance();
		ParentUserInfo userInfo = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "";
			sql = "select * from tbl_parent_userinfo where id=?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if (rs.next()) {
				userInfo = new ParentUserInfo();
				userInfo.setPhone(rs.getString("phone"));
				userInfo.setId(rs.getString("id"));
				userInfo.setNikeName(rs.getString("nickName"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setArea(rs.getString("area"));
				userInfo.setBirthday(rs.getString("birthday"));
				userInfo.setAcieve(rs.getString("achieve"));
				userInfo.setPersonalWord(rs.getString("personalWord"));
				userInfo.setHeaderImg(rs.getString("headimg"));
				userInfo.setType(rs.getType());
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
	//修改密码
	public void changePwd(String phone,String newPwd) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql  = "update tbl_user set password='"+newPwd+"' where phone='"+phone+"'";
			psmt = conn.prepareStatement(sql);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("密码修改成功");
			}else {
				System.out.println("密码修改失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//修改用户信息
	public void changeUserInfo(String phone,int type,String flag,String data) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "";
			if(type==0) {
				System.out.println("type=0");
				sql = "update tbl_parent_userinfo set "+flag+"='"+data+"' where phone='"+phone+"'";
			}else if(type==1) {
				System.out.println("type=1");
				sql = "update tbl_child_userinfo set "+flag+"='"+data+"' where phone='"+phone+"'";
			}
			psmt = conn.prepareStatement(sql);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println(data+"修改成功");
			}else {
				System.out.println(data+"修改失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//存储用户头像图片名称
	public void saveHeaderImg(String phone,int type,String headimg) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "";
			if(type==0) {
				System.out.println("header--type=0");
				sql = "update tbl_parent_userinfo set headimg='"+headimg+"' where phone='"+phone+"'";
			}else if(type==1) {
				System.out.println("header--type=1");
				sql = "update tbl_child_userinfo set headerImg='"+headimg+"' where phone='"+phone+"'";
			}
			psmt = conn.prepareStatement(sql);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("头像修改成功");
			}else {
				System.out.println("头像修改失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//添加父母子女关联-->只能子女发送请求，父母接收
	public void addNewRelation(String receivePhone,String receiveName,String sendPhone,String sendName,String setName) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_connect values(?,?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, receivePhone);
			psmt.setString(3, receiveName);
			psmt.setString(4, sendPhone);
			psmt.setString(5, sendName);
			psmt.setString(6, setName);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("添加关联成功");
			}else {
				System.out.println("添加关联失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void addRelationRequest(String sendPhone,String sendName,String receivePhone,int type) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_relations_request values(?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, sendPhone);
			psmt.setString(3, sendName);
			psmt.setString(4, receivePhone);
			psmt.setInt(5, type);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("请求建立关系成功");
			}else {
				System.out.println("请求建立关系失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//查询我的关联
	public List<String> findMyRelation(String sendPerson) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<String> parentPhoneList = new ArrayList<>();
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_connect where sendPhone='"+sendPerson+"'";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				String phone = rs.getString("receivePhone");
				parentPhoneList.add(phone);
				System.out.println("UserDao--"+phone);
			}
			rs.close();
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parentPhoneList;
	}
	public List<SendPerson> queryRequset(String phone){
		List<SendPerson> list = new ArrayList<SendPerson>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_relations_request where receive_phone=? and type=0";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, phone);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SendPerson sendPerson = new SendPerson();
				sendPerson.setSendName(rs.getString("send_name"));
				sendPerson.setSendPhone(rs.getString("send_phone"));
				list.add(sendPerson);
			}
			rs.close();
			ps.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				util.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	//修改关联信息
	public void updateRelations(String phone,String send_phone,String type) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "update tbl_relations_request set type='"+type+"' where receive_phone='"+phone+"' and send_phone='"+send_phone+"'";
			System.out.println(sql);
			psmt = conn.prepareStatement(sql);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("修改成功");
			}else {
				System.out.println("修改失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
