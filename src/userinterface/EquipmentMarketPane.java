package userinterface;

import catchgame.Constants;
import catchgame.GameControl.BuyEquipmentHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EquipmentMarketPane extends VBox
{
	// for the name of the market
	public Text txtMarketName;

	// to display current prices
	private GridPane saleGridPane = new GridPane();
	
	// shows the current price for each type of SeaCreature
	private Text[] currentPricesTextArray = new Text[Constants.SUPPORTED_EQUIPMENT.length];
	// holds the buttons to buy the items
	private Button[] purchaseBtnArray = new Button[Constants.SUPPORTED_EQUIPMENT.length];
	// shows how many of a type of SeaCreature player has
	private Text[] numOnHandTextArray = new Text[Constants.SUPPORTED_EQUIPMENT.length];

	public EquipmentMarketPane(String name)// , EventHandler<ActionEvent> buyEquipmentAction)
	{
		// set the title
		txtMarketName = new Text(name);

		// initialize price GridGane for each row, start with titles
		saleGridPane.add(new Text("Item"), 0, 0);
		saleGridPane.add(new Text(""), 1, 0);
		saleGridPane.add(new Text("Price"), 2, 0);
		saleGridPane.add(new Text(""), 3, 0);
		saleGridPane.add(new Text("Number You Have"), 4, 0);
		
		for (int i = 0; i < Constants.SUPPORTED_EQUIPMENT.length; i++)
		{
			Enum<?> currentItem = Constants.SUPPORTED_EQUIPMENT[i];

			// get the thumbnail img
			ImageView curImage = new ImageView(Constants.getImage(currentItem));
			curImage.setFitHeight(50);
			curImage.setFitWidth(50);

			// get the items name
			Text curNameText = new Text(currentItem.toString());

			// initialize the item's price text
			currentPricesTextArray[i] = new Text("price not set yet");

			// initialize the items button
			purchaseBtnArray[i] = new Button("Buy Item");

			// initialize the num on hand text
			numOnHandTextArray[i] = new Text("Number Not Set");

			saleGridPane.add(curImage, 0, i+1);
			saleGridPane.add(curNameText, 1, i+1);
			saleGridPane.add(currentPricesTextArray[i], 2, i+1);
			saleGridPane.add(purchaseBtnArray[i], 3, i+1);
			saleGridPane.add(numOnHandTextArray[i], 4, i+1);
		}

		this.getChildren().addAll(txtMarketName, saleGridPane);
		
		// center stuff
		this.setAlignment(Pos.CENTER);
		saleGridPane.setAlignment(Pos.CENTER);
		
		// spacing
		saleGridPane.setHgap(10);
		saleGridPane.setVgap(5);
		this.setSpacing(5);
		
		// a little padding
		this.setPadding(new Insets(5, 5, 5, 5));

	}

	public void setEquipOnHandTextAt(int i, String str)
	{
		this.numOnHandTextArray[i].setText(str);
	}

	public void setBtnSellActionAt(int i, BuyEquipmentHandler buyEquipAction)
	{
		this.purchaseBtnArray[i].setOnAction(buyEquipAction);
	}

	public void setCurrentPricesTextAt(int i, String str)
	{
		this.currentPricesTextArray[i].setText("$" + str);
	}

	public Text[] getCurrentPricesTextArray()
	{
		return this.currentPricesTextArray;
	}
}
