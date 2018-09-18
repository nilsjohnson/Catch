package resources;

import java.io.Serializable;

import graphicclasses.FishGraphic;

public class Fish extends SeaCreature<FishSpecies> implements Serializable
{
	
	transient FishGraphic fishGraphic=null;

	public Fish(){
		super();
	}
	
	public Fish(FishSpecies species, double weight)
	{
		super(species, weight);
		super.setSpeed(10);
	}
	
	public Fish(FishSpecies species, double weight, double speedFactor)
	{
		super(species, weight, speedFactor);
	}
	
	public void setFishBodyByWeight(){
		fishGraphic=new FishGraphic(this);
	}
	
	public FishGraphic getFishGraphic(){
		return fishGraphic;
	}
}
