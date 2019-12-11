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
	public void sendNewAlarm(int alarmId, String alarmTime, String sendPerson, String receivePerson, String content){
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = util.getConnection();
			String sql = "insert into tbl_alarm values(?,?,?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, 0);
			psmt.setString(2, alarmTime);
			psmt.setString(3, sendPerson);
			psmt.setString(4, receivePerson);
			psmt.setString(5, content);
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
	//读取闹钟列表
	public List<AlarmBean> allAlarm(String phone) {
		DBUtil util = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<AlarmBean> list = new ArrayList<>();
		try {
			conn = util.getConnection();
			String sql = "select * from tbl_alarm";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				AlarmBean alarmBean = new AlarmBean();
				int alarmId = rs.getInt("alarmId");
				String alarmTime = rs.getString("alarmTime");
				String sendPersonId = rs.getString("sendPersonId");
				String receivePersonId = rs.getString("receivePersonId");
				String content = rs.getString("content");
				alarmBean.setAlarmId(alarmId);
				alarmBean.setAlarmTime(alarmTime);
				alarmBean.setSendPersonId(sendPersonId);
				alarmBean.setReceivePersonId(receivePersonId);
				alarmBean.setContent(content);
				list.add(alarmBean);
			}
			rs.close();
			psmt.close();
			util.closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}