package graphicclasses;

import catchgame.Constants;
import javafx.scene.image.Image;

/**
 * All SeaCreatureGraphics need an Image, and all those 
 * Images need a url that can be gotten if we only know
 * the enum of the species
 * @author mattroberts
 *
 */
public class AbstractSeaCreatureGraphic {
	protected Image seaCreatureImage;
	
	/**
	 * Gets the "Small" image for a given species based on its
	 * enum
	 * @param e the species to get the "Small" image for
	 * @return the "Small" image of the species
	 */
	public static final Image getImage(final Enum<?> e)
	{
		for(int i = 0; i < Constants.SUPPORTED_SPECIES.length; i++ )
		{
			if(e == Constants.SUPPORTED_SPECIES[i])
			{
				return (new Image("img/" + Constants.SUPPORTED_SPECIES[i].toString().toLowerCase() + "Small.png"));
			}
		}
		
		return null;	
	}
	
}
