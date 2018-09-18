package resources;

import java.io.Serializable;

public class Boat extends Equipment<BoatTypes> implements Serializable
{
	public Boat(BoatTypes boatType)
	{
		super(boatType);
	}
	
}
