package authentication;

import java.util.Random;

/**
 * This class can encrypt and decrypt Strings by shifting each value based upon
 * a key. Keys and plain-text can be any ascii character, and any length.
 * 
 * @author Nils Johnson
 *
 */
public class EncryptionFilter
{
	private static int KEY_LENGTH = 35;

	public static int MAX = 255;

	/**
	 * Encrypts a String
	 * @param plainText The String to encrypt.
	 * @param key The String being used to encrypt.
	 * @return An encrypted String. 
	 */
	public static String encrypt(String plainText, String key)
	{
		StringBuilder cipherText = new StringBuilder(plainText.length());
		for (int i = 0; i < plainText.length(); i++)
		{
			char temp = (char) (plainText.charAt(i) + (key.charAt(i % key.length())));
			cipherText.append(temp);
		}
		return cipherText.toString();
	}

	/**
	 * Decrypts a String
	 * @param cipherText The encrypted String.
	 * @param key The String that will decrypt the cipherText
	 * @return A decrypted String.
	 */
	public static String decrypt(String cipherText, String key)
	{
		StringBuilder plainText = new StringBuilder(cipherText.length());
		for (int i = 0; i < cipherText.length(); i++)
		{
			char temp = (char) (cipherText.charAt(i) - (key.charAt(i % key.length())));

			if ((int) (temp) < 0)
			{
				temp += MAX;
			}
			plainText.append(temp);
		}
		return plainText.toString();
	}

	/**
	 * Generates random keys for encrypting. 
	 * @return A randomly generated String to be used as a key.
	 */
	public static String getRandomKey()
	{
		Random rand = new Random();
		StringBuilder key = new StringBuilder(KEY_LENGTH);

		for (int i = 0; i < KEY_LENGTH; i++)
		{
			key.append((char) (rand.nextInt(255) + 0));
		}

		return key.toString();
	}
}