package catchgame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import catchgame.Packets.LeaderBoardPacket;
import catchgame.Packets.LeaderBoardRow;
import utilities.NumberUtilities;

public class LeaderBoardDAO extends DAO
{
	public LeaderBoardDAO() throws SQLException
	{
		super();
	}

	public LeaderBoardPacket getLeaderBoard()
	{
		String name;
		double totalMoneyEarned;
		double cashOnHand;
		int numCreaturesCaught;

		LeaderBoardRow[] scores = new LeaderBoardRow[Constants.NUM_HIGH_SCORES];

		try
		{
			openConnection();

			String userNameStatement = "SELECT userName, totalEarnings, cashOnHand, numCreaturesCaught FROM LeaderBoard ORDER BY totalEarnings DESC LIMIT " + Integer.toString(Constants.NUM_HIGH_SCORES);
			PreparedStatement preparedStatement = connection.prepareStatement(userNameStatement);

			ResultSet rSet = preparedStatement.executeQuery();

			int i = 0;

			// if the query returns, the user exists
			while (rSet.next())
			{
				name = rSet.getString(1);
				totalMoneyEarned = NumberUtilities.round(Double.parseDouble(rSet.getString(2)), 2);
				cashOnHand = NumberUtilities.round(Double.parseDouble(rSet.getString(3)), 2);
				numCreaturesCaught = Integer.parseInt((rSet.getString(4)));

				scores[i] = new LeaderBoardRow(name, totalMoneyEarned, cashOnHand, numCreaturesCaught);
				
				i++;
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		
		return new LeaderBoardPacket(scores);
	}

	public void update(LeaderBoardRow row)
	{
		String name = row.name;
		double totalMoneyEarned = row.totalMoneyEarned;
		double cashOnHand = row.cashOnHand;
		int totalCatches = row.totalCatches;
		
		String statement;
		PreparedStatement prepStatement;

		try
		{
			openConnection();
			
			statement = "insert or replace into LeaderBoard (userName, totalEarnings , cashOnHand, numCreaturesCaught) " + "values (?, ?, ?, ?)";
			prepStatement = connection.prepareStatement(statement);
			
			prepStatement.setString(1, name);
			prepStatement.setString(2, Double.toString(totalMoneyEarned));
			prepStatement.setString(3, Double.toString(cashOnHand));
			prepStatement.setString(4, Integer.toString(totalCatches));
			
			prepStatement.executeUpdate();		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
	}
}

