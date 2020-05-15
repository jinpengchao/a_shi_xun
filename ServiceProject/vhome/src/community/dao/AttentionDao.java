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
import entity.ParentUserInfo;

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
	public int selectIfAttention(String myId,String oppositeId) {
		DBUtil util = new DBUtil();
		int n = 0;
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_myattentions where attentionPersonId='"+oppositeId+"' and personId='"+myId+"'";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt = con.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				n=1;
			}else {
				n=0;
			}
			
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
	 * 
	 *  @title:queryFuns
	 * @Description: 查询所有的粉丝
	 * @throws上午10:41:00
	 * returntype:List<AttentionBean>
	 */
	public List<AttentionBean> queryFuns(String personId){
		List<AttentionBean> list = new ArrayList<AttentionBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_myattentions where attentionPersonId = ?";
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
	 * 
	 *  @title:queryFunsCount
	 * @Description: 查询粉丝数量
	 * @throws上午8:24:11
	 * returntype:int
	 */
	public int queryFunsCount(String attentionPersonId){
		int num = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select count(*) from tbl_myattentions where attentionPersonId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, attentionPersonId);
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
	
	/**
	 * 
	 *  @title:delAttention
	 * @Description: 通过个人id和关注人id取消关注
	 * @throws上午8:22:31
	 * returntype:int
	 */
	public int delAttention(String personId,String attentionPersonId) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_myattentions where personId=? and attentionPersonId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
			ps.setString(2, attentionPersonId);
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
