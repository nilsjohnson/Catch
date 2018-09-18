package animation_work_and_driver;

import catchgame.Constants;
import catchgame.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.SimpleFishingPane;

/**
 * This class was originally used for testing the 
 * ClientSubOceanAnimator, and now isn't fully up to date,
 * but is useful because it lets you play around with an
 * animation without having to log in to Catch!
 * @author mattroberts
 *
 */
public class AnimationDriver extends Application{

	SimpleFishingPane simpleFishingPane;
	@Override
	public void start(Stage primaryStage)
	{
		loadSimpleFishingPanePane(primaryStage);
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}	
	
	/**
	 * Loads the SimpleFishingPane and launches a FishingActivity
	 * @param primaryStage
	 */
	public void loadSimpleFishingPanePane(Stage primaryStage)
	{
		simpleFishingPane = new SimpleFishingPane();
		Scene simpleFishingScene = new Scene(simpleFishingPane, Constants.INITIAL_SIMPLE_FISHING_PANE_WIDTH, 
				Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT);
		primaryStage.setScene(simpleFishingScene);
		primaryStage.setTitle("Fishing Activity");
		FishingActivityForDriver fishingActivityV2=new FishingActivityForDriver(simpleFishingPane, new Player("JaneFisher"));
		primaryStage.setOnCloseRequest(e ->
		{
			
			System.out.println("shutting down animation driver");
			fishingActivityV2.ocean.shutDownOcean();
			fishingActivityV2.getClientFishingActivityFishManager().stopAnimation();
			System.out.println("animation driver shut down");
			
		});
		primaryStage.show();
		primaryStage.requestFocus();
		
	}
}