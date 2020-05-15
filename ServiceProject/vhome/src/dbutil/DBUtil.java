package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库管理类，用于维护数据库的打开或关闭
 *	
 */
public class DBUtil {
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String CONN_STR = "jdbc:mysql://localhost:3306/wefamily_db??autoReconnect=true&useUnicode=true&characterEncoding=utf-8";
	private final String USER = "root";
	private final String PWD = "";
	private static DBUtil util;
	private Connection conn;
	
	/**
	 * 加载数据库配置文件,初始化配置文件中的数据
	 */
	public static DBUtil getInstance() {
		if(null == util) {
			util = new DBUtil();
		}
		return util;
	}
	/**
	 * 获取数据库连接
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if(null == conn || conn.isClosed()) {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(CONN_STR,USER,PWD);
		}
		return conn;
	}
	/**
	 * 关闭连接
	 * @throws SQLException 
	 */
	public void closeConnection() throws SQLException {
		if(null != conn || !conn.isClosed()) {
			conn.close();
		}
	}
}
