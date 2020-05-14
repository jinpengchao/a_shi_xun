package community.dao;
//章鹏

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import entity.PostExamineBean;

public class PostExamineDao {
	/**
	 * @author:章鹏
	 *  @title:delPost
	 * @Description: 根据是否审核查找帖子
	 * @throws上午10:50:22
	 * returntype:List<Post>
	 */
	public List<PostExamineBean> findBeansByExamine(String examine){
		List<PostExamineBean> list = new ArrayList<PostExamineBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_post_copy where examine=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, examine);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PostExamineBean post = new PostExamineBean();
				post.setId(rs.getInt("id"));
				post.setNickName(rs.getString("nickName"));
				post.setHeadimg(rs.getString("headimg"));
				post.setPostContent(rs.getString("content"));
				post.setPersonId(rs.getString("personId"));
				post.setTime(rs.getString("time"));
				post.setImgs(rs.getString("imgs"));
				post.setExamineString(rs.getString("examine"));
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
	 *  @title:insertPost
	 * @Description: 插入单条帖子
	 * @throws上午9:12:53
	 * returntype:long
	 */
	public long insertPost(PostExamineBean post) {
		long n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_post_copy(id,nickName,headimg,content,personId,time,imgs,examine) values(?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, post.getNickName());
			ps.setString(3, post.getHeadimg());
			ps.setString(4, post.getPostContent());
			ps.setString(5, post.getPersonId());
			ps.setString(6, post.getTime());
			ps.setString(7, post.getImgs());
			ps.setString(8, post.getExamineString());
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
