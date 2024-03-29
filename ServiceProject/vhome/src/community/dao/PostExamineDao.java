package community.dao;
//章鹏

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import community.controller.PostReport;
import dbutil.DBUtil;
import entity.PostBean;
import entity.PostExamineBean;

public class PostExamineDao {
	/**
	 * 
	 *  @title:insertPost
	 * @Description: 插入单条帖子
	 * @throws上午9:12:53
	 * returntype:long
	 */
	public long insertPost1(PostBean post,String rId,String phone) {
		long n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_post_report(id,nickName,headimg,content,personId,time,imgs,rId,phone) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, post.getId());
			ps.setString(2, post.getNickName());
			ps.setString(3, post.getHeadimg());
			ps.setString(4, post.getPostContent());
			ps.setString(5, post.getPersonId());
			ps.setString(6, post.getTime());
			ps.setString(7, post.getImgs());
			ps.setString(8, rId);
			ps.setString(9, phone);
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
	 *  @title:
	 * @Description: 查询所有收到举报的帖子
	 * @throws上午9:12:53
	 * returntype:long
	 */
	public List<PostBean> findAll(){
		List<PostBean> list = new ArrayList<PostBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_post_report";
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
				post.setrId(rs.getString("rId"));
				post.setPhone(rs.getString("phone"));
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
				post.setPhone(rs.getString("phone"));
				post.setTime(rs.getString("time"));
				post.setImgs(rs.getString("imgs"));
				post.setExamine(rs.getString("examine"));
				post.setrId(rs.getString("rId"));
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
	 * @author:章鹏
	 *  @title:delPost
	 * @Description: 根据是否审核查找帖子
	 * @throws上午10:50:22
	 * returntype:List<Post>
	 */
	public List<PostExamineBean> findAllByPersonId(String personId){
		List<PostExamineBean> list = new ArrayList<PostExamineBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_post_copy where personId=? order by time desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
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
				post.setExamine(rs.getString("examine"));
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
			String sql = "insert into tbl_post_copy(id,nickName,headimg,content,personId,phone,time,imgs,examine,rId) values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, post.getNickName());
			ps.setString(3, post.getHeadimg());
			ps.setString(4, post.getPostContent());
			ps.setString(5, post.getPersonId());
			ps.setString(6, post.getPhone());
			ps.setString(7, post.getTime());
			ps.setString(8, post.getImgs());
			ps.setString(9, post.getExamine());
			ps.setString(10, post.getrId());
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
	 * 点击批准修改为审核通过
	 *  @title:changeNameById
	 * @Description: todo
	 * @throws下午3:48:09
	 * returntype:int
	 */
	public void changeExamineByPId(String id,String examineString) {
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "update tbl_post_copy set examine = ? where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, examineString);
			ps.setString(2, id);
			ps.executeUpdate();
			ps.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	/**
	 * 
	 *  @title:delPost
	 * @Description: 按照id删除收到举报帖子
	 * @throws上午10:50:22
	 * returntype:int
	 */
	public int delPostByReport(int id) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_post_report where id = ?";
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
	 * 通过id查询单个帖子
	 *  @title:queryPosts
	 * @Description: todo
	 * @throws下午7:20:56
	 * returntype:PostBean
	 */
	public PostExamineBean queryPosts(int id){
		PostExamineBean post = new PostExamineBean();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_post_copy where id = ?";
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
				post.setExamine(rs.getString("examine"));
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
	 * 去掉审核信息
	 *  @title:queryPosts
	 * @Description: todo
	 * @throws下午7:20:56
	 * returntype:PostBean
	 */
	public PostBean deleteExamine(PostExamineBean postExamineBean) {
		PostBean post=new PostBean();
		post.setId(postExamineBean.getId());
		post.setNickName(postExamineBean.getNickName());
		post.setHeadimg(postExamineBean.getHeadimg());
		post.setPostContent(postExamineBean.getPostContent());
		post.setPersonId(postExamineBean.getPersonId());
		post.setTime(postExamineBean.getTime());
		post.setImgs(postExamineBean.getImgs());
		return post;
	}
}
