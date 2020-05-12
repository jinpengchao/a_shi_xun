/**
 * @Title:HealthHouseDao.java
 * @Packagecommunity.dao
 * @Description: TODO
 * @auther wzw
 * @date 2019年11月26日
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
import com.entity.HealthHouse;

/**
 * @ClassName: HealthHouseDao
 * @Description: TODO
 * @author wzw
 * @date 2019年11月26日
 *
 */
public class HealthHouseDao {
	
	/**
	 * 
	 *  @title:insertHouse
	 * @Description: 插入一条养生居数据
	 * @throws上午9:51:59
	 * returntype:long
	 */
	public int insertHouse(HealthHouse house) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "insert into tbl_healthhouse values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, house.getId());
			ps.setString(2, house.getPublishTime());
			ps.setString(3, house.getTitle());
			ps.setString(4, house.getResource());
			ps.setString(5, house.getViewImg());
			ps.setString(6, house.getAddress());
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
	 *  @title:queryHouse
	 * @Description: 查询全部的养生居数据
	 * @throws上午10:40:35
	 * returntype:List<HealthHouse>
	 */
	public List<HealthHouse> queryHouse(){
		List<HealthHouse> list = new ArrayList<HealthHouse>();
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "select * from tbl_healthhouse";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HealthHouse house = new HealthHouse();
				house.setId(rs.getInt("id"));
				house.setPublishTime(rs.getString("publishTime"));
				house.setTitle(rs.getString("title"));
				house.setResource(rs.getString("resource"));
				house.setViewImg(rs.getString("viewImg"));
				house.setAddress(rs.getString("address"));
				list.add(house);
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
	 *  @title:delHouse
	 * @Description: 按照id删除
	 * @throws上午10:53:57
	 * returntype:int
	 */
	public int delHouse(int id) {
		int n = 0;
		DBUtil util = new DBUtil();
		try {
			Connection con = util.getConnection();
			String sql = "delete from tbl_healthhouse where id = ?";
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
