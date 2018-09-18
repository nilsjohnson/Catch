package resources;

import java.io.Serializable;

public class SimpleFishingItem extends Equipment<SimpleFishingItemType> implements Serializable
{

	public SimpleFishingItem(SimpleFishingItemType type)
	{
		super(type);
	}

}
