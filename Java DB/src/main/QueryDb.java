package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class QueryDb {
	public static final String SQL_STATEMENT = "SELECT * from procedures"+ "";

	public static void main(String[] args) throws SQLException {
		Connection cons = DriverManager.getConnection(DbHelper.JDBC_URL);
		Statement statement =  cons.createStatement();
		ResultSet rs = statement.executeQuery(SQL_STATEMENT);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		for(int i = 1 ; i<=columnCount; i ++)
		{
			System.out.format("%20s", rsmd.getColumnName(i) + " | ");
		}
		
		while (rs.next())
		{
			System.out.println("");
			for(int i = 1 ; i<=columnCount; i ++)
			{
				System.out.format("%20s", rs.getString(i) + " | ");
			} 
		}

		if(statement != null)
		{
			statement.close();
		}
		
		if(cons!=null)
		{
			cons.close();
		}
	}

}