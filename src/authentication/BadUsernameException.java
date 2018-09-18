package authentication;

import java.util.ArrayList;

/**
 * This Exception is thrown when a user attempts to make a new account with an
 * invalid user name. It contains an ArrayList with enums describing each case
 * why the user name might invalid. It contains a String describing the reason
 * the Exception was thrown.
 * 
 * @author Nils
 *
 */
public class BadUsernameException extends NewUserException
{
	// TODO use built in string from parent Exception class.
	private String message = null;
	private ArrayList<UsernameError> errorList = new ArrayList<>();

	public BadUsernameException(ArrayList<UsernameError> errorList)
	{
		this.setErrorList(errorList);
		StringBuilder temp = new StringBuilder();
		boolean shouldBreak = false;

		for (int i = 0; i < errorList.size(); i++)
		{
			switch (errorList.get(i))
			{
			case HAS_ILLEGAL_CHAR:
				temp.append("Has Invalid Character");
				break;
			case TOO_LONG:
				temp.append("Needs Lowercase ");
				break;
			case TOO_SHORT:
				temp.append("Needs Uppercase");
				break;
			case UNAVAILABLE:
				temp.append("Needs Number");
				break;
			default:
				message = "Illegal Username";
				shouldBreak = true;
				break;
			}

			if (shouldBreak)
			{
				break;
			}

			if (i >= 1 && i < errorList.size())
			{
				temp.append(", ");
			}
		}
		if (!shouldBreak)
		{
			message = temp.toString();
		}
	}

	public ArrayList<UsernameError> getErrorList()
	{
		return errorList;
	}

	public void setErrorList(ArrayList<UsernameError> errorList)
	{
		this.errorList = errorList;
	}
}