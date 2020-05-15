/**
 * @Title:CollecitonDao.java
 * @Packagecommunity.dao
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月7日
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
import entity.CollectionBean;

/**
 * @ClassName: CollecitonDao
 * @Description: TODO
 * @author wzw
 * @date 2019年12月7日
 *
 */
public class CollectionDao {
	/**
	 * 
	 *  @title:insertCollection
	 * @Description: 插入一条收藏的帖子
	 * @throws下午2:07:31
	 * returntype:int
	 */
	public int insertCollection(CollectionBean collection) {
		int i = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_mycollection values(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, collection.getPersonId());
			ps.setInt(3, collection.getPostId());
			ps.setString(4, collection.getTime());
			i = ps.executeUpdate();
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
		return i;
	}
	/**
	 * 
	 *  @title:queryCollection
	 * @Description: 查找某一用户收藏的所有的帖子
	 * @throws下午2:14:29
	 * returntype:List<CollectionBean>
	 */
	public List<CollectionBean> queryCollection(String personId){
		List<CollectionBean> list = new ArrayList<CollectionBean>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_mycollection where personId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CollectionBean collection = new CollectionBean();
				collection.setId(rs.getInt("id"));
				collection.setPersonId(personId);
				collection.setPostId(rs.getInt("postId"));
				collection.setTime(rs.getString("time"));
				list.add(collection);
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
	 *  @title:queryCollection
	 * @Description: 通过personId和postId查询是够收藏过
	 * @throws下午3:53:29
	 * returntype:String
	 */
	public String queryCollection(CollectionBean collection){
		String exist = null;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_mycollection where personId = ? and postId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, collection.getPersonId());
			ps.setInt(2, collection.getPostId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				exist = "exist";
			}else {
				exist = "notExist";
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
		return exist;
		
	}
	/**
	 * 通过personId和postId删除指定的收藏信息
	 *  @title:delCollection
	 * @Description: todo
	 * @throws下午3:50:34
	 * returntype:int
	 */
	public int delCollection(String personId,int postId){
		int i = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_mycollection where personId = ? and postId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, personId);
			ps.setInt(2, postId);
			i = ps.executeUpdate();
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
		return i;
		
	}
	
	/**
	 * 
	 *  @title:delCollecion
	 * @Description: 删除指定id的收藏帖子
	 * @throws下午2:21:41
	 * returntype:int
	 */
	public int delCollecion(int id) {
		int i = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_mycollection where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			i = ps.executeUpdate();
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
		return i;
	}
	/**
	 * 根据postId删除收藏信息
	 *  @title:delCollecionByPostId
	 * @Description: todo
	 * @throws下午1:16:00
	 * returntype:int
	 */
	public int delCollecionByPostId(int postId) {
		int i = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_mycollection where postId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, postId);
			i = ps.executeUpdate();
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
		return i;
	}
	
}
