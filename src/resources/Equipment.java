package resources;

import java.io.Serializable;

public abstract class Equipment<T> implements Serializable
{
	private T type;
	private Usage usage;
	
	protected Equipment(T type)
	{
		this.type = type;
		
		if (type instanceof SimpleFishingItemType)
		{
			usage = Usage.SIMPLE_FISHING;
		}
		else if (type instanceof BoatTypes)
		{
			usage = Usage.BOAT_FISHING;
		}
		// in this case, T and useage will be the same. At this point, we only trap lobsters, 
		// so there is no enum list of trap types. 
		else if (type == Usage.LOBSTER_TRAPPING)
		{
			usage = Usage.LOBSTER_TRAPPING;
		}
		else
		{
			System.out.println("This equipment doesnt have a use :(\n add one in Equipment.java");
		}

	}
	public T getType()
	{
		return this.type;
	}
	
	public Usage getUsage()
	{
		return this.usage;
	}
	
}



/*package resources;

public abstract class Equipment
{
	private Usage use;
	
	protected Equipment(EquipType type)
	{
		this.type = type;

		switch (type)
		{
		case SMALL_NET:
			use = Usage.FISHING;
			break;
		case LARGE_NET:
			use = Usage.FISHING;
			break;
		case FISHING_SKIFF:
			use = Usage.BOAT;
			break;
		case TRAWLER:
			use = Usage.BOAT;
			break;
		case COMMERCIAL_TRAWLER:
			use = Usage.BOAT;
			break;
		case LOBSTER_POT:
			use = Usage.TRAP;
			break;
		default:
			// TOOD
			System.out.println("Thats not an a piece of equipment the 'Equipment' constructor has a case for! :(");
		}

	}

	public Useage getUse()
	{
		return this.use;
	}

}

*/