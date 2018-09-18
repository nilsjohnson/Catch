package animation_work_and_driver;

import java.io.IOException;
import java.util.ArrayList;
import catchgame.Constants;
import catchgame.Ocean;
import catchgame.Player;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import resources.Fish;
import resources.FishSpecies;
import resources.Shellfish;
import resources.ShellfishSpecies;
import userinterface.SimpleFishingPane;
import utilities.ArrayListUtilities;
import utilities.NumberUtilities;

public class FishingActivityForDriver {
	private SimpleFishingPane simpleFishingPane;

	private Player player;

	private ClientSubOcean clientSubOcean;
	private ClientSubOceanAnimatorForDriver ClientSubOceanAnimatorForDriver;

	// for debugging
	Ocean ocean = new Ocean();

	public ClientSubOceanAnimatorForDriver getClientFishingActivityFishManager() {
		return ClientSubOceanAnimatorForDriver;
	}

	/**
	 * This constructs a FishingActivity by putting SeaCreatures on the screen.
	 * 
	 * @param gamePane
	 *            the gamepane where the FishingActivity's UI will be held
	 * @param toServer
	 *            an ObjectOutputStream to the server
	 * @param fromServer
	 *            an ObjectInputStream from the server
	 * @param player
	 *            the player who will be affected by the user fishing
	 * @throws IOException
	 */
	public FishingActivityForDriver(SimpleFishingPane simpleFishingPane,
			Player player) {
		this.simpleFishingPane = simpleFishingPane;
		clientSubOcean = new ClientSubOcean();
		ClientSubOceanAnimatorForDriver = new ClientSubOceanAnimatorForDriver(simpleFishingPane);
		this.player = player;
		testAddFishToFishingActivity();
		ClientSubOceanAnimatorForDriver.doBasicClientSubOceanAnimation();
	}

	/**
	 * Method for adding fish to the clientSubOcean without
	 * any server necessary
	 */
	public void testAddFishToFishingActivity() {
		try {
			System.out.println("add fish");
			ArrayList<Fish> sampleCod = ocean.extractAndReturnABunchOfFish(FishSpecies.COD,
					(clientSubOcean.currentPopulationCod + 90), clientSubOcean.maxPopulationCod);
			ClientSubOceanAnimatorForDriver.codPopulation.addAll(sampleCod);
			ArrayList<Fish> sampleSalmon = ocean.extractAndReturnABunchOfFish(FishSpecies.SALMON,
					(clientSubOcean.currentPopulationSalmon + 60), clientSubOcean.maxPopulationSalmon);
			ClientSubOceanAnimatorForDriver.salmonPopulation.addAll(sampleSalmon);
			ArrayList<Fish> sampleTuna = ocean.extractAndReturnABunchOfFish(FishSpecies.TUNA,
					(clientSubOcean.currentPopulationTuna + 60), clientSubOcean.maxPopulationTuna);
			ClientSubOceanAnimatorForDriver.tunaPopulation.addAll(sampleTuna);
			ArrayList<Shellfish> sampleLobsters = ocean.ecxtractAndReturnABunchOfShellfish(ShellfishSpecies.LOBSTER,
					(clientSubOcean.currentPopulationLobster + 20), clientSubOcean.maxPopulationLobster);
			ClientSubOceanAnimatorForDriver.lobsterPopulation.addAll(sampleLobsters);
			ArrayList<Shellfish> sampleCrab = ocean.ecxtractAndReturnABunchOfShellfish(ShellfishSpecies.CRAB,
					(clientSubOcean.currentPopulationCrab + 60), clientSubOcean.maxPopulationCrab);
			ClientSubOceanAnimatorForDriver.crabPopulation.addAll(sampleCrab);
			ArrayList<Shellfish> sampleOysters = ocean.ecxtractAndReturnABunchOfShellfish(ShellfishSpecies.OYSTER,
					(clientSubOcean.currentPopulationOyster + 40), clientSubOcean.maxPopulationOyster);
			ClientSubOceanAnimatorForDriver.oysterPopuliation.addAll(sampleOysters);
			System.out.println("sample cod size: "+sampleCod.size());
			addFishPacketToScreen(sampleCod, Constants.DISTANCE_FROM_TOP, 
					(int)(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.BOTTOM_COEFFICIENT)+Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT);
			addFishPacketToScreen(sampleSalmon, Constants.DISTANCE_FROM_TOP, 
					(int)(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.BOTTOM_COEFFICIENT)+Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT);
			addFishPacketToScreen(sampleTuna, Constants.DISTANCE_FROM_TOP, 
					(int)(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.BOTTOM_COEFFICIENT)+Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT);
			addShellfishPacketToScreen(sampleLobsters, 
					(int)(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.TOP_COEFFICIENT)+Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT, Constants.DISTANCE_FROM_BOTTOM);

			addShellfishPacketToScreen(sampleCrab, 
					(int)(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.TOP_COEFFICIENT)+Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT, Constants.DISTANCE_FROM_BOTTOM);

			addShellfishPacketToScreen(sampleOysters, 
					(int)(Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT*Constants.TOP_COEFFICIENT)+Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT, Constants.DISTANCE_FROM_BOTTOM);

		
		} catch (Exception e) {
		}
	}

