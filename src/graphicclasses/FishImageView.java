package graphicclasses;

import javafx.scene.image.ImageView;
import resources.Fish;
/**
 * An imageview with a fish reference
 * @author mattroberts
 *
 */
public class FishImageView extends ImageView{
	
	Fish fish;
	
	/**
	 * Constructs the FishImageView and assigns
	 * the fish to the fish reference
	 * @param fish the fish to be referenced
	 */
	public FishImageView(Fish fish){
		this.fish=fish;
	}
}
