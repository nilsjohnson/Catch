package resources;

import java.util.ArrayList;

public class ShellfishBushel
{
	private ShellfishSpecies speciesInBushel;
	private double totalBushelWeight = 0;
	private ArrayList <Shellfish> items = new ArrayList<>();
	
	public ShellfishBushel(ShellfishSpecies species)
	{
		setBushelContent(species);
	}

	public void add(Shellfish item)
	{
		if(item.getSpecies() == speciesInBushel)
		{
			items.add(item);
			updateTotalBushelWeight();
		}
		else
		{
			System.out.println("This bushel is for species type " + speciesInBushel.toString() );
		}	
	}

	
	private void updateTotalBushelWeight()
	{
		totalBushelWeight += items.get(items.size()-1).getWeight();
	}

	public double getWeight()
	{
		return this.totalBushelWeight;
	}

	public ShellfishSpecies getBushelContent()
	{
		return speciesInBushel;
	}

	public void setBushelContent(ShellfishSpecies species)
	{
		this.speciesInBushel = species;
	}
}
