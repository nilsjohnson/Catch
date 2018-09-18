package catchgame;

import java.io.Serializable;
import java.util.ArrayList;

import catchgame.GameControl.SendStatsHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import resources.BoatTypes;
import resources.Equipment;
import resources.FishSpecies;
import resources.SeaCreature;
import resources.ShellfishSpecies;
import resources.SimpleFishingItemType;
import utilities.NumberUtilities;

/**
 * @author Nils
 */
public class Player extends authentication.User implements Serializable
{
	// general stats
	private double cashOnHand;
	private double totalEarned;
	private int totalCatches;;

	// arrays to hold resources, when serializing
	private SeaCreature[] iceChestArray = null;
	private Equipment[] toolChestArray = null;

	// resources as observable lists, for gameplay
	private transient ObservableList<Equipment> toolChest = null;
	private transient ObservableList<SeaCreature> iceChest = null;

	// flag to mark if the arrays need to be copied to the observable lists
	private boolean observableListsLoaded = false;

	// allows the player to send stats to the server
	private transient SendStatsHandler sendStatsHandler = null;

	public Player(String username)
	{
		super(username);
		this.cashOnHand = 0;
		this.totalEarned = 0;
		this.totalCatches = 0;
	}

	public void addSeaCreatureToIceChest(SeaCreature item)
	{
		if (!observableListsLoaded)
		{
			loadObservableLists();
		}

		iceChest.add(item);
		totalCatches++;
		sendStatsToServer();
	}

	public void removeSeaCreatureFromIceChest(int i)
	{
		iceChest.remove(i);
		sendStatsToServer();
	}

	public void addItemToToolChest(Equipment item)
	{
		if (!observableListsLoaded)
		{
			loadObservableLists();
		}

		toolChest.add(item);
		sendStatsToServer();
	}

	public double getCashOnHand()
	{
		return NumberUtilities.round(cashOnHand, 2);
	}

	public void addMoney(double amount)
	{
		cashOnHand += amount;
		totalEarned += amount;
		sendStatsToServer();
	}

	public void subtractMoney(double d)
	{
		cashOnHand -= d;
		sendStatsToServer();
	}

	public SeaCreature<?> getSeaCreatureAt(int index)
	{
		return this.iceChest.get(index);
	}

	public ObservableList<SeaCreature> getIceChest()
	{
		if (observableListsLoaded != true)
		{
			loadObservableLists();
		}

		return iceChest;
	}

	/**
	 * Must be called prior to serialization. Converts Observable Lists to primitive
	 * arrays.
	 */
	public void prepareToSerialze()
	{
		if (iceChest != null)
		{
			iceChestArray = new SeaCreature[iceChest.size()];

			for (int i = 0; i < iceChestArray.length; i++)
			{
				iceChestArray[i] = iceChest.get(i);
			}
		}

		if (toolChest != null)
		{
			toolChestArray = new Equipment[toolChest.size()];

			for (int i = 0; i < toolChestArray.length; i++)
			{
				toolChestArray[i] = toolChest.get(i);
			}
		}

		observableListsLoaded = false;
	}

	// TODO - make work with equipment, done, cleanup though!af
	private void loadObservableLists()
	{
		// if iceChest is null and the iceChestArray is not null, it means that this
		// object was recently deserialized and needs to have the array copied to the
		// observable list
		if (iceChest == null && iceChestArray != null)
		{
			iceChest = FXCollections.observableArrayList(iceChestArray);
		}
		// if both are null, it means no SeaCreatures have ever been caught. Set the
		// iceChest to be empty;
		else if (iceChest == null && iceChestArray == null)
		{
			iceChest = FXCollections.observableArrayList();
		}

		if (toolChest == null && toolChestArray != null)
		{
			toolChest = FXCollections.observableArrayList(toolChestArray);
		}
		// if both are null, it means no SeaCreatures have ever been caught. Set the
		// iceChest to be empty;
		else if (toolChest == null && toolChestArray == null)
		{
			toolChest = FXCollections.observableArrayList();
		}

		// set the object to not do this again, until it gets serialized
		observableListsLoaded = true;
	}

	/**
	 * @param species that you wish to know how many the player has.
	 * @return the number of that species the player has.
	 */
	public int getNumOf(Enum<?> species)
	{
		if (!observableListsLoaded)
		{
			loadObservableLists();
		}

		int numSpecies = 0;

		if (species instanceof FishSpecies || species instanceof ShellfishSpecies)
		{
			for (SeaCreature<?> creature : iceChest)
			{
				if (creature.getSpecies() == species)
				{

					numSpecies++;
				}
			}
		}
		else
		{
			for (Equipment<?> equip : toolChest)
			{
				if (equip.getType() == species)
				{

					numSpecies++;
				}
			}
		}
		return numSpecies;
	}

	public SeaCreature<?> getSeaNextSeaCreature(Enum<?> species)
	{
		boolean creatureFound = false;
		int i = 0;
		SeaCreature<?> creature = null;

		while (!creatureFound && i < iceChest.size())
		{
			if (iceChest.get(i).getSpecies() == species)
			{
				creature = iceChest.get(i);
				creatureFound = true;
				iceChest.remove(i);
			}
			i++;
		}

		return creature;
	}

	public ObservableList<?> getToolChest()
	{
		// move this or something, do this intuitively
		loadObservableLists();
		return this.toolChest;
	}

	public double getTotalEarned()
	{
		return NumberUtilities.round(totalEarned, 2);
	}

	public int getTotalCatches()
	{

		return totalCatches;
	}

	public void setStatSendHandler(SendStatsHandler sendStatsHandler)
	{
		this.sendStatsHandler = sendStatsHandler;
	}

	private void sendStatsToServer()
	{
		if (sendStatsHandler != null)
		{
			sendStatsHandler.send();
		}
	}
}
