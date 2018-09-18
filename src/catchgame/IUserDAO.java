package catchgame;

/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/
/*
Purpose: to create methods that the rest of the program can depend on to be 
carried out by the DAO, however it does that.  That way, the rest of the 
program will not need to be reengineered however the DAO is implemented
(and if this implmentation changes)

Modification info:
no changes known
*/

import java.io.FileNotFoundException;
import java.io.IOException;

import authentication.BadLoginException;
import authentication.NewUserException;
import authentication.User;

/**
 * This interface states which methods the DAO is to implement. This gives us
 * the advantage of being able to work on the program now and have it function as it
 * will, when the database is actually implemented.
 * 
 * @author Nils
 *
 */
public interface IUserDAO
{
	// methods to override
	void createUser(String userName, String enteredPassword, String enteredPasswordConfirm) throws NewUserException, FileNotFoundException, IOException;
	
	void deleteUser(String username);
	
	User getUser(String enteredUserName, String enteredPassword) throws BadLoginException, FileNotFoundException, IOException;

	boolean usernameIsAvailable(String enteredUserName);

	int getNumberOfUsers();
}

