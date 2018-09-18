package authentication;

/**
 * This class is base class for different types of exceptions that can be thrown
 * when making a new account.
 * 
 * @author Nils Johnson
 */

public abstract class NewUserException extends Exception
{
	// TODO - set this up use the built in method for messages.
	protected String message;

	public String getErrorMessage()
	{
		return this.message;
	}

	/**
	 * These are the possible errors that can be describe an incorrectly formatted
	 * password.
	 * 
	 * @author Nils Johnson
	 */
	public enum PasswordError
	{
		TOO_SHORT,
		NEEDS_UPPER,
		NEEDS_LOWER,
		NEEDS_NUMBER,
		NEEDS_SPECIAL_CHAR,
		HAS_ILLEGAL_CHAR,
		TOO_LONG,
		MIS_MATCH;
	}
	
	/**
	 * These are used to represent the possible errors that can occur when creating
	 * a user name.
	 * 
	 * @author Nils
	 *
	 */
	public enum UsernameError
	{
		TOO_SHORT,
		TOO_LONG,
		UNAVAILABLE,
		HAS_ILLEGAL_CHAR;
	}

}


