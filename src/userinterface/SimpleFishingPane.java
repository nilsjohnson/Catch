package userinterface;



import catchgame.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A SimpleFishingPane of the width and height given in constants,
 * and with an ocean image for a proportion of the screen, with
 * the rest of the screen beneath it given a gray rectangle
 * to look like a rocky sea floor
 * @author mattroberts
 *
 */
public class SimpleFishingPane extends Pane
{
	
	

	/**
	 * Constructs a SimpleFishingPane of the width and height given 
	 * in constants, and with an ocean image for a proportion of the 
	 * screen, with the rest of the screen beneath it given a gray 
	 * rectangle to look like a rocky sea floor
	 */
	public SimpleFishingPane()
	{
		this.setMinWidth(Constants.INITIAL_SIMPLE_FISHING_PANE_WIDTH);
		this.setMinHeight(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT);
		
		Rectangle clippingRectangle=new Rectangle(Constants.INITIAL_SIMPLE_FISHING_PANE_WIDTH,
				Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT);
		ImageView oceanImageView=new ImageView();
		Image oceanImage=new Image("img/ocean.png");
		oceanImageView.setImage(oceanImage);
		oceanImageView.setFitWidth(Constants.INITIAL_SIMPLE_FISHING_PANE_WIDTH);
		oceanImageView.setFitHeight(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT/*Constants.TOP_COEFFICIENT*/);
		Rectangle rocksRectangle=new Rectangle(Constants.INITIAL_SIMPLE_FISHING_PANE_WIDTH,
				Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.BOTTOM_COEFFICIENT);
		rocksRectangle.setTranslateY(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.TOP_COEFFICIENT);
		rocksRectangle.setFill(Color.LIGHTGREY);
		this.getChildren().addAll(oceanImageView/*, rocksRectangle*/);
		this.setClip(clippingRectangle);
	}
}