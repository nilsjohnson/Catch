package catchgame;
/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/

/*
Purpose: to load a FrequencyHistogramPane.  Since the control for the pane
(besides what's handled by JavaFX behind the scenes) is so simple, right now
that occurs in the FrequencyHistogramPane itself

Modification info:
new
*/
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.FrequencyHistogramPane;
/**
 * 
 * @author Matt Roberts
 * @author Thanh Lam
 *
 */
public class FrequencyHistogram {

	Ocean ocean;
	
	FrequencyHistogramPane fhPane;
	
	public FrequencyHistogram(Ocean ocean){
		this.ocean=ocean;
		loadFHPane();
	}
	
	public void loadFHPane()
	{
		Stage fh_Stage=new Stage();
		fhPane = new FrequencyHistogramPane(ocean);
		Scene loginScene = new Scene(fhPane, Constants.FREQUENCY_HISTOGRAM_PANE_WIDTH, Constants.FREQUENCY_HISTOGRAM_PANE_HEIGHT);
		fh_Stage.setScene(loginScene);
		fh_Stage.setTitle("Frequency Histogram of Ocean");
		fh_Stage.centerOnScreen();
		fh_Stage.show();
		fh_Stage.requestFocus();
	}
}
