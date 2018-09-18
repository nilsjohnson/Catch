package authentication;

/**
 * This Exception class is thrown when a user is attempting to make an account and enters in an invalid password.
 * It contains an ArrayList with enums describing each case why the password might invalid.
 * It contains a String describing the reason the Exception was thrown.
 * 
 *  @author Nils Johnson
 */
import java.util.ArrayList;

public class BadPasswordException extends NewUserException
{
	private ArrayList<PasswordError> errorList;
	private String message = null;

	public BadPasswordException(ArrayList<PasswordError> errorList)
	{
		StringBuilder temp = new StringBuilder();
		boolean shouldBreak = false;
		this.setErrorList(errorList);
		
		for (int i = 0; i < errorList.size(); i++)
		{
			switch (errorList.get(i))
			{
			case HAS_ILLEGAL_CHAR:
				temp.append("Has Invalid Character");
				break;
			case NEEDS_LOWER:
				temp.append("Needs Lowercase ");
				break;
			case NEEDS_UPPER:
				temp.append("Needs Uppercase");
				break;
			case NEEDS_NUMBER:
				temp.append("Needs Number");
				break;
			case NEEDS_SPECIAL_CHAR:
				temp.append("Needs Special Character");
				break;
			default:
				message = "Illegal Password";
				shouldBreak = true;
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
		if(!shouldBreak)
		{
			message = temp.toString();
		}
	}

	public void setErrorList(ArrayList<PasswordError> errorList)
	{
		this.errorList = errorList;
	}
	
	public ArrayList<PasswordError> getErrorList()
	{
		return errorList;
	}
}
