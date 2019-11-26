package community.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.Post;

public class PostDao {
	private Connection con;
	//插入单条帖子
	public long insertPost(Post post) {
		long n = 0;
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "insert into post value(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, post.getPostId());
			ps.setString(2, post.getPostTitle());
			ps.setString(3, post.getPostContent());
			ps.setString(4, post.getPostSendPersonId());
			ps.setString(5, post.getPostTime());
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
	//查询全部的帖子,保存 到list中并返回
	public List<Post> queryPosts(){
		List<Post> list = new ArrayList<Post>();
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "select * from post";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPostId(rs.getString("id"));
				post.setPostTitle(rs.getString("title"));
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
		}
		return list;
	}
}
