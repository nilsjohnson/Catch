package authentication;

import java.io.Serializable;

/**
 * This class is meant to be used as a base class for users who are logging in
 * using the tools in the authentication class. It is serializable, but will not
 * store the users password as plain text
 * 
 * @author Nils
 *
 */
public class User implements Serializable
{
	protected String username;
	private String pwCipherText;
	// IMPORTANT - do not serialize
	private transient String pwPlainText;

	public User(String username)
	{
		this.username = username;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getPwCipherText()
	{
		return pwCipherText;
	}

}
