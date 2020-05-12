/**
 * @Title:CommentDao.java
 * @Packagecommunity.dao
 * @Description: TODO
 * @auther wzw
 * @date 2019年11月27日
 * @version v1.0
 */
package com.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbutil.DBUtil;
import com.entity.CommentDetailBean;

/**
 * @ClassName: CommentDao
 * @Description: TODO
 * @author wzw
 * @date 2019年11月27日
 *
 */ 
public class CommentDao {
	//通过id修改头像logo
	public int changeImg(String logo,String personId) {
		int n=0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "update tbl_comment set headimg=? where personId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, logo);
			ps.setString(2, personId);
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
	 * 通过id修改评论人昵称
	 *  @title:changeName
	 * @throws下午4:15:05
	 * returntype:int
	 */
	public int changeName(String nickName,String personId) {
		int n=0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "update tbl_comment set nickName=? where personId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nickName);
			ps.setString(2, personId);
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
	 *  @title:insertComment
	 * @Description: 保存评论到数据库
	 * @throws下午8:48:30
	 * returntype:int
	 */
	public int insertComment(CommentDetailBean comment) {
		DBUtil util = new DBUtil();
		int n = 0;
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_comment values(?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, comment.getPostId());
			ps.setString(3, comment.getPersonId());
			ps.setString(4, comment.getNickName());
			ps.setString(5, comment.getHeadimg());
			ps.setString(6, comment.getContent());
			ps.setString(7, comment.getTime());
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
	 * @Description: 查询指定帖子全部的评论
	 * @throws下午9:04:03
	 * returntype:List<Comment>
	 */
	public List<CommentDetailBean> queryComment(int postId){
		List<CommentDetailBean> list = new ArrayList<CommentDetailBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_comment where postId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, postId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CommentDetailBean comment = new CommentDetailBean();
				comment.setId(rs.getInt("id"));
				comment.setPostId(rs.getInt("postId"));
				comment.setPersonId(rs.getString("personId"));
				comment.setContent(rs.getString("content"));
				comment.setTime(rs.getString("time"));
				comment.setNickName(rs.getString("nickName"));
				comment.setHeadimg(rs.getString("headimg"));
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
	//查询我的评论
	public List<Integer> queryComment(String personId){
		List<Integer> list = new ArrayList<Integer>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_comment where personId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int a = rs.getInt("id");
				list.add(a);
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
	 *  @title:queryCommentCount
	 * @Description: 查询一个帖子的评论总数
	 * @throws下午9:47:33
	 * returntype:int
	 */
	public int queryCommentCount(int postId){
		int num = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select count(*) from tbl_comment where postId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, postId);
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
