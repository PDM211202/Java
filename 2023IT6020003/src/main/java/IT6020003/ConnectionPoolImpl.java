package IT6020003;

import java.sql.*;
import java.util.*;

public class ConnectionPoolImpl implements ConnectionPool {
	// Trình điều khiển làm việc với MySQL
	private String driver;

	// đường dẫn thực thi
	private String url;

	// tài khoản làm việc
	private String username;
	private String password;

	// đối tượng lưu trữ kết nối
	private Stack<Connection> pool;

	public ConnectionPoolImpl() {
		// Xác định trình điều khiển
		// this.driver = "com.mysql.cj.Driver";
		this.driver = "com.mysql.cj.jdbc.Driver";

		// nạp trình điều khiển
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.url = "jdbc:mysql://localhost:3306/it6020003_data";
		this.username = "it6020003_pdmanh";
		this.password = "123456";

		// xác định bộ nhớ
		this.pool = new Stack<>();
	}

	@Override
	public Connection getConnection(String objectName) throws SQLException {
		// TODO Auto-generated method stub

		if (this.pool.isEmpty()) {
			System.out.println(objectName + " have created a new connection");
			return DriverManager.getConnection(this.url, this.username, this.password);
		} else {
			System.out.println(objectName + " have popped the connection");
			return this.pool.pop();
		}
	}

	@Override
	public void releaseConnection(Connection con, String objectName) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(objectName + "have pushed the connection");
		this.pool.push(con);

	}

	protected void finalize() throws Throwable {
		// loại bỏ các kết nối trong pool
		this.pool.clear();
		this.pool = null;
		System.out.println("ConnectionPool is closed");
	}
}
