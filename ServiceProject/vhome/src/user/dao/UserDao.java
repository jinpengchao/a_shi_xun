package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.AdminMessage;
import entity.NewTicketBody;
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
	public int getType(String phone) {
		DBUtil util = DBUtil.getInstance();
		int type =-1;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_user where phone='"+phone+"'";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs.next()) {
				type = rs.getInt("type");
				System.out.println("typetypetype！"+type);
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
		return type;
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
				}
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
//			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				util.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				util.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				if(null!=rs.getString("receiveName") && null!=rs.getString("receivePhone")) {
					String phone = rs.getString("receivePhone");
					String nickName = rs.getString("receiveName");
					parentPhoneList.add(nickName+"("+phone+")");
					System.out.println("UserDao--"+phone);
				}
			}
			rs.close();
			psmt.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				util.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
//			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				util.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void updateReadable(int id) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "update tbl_admin_message set unread='"+1+"' where id='"+id+"'";
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
	public void saveQuestion(String name, String phone,String registationID,String content,String subject,int status) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_questions values(?,?,?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, name);
			psmt.setString(3, phone);
			psmt.setString(4, registationID);
			psmt.setString(5, subject);
			psmt.setString(6, content);
			psmt.setInt(7, status);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("发送反馈成功");
			}else {
				System.out.println("发送反馈失败");
			}
			psmt.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				util.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public List<NewTicketBody> findAllQuestuins(int status) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<NewTicketBody> questionsList = new ArrayList<>();
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_questions where status=?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, status);
			rs = psmt.executeQuery();
			while(rs.next()) {
				NewTicketBody ticketBody = new NewTicketBody();
				ticketBody.setId(rs.getInt("id"));
				ticketBody.setCreatorName(rs.getString("name"));
				ticketBody.setCreatorPhone(rs.getString("phone"));
				ticketBody.setSubject(rs.getString("theme"));
				ticketBody.setContent(rs.getString("content"));;
				ticketBody.setRegistrationId(rs.getString("registrationID"));;
				questionsList.add(ticketBody);
			}
			rs.close();
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionsList;
	}
	public void updateQuestions(int id) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "update tbl_questions set status='"+1+"' where id='"+id+"'";
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
	public List<AdminMessage> selectAllAdminMessage(String phone) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<AdminMessage> messageList = new ArrayList<>();
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_admin_message where phone=? order by id desc";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, phone);
			rs = psmt.executeQuery();
			while(rs.next()) {
				AdminMessage message = new AdminMessage();
				message.setId(rs.getInt("id"));
				message.setContent(rs.getString("content"));
				message.setPostId(rs.getInt("postId"));
				message.setReadable(rs.getInt("unread"));
				message.setContent_answer(rs.getString("content_answer"));
				messageList.add(message);
			}
			rs.close();
			psmt.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				util.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return messageList;
	}
	
	public void saveAnswers(int id,String phone,String content,String registrationID) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_answers values(?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, phone);
			psmt.setString(3, content);
			psmt.setInt(4, id);
			psmt.setString(5, registrationID);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("发送回复成功");
			}else {
				System.out.println("发送回复失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void saveMessage(int id,String title,String phone,String personId,String content) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			
			conn = util.getConnection();
			String sql = "insert into tbl_admin_message values(?,?,?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, title);
			psmt.setInt(3, id);
			psmt.setString(4, phone);
			psmt.setString(5, personId);
			psmt.setInt(6, 0);
			psmt.setString(7, content);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("发送回复成功");
			}else {
				System.out.println("发送回复失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String getContentAndroidandHTML(String postId) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		String content = "";
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_answers where postId=?";
			psmt = conn.prepareStatement(sql);
			int idss = Integer.parseInt(postId);
			int id = idss;
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				content = rs.getString("content");
			}else {
				content = "啊哦~数据走丢了~o(╥﹏╥)o";
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content;
	}
	//-----------------------------后台----------------------------------
	//获取用户列表
	public List<ParentUserInfo> getUserList() {
		List<ParentUserInfo> list= new ArrayList<ParentUserInfo>(); 
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_parent_userinfo";
			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				ParentUserInfo parent = new ParentUserInfo();
				parent.setPhone(rs.getString(1));
				parent.setId(rs.getString(2));
				parent.setNikeName(rs.getString(3));
				parent.setSex(rs.getString(4));
				parent.setArea(rs.getString(5));
				parent.setImei(rs.getInt(6));
				parent.setPersonalWord(rs.getString(8));
				parent.setStatus(rs.getString(7));
				parent.setHeaderImg(rs.getString(9));
				list.add(parent);
			}
			psmt.close();
			rs.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public long getSum(String tbl_name) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		long sum = 0;
		try {
			conn = util.getConnection();
			String sql = "select count(*) from "+tbl_name;
			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				sum = rs.getLong(1); 
			}
			psmt.close();
			rs.close();
			util.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
	}
	public void delUser(String id) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "delete from tbl_parent_userInfo where id='"+id+"'";
			psmt = conn.prepareStatement(sql);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("删除成功");
			}else {
				System.out.println("删除失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateUser(ParentUserInfo pf) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "update tbl_parent_userInfo set "
					+ "phone=?,nickName=?,sex=?,area=?,imei=?,status=?,personalWord=?,headimg=?"
					+ " where id=?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,pf.getPhone());
			psmt.setString(2,pf.getNikeName());
			psmt.setString(3,pf.getSex());
			psmt.setString(4,pf.getArea());
			psmt.setInt(5, pf.getImei());
			psmt.setString(6, pf.getStatus());
			psmt.setString(7, pf.getPersonalWord());
			psmt.setString(8,pf.getHeaderImg());
			psmt.setString(9, pf.getId());
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("更新成功");
			}else {
				System.out.println("更新失败");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<ParentUserInfo> getUserPosted() {
		List<ParentUserInfo> list= new ArrayList<ParentUserInfo>(); 
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_parentuser_reported";
			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				ParentUserInfo parent = new ParentUserInfo();
				parent.setPhone(rs.getString(1));
				parent.setNikeName(rs.getString(2));
				parent.setPersonalWord(rs.getString(3));
				parent.setHeaderImg(rs.getString(4));
				parent.setStatus(rs.getString(5));
				parent.setCloseDays(rs.getLong(6));
				list.add(parent);
			}
			psmt.close();
			rs.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean compareNickName(String name) {//验证生成的随机昵称重复
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		long sum = 0;
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_user where nickName ='"+name+"'";
			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				psmt.close();
				rs.close();
				util.closeConnection();
				return true;
			}else {
				psmt.close();
				rs.close();
				util.closeConnection();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//根据违规内容不同分8种情况重置用户信息
	public void definateColseDays(String nameMark ,String pwMark,String headerMark,int days,String phone) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		String sql=null;
		PreparedStatement psmt = null;
		String result = nameMark+pwMark+headerMark;//2^3种情况
		switch(result) {
		case "TTT":
			System.out.println("审核通过，无问题！！");
		case "FTT":
			sql = "update tbl_parent_userInfo set "
					+ "nickName=?,status=?,closeDays=?"
					+ " where phone=?";
			try {
				conn = util.getConnection();
				psmt = conn.prepareStatement(sql);
				String radomName = "随机昵称"+Math.random();
				while(!compareNickName(radomName)){
					psmt.setString(1,radomName);
				}
				psmt.setString(2,"封禁");
				psmt.setInt(3, days);
				psmt.setString(4, phone);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("审核完成");
				}else {
					System.out.println("审核失败");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "TFT":
			sql = "update tbl_parent_userInfo set "
					+ "personalWord=?,status=?,closeDays=?"
					+ " where phone=?";
			try {
				conn = util.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1,"点击添加");
				psmt.setString(2,"封禁");
				psmt.setInt(3, days);
				psmt.setString(4, phone);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("审核完成");
				}else {
					System.out.println("审核失败");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "TTF":
			sql = "update tbl_parent_userInfo set "
					+ "headimg=?,status=?,closeDays=?"
					+ " where phone=?";
			try {
				conn = util.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1,"headMoRen.jpg");
				psmt.setString(2,"封禁");
				psmt.setInt(3, days);
				psmt.setString(4, phone);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("审核完成");
				}else {
					System.out.println("审核失败");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "FFT":
			sql = "update tbl_parent_userInfo set "
					+ "nickName=?,personalWord=?,status=?,closeDays=?"
					+ " where phone=?";
			try {
				conn = util.getConnection();
				psmt = conn.prepareStatement(sql);
				String radomName = "随机昵称"+Math.random();
				while(!compareNickName(radomName)){
					psmt.setString(1,radomName);
				}
				psmt.setString(2, "点击添加");
				psmt.setString(3,"封禁");
				psmt.setInt(4, days);
				psmt.setString(5, phone);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("审核完成");
				}else {
					System.out.println("审核失败");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "FTF":
			sql = "update tbl_parent_userInfo set "
					+ "nickName=?,headimg=?,status=?,closeDays=?"
					+ " where phone=?";
			try {
				conn = util.getConnection();
				psmt = conn.prepareStatement(sql);
				String radomName = "随机昵称"+Math.random();
				while(!compareNickName(radomName)){
					psmt.setString(1,radomName);
				}
				psmt.setString(2, "headMoRen.jpg");
				psmt.setString(3,"封禁");
				psmt.setInt(4, days);
				psmt.setString(5, phone);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("审核完成");
				}else {
					System.out.println("审核失败");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "TFF":
			sql = "update tbl_parent_userInfo set "
					+ "personalWord=?,headimg=?,status=?,closeDays=?"
					+ " where phone=?";
			try {
				conn = util.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1,"点击添加");
				psmt.setString(2, "headMoRen.jpg");
				psmt.setString(3,"封禁");
				psmt.setInt(4, days);
				psmt.setString(5, phone);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("审核完成");
				}else {
					System.out.println("审核失败");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "FFF":
			sql = "update tbl_parent_userInfo set "
					+ "nickName=?,personalWord=?,headimg=?,status=?,closeDays=?"
					+ " where phone=?";
			try {
				conn = util.getConnection();
				psmt = conn.prepareStatement(sql);
				String radomName = "随即昵称"+Math.random();
				while(!compareNickName(radomName)){
					psmt.setString(1,radomName);
				}
				psmt.setString(2, "点击添加");
				psmt.setString(3, "headMoRen.jpg");
				psmt.setString(4,"封禁");
				psmt.setInt(5, days);
				psmt.setString(6, phone);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("审核完成");
				}else {
					System.out.println("审核失败");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}

	}
}
