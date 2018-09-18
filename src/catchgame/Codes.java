package catchgame;
/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/
/*
Purpose: to have int values for all server/client request and response codes,
all in one class (like a c++ struct)

*/
/**
 * Constant values that represent requests or responses
 */
public class Codes
{
	// login response codes
	public final static int LOGIN_SUCCESS_CODE = 0;
	public final static int LOGIN_ERR_INVALID_PASSWORD_CODE = 1;
	public final static int LOGIN_ERR_USER_NOT_FOUND_CODE = 2;
	public final static int LOGIN_ERR_NO_USERS_FOUND_CODE = 3;
	public final static int LOGIN_ERR_INVALID_ATTEMPT_CODE = 4;
	public final static int LOGIN_ERR_UNKNOWN_ERROR_CODE = 5;
	
	// new user response codes
	public final static int NEW_USER_SUCESS_CODE = 10;
	public final static int NEW_USER_ERR_NAME_TAKEN_CODE = 11;
	public final static int NEW_USER_ERR_ILLEGAL_PW_CODE = 12; 
	public final static int NEW_USER_ERR_ILLEGAL_NAME_CODE = 13; 
	public final static int NEW_USER_ERR_UNKNOWN_CODE = 13; 

	// client request codes
	public static final int LOGOUT_CODE = 20;
	public static final int DELETE_ACCOUNT_CODE = 21;
	public static final int GET_LEADER_BOARD_CODE = 0;
}
