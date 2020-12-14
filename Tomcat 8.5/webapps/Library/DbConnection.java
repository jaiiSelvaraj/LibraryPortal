import java.sql.*;
import java.util.*;
public  class DbConnection
{
		public Connection getDbConnection()
		{
			Connection con=null;
		try
		{
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","root");
		}catch(Exception e){
			System.out.println("Couldn't Connect database");
		}
		return con;
		}
}