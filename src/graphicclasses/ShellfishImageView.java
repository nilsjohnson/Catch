package graphicclasses;

import javafx.scene.image.ImageView;
import resources.Shellfish;

/**
 * An imageview with a shellfish reference
 * @author mattroberts
 *
 */
public class ShellfishImageView extends ImageView{
	
	Shellfish shellfish;
	
	/**
	 * Constructs the ShellfishImageView and assigns
	 * the shellfish to the shellfish reference
	 * @param shellfish the shellfish to be referenced
	 */
	public ShellfishImageView(Shellfish shellfish){
		this.shellfish=shellfish;
	}
}
