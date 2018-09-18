package catchgame;

/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/
/*
Purpose:
the purpose of RequestPacket is to hold a code from the client
the purpose of the ResultPacket is to hold a code from the server
the purpose of the LoginPacket is to hold the info username
and password necessary for logging in
the purpose of the NewUserPacket is to hold the info username,
password, and confirmed password necessary for creating a new account
the purpose of the SeaCreaturePacket is to sent ArrayLists of all species 
of the SeaCreatures to the client
the purpose of the ClientSubOceanSeaCreatureStatePacket is to send
info on the current population and max population for each species
in the client's subOcean

Modification info: 
both SeaCreaturePacket and ClientSubOceanSeaCreatureStatePacket 
have been updated to work for (and require info of) all the SeaCreatures 
species
*/
import java.io.Serializable;
import java.util.ArrayList;

import resources.Fish;
import resources.Shellfish;
/**
 * This class defines various packets for client/server communication used throughout the program. 
 */
public class Packets
{
	/**
	 * Packet to hold a a request code. Tells the receiver to do something.
	 */
	public static class RequestPacket implements Serializable
	{
		public int code;

		RequestPacket(int requestCode)
		{
			this.code = requestCode;
		}
	}

	/**
	 * Packet to let the sender know the result of what happened.
	 */
	public static class ResultPacket implements Serializable
	{
		public int code;

		ResultPacket(int resultCode)
		{
			this.code = resultCode;
		}
	}

	/**
	 * Packet to send login information.
	 */
	public static class LoginPacket implements Serializable
	{
		public String enteredName;
		public String enteredPassword;

		public LoginPacket(String name, String password)
		{
			this.enteredName = name;
			this.enteredPassword = password;
		}
	}
	
	/**
	 * Packet to send new user information.
	 */
	public static class NewUserPacket implements Serializable
	{
		public String enteredName;
		public String enteredPassword;
		public String enteredPasswordConfirm;

		public NewUserPacket(String name, String password, String passwordConfirm)
		{
			this.enteredName = name;
			this.enteredPassword = password;
			this.enteredPasswordConfirm = passwordConfirm;
		}
	}

	/**
	 * @author Matt Roberts
	 * Packet to send SeaCreatures to client during game.
	 */
	public static class SeaCreaturesPacket implements Serializable
	{
		ArrayList<Fish> codPopulation = new ArrayList<>();
		ArrayList<Fish> salmonPopulation = new ArrayList<>();
		ArrayList<Fish> tunaPopulation = new ArrayList<>();
		ArrayList<Shellfish> oysterPopulation = new ArrayList<>();
		ArrayList<Shellfish> lobsterPopulation = new ArrayList<>();
		ArrayList<Shellfish> crabPopulation = new ArrayList<>();

		SeaCreaturesPacket(ArrayList<Fish> codPopulation, ArrayList<Fish> salmonPopulation,
				ArrayList<Fish> tunaPopulation, ArrayList<Shellfish> oysterPopulation,
				ArrayList<Shellfish> lobsterPopulation, ArrayList<Shellfish> crabPopulation)
		{
			this.codPopulation = codPopulation;
			this.salmonPopulation=salmonPopulation;
			this.tunaPopulation=tunaPopulation;
			this.oysterPopulation=oysterPopulation;
			this.lobsterPopulation=lobsterPopulation;
			this.crabPopulation=crabPopulation;
		}
		SeaCreaturesPacket(){
			
		}
	}

	/**
	 * @author Matt Roberts
	 * Packet to send to the server to describe the state of the resources currently available to the client.
	 */
	public static class ClientSubOceanSeaCreatureStatePacket implements Serializable
	{
		int currentPopulationCod=0;
		int maxPopulationCod=Constants.COD_MAX_POPULATION/10;
		int currentPopulationSalmon=0;
		int maxPopulationSalmon=Constants.SALMON_MAX_POPULATION/10;
		int currentPopulationTuna=0;
		int maxPopulationTuna=Constants.TUNA_MAX_POPULATION/10;
		int currentPopulationOyster=0;
		int maxPopulationOyster=Constants.OYSTER_MAX_POPULATION/10;
		int currentPopulationLobster=0;
		int maxPopulationLobster=Constants.LOBSTER_MAX_POPULATION/10;
		int currentPopulationCrab=0;
		int maxPopulationCrab=Constants.CRAB_MAX_POPULATION/10;

		public ClientSubOceanSeaCreatureStatePacket(int currentPopulationCod, int maxPopulationCod,
				int currentPopulationSalmon, int maxPopulationSalmon,
				int currentPopulationTuna, int maxPopulationTuna,
				int currentPopulationOyster, int maxPopulationOyster,
				int currentPopulationLobster, int maxPopulationLobster,
				int currentPopulationCrab, int maxPopulationCrab)
		{
			this.currentPopulationCod = currentPopulationCod;
			this.maxPopulationCod = maxPopulationCod;
			this.currentPopulationSalmon=currentPopulationSalmon;
			this.maxPopulationSalmon=maxPopulationSalmon;
			this.currentPopulationTuna=currentPopulationTuna;
			this.maxPopulationTuna=maxPopulationTuna;
			this.currentPopulationOyster=currentPopulationOyster;
			this.maxPopulationOyster=maxPopulationOyster;
			this.currentPopulationLobster=currentPopulationLobster;
			this.maxPopulationLobster=maxPopulationLobster;
			this.currentPopulationCrab=currentPopulationCrab;
			this.maxPopulationCrab=maxPopulationCrab;
			
		}
	}
	
	/**
	 * Holds values for an entry of the leaderboard.
	 */
	public static class LeaderBoardRow implements Serializable
	{
		public String name;
		public double totalMoneyEarned;
		public double cashOnHand;
		public int totalCatches;
		
		public LeaderBoardRow(String name, double totalMoneyEarned, double cashOnHand, int totalCatches)
		{
			this.name = name;
			this.totalMoneyEarned = totalMoneyEarned;
			this.cashOnHand = cashOnHand;
			this.totalCatches = totalCatches;
		}
		
		@Override
		public String toString()
		{
			return "Row: Name = " + name + ", totalEarned = " + totalMoneyEarned + ", cashOnHand = " + cashOnHand + ", totalCatches = " + totalCatches;
		}
	}
	
	/**
	 * Used to send the high scores from the server to the client
	 */
	public static class LeaderBoardPacket implements Serializable
	{
		public LeaderBoardRow[] rows;
		
		public LeaderBoardPacket(LeaderBoardRow[] rows)
		{
			this.rows = rows;
		}
	}
}
