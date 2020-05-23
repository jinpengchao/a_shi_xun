package alarm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.AlarmBean;
public class AlarmDao {
	//发送新闹钟
	public void sendNewAlarm(int alarmId, String alarmTime, String sendPerson, String receivePerson, String content ,int clocktype){
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_alarm values(?,?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, alarmTime);
			psmt.setString(3, sendPerson);
			psmt.setString(4, receivePerson);
			psmt.setString(5, content);
			psmt.setInt(6, clocktype);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("AlarmDao--添加闹钟成功");
			}else
				System.out.println("AlarmDao--添加闹钟失败");
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//遍历常用闹钟列表
	public List<String> queryNormalAlarm(String phone){
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		List<String> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_remind where phone='"+phone+"'";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				String content = rs.getString("content");
				list.add(content);
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//存储常用闹钟列表
	public void saveNormalAlarm(String content,String phone){
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_remind values(?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, content);
			psmt.setString(3, phone);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("AlarmDao--常用闹钟列表添加成功");
			}else
				System.out.println("AlarmDao--常用闹钟列表添加失败");
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//读取闹钟列表
	public List<AlarmBean> allAlarm(String phone) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<AlarmBean> list = new ArrayList<>();
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_alarm where receivePersonId='"+phone+"'";
			System.out.println(sql );
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				AlarmBean alarmBean = new AlarmBean();
				int alarmId = rs.getInt("alarmId");
				String alarmTime = rs.getString("alarmTime");
				String sendPersonId = rs.getString("sendPersonId");
				String receivePersonId = rs.getString("receivePersonId");
				String content = rs.getString("content");
				String clocktype = rs.getString("clocktype");

				alarmBean.setAlarmId(alarmId);
				alarmBean.setAlarmTime(alarmTime);
				alarmBean.setSendPersonId(sendPersonId);
				alarmBean.setReceivePersonId(receivePersonId);
				alarmBean.setContent(content);
				alarmBean.setClocktype(clocktype);

				list.add(alarmBean);
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
		return list;
	}
	//读取我的发送列表
	public List<AlarmBean> mySendedAlarm(String phone) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt1 = null;
		ResultSet rs1 = null;
		List<AlarmBean> list = new ArrayList<>();
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_alarm where sendPersonId='"+phone+"'";
			psmt1 = conn.prepareStatement(sql);
			rs1 = psmt1.executeQuery();
			while(rs1.next()) {
				AlarmBean alarmBean = new AlarmBean();
				int alarmId = rs1.getInt("alarmId");
				String alarmTime = rs1.getString("alarmTime");
				String sendPersonId = rs1.getString("sendPersonId");
				String receivePersonId = rs1.getString("receivePersonId");
				String content = rs1.getString("content");
				String clocktype = rs1.getString("clocktype");

				alarmBean.setAlarmId(alarmId);
				alarmBean.setAlarmTime(alarmTime);
				alarmBean.setSendPersonId(sendPersonId);
				alarmBean.setReceivePersonId(receivePersonId);
				alarmBean.setContent(content);
				alarmBean.setClocktype(clocktype);

				list.add(alarmBean);
			}
			rs1.close();
			psmt1.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//删除已经发送的闹钟
	public void removeSendedAlarm(String content) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "delete from tbl_alarm where content='"+content+"'";
			psmt = conn.prepareStatement(sql);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("AlarmDao--闹钟删除成功"+content);
			}else {
				System.out.println("AlarmDao--闹钟删除成功");
			}
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//删除常用列表闹钟
		public void removeNormalAlarm(String content) {
			DBUtil util = DBUtil.getInstance();
			Connection conn = null;
			PreparedStatement psmt = null;
			try {
				conn = util.getConnection();
				String sql = "delete from tbl_remind where content='"+content+"'";
				psmt = conn.prepareStatement(sql);
				int rs = psmt.executeUpdate();
				if(rs>0) {
					System.out.println("AlarmDao--常用闹钟删除成功"+content);
				}else {
					System.out.println("AlarmDao--常用闹钟删除成功");
				}
				psmt.close();
				util.closeConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	//修改闹钟信息
	public void chandeAlarm(int alarmId,String time,String content,int clocktype) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "";
			if(":".equals(time)) {
				sql = "update tbl_alarm set clocktype='"+clocktype+"' where content='"+content+"'";
			}else {
				sql = "update tbl_alarm set alarmTime='"+time+"',content='"+content+"',clocktype=1 where alarmId='"+alarmId+"'";
			}
		
			psmt = conn.prepareStatement(sql);
			int rs = psmt.executeUpdate();
			if(rs>0) {
				System.out.println("AlarmDao--闹钟信息修改成功"+sql);
			}else {
				System.out.println("AlarmDao--闹钟信息修改失败");
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