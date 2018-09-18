package market;

import java.util.ArrayList;
import java.util.Date;

import resources.FishSpecies;
import resources.SeaCreature;
import resources.ShellfishSpecies;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import catchgame.Constants;
import catchgame.GameControl.SeafoodPriceSetEventHandler;

/**
 * Seafood market sets up the market with inventory and prices
 * It also regulates the prices.
 * 
 *  Make prices change at a set interval of time. 
 *  (Example: Every 10 minutes, make the price for any 
 *  item go up or down percentage, depending on how stable the market is.)
 *  Use the Constants class to get the market fluctuation value, and how long before prices expire.
 * 
 * @author caileighfitzgerald
 *
 */
public class SeafoodMarket extends Market<SeaCreature, Enum>
{
	// handler to tell the rest of the program there are new prices
	private SeafoodPriceSetEventHandler priceSetHandler;
	
	// Dictionary to hold merch and prices
	private HashMap<Enum, Double> inventory; // type : price
	// Iterator of traversing hashmap
	private Iterator<Enum> keySetIterator;
	
	// for random number generator which creates market flux
	private Random rand;
	private Timer timer;

	public SeafoodMarket(String name, SeafoodPriceSetEventHandler updatePricePerPoundHandler)
	{
		super(name);
		// set the handler
		priceSetHandler = updatePricePerPoundHandler;
	
		// populate hashmap with inventory
		inventory = new HashMap<Enum, Double>();
		// for fish species
		inventory.put(FishSpecies.COD, Constants.COD_INITIAL_PRICE_PER_POUND);
		inventory.put(FishSpecies.SALMON, Constants.SALMON_INITIAL_PRICE_PER_POUND);
		inventory.put(FishSpecies.TUNA, Constants.TUNA_INITIAL_PRICE_PER_POUND);
		// for shellfish
		inventory.put(ShellfishSpecies.OYSTER, Constants.OYSTER_INITIAL_PRICE_PER_POUND);
		inventory.put(ShellfishSpecies.LOBSTER, Constants.LOBSTER_INITIAL_PRICE_PER_POUND);
		inventory.put(ShellfishSpecies.CRAB, Constants.CRAB_INITIAL_PRICE_PER_POUND);

		// initialize iterator for traversal
		this.keySetIterator = inventory.keySet().iterator();
		
		// initialize rand
		this.rand = new Random();
		
		// call marketflux() to start a timer thread
		this.marketFlux();
	}
	
	public void forcePriceUpdate() throws Exception
	{
		// do this after a price change to tell the program there are new prices
		priceSetHandler.setPrices();
	}

	public double getRandPriceCoefficient() 
	{
		double tempVal;	// this will be multiplied by the price and added
		while ((tempVal = rand.nextDouble()) != 0.0) 
		{
			return tempVal;
		}
		return tempVal; // Eclipse forces a return outside loop
	}
	
	public double adjustPrice(double currentPrice)
	{
		if (rand.nextBoolean()) {	// if true, price flux is positive
			return (currentPrice += (currentPrice * getRandPriceCoefficient()));
		}
		else {						// if false, price flux is negative UNLESS it would make the price 0
									// then it will add
			if (currentPrice - (currentPrice * getRandPriceCoefficient()) <= 0.0) {
				return (currentPrice += (currentPrice * getRandPriceCoefficient()));
			}
			else {
				return (currentPrice -= (currentPrice * getRandPriceCoefficient()));
			}
		}
	}
	
	public void updatePrices() throws Exception // this is called in the run() method. Iterates through map
	{
		// update prices for inventory using rand
		for (Enum key : this.inventory.keySet())
		{	// replaces price with (old price * rand co eff)
			this.inventory.replace(key, adjustPrice(this.inventory.get(key)));
		}
		// now let the GUI know the prices are updated
		forcePriceUpdate();
	}
	
	public void marketFlux()  // this one will contain the thread for checking the time and updated price
	{
		timer = new Timer();
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() {
				try {
					updatePrices();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		timer.schedule(task, 0, 30_000); // update every 30 seconds
	}
	
	public void shutdownSeaFoodMarket(){
		if (timer!=null){
			timer.cancel();
		}
	}

	public double getCurrentPricePerPound(Enum species) throws Exception 	// FOR NOW -caileigh
	{
		if (this.inventory.containsKey(species)) {
			return this.inventory.get(species); // returns the value which is the current price per pound
		}
		else {
			throw new Exception("We do not have this species in our current inventory");
		}
	}

	@Override
	public String getMarketType()
	{
		return "Seafood Market";
	}

	@Override
	public double sellItem(SeaCreature item)
	{
		if(this.inventory.containsKey(item.getSpecies()))  	// Hash maps rule -caileigh
			return (this.inventory.get(item.getSpecies()));
		else 
			return 0.0; // possibly worth making market exception class -caileigh
	}

}