	/**
	 * Adds fish to the screen between the topOffset and bottomOffset and of the
	 * given color
	 * 
	 * @param fishUpdate
	 *            the fish to add to the screen
	 * @param topOffset
	 *            the highest the fish can be on the screen
	 * @param bottomOffset
	 *            the lowest the fish can be on the screen
	 */
	public void addFishPacketToScreen(ArrayList<Fish> fishUpdate, int topOffset, int bottomOffset) {
		for (int i = 0; i <= fishUpdate.size() - 1; i++) {
			addFishToScreen(fishUpdate.get(i), topOffset, bottomOffset);
		}
	}

	/**
	 * Adds shellfish to the screen between the topOffset and bottomOffset and
	 * of the given color
	 * 
	 * @param shellfishUpdate
	 *            the shellfish to add to the screen
	 * @param topOffset
	 *            the highest the shellfish can be on the screen
	 * @param bottomOffset
	 *            the lowest the shellfish can be on the screen
	 */
	public void addShellfishPacketToScreen(ArrayList<Shellfish> shellfishUpdate, int topOffset, int bottomOffset) {
		for (int i = 0; i <= shellfishUpdate.size() - 1; i++) {
			addShellfishToScreen(shellfishUpdate.get(i), topOffset, bottomOffset);
		}
	}

