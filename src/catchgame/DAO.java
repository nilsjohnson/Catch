package catchgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import authentication.Authenticator;

public abstract class DAO
{
	// for DB connection
	public static String url = "jdbc:sqlite:catch.db";
	public static Connection connection = null;
	
	// Max allowed length of path to user file
	private static final String FILE_PATH_LENGTH = "50";

	protected DAO() throws SQLException
	{
		String createUserTableQuery = "CREATE TABLE IF NOT EXISTS Users (\n" + "userName varchar(" +
				Authenticator.MAX_NAME_LENGTH +
				") not null, \n" +
				"passwordCipher varchar(" +
				Authenticator.MAX_PW_LENGTH +
				") not null, \n" +
				"filePath varchar(" +
				FILE_PATH_LENGTH +
				") not null, \n" +
				"isOnline bit not null," +
				"PRIMARY KEY (userName)" +
				");";
		
		String createLeaderBoardTableQuery = "CREATE TABLE IF NOT EXISTS LeaderBoard ("
				+ "userName varchar(50) not null,"
				+ "totalEarnings double not null,"
				+ "cashOnHand double not null,"
				+ "numCreaturesCaught not null,"
				+ "primary key (userName)" 
				+ ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
		{
			stmt.execute(createUserTableQuery);
			stmt.execute(createLeaderBoardTableQuery);
		}
	}
	
	/**
	 * use to close connection after accessing database
	 */
	protected void closeConnection()
	{
		try
		{
			if (connection != null)
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * use to open connection prior to accessing database
	 */
	protected void openConnection()
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = DriverManager.getConnection(url);
			}
		}
		catch (SQLException e)
		{
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

}
