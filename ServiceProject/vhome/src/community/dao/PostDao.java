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
	
	/**
	 * 通过个人id修改头像
	 *  @title:changeImgByPId
	 * @throws下午3:53:35
	 * returntype:int
	 */
	public int changeImgByPId(String logo,String personId) {
		int n=0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "update tbl_post set headimg = ? where personId=?";
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
		}
		return n;
	}
	/**
	 * 通过个人id修改昵称
	 *  @title:changeNameById
	 * @Description: todo
	 * @throws下午3:48:09
	 * returntype:int
	 */
	public int changeNameByPId(String personId,String nickName) {
		int k = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "update tbl_post set nickName = ? where personId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nickName);
			ps.setString(2, personId);
			k = ps.executeUpdate();
			ps.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
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
			Connection con = util.getConnection();
			String sql = "insert into tbl_post(id,nickName,headimg,content,personId,time,imgs) values(?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, post.getId());
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
			Connection con = util.getConnection();
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
	 *  @title:queryPosts
	 * @Description: 根据用户id重载查询方法
	 * @throws下午8:03:14
	 * returntype:List<PostBean>
	 */
	public List<PostBean> queryPosts(String personId){
		List<PostBean> list = new ArrayList<PostBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_post where personId = ? order by time desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
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
	 * 通过id查询单个帖子
	 *  @title:queryPosts
	 * @Description: todo
	 * @throws下午7:20:56
	 * returntype:PostBean
	 */
	public PostBean queryPosts(int id){
		PostBean post = new PostBean();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_post where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				post.setId(rs.getInt("id"));
				post.setNickName(rs.getString("nickName"));
				post.setHeadimg(rs.getString("headimg"));
				post.setPostContent(rs.getString("content"));
				post.setPersonId(rs.getString("personId"));
				post.setTime(rs.getString("time"));
				post.setImgs(rs.getString("imgs"));
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
		return post;
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
			Connection con = util.getConnection();
			String sql = "delete from tbl_post_copy where id = ?";
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
	public int delPost1(int id) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
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
