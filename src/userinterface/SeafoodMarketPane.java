package userinterface;


import catchgame.Constants;
import catchgame.GameControl.IsValidQuantityListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * This class is the GUI for the "market" for selling SeaCreatures.
 */
public class SeafoodMarketPane extends VBox
{
	// for titles
	private Label lblPrices = new Label("Current Prices");
	private Label lblMyResources = new Label("My Sea Creatures");
	private Label lblToSell = new Label("Resources To Sell");

	// for the name dispayed at top of the market
	public Text txtMarketName;

	// to display current prices and entry for transaction
	public GridPane priceGridPane = new GridPane();
	// to make priceGridPane centered
	private StackPane priceGridPaneHolder = new StackPane();

	// to fire event to do the transaction
	private Button btnDoTransaction = new Button("Sell My Catch!");

	// for entry of how many SeaCreatures the player wants to sell
	private TextField[] numCreaturesToSellTextFields = new TextField[Constants.SUPPORTED_SPECIES.length];
	// shows how many of a type of SeaCreature player has
	private Text[] creaturesOnHandTextArray = new Text[Constants.SUPPORTED_SPECIES.length];
	// shows the current price for each type of SeaCreature
	private Text[] currentPricesTextArray = new Text[Constants.SUPPORTED_SPECIES.length];

	public SeafoodMarketPane(String name, EventHandler<ActionEvent> sellFishAction)
	{
		// set the title
		txtMarketName = new Text(name);

		// initialize price GridGane, start with the titles
		// node col row
		priceGridPane.add(new Text("Item"), 0, 0);
		priceGridPane.add(new Text(""), 1, 0);
		priceGridPane.add(new Text("Price"), 2, 0);
		priceGridPane.add(new Text("To Sell"), 3, 0);
		priceGridPane.add(new Text("Number You Have"), 4, 0);
		
		for (int i = 0; i < Constants.SUPPORTED_SPECIES.length; i++)
		{
			Enum<?> currentSpecies = Constants.SUPPORTED_SPECIES[i];

			// get the image and add to pane
			ImageView curImage = new ImageView(Constants.getImage(currentSpecies));
			curImage.setFitHeight(Constants.IMG_THUMBNAIL_HEIGHT);
			curImage.setFitWidth(Constants.IMG_THUMBNAIL_WDITH);
			priceGridPane.add(curImage, 0, i+1);
			
			// get the name and add to pane
			String curName = currentSpecies.toString();
			priceGridPane.add(new Text(curName), 1, i+1);
		    
		    // init the price/lb text and add to pane
			Text curText = currentPricesTextArray[i] = new Text("Prices Not Set");
			priceGridPane.add(currentPricesTextArray[i], 2, i+1);
			
			// init the TextField to enter how many to sell and add to pane
			TextField curTextField = new TextField();
			curTextField.setMaxWidth(50);
			numCreaturesToSellTextFields[i] = curTextField;
			priceGridPane.add(numCreaturesToSellTextFields[i], 3, i+1);
			
			// init a new Text for the 'you have _ ' Text
			creaturesOnHandTextArray[i] = new Text("not yet set");
			priceGridPane.add(creaturesOnHandTextArray[i], 4, i+1);
		}
		
		
		this.getChildren().addAll(txtMarketName, priceGridPane, btnDoTransaction);

		// center nodes
		this.setAlignment(Pos.CENTER);
		priceGridPane.setAlignment(Pos.CENTER);
		
		//set  spacing
		priceGridPane.setHgap(10);
		priceGridPane.setVgap(5);
		this.setSpacing(5);
		
		// a little padding
		this.setPadding(new Insets(5, 5, 5, 5));

		// set the buttons action
		btnDoTransaction.setOnAction(sellFishAction);
	}

	public void setCreaturesOnHandTextAt(int i, String str)
	{
		this.creaturesOnHandTextArray[i].setText(str);
	}

	public void setSpeciesToSellFfAt(int i, String str)
	{
		this.numCreaturesToSellTextFields[i].setText(str);
	}
	
	public void addNumToSellTfListener(int i, IsValidQuantityListener isIntegerTextFieldListener)
	{
		isIntegerTextFieldListener.setTextField(numCreaturesToSellTextFields[i]);
		numCreaturesToSellTextFields[i].textProperty().addListener(isIntegerTextFieldListener);
	}
	
	public TextField[] getNumCreaturesToSellTextFields()
	{
		return this.numCreaturesToSellTextFields;
	}

	public void setCurrentPricesTextAt(int i, String str)
	{
		// I added a decimal obj so that the price would only have 2 decimal places
		//	i.e. 12.463729479 will show, 12.46
		// -caileigh
	    //DecimalFormat df = new DecimalFormat("#.##");
	    //df.setRoundingMode(RoundingMode.FLOOR);
	    //double truncDouble = new Double(df.format(currentPricesTextArray[i]));
	    
		this.currentPricesTextArray[i].setText("$" + str + "/lb");
	    //this.currentPricesTextArray[i].setText("$" + truncDouble + "/lb");
	}

	public Text[] getCurrentPricesTextArray()
	{
		return this.currentPricesTextArray;
	}
}
