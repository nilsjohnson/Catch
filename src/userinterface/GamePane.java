package userinterface;

/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/

/*
Purpose: to let user navigate between MyStatsPane, MarketsPane,
and SimpleFishingPane
SimpleFishingPane's purpose is to launch a FishingActivity when
it is loaded if one has not already been launched
MyStatsPane just gets stats from player object
MarketsPane is in progress

Modification info:
SimpleFishingActivity launches a FishingActivity,
and only if one has not already been launched
MarketsPane is in progress
*/


import catchgame.Constants;
import catchgame.GameControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import catchgame.GameControl.FetchStatsHandler;
import catchgame.GameControl.FishingActivityActions;

/**
 * Primary container for all gameplay GUI components.
 */
public class GamePane extends VBox
{
	// pane to hold the main nodes for what the player is doing
	private StackPane primaryPane = new StackPane();

	// panes that can go in pimary Pane
	public SimpleFishingPane simpleFishingPane = new SimpleFishingPane();
	public SeafoodMarketPane seafoodMarketPane;
	public EquipmentMarketPane equipmentMarketPane;
	public StatsVBox statsPane = new StatsVBox();

	// handler to start fishin'
	private GameControl.FishingActivityActions fishingActivityActions;

	private boolean fishingStarted = false;

	// for dropdown menu
	public MenuBar menuBar = new MenuBar();
	private Menu fileMenu = new Menu("File");
	private MenuItem accountDeleteMenuItem = new MenuItem("Delete Account");
	private MenuItem saveMenuItem = new MenuItem("Save");
	private MenuItem exitMenuItem = new MenuItem("Exit");

	// to trigger updating leaderboard
	private FetchStatsHandler updateStatsHandler;

	// general action output for user
	public TextArea taGameOutput = new TextArea();

	// buttons
	private Button btnGoFishing = new Button("Go Fishing");
	private Button btnGoToSeaFoodMarket = new Button("Sell Fish");
	private Button btnGoToEquipMarket = new Button("Buy Equipment");
	private Button btnCheckScores = new Button("Score Board");

	private HBox buttonHBox = new HBox();

	public GamePane(EventHandler<ActionEvent> sellFishAction, FishingActivityActions fishingActivityActions,	
			EventHandler<ActionEvent> deleteAccountAction, EventHandler<ActionEvent> saveAction, EventHandler<ActionEvent> exitAction, 
			String seaFoodMarketName, String equipMarketName, FetchStatsHandler updateStatsHandler)
	{

		this.fishingActivityActions = fishingActivityActions;
		this.updateStatsHandler = updateStatsHandler;
		
		// makes it so the game doesnt resize when swapping different panes into primary pane
		primaryPane.setPrefWidth(Constants.INITIAL_SIMPLE_FISHING_PANE_WIDTH);
		primaryPane.setPrefHeight(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT);
		
		seafoodMarketPane = new SeafoodMarketPane(seaFoodMarketName, sellFishAction);
		equipmentMarketPane = new EquipmentMarketPane(equipMarketName);

		// set up menu
		fileMenu.getItems().addAll(accountDeleteMenuItem, saveMenuItem, exitMenuItem);
		menuBar.getMenus().add(fileMenu);
		
		// give stats pane initial image
		primaryPane.setBackground(new Background(new BackgroundImage(new Image("img/seafood_market.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

		primaryPane.getChildren().add(statsPane);

		// set actions
		accountDeleteMenuItem.setOnAction(deleteAccountAction);
		saveMenuItem.setOnAction(saveAction);
		exitMenuItem.setOnAction(exitAction);

		// make pane width the same as parent, helps with responsiveness
		seafoodMarketPane.prefWidthProperty().bind(this.widthProperty());
		equipmentMarketPane.prefWidthProperty().bind(this.widthProperty());
		statsPane.prefWidthProperty().bind(this.widthProperty());
		
		// define internal actions for swapping out panes
		btnGoFishing.setOnAction(e ->
		{
			primaryPane.setBackground(null);
			primaryPane.getChildren().clear();
			primaryPane.getChildren().add(simpleFishingPane);
			if (fishingStarted)
			{
				// do nothing, keep fishin'!
			}
			else
			{
				fishingActivityActions.startFishingActivity();
				fishingStarted = true;
			}
		});

		btnCheckScores.setOnAction(e ->
		{
			primaryPane.getChildren().clear();
			
			Image img = new Image("img/leaderboard.png");
			BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
			primaryPane.setBackground(new Background(background));
			updateStatsHandler.fetch();
			primaryPane.getChildren().add(statsPane);
		});

		btnGoToSeaFoodMarket.setOnAction(e ->
		{
			primaryPane.getChildren().clear();
			
			Image img = new Image("img/seafood_market.png");
			BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
			primaryPane.setBackground(new Background(background));
			primaryPane.getChildren().add(seafoodMarketPane);
		});

		btnGoToEquipMarket.setOnAction(e ->
		{
			primaryPane.getChildren().clear();
			
			Image img = new Image("img/equip_market.png");
			BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
			primaryPane.setBackground(new Background(background));
			primaryPane.getChildren().add(equipmentMarketPane);
		});
		
		// position buttons in box
		buttonHBox.setAlignment(Pos.CENTER);
		buttonHBox.setSpacing(10);
		buttonHBox.setPadding(new Insets(5, 5, 5, 5));
		
		// put these buttons in a box
		buttonHBox.getChildren().addAll(btnGoFishing, btnGoToSeaFoodMarket, btnGoToEquipMarket, btnCheckScores);
		
		// set up the TextArea for game output

		taGameOutput.setEditable(false);
		

		
		// put children on the pane
		this.getChildren().addAll(menuBar, primaryPane, buttonHBox, taGameOutput);
		
		// paddin' and stuff
		taGameOutput.setPadding(new Insets(5, 5, 5, 5));		
	}

	public void appendOutput(String str)
	{
		if (!str.equals(""))
		{
			this.taGameOutput.appendText(str + "\n");
		}
	}

}
