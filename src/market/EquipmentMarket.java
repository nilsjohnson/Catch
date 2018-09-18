package market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import catchgame.Constants;
import catchgame.GameControl.SetCurrentEquipPricesHandler;
import resources.Boat;
import resources.BoatTypes;
import resources.Equipment;
import resources.FishSpecies;
import resources.ShellfishSpecies;
import resources.SimpleFishingItem;
import resources.SimpleFishingItemType;
import resources.Usage;

import java.util.HashMap;
import java.util.Iterator;

/*
 fill in all these methods.
 Notice there is no method to buy an item. That is because Equipment resources are unlimited, and then can just be made
 in the event handler for when a player purchases something. This class just determines the values.  
 */
public class EquipmentMarket extends Market<Equipment, Enum>
{	
	public SetCurrentEquipPricesHandler updatePriceHandler;
	
	// Dictionary to hold merch and prices
	private HashMap<Enum, Double> inventory; // type : price
	// Iterator of traversing hashmap
	private Iterator<Enum> keySetIterator;
	
	public EquipmentMarket(String name, SetCurrentEquipPricesHandler updatePriceHandler)
	{
		super(name);
		// set the handler
		this.updatePriceHandler = updatePriceHandler;
		
		// populate hashmap with inventory
		inventory = new HashMap<Enum, Double>();
		// for fish species
		inventory.put(BoatTypes.COMMERCIAL_TRAWLER, Constants.COMMERCIAL_TRAWLER_PRICE);
		inventory.put(BoatTypes.TRAWLER, Constants.TRAWLER_PRICE);
		inventory.put(BoatTypes.FISHING_SKIFF, Constants.FISHING_SKIFF_PRICE);
		inventory.put(SimpleFishingItemType.BEER, Constants.BEER_PRICE);
		inventory.put(SimpleFishingItemType.FISHING_POLE, Constants.FISHING_POLE_PRICE);
		
		// initialize iterator for traversal
		this.keySetIterator = inventory.keySet().iterator();
	}

	public void forcUpdate()
	{
		updatePriceHandler.setPrices();
	}
	
	public Equipment<?> buyItem(Enum<?> desiredItem) throws Exception
	{
		/*if (this.inventory.containsKey(desiredItem)) {
			return this.inventory.get(desiredItem); // returns the value which is the price
		}
		else {
			throw new Exception("We do not have this equipment in our current inventory");
		}*/
		
		
		if(desiredItem == BoatTypes.COMMERCIAL_TRAWLER)
		{
			return new Boat(BoatTypes.COMMERCIAL_TRAWLER);
		}
		
		if(desiredItem == BoatTypes.TRAWLER)
		{
			return new Boat(BoatTypes.TRAWLER);
		}
		
		if(desiredItem == BoatTypes.FISHING_SKIFF)
		{
			return new Boat(BoatTypes.FISHING_SKIFF);
		}
		
		if(desiredItem == SimpleFishingItemType.BEER)
		{
			return new SimpleFishingItem(SimpleFishingItemType.BEER);
		}
		
		if(desiredItem == SimpleFishingItemType.FISHING_POLE)
		{
			return new SimpleFishingItem(SimpleFishingItemType.FISHING_POLE);
		}
		
		else
		{
			throw new Exception("We do not have this equipment in our current inventory");
		}
	}
	
	// price for purchasing new equipment
	public double getCurrentPrice(Enum item) throws Exception
	{
		if (this.inventory.containsKey(item)) {
			return this.inventory.get(item); // returns the value which is the price
		}
		else {
			throw new Exception("We do not have this equipment in our current inventory");
		}
	}

	@Override
	public String getMarketType()
	{
		return "Equipment Market";
	}

	@Override
	public double sellItem(Equipment item)
	{
		// At this time the equipment market has an "unlimited inventory"
		// If the inventory was limited, this method would decrement the count for the item
		return 0;
	}
	
	// amount of money you receive when you sell old equip
	public double getItemValue(Equipment item)throws Exception
	{
		if (this.inventory.containsKey(item)) {
			return this.inventory.get(item); // returns the value which is the price
		}
		else {
			throw new Exception("We do not have this equipment in our current inventory");
		}
	}
}
