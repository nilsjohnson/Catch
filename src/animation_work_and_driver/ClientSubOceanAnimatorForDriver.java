package animation_work_and_driver;

import java.util.ArrayList;

import catchgame.Constants;
import graphicclasses.FishImageView;
import graphicclasses.ShellfishImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import resources.Fish;
import resources.Shellfish;
import userinterface.SimpleFishingPane;
import utilities.NumberUtilities;


/**
 * Animates SeaCreatures on a SimpleFishingPane
 * @author mattroberts
 *
 */
public class ClientSubOceanAnimatorForDriver {

	SimpleFishingPane simpleFishingPane;

	ArrayList<Fish> codPopulation = new ArrayList<>();
	ArrayList<Fish> salmonPopulation = new ArrayList<>();
	ArrayList<Fish> tunaPopulation = new ArrayList<>();

	// resounces.ShellFishSpecies
	ArrayList<Shellfish> lobsterPopulation = new ArrayList<>();
	ArrayList<Shellfish> crabPopulation = new ArrayList<>();
	ArrayList<Shellfish> oysterPopuliation = new ArrayList<>();

	Timeline timeline;

	/**
	 * Animates SeaCreatures on a SimpleFishingPane
	 * @param simpleFishingPane the pane on which the animation will occur
	 */
	ClientSubOceanAnimatorForDriver(SimpleFishingPane simpleFishingPane) {
		this.simpleFishingPane = simpleFishingPane;
	}

	/**
	 * Starts an animation that works by calling makeSeaCreaturesOnScreenGo()
	 * every one-twentieth of a second
	 */
	void doBasicClientSubOceanAnimation() {
		Timeline timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.05), event -> {
			makeSeaCreaturesOnScreenGo();
		});

		timeline.getKeyFrames().addAll(keyFrame); // add frame to the timeline
													// KeyFrames
		timeline.setCycleCount(Timeline.INDEFINITE);

		timeline.play();
	}

	/**
	 * So the animation thread can be stopped
	 */
	void stopAnimation() {
		if (timeline != null) {
			timeline.stop();
		} else {
			System.out.println("Timeline was null.");
		}
	}

	/**
	 * Animates the SeaCreatures, by animating each individual population
	 */
	private void makeSeaCreaturesOnScreenGo() {
		makeFishOnScreenGo(codPopulation);
		makeFishOnScreenGo(salmonPopulation);
		makeFishOnScreenGo(tunaPopulation);
		makeShellfishOnScreenGo(lobsterPopulation);
		makeShellfishOnScreenGo(crabPopulation);
		makeShellfishOnScreenGo(oysterPopuliation);

	}

	/**
	 * Animates a fish population, taking into account a fish's speed,
	 * and notably making it reenter the screen at a random edge point
	 * when it has left fully
	 * @param fishPopulation the population to be animated
	 */
	private void makeFishOnScreenGo(ArrayList<Fish> fishPopulation) {
		for (Fish fish : fishPopulation) {
			FishImageView fishImageView = fish.getFishGraphic().getFishImageView();
			fishImageView.setTranslateX(fishImageView.getTranslateX() 
					+ fish.getSpeed() * fish.getDirecTion());
			if ((fishImageView.getTranslateX() > simpleFishingPane.getWidth() 
					&& fish.getDirecTion() == Constants.RIGHT)
					|| (fishImageView.getTranslateX() < -fishImageView.getFitWidth()
							&& fish.getDirecTion() == Constants.LEFT)) {
				makeFishAppearFromOffScreen(fish);
			}
		}
	}

	/**
	 * Animates a shellfish population, taking into account a shellfish's speed,
	 * and notably making it reenter the screen at a random edge point
	 * when it has left fully
	 * @param shellfishPopulation the population to be animated
	 */
	private void makeShellfishOnScreenGo(ArrayList<Shellfish> shellfishPopulation) {
		for (Shellfish shellFish : shellfishPopulation) {
			ShellfishImageView shellfishImageView = shellFish.getShellfishGraphic().getShellfishImageView();
			shellfishImageView.setTranslateX(
					shellfishImageView.getTranslateX() + shellFish.getSpeed() * shellFish.getDirecTion());
			if ((shellfishImageView.getTranslateX() > simpleFishingPane.getWidth()
					&& shellFish.getDirecTion() == Constants.RIGHT)
					|| (shellfishImageView.getTranslateX() < -shellfishImageView.getImage().getWidth()
							&& shellFish.getDirecTion() == Constants.LEFT)) {
				makeShellfishAppearFromOffScreen(shellFish);
			}
		}
	}

	/**
	 * Makes a fish "swim" onto the screen, with its image facing 
	 * the proper direction, and from a random height in the proper range
	 * @param fish the fish to swim on screen
	 */
	private void makeFishAppearFromOffScreen(Fish fish) {
		int leftOrRight = NumberUtilities.getRandomInt(0, 1);
		FishImageView fishImageView = fish.getFishGraphic().getFishImageView();
		if (leftOrRight == 1) {
			fishImageView.setScaleX(1);
			// move fish to far left
			fishImageView.setTranslateX(-fishImageView.getFitWidth() - 20);
			// set direction to right
			fish.setDirecTion(true);
		}
		// go left
		else {
			fishImageView.setScaleX(-1);
			// move fish to far right
			fishImageView.setTranslateX(simpleFishingPane.getWidth() + 20);
			// set direction to left
			fish.setDirecTion(false);
		}
		// get a random height within proper range
		fishImageView.setTranslateY(NumberUtilities.getRandomDouble(Constants.DISTANCE_FROM_TOP,
				(int) simpleFishingPane.getHeight() * Constants.TOP_COEFFICIENT - fishImageView.getFitHeight()
						- Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT));
	}

	/**
	 * Makes a shellfish "walk" onto the screen, with its image facing 
	 * the proper direction, and from a random height in the proper range
	 * @param shellfish the shellfish to walk onto the screen
	 */
	private void makeShellfishAppearFromOffScreen(Shellfish shellfish) {
		int leftOrRight = NumberUtilities.getRandomInt(0, 1);
		ShellfishImageView shellfishImageView = shellfish.getShellfishGraphic().getShellfishImageView();
		// go right
		if (leftOrRight == 1) {
			// set image to rightward-facing
			shellfishImageView.setScaleX(1);
			// move fish to far left
			shellfishImageView.setTranslateX(-shellfishImageView.getFitWidth() - 20);
			// set direction to right
			shellfish.setDirecTion(true);
		}
		// go left
		else {
			shellfishImageView.setScaleX(-1);
			// move fish to far right
			shellfishImageView.setTranslateX(simpleFishingPane.getWidth() + 20);
			// set direction to left
			shellfish.setDirecTion(false);
		}
		// get a random height within proper range
		shellfishImageView.setTranslateY(NumberUtilities.getRandomDouble(
				((int) simpleFishingPane.getHeight() * Constants.TOP_COEFFICIENT
						+ Constants.ONE_HALF_FISH_SHELLFISH_SEPERATION_HEIGHT),
				(int) simpleFishingPane.getHeight() - shellfishImageView.getFitHeight()
						- Constants.DISTANCE_FROM_BOTTOM));
	}
}
