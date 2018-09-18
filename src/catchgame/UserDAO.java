package catchgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import authentication.Authenticator;
import authentication.BadLoginException;
import authentication.BadPasswordException;
import authentication.BadUsernameException;
import authentication.EncryptionFilter;

import authentication.NewUserException;
import authentication.NewUserException.PasswordError;
import authentication.NewUserException.UsernameError;
import authentication.BadLoginException.LoginError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Nils Johnson
 */
// TODO - Try with resources for each call, instead of try-catch-finally
public class UserDAO extends DAO implements IUserDAO
{
	// allows monitoring or online users
	private ObservableList<String> onlineUserList = FXCollections.observableArrayList();

	/**
	 * This constructor will make a table of Users if it does not exist.
	 * 
	 * @throws SQLException
	 */
	public UserDAO() throws SQLException
	{
		super();
	}

	/**
	 * This method inserts a user into the SQLite database
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Override
	public void createUser(String enteredUsername, String enteredPassword, String enteredPwConfirm) throws NewUserException, FileNotFoundException, IOException
	{
		// verify a legally formatted name is entered
		ArrayList<UsernameError> usernameErrorList = Authenticator.checkUsernameLegality(enteredUsername);

		// if there is an error list, throw an exception with that list
		if (usernameErrorList != null)
		{
			BadUsernameException e = new BadUsernameException(usernameErrorList);
			throw (e);
		}

		// check to see if name is available
		if (!usernameIsAvailable(enteredUsername))
		{
			ArrayList<UsernameError> errors = new ArrayList<>();
			errors.add(UsernameError.UNAVAILABLE);
			NewUserException e = new BadUsernameException(errors);
			throw (e);
		}

		// verify password legality
		ArrayList<PasswordError> pwErrorList = Authenticator.checkPasswordLegality(enteredPassword);

		// if pw was illegal and they match
		if (pwErrorList != null && enteredPassword.equals(enteredPwConfirm))
		{
			BadPasswordException e = new BadPasswordException(pwErrorList);
			throw (e);
		}
		// if pw was illegal and they don't match
		if (pwErrorList != null && !enteredPassword.equals(enteredPwConfirm))
		{
			pwErrorList.add(PasswordError.MIS_MATCH);
			BadPasswordException e = new BadPasswordException(pwErrorList);
			throw (e);
		}
		// if 'entredPassword' was legal but doesnt match the confirm
		if (!enteredPassword.equals(enteredPwConfirm))
		{
			pwErrorList = new ArrayList<>();
			BadPasswordException e = new BadPasswordException(pwErrorList);
			throw (e);
		}

		// if everything checks out, then put in the database
		try
		{
			openConnection();
			String statement = "insert into Users (userName, passwordCipher , filePath, isOnline) " + "values (?, ?, ?, ?)";
			PreparedStatement preparedInsertStatement = connection.prepareStatement(statement);

			preparedInsertStatement.setString(1, enteredUsername);
			preparedInsertStatement.setString(2, EncryptionFilter.encrypt(enteredPassword, enteredPassword));
			preparedInsertStatement.setString(3, enteredUsername + ".dat");
			preparedInsertStatement.setString(4, "0");

			preparedInsertStatement.executeUpdate();
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

		// make that user a file and serialize an empty player
		// File pwListFile = new File();
		Player player = new Player(enteredUsername);

		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(enteredUsername + ".dat"));)
		{
			player.prepareToSerialze();
			output.writeObject(player);
		}
	}

	@Override
	public Player getUser(String enteredUsername, String enteredPassword) throws BadLoginException, FileNotFoundException, IOException
	{
		// If the input is blatantly not valid, throw exception before making database
		// call.
		if (enteredUsername.length() < Authenticator.MIN_NAME_LENGTH || enteredPassword.length() < Authenticator.MIN_PW_LENGTH)
		{
			throw new BadLoginException(LoginError.INVALID_ATTEMPT);
		}

		// variables used within method to determine the result of the login attempt
		Player player = null;
		String returnedUsername = null;
		String returnedPasswordCipher = null;
		String returnedFilePath = null;

		try
		{
			openConnection();

			String userNameStatement = "SELECT userName, passwordCipher, filePath  FROM Users WHERE userName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(userNameStatement);
			preparedStatement.setString(1, enteredUsername);

			ResultSet rSet = preparedStatement.executeQuery();

			// if the query returns, the user exists
			if (rSet.next())
			{
				returnedUsername = rSet.getString(1);
				returnedPasswordCipher = rSet.getString(2);
				returnedFilePath = rSet.getString(3);

			}
			// otherwise the user does not exist. - TODO consider just doing a null check
			// here.
			else if (returnedUsername == null || !enteredUsername.equals(returnedUsername))
			{
				throw new BadLoginException(LoginError.USER_NOT_FOUND);
			}

			// see if the entered password matches the passwordChipher when decrypted with
			// the provided password
			if (!enteredPassword.equals(EncryptionFilter.decrypt(returnedPasswordCipher, enteredPassword)))
			{
				throw new BadLoginException(LoginError.INVALID_PASSWORD);
			}

			//   String sql = "UPDATE Registration " +
            //"SET age = 30 WHERE id in (100, 101)";
			
			
			// mark user as online and put them in the arrayList of online users
			String setUserOnline = "UPDATE Users SET isOnline = ? WHERE userName = ?";
			PreparedStatement setIsOnlineStatement = connection.prepareStatement(setUserOnline);
			setIsOnlineStatement.setString(2, "1");
			setIsOnlineStatement.setString(1, enteredUsername);
			setIsOnlineStatement.executeUpdate();
			onlineUserList.add(enteredUsername);

			// Deserialize the object found at the path
			// this can throw FileNotFoundException, IOException back to wherever getUser
			// was called from.
			try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(returnedFilePath));)
			{
				player = (Player) input.readObject();
			}
			catch (ClassNotFoundException e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
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

		return player;
	}

	@Override
	public boolean usernameIsAvailable(String enteredUserName)
	{
		boolean result = true;
		try
		{
			openConnection();

			String statement = "select userName from Users where userName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, enteredUserName);

			ResultSet rSet = preparedStatement.executeQuery();

			if (rSet.next())
			{
				String returnedName = rSet.getString(1);
				if (returnedName.equals(enteredUserName))
				{
					result = false;
				}
			}
			else
			{
				result = true;
			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return result;
	}

	@Override
	public void deleteUser(String username)
	{
		try
		{
			openConnection();

			String statement = "DELETE from Users where userName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			preparedStatement.setString(1, username);

			preparedStatement.executeUpdate();

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}

		File userFile = new File(username + ".dat");

		if (userFile.delete())
		{
			System.out.println("File Deleted");
		}
		else
		{
			System.out.println("File Delete Fail");
		}
	}

	@Override
	public int getNumberOfUsers()
	{
		int numUsers = 0;
		try
		{
			openConnection();

			String statement = "SELECT COUNT(*) from Users";
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			ResultSet rSet = preparedStatement.executeQuery();

			if (rSet.next())
			{
				numUsers = rSet.getInt(1);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return numUsers;
	}

	

	public void savePlayer(Player player) throws FileNotFoundException, IOException
	{
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream((player.getUsername() + ".dat")));)
		{
			output.writeObject(player);
		}

	}

	public void logPlayerOut(String username)
	{
		try
		{
			openConnection();

			// mark user as offline in the database
			String setUserOnline = "UPDATE Users SET isOnline = ? WHERE userName = ?";
			PreparedStatement setIsOnlineStatement = connection.prepareStatement(setUserOnline);
			setIsOnlineStatement.setString(2, "1");
			setIsOnlineStatement.setString(1, username);
			setIsOnlineStatement.executeUpdate();
			
			// remove them from list of online users
			onlineUserList.remove(username);

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
	}

	public ObservableList<String> getOnlinePlayerList()
	{
		return this.onlineUserList;
	}
}
