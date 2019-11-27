/**
 * @Title:CommentDao.java
 * @Packagecommunity.dao
 * @Description: TODO
 * @auther wzw
 * @date 2019年11月27日
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
import entity.Comment;

/**
 * @ClassName: CommentDao
 * @Description: TODO
 * @author wzw
 * @date 2019年11月27日
 *
 */
public class CommentDao {

	/**
	 * 
	 *  @title:insertComment
	 * @Description: 保存评论到数据库
	 * @throws下午8:48:30
	 * returntype:int
	 */
	public int insertComment(Comment comment) {
		DBUtil util = new DBUtil();
		int n = 0;
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_comment values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, comment.getId());
			ps.setInt(2, comment.getPostId());
			ps.setString(3, comment.getPersonId());
			ps.setString(4, comment.getContent());
			ps.setString(5, comment.getTime());
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
	 *  @title:queryComment
	 * @Description: 查询全部的评论
	 * @throws下午9:04:03
	 * returntype:List<Comment>
	 */
	public List<Comment> queryComment(){
		List<Comment> list = new ArrayList<Comment>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_comment";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setPostId(rs.getInt("postId"));
				comment.setPersonId(rs.getString("personId"));
				comment.setContent(rs.getString("content"));
				comment.setTime(rs.getString("time"));
				list.add(comment);
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
	 *  @title:delComment
	 * @Description: 删除指定id的评论
	 * @throws下午9:08:04
	 * returntype:int
	 */
	public int delComment(int id) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_comment where id=?";
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
