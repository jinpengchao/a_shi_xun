package alarm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbutil.DBUtil;
public class AlarmDao {
	//添加用户信息
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
}
