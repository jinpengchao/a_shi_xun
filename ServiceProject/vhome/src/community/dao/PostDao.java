package community.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.PostBean;

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
	public long insertPost(PostBean post) {
		long n = 0;
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "insert into tbl_post(id,nickName,headimg,content,personId,time,imgs) values(?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, post.getNickName());
			ps.setString(3, post.getHeadimg());
			ps.setString(4, post.getPostContent());
			ps.setString(5, post.getPersonId());
			ps.setString(6, post.getTime());
			ps.setString(7, post.getImgs());
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
	public List<PostBean> queryPosts(){
		List<PostBean> list = new ArrayList<PostBean>();
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "select * from tbl_post order by time desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PostBean post = new PostBean();
				post.setId(rs.getInt("id"));
				post.setNickName(rs.getString("nickName"));
				post.setHeadimg(rs.getString("headimg"));
				post.setPostContent(rs.getString("content"));
				post.setPersonId(rs.getString("personId"));
				post.setTime(rs.getString("time"));
				post.setImgs(rs.getString("imgs"));
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
