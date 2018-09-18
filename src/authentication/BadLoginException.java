package authentication;

/**

 * This Exception class is thrown when a user cannot login.
 * It describes why providing by an enum value corresponding to the problem,
 * or a String based on that value.
 *  
 * @author Nils
 *
 */

public class BadLoginException extends Exception
{
	private LoginError loginError;
	private String message = null;
	
	public BadLoginException(LoginError e)
	{
		switch (e)
		{
		case INVALID_PASSWORD:
			message = "Invalid Password";
			break;
		case NO_USERS:
			message = "No Accounts Exist. Make a new one!";
			break;
		case USER_NOT_FOUND:
			message = "User Not Found";
			break;
		case INVALID_ATTEMPT:
			message = "Incomplete Login Credentials";
			break;
		default:
			message = "Login Error";
		}
		loginError = e;
	}
	
	public BadLoginException(LoginError e, String message)
	{
		this.message = message;
		this.loginError = e;
	}

	public LoginError getError()
	{
		return this.loginError;
	}

	public String getLoginErrorMessage()
	{
		return this.message;
	}
	
	public enum LoginError
	{
		/**
		 * Password does not decyrpt to be the entered password
		 */
		INVALID_PASSWORD,

		/**
		 * Username match not found
		 */
		USER_NOT_FOUND,

		/**
		 * There are no users
		 */
		NO_USERS,

		/**
		 * User input for name or password is too short. Flags condition in which a database
		 * call should not be made, due to obvious login issue. Example Useage: User simply didnt enter a password.
		 */
		INVALID_ATTEMPT
	}
}