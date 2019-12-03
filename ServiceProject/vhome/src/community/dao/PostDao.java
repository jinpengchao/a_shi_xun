package community.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.Post;

/**
 * 
 * @ClassName: PostDao
 * @Description: TODO
 * @author wzw
 * @date 2019年11月26日
 *
 */
public class PostDao {
	private Connection con;
	/**
	 * 
	 *  @title:insertPost
	 * @Description: 插入单条帖子
	 * @throws上午9:12:53
	 * returntype:long
	 */
	public long insertPost(Post post) {
		long n = 0;
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "insert into tbl_post values(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, post.getPostContent());
			ps.setString(3, post.getPostSendPersonId());
			ps.setString(4, post.getPostTime());
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
	 *  @title:queryPosts
	 * @Description: 查询全部的帖子,保存 到list中并返回
	 * @throws上午9:12:32
	 * returntype:List<Post>
	 */
	public List<Post> queryPosts(){
		List<Post> list = new ArrayList<Post>();
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "select * from tbl_post order by publishTime desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPostId(rs.getInt("id"));
				post.setPostContent(rs.getString("content"));
				post.setPostSendPersonId(rs.getString("personId"));
				post.setPostTime(rs.getString("publishTime"));
				list.add(post);
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
	 *  @title:delPost
	 * @Description: 按照id删除帖子
	 * @throws上午10:50:22
	 * returntype:int
	 */
	public int delPost(int id) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "delete from tbl_post where id = ?";
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
