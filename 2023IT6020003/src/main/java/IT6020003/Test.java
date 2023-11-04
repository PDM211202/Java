package IT6020003;

import java.sql.*;
//import java.util.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionPoolImpl connectionPool = new ConnectionPoolImpl();

        try {
            Connection connection = connectionPool.getConnection("Test");

            System.out.println("Database name: " + connection.getCatalog());

            connectionPool.releaseConnection(connection, "Test");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi làm việc với kết nối: " + e.getMessage());
        }
	}
}
