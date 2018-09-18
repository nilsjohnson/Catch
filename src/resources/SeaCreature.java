package resources;

import java.io.Serializable;

import catchgame.Constants;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.scene.shape.Circle;
import utilities.NumberUtilities;

/**
 */
public abstract class SeaCreature <T> implements Serializable
{
	protected static int totalPopulation = 0;
	
	private double weight;
	private T species;
	private double speed;
	private double speedFactor;
	private short direction=Constants.RIGHT;

	SeaCreature(){
		
	}
	
	protected SeaCreature(T species, double weight)
	{
		setSpecies(species);
		setWeight(weight);
		totalPopulation++;
	}
	
	protected SeaCreature(T species, double weight, double speedFactor)
	{
		setSpecies(species);
		setWeight(weight);
		setSpeedFactor(speedFactor);
		calculateSpeed();
		totalPopulation++;
	}
	
	private void setSpecies(T species)
	{
		this.species = species;	
	}

	public void setWeight(double weight)
	{
		if (weight > 0)
		{
			weight = NumberUtilities.round(weight, 2);
			this.weight = weight;
		}
		else
		{
			weight = 1;
		}
	}

	public double getWeight()
	{
		return weight;
	}

	public T getSpecies()
	{
		return this.species;
	}
	
	@Override
	public String toString()
	{
		return this.getSpecies().toString() + ", " + this.getWeight() + " pounds";
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void setSpeed(double speed){
		if (speed > 0)
		{
			this.speed = speed;
		}
		else
		{
			speed = 1;
		}
	}
	
	public void calculateSpeed(){
		speed=speedFactor*weight*Constants.SPEED_ADJUSTMENT_CONSTANT;
	}
	
	public void setSpeedFactor(double speedFactor)
	{
		if (speedFactor > 0)
		{
			this.speedFactor = speedFactor;
		}
		else
		{
			speedFactor = 1;
		}
	}
	
	public double getDirecTion(){
		return direction;
	}
	
	public void setDirecTion(boolean right){
		if (right)
		{
			this.direction = Constants.RIGHT;
		}
		else
		{
			this.direction = Constants.LEFT;
		}
	}
}
