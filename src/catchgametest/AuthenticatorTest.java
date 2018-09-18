package catchgametest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import authentication.EncryptionFilter;

public class AuthenticatorTest
{

	@Test
	public void testEntryptionFilterShouldEncryptsAndDecyrpts()
	{
		String plainText = "Moose are the largest of all the deer species. Males are immediately recognizable by their huge antlers, which can spread 6 feet from end to end.";
		String key = EncryptionFilter.getRandomKey();
		
		String cipherText = EncryptionFilter.encrypt(plainText, key);
		String deCipheredText = EncryptionFilter.decrypt(cipherText, key);
				
		System.out.println(plainText);
		System.out.println(deCipheredText);
				
		assertTrue("A String should be able to encrypt and dycrpt", plainText.equals(deCipheredText));
	}
}
