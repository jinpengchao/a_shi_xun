/**
 * @Title:AttentionDao.java
 * @Packagecommunity.dao
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月11日
 * @version v1.0
 */
package community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.AttentionBean;

/**
 * @ClassName: AttentionDao
 * @Description: TODO
 * @author wzw
 * @date 2019年12月11日
 *
 */
public class AttentionDao {

	/**
	 * 保存关注数据
	 *  @title:insertAttention
	 * @Description: todo
	 * @throws上午9:59:10
	 * returntype:int
	 */
	public int insertAttention(AttentionBean attention) {
		DBUtil util = new DBUtil();
		int n = 0;
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_myattentions values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, attention.getAttentionPersonId());
			ps.setString(3, attention.getPersonId());
			n = ps.executeUpdate();
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
		return n;
	}
	
	/**
	 * 查询个人关注的所有人
	 *  @title:queryAttention
	 * @Description: todo
	 * @throws上午10:06:38
	 * returntype:List<AttentionBean>
	 */
	public List<AttentionBean> queryAttention(String personId){
		List<AttentionBean> list = new ArrayList<AttentionBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_myattentions where personId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				AttentionBean attention = new AttentionBean();
				attention.setId(rs.getInt("id"));
				attention.setAttentionPersonId(rs.getString("attentionPersonId"));
				attention.setPersonId(rs.getString("personId"));
				list.add(attention);
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
	/**
	 * 查询个人的关注人的数目
	 *  @title:queryAttentionCount
	 * @Description: todo
	 * @throws上午10:10:26
	 * returntype:int
	 */
	public int queryAttentionCount(String personId){
		int num = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select count(*) from tbl_myattentions where personId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
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
		return num;
		
	}
	/**
	 * 取消关注
	 *  @title:delAttention
	 * @Description: todo
	 * @throws上午10:11:37
	 * returntype:int
	 */
	public int delAttention(int id) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_myattentions where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			n = ps.executeUpdate();
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
		return n;
	}
}
