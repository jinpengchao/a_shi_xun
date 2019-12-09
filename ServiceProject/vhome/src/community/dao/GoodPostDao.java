/**
 * @Title:GoodPostDao.java
 * @Packagecommunity.dao
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月6日
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
import entity.GoodPostBean;

/**
 * @ClassName: GoodPostDao
 * @Description: TODO
 * @author wzw
 * @date 2019年12月6日
 *
 */
public class GoodPostDao {

	private Connection con;
	/**
	 * 
	 *  @title:insertGoodPost
	 * @Description: 插入点赞信息
	 * @throws下午8:13:12
	 * returntype:int
	 */
	public int insertGoodPost(GoodPostBean goodPost) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "insert into tbl_goodpost values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, goodPost.getPostId());
			ps.setString(3, goodPost.getGoodPersonId());
			ps.setString(4, goodPost.getPublishPersonId());
			ps.setString(5, goodPost.getTime());
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
	 *  @title:queryGoodPosts
	 * @Description: 根据帖子id查询点赞信息
	 * @throws下午8:22:31
	 * returntype:List<GoodPostBean>
	 */
	public List<GoodPostBean> queryGoodPosts(int postId){
		List<GoodPostBean> list = new ArrayList<>();
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "select * from tbl_goodpost where postId = ? order by time desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1,postId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GoodPostBean goodPost = new GoodPostBean();
				goodPost.setId(rs.getInt("id"));
				goodPost.setPostId(rs.getInt("postId"));
				goodPost.setGoodPersonId(rs.getString("goodPersonId"));
				goodPost.setPublishPersonId(rs.getString("publishPersonId"));
				goodPost.setTime(rs.getString("time"));
				list.add(goodPost);
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
	 *  @title:queryGoodPosts
	 * @Description:通过点赞人的id查询某一个人点赞的所有的帖子
	 * @throws下午4:25:12
	 * returntype:List<GoodPostBean>
	 */
	public List<GoodPostBean> queryGoodPosts(String goodPersonId){
		List<GoodPostBean> list = new ArrayList<>();
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "select * from tbl_goodpost where goodPersonID = ? order by time desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,goodPersonId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GoodPostBean goodPost = new GoodPostBean();
				goodPost.setId(rs.getInt("id"));
				goodPost.setPostId(rs.getInt("postId"));
				goodPost.setGoodPersonId(rs.getString("goodPersonId"));
				goodPost.setPublishPersonId(rs.getString("publishPersonId"));
				goodPost.setTime(rs.getString("time"));
				list.add(goodPost);
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
	 *  @title:queryGoodPostCount
	 * @Description: 通过postId查询点赞数量
	 * @throws下午8:43:31
	 * returntype:int
	 */
	public int queryGoodPostCount(int postId){
		int num = 0;
		DBUtil util = new DBUtil();
		try {
			con = util.getConnection();
			String sql = "select COUNT(*) from tbl_goodpost where postId = ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1,postId);
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
}
