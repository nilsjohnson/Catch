package graphicclasses;

import catchgame.Constants;
import resources.Fish;

/**
 * Contains a reference to a fish and a FishImageView and makes
 * the FishImageView sized based on the weight of the fish
 * @author mattroberts
 *
 */
public class FishGraphic extends AbstractSeaCreatureGraphic{
	private Fish fish;
	private FishImageView fishImageView;
	
	/**
	 * Tells super to get the appropriate image and then scales
	 * it based on the fish's weight
	 * @param fish the fish to be displayed
	 */
	public FishGraphic(Fish fish){
		this.fish=fish;
		
		fishImageView=new FishImageView(fish);
		super.seaCreatureImage=AbstractSeaCreatureGraphic.getImage(fish.getSpecies());
		System.out.println("got image");
		fishImageView.setImage(super.seaCreatureImage);
		System.out.println("set image");
		short WEIGHT_GRAPHIC_MULTIPLE=10;
		switch (fish.getSpecies()){
		case COD:
			WEIGHT_GRAPHIC_MULTIPLE=Constants.SEACREATURE_WEIGHT_GRAPHIC_MULTIPLE;
			break;
		case SALMON:
			WEIGHT_GRAPHIC_MULTIPLE=Constants.SEACREATURE_WEIGHT_GRAPHIC_MULTIPLE;
			break;
		case TUNA:
			WEIGHT_GRAPHIC_MULTIPLE=Constants.SEACREATURE_WEIGHT_GRAPHIC_MULTIPLE;
			break;
		}
		fishImageView.setFitWidth(fish.getWeight()*WEIGHT_GRAPHIC_MULTIPLE);
		fishImageView.setFitHeight(this.seaCreatureImage.getHeight()*fishImageView.getFitWidth()/this.seaCreatureImage.getWidth());
		fishImageView.setSmooth(true);
		fishImageView.setCache(true);
		
	}
	
	/**
	 * public method for getting the FishImageView
	 * @return this graphic's FishImageView
	 */
	public FishImageView getFishImageView(){
		return fishImageView;
	}
	
}
