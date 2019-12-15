/**
 * @Title:ReplyDao.java
 * @Packagecommunity.dao
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月10日
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
import entity.ReplyDetailBean;

/**
 * @ClassName: ReplyDao
 * @Description: TODO
 * @author wzw
 * @date 2019年12月10日
 *
 */
public class ReplyDao {

	/**
	 * 
	 *  @title:insertReply
	 * @Description: 保存回复数据
	 * @throws上午8:35:27
	 * returntype:int
	 */
	public int insertReply(ReplyDetailBean reply) {
		DBUtil util = new DBUtil();
		int n = 0;
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_reply_comment values(?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, reply.getCommentId());
			ps.setString(3, reply.getNickName());
			ps.setString(4, reply.getHeadimg());
			ps.setString(5, reply.getPersonId());
			ps.setInt(6, reply.getReplyTotal());
			ps.setString(7, reply.getContent());
			ps.setString(8, reply.getTime());
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
	 *  @title:queryReply
	 * @Description: todo
	 * @throws上午8:49:38
	 * returntype:List<ReplyDetailBean>
	 */
	public List<ReplyDetailBean> queryReply(int commentId){
		List<ReplyDetailBean> list = new ArrayList<ReplyDetailBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_reply_comment where commentId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, commentId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyDetailBean reply = new ReplyDetailBean();
				reply.setId(rs.getInt("id"));
				reply.setCommentId(commentId);
				reply.setNickName(rs.getString("nickName"));
				reply.setHeadimg(rs.getString("headimg"));
				reply.setPersonId(rs.getString("personId"));
				reply.setContent(rs.getString("content"));
				reply.setReplyTotal(rs.getInt("replyTotal"));
				reply.setTime(rs.getString("time"));
				list.add(reply);
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
	 *  @title:queryReplyCount
	 * @Description: 查询总数
	 * @throws上午8:52:40
	 * returntype:int
	 */
	public int queryReplyCount(int commentId){
		int num = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select count(*) from tbl_reply_comment where commentId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, commentId);
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
	 *  @title:delReply
	 * @Description: 删除指定id 的回复
	 * @throws上午8:58:36
	 * returntype:int
	 */
	public int delReply(int id) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_reply_comment where id=?";
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
	 * 删除指定评论id的回复
	 *  @title:delCommentReply
	 * @Description: todo
	 * @throws下午12:08:37
	 * returntype:int
	 */
	public int delCommentReply(int commentId) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_reply_comment where commentId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, commentId);
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