	/**
	 * Adds an individual fish to the screen between the topOffset and
	 * bottomOffset and of the given color
	 * 
	 * @param fish
	 *            the fish to add to the screen
	 * @param topOffset
	 *            the highest the fish can be on the screen
	 * @param bottomOffset
	 *            the lowest the fish can be on the screen
	 */
	public void addFishToScreen(Fish fish, int topOffset, int bottomOffset) {
		fish.setFishBodyByWeight();
		System.out.println("set FishBody");
		double width = simpleFishingPane.getMinWidth();
		double height = simpleFishingPane.getMinHeight();
		fish.getFishGraphic().getFishImageView().setTranslateX(
				NumberUtilities.getRandomDouble(0, width - fish.getFishGraphic().getFishImageView().getFitWidth()));
		fish.getFishGraphic().getFishImageView().setTranslateY(NumberUtilities.getRandomDouble(topOffset,
				height - bottomOffset - fish.getFishGraphic().getFishImageView().getFitHeight()));
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				simpleFishingPane.getChildren().add(fish.getFishGraphic().getFishImageView());
				fish.getFishGraphic().getFishImageView().setOnMouseClicked(new ExtractFishAction(fish));
			}
		});
	}

	/**
	 * Adds an individual shellfish to the screen between the topOffset and
	 * bottomOffset and of the given color
	 * 
	 * @param shellfish
	 *            the shellfish to add to the screen
	 * @param topOffset
	 *            the highest the shellfish can be on the screen
	 * @param bottomOffset
	 *            the lowest the shellfish can be on the screen
	 */
	public void addShellfishToScreen(Shellfish shellfish, int topOffset, int bottomOffset) {
		shellfish.setShellfishBodyByWeight();
		double width = Constants.INITIAL_SIMPLE_FISHING_PANE_WIDTH;// simpleFishingPane.getMinWidth();
		double height = Constants.INITIAL_SIMPLE_FISHING_PANE_HEIGHT;// simpleFishingPane.getMinHeight();
		shellfish.getShellfishGraphic().getShellfishImageView().setTranslateX(NumberUtilities.getRandomDouble(0,
				width - shellfish.getShellfishGraphic().getShellfishImageView().getFitWidth()));
		shellfish.getShellfishGraphic().getShellfishImageView().setTranslateY(NumberUtilities.getRandomDouble(topOffset,
				height - bottomOffset - shellfish.getShellfishGraphic().getShellfishImageView().getFitHeight()));
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				simpleFishingPane.getChildren().add(shellfish.getShellfishGraphic().getShellfishImageView());
				shellfish.getShellfishGraphic().getShellfishImageView()
						.setOnMouseClicked(new ExtractShellfishAction(shellfish));
			}
		});
	}

	/**
	 * Action that occurs when a fish is caught.
	 */
	private class ExtractFishAction implements EventHandler<MouseEvent> {
		Fish fish;

		ExtractFishAction(Fish fish) {
			this.fish = fish;
		}

		@Override
		public void handle(MouseEvent e) {
			try {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						player.addSeaCreatureToIceChest(fish);
						simpleFishingPane.getChildren().remove(fish.getFishGraphic().getFishImageView());
						switch (fish.getSpecies()) {
						case COD:
							ArrayListUtilities.removeObjectFromArrayList(ClientSubOceanAnimatorForDriver.codPopulation,
									fish);
							System.out.println("Cod: " + ClientSubOceanAnimatorForDriver.codPopulation.size());
							clientSubOcean.currentPopulationCod--;
							break;
						case SALMON:
							ArrayListUtilities
									.removeObjectFromArrayList(ClientSubOceanAnimatorForDriver.salmonPopulation, fish);
							System.out.println("Salmon: " + ClientSubOceanAnimatorForDriver.salmonPopulation.size());
							clientSubOcean.currentPopulationSalmon--;
							break;

						case TUNA:
							ArrayListUtilities
									.removeObjectFromArrayList(ClientSubOceanAnimatorForDriver.tunaPopulation, fish);
							System.out.println("Tuna: " + ClientSubOceanAnimatorForDriver.tunaPopulation.size());
							clientSubOcean.currentPopulationTuna--;
							break;
						}

					}
				});
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		};
	}

	/**
	 * Action that occurs when a shellfish is caught.
	 */
	private class ExtractShellfishAction implements EventHandler<MouseEvent> {
		Shellfish shellfish;

		ExtractShellfishAction(Shellfish shellfish) {
			this.shellfish = shellfish;
		}

		@Override
		public void handle(MouseEvent e) {
			try {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						player.addSeaCreatureToIceChest(shellfish);
						simpleFishingPane.getChildren().remove(shellfish.getShellfishGraphic().getShellfishImageView());
						switch (shellfish.getSpecies()) {
						case LOBSTER:
							ArrayListUtilities.removeObjectFromArrayList(
									ClientSubOceanAnimatorForDriver.lobsterPopulation, shellfish);
							System.out.println("Lobster: " + ClientSubOceanAnimatorForDriver.lobsterPopulation.size());
							clientSubOcean.currentPopulationLobster--;
							break;
						case CRAB:
							ArrayListUtilities.removeObjectFromArrayList(
									ClientSubOceanAnimatorForDriver.crabPopulation, shellfish);
							System.out.println("Crab: " + ClientSubOceanAnimatorForDriver.crabPopulation.size());
							clientSubOcean.currentPopulationCrab--;
							break;

						case OYSTER:
							ArrayListUtilities.removeObjectFromArrayList(
									ClientSubOceanAnimatorForDriver.oysterPopuliation, shellfish);
							System.out.println("Oyster: " + ClientSubOceanAnimatorForDriver.oysterPopuliation.size());

							clientSubOcean.currentPopulationOyster--;
							break;
						}
					}
				});
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		};
	}

	/**
	 * For keeping track of all the SeaCreatures on the client's side.
	 */
	class ClientSubOcean {

		int currentPopulationCod = 0;
		int maxPopulationCod = Constants.COD_MAX_POPULATION / 10;
		int currentPopulationSalmon = 0;
		int maxPopulationSalmon = Constants.SALMON_MAX_POPULATION / 10;
		int currentPopulationTuna = 0;
		int maxPopulationTuna = Constants.TUNA_MAX_POPULATION / 10;
		int currentPopulationOyster = 0;
		int maxPopulationOyster = Constants.OYSTER_MAX_POPULATION / 10;
		int currentPopulationLobster = 0;
		int maxPopulationLobster = Constants.LOBSTER_MAX_POPULATION / 10;
		int currentPopulationCrab = 0;
		int maxPopulationCrab = Constants.CRAB_MAX_POPULATION / 10;
	}
}
