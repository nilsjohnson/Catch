package catchgame;

/*
Class by Dr. Java and the JavaDocs
Nils Johnson, Caileigh Fitzgerald, Thanh Lam, and Matt Roberts
Date: 11-27-2017
*/
/*
Purpose: to generate, "gives away", and regenerate SeaCreatures. 
It can determine a number of sea creatures to give based on recieved
info, and can regeenrate sea creatures based on there previous
population, the time that has passed, and the max population
the ocean can hold.  Currently holds cod, salmon, tuna,
lobster, oysters, and crab.

Modification info:
Ocean has been refined to have more efficient code then in the orignal
submission, and better naming.  The overall effect has not changed however,
except that the get population methods noew return the ArrayLists' sizes,
as opposed to a random number bettwen 0 and max possible.
*/
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import resources.Fish;
import resources.FishSpecies;
import resources.Shellfish;
import resources.ShellfishSpecies;
import utilities.NumberUtilities;

/**
 * This class generates, "gives away", and regenerates SeaCreatures. 
 * It has methods to create SeaCreatures, "give away" sea creatures,
 * and regenerate SeaCreature populations until they each reach their
 * max or "carrying capacity" populations.
 * 
 * @author Matt Roberts
 * @author Thanh Lam
 */
public class Ocean implements Serializable
{
	// have a population for each FishSpecies and ShellfishSpecies
	
	// resounces.FishSpecies
	private ArrayList<Fish> codPopulation = new ArrayList<>();
	private ArrayList<Fish> salmonPopulation = new ArrayList<>();
	private ArrayList<Fish> tunaPopulation = new ArrayList<>();

	// resounces.ShellFishSpecies
	private ArrayList<Shellfish> lobsterPopulation = new ArrayList<>();
	private ArrayList<Shellfish> crabPopulation = new ArrayList<>();
	private ArrayList<Shellfish> oysterPopulation = new ArrayList<>();
	
	//for determining population growth
	private double relativeGrowthRate = 0.02;
	//also for determining population growth
	private double regenerationTimeInterval=10;
	
	//for timed calls to rgenerateOcean function
	private Timer oceanRegenerationTimer;

	/**
	 * If it is a new game, fills the Ocean to max population;
	 * otherwise it loads the saved ocean;
	 * regardless, it then starts regenerateOcean to populate Ocean
	 * with SeaCreatures (up to max population).
	 */
	public Ocean()
	{
		//System.out.println("In the Ocean Constructor");
		boolean newGame = true;
		if (newGame)
		{
			fillOceanInitially();
		}
		else
		{
			// load old ocean
		}
		regenerateOcean();

	}
	
	/**
	 * The public method that determines how many Fish the Ocean should 
	 * extract and return, based on the plentifulness of the species in the Ocean compared
	 * with the plentifulness of the species in the client's program or screen
	 * 
	 * @param fishSpecies the relevant species
	 * @param clientCurrentPopulation the number of the species in the client's program or screen
	 * @param clientMaxPopulation the highest population of the species the client's program or screen
	 * should hold
	 * @return the proper number of Fish extracted from the ocean, given the Ocean's state 
	 * and the parameters of the method call
	 * @throws Exception thrown is the Ocean tries to extract more of a species than it has, but 
	 * current implementation should avoid this
	 */
	public ArrayList<Fish> extractAndReturnABunchOfFish(FishSpecies fishSpecies, 
			int clientCurrentPopulation,int clientMaxPopulation) throws Exception
	{
		int oceanMaxPopulation=0;
		ArrayList<Fish> fishPopulation=null;
		switch (fishSpecies)
		{
		case COD:
			fishPopulation=codPopulation;
			oceanMaxPopulation=Constants.COD_MAX_POPULATION;
			break;
		case SALMON:
			fishPopulation=salmonPopulation;
			oceanMaxPopulation=Constants.SALMON_MAX_POPULATION;
			break;
		case TUNA:
			fishPopulation=tunaPopulation;
			oceanMaxPopulation=Constants.TUNA_MAX_POPULATION;
			break;
		default:
			break;
		}
		double oceanPlentifullness
			=(double)fishPopulation.size()/(double)oceanMaxPopulation;
		double clientPlentifullness 
			= (double) clientCurrentPopulation / (double) clientMaxPopulation;
		//System.out.println("Ocean p: " + oceanPlentifullness);
		//System.out.println("Client p:" + clientPlentifullness);
		if (oceanPlentifullness > clientPlentifullness)
		{
			int numFish = (int) (oceanPlentifullness 
					* clientMaxPopulation) - clientCurrentPopulation;
			//System.out.println("Number of fish for packet" + numFish);
			return extractABunchOfFish(fishPopulation, numFish);
		}
		else
		{
			ArrayList<Fish> fishPacket = new ArrayList<>();
			return fishPacket;
		}
	}

	/**
	 * The public method that determines how many Shellfish the Ocean should 
	 * extract and return, based on the plentifulness of the species in the Ocean compared
	 * with the plentifulness of the species in the client's program or screen
	 * 
	 * @param shellfishSpecies the relevant species
	 * @param clientCurrentPopulation the number of the species in the client's program or screen
	 * @param clientMaxPopulation the highest population of the species the client's program or screen
	 * should hold
	 * @return the proper number of Shellfish extracted from the ocean, given the Ocean's state 
	 * and the parameters of the method call
	 * @throws Exception thrown is the Ocean tries to extract more of a species than it has, but 
	 * current implementation should avoid this
	 */
	public ArrayList<Shellfish> ecxtractAndReturnABunchOfShellfish(ShellfishSpecies 
			shellfishSpecies, int clientCurrentPopulation, 
			int clientMaxPopulation) throws Exception
	{
		int oceanMaxPopulation=0;
		ArrayList<Shellfish> shellfishPopulation=null;
		switch (shellfishSpecies)
		{
		case OYSTER:
			shellfishPopulation=oysterPopulation;
			oceanMaxPopulation=Constants.OYSTER_MAX_POPULATION;
			break;
		case LOBSTER:
			shellfishPopulation=lobsterPopulation;
			oceanMaxPopulation=Constants.LOBSTER_MAX_POPULATION;
			break;
		case CRAB:
			shellfishPopulation=crabPopulation;
			oceanMaxPopulation=Constants.CRAB_MAX_POPULATION;
			break;
		default:
			break;
		}
		double oceanPlentifullness
			=(double)shellfishPopulation.size()/(double)oceanMaxPopulation;
		double clientPlentifullness 
			= (double) clientCurrentPopulation / (double) clientMaxPopulation;
		//System.out.println("Ocean p: " + oceanPlentifullness);
		//System.out.println("Client p:" + clientPlentifullness);
		if (oceanPlentifullness > clientPlentifullness)
		{
			int numShellfish = (int) (oceanPlentifullness * clientMaxPopulation) - clientCurrentPopulation;
			//System.out.println("Number of shellfish for packet" + numShellfish);
			return extractABunchOfShellfish(shellfishPopulation, numShellfish);
		}
		else
		{
			ArrayList<Shellfish> shellfishPacket = new ArrayList<>();
			return shellfishPacket;
		}
	}

	/**
	 * Extracts the specified number of Fish
	 * @param fishPopulation the Fish population to extract from
	 * @param num the number of Fish to extract
	 * @return the extracted Fish
	 * @throws Exception thrown is the num to extract is greater than the size of the list
	 */
	private ArrayList<Fish> extractABunchOfFish(ArrayList<Fish> fishPopulation, int num) 
			throws Exception
	{
		ArrayList<Fish> extractedFishes = new ArrayList<Fish>();
		for (int i = 0; i <= num - 1; i++)
		{
			extractedFishes.add(extractFish(fishPopulation));
		}

		return extractedFishes;
	}

	/**
	 * Extracts the specified number of Shellfish
	 * @param shellfishPopulation the Shellfish population to extract from
	 * @param num the number of Shellfish to extract
	 * @return the extracted Shellfishes
	 * @throws Exception thrown is the num to extract is greater than the size of the list
	 */
	private ArrayList<Shellfish> extractABunchOfShellfish(ArrayList<Shellfish> 
	shellfishPopulation, int num) throws Exception
	{
		ArrayList<Shellfish> extractedShellfish = new ArrayList<Shellfish>();
		for (int i = 0; i < num - 1; i++)
		{
			extractedShellfish.add(extractShellfish(shellfishPopulation));
		}
		return extractedShellfish;
	}

	/**
	 * Extracts a Fish of the given shellfishPopulation by removing it
	 * from the ArrayList and returning it
	 * @param fishPopulation the population to extract from
	 * @returnthe Fish extracted
	 * @throws Exception thrown if the ArrayList is empty when the method is called
	 */
	private Fish extractFish(ArrayList<Fish> fishPopulation) throws Exception
	{
		Fish fish = fishPopulation.get(fishPopulation.size() - 1);
		fishPopulation.remove(fishPopulation.size() - 1);

		return fish;
	}

	/**
	 * Extracts a Shellfish of the given shellfishPopulation by removing it
	 * from the ArrayList and returning it
	 * @param shellfishPopulation the population to extract from
	 * @return the Shellfish extracted
	 * @throws Exception thrown if the ArrayList is empty when the method is called
	 */
	private Shellfish extractShellfish(ArrayList<Shellfish> shellfishPopulation) throws Exception
	{
		Shellfish shellfish = shellfishPopulation.get(shellfishPopulation.size() - 1);
		shellfishPopulation.remove(shellfishPopulation.size() - 1);
		return shellfish;
	}
	
	/**
	 * Fills the Ocean's populations to initial size by calling
	 * addABunchOfCod, addABunchOfSalmon, addABunchOfTuna,
	 * addABunchOfOyster, addABunchOfLobster, addABunchOfCrab,
	 * with the parameters of initial ocean population sizes given in Constants
	 */
	private void fillOceanInitially()
	{
		addABunchOfCrab(Constants.CRAB_INITIAL_POPULATION);
		addABunchOfCod(Constants.COD_INITIAL_POPULATION);
		addABunchOfSalmon(Constants.SALMON_INITIAL_POPULATION);
		addABunchOfTuna(Constants.TUNA_INITIAL_POPULATION);
		addABunchOfOyster(Constants.OYSTER_INITIAL_POPULATION);
		addABunchOfLobster(Constants.LOBSTER_INITIAL_POPULATION);
	}

	/**
	 * Adds SeaCreatures to Ocean by calling updateAllSeaCreaturePopulations
	 * growth formula every 5 seconds.
	 */
	private void regenerateOcean()
	{
		oceanRegenerationTimer = new Timer();
		TimerTask oceanRegenerationTask;
		oceanRegenerationTask = new TimerTask()
		{

			@Override
			public void run()
			{
				updateAllSeaCreaturePopulations();
			}

		};
		oceanRegenerationTimer.schedule(oceanRegenerationTask, 0, 5_000);
	}
	
	public void shutDownOcean(){
		oceanRegenerationTimer.cancel();
	}
	
	/**
	 * Updates all the SeaCreature populations in ocean by calling
	 * updateCodPopulation, updateSalmonPopulation, updateTunaPopulation,
	 * updateOysterPopulation, updateLobsterPopulation, updateCrabPopulation
	 */
	private void updateAllSeaCreaturePopulations(){
		updateCodPopulation();
		updateSalmonPopulation();
		updateTunaPopulation();
		updateOysterPopulation();
		updateLobsterPopulation();
		updateCrabPopulation();
	}
	
	/**
	 *  Updates the codPopulation by calling updateFishPopulation
	 * with the appropriate parameters
	 */
	private void updateCodPopulation(){
		updateFishPopulation(regenerationTimeInterval, 
				codPopulation, Constants.COD_MAX_POPULATION, 
				FishSpecies.COD);
	}
	
	/**
	 *  Updates the salmonPopulation by calling updateFishPopulation
	 * with the appropriate parameters
	 */
	private void updateSalmonPopulation(){
		updateFishPopulation(regenerationTimeInterval, 
				salmonPopulation, Constants.SALMON_MAX_POPULATION, 
				FishSpecies.SALMON);
	}
	
	/**
	 * Updates the tunaPopulation by calling updateFishPopulation
	 * with the appropriate parameters
	 */
	private void updateTunaPopulation(){
		updateFishPopulation(regenerationTimeInterval, 
				tunaPopulation, Constants.TUNA_MAX_POPULATION, 
				FishSpecies.TUNA);
	}
	
	/**
	 * Updates the oysterPopulation by calling updateShellfishPopulation
	 * with the appropriate parameters
	 */
	private void updateOysterPopulation(){
		updateShellfishPopulation(regenerationTimeInterval, 
				oysterPopulation, Constants.OYSTER_MAX_POPULATION, 
				ShellfishSpecies.OYSTER);
	}
	
	/**
	 * Updates the lobsterPopulation by calling updateShellfishPopulation
	 * with the appropriate parameters
	 */
	private void updateLobsterPopulation(){
		updateShellfishPopulation(regenerationTimeInterval, 
				lobsterPopulation, Constants.LOBSTER_MAX_POPULATION, 
				ShellfishSpecies.LOBSTER);
	}
	
	/**
	 * Updates the crabPopulation by calling updateShellfishPopulation
	 * with the appropriate parameters
	 */
	private void updateCrabPopulation(){
		updateShellfishPopulation(regenerationTimeInterval, 
				crabPopulation, Constants.CRAB_MAX_POPULATION, 
				ShellfishSpecies.CRAB);
	}
	
	/**
	 * Updates the given Fish population of the given species by
	 * calling addABunchOfFish to add the appropriate number of additions according to 
	 * determineNumberNewFishOfPopulationGrowth
	 * @param elapsedTime the time, real or virtual, since the population was last updated
	 * @param fishPopulationAL the population to be updated
	 * @param speciesMaxPopulation the largest population the Ocean allows
	 * @param fishSpecies the species to be updated
	 */
	private void updateFishPopulation(double elapsedTime, 
			ArrayList<Fish> fishPopulationAL, int speciesMaxPopulation, 
			FishSpecies fishSpecies){
		int fishToAdd = determineNumberNewFishOfPopulationGrowth(elapsedTime, 
				fishPopulationAL, speciesMaxPopulation);
		// System.out.println("Cod to add "+codToAdd);
		addABunchOfFish(fishPopulationAL, fishSpecies, fishToAdd);
	}
	
	/**
	 * Updates the given Shellfish population of the given species by
	 * calling addABunchOfShellfish to add the appropriate number of additions according to 
	 * determineNumberNewShellfishOfPopulationGrowth
	 * @param elapsedTime the time, real or virtual, since the population was last updated
	 * @param shellfishPopulationAL the population to be updated
	 * @param speciesMaxPopulation the largest population the Ocean allows
	 * @param shellfishSpecies the species to be updated
	 */
	private void updateShellfishPopulation(double elapsedTime, 
			ArrayList<Shellfish> shellfishPopulationAL, int speciesMaxPopulation, 
			ShellfishSpecies shellfishSpecies){
		int fishToAdd = determineNumberNewShellfishOfPopulationGrowth(elapsedTime, 
				shellfishPopulationAL, speciesMaxPopulation);
		// System.out.println("Cod to add "+codToAdd);
		addABunchOfShellfish(shellfishPopulationAL, shellfishSpecies, fishToAdd);
	}
	
	/**
	 * Determines the appropriate number of Fish organisms of a species
	 * that would come into existence given the given time interval, previous 
	 * population, and the max population Ocean allows, using the general 
	 * population growth formula found on Wikipedia.
	 * @param elapsedTime the time interval, real or virtual, that is supposed to have passed
	 * @param oldPopulationAL the population in question
	 * @param speciesMaxPopulation the max population Ocean allows of that species
	 * @return the number of Fish of the given species that the
	 * population should have reproduced
	 */
	private int determineNumberNewFishOfPopulationGrowth(double elapsedTime, 
			ArrayList<Fish> oldPopulationAL, int speciesMaxPopulation){
		int carryingCapacityPopulation = speciesMaxPopulation;
		int lastPopulation = oldPopulationAL.size();
		double A = (double) (carryingCapacityPopulation - lastPopulation) / (double) lastPopulation;
		double denominator = 1 + A * Math.exp(-relativeGrowthRate * elapsedTime);
		double rawPopulation = (double) carryingCapacityPopulation / denominator;
		// make it an int becuase we can't have (viable) fractions of SeaCreature
		int updatedPopulationNumber = (int) rawPopulation;
		// next line for debug
		//System.out.println(updatedPopulation);
		return updatedPopulationNumber - oldPopulationAL.size();
	}
	
	/**
	 * Determines the appropriate number of Shellfish organisms of a species
	 * that would come into existence given the given time interval, previous 
	 * population, and the max population Ocean allows, using the general 
	 * population growth formula found on Wikipedia.
	 * @param elapsedTime the time interval, real or virtual, that is supposed to have passed
	 * @param oldPopulationAL the population in question
	 * @param speciesMaxPopulation the max population Ocean allows of that species
	 * @return the number of Shellfish of the given species that the
	 * population should have reproduced
	 */
	private int determineNumberNewShellfishOfPopulationGrowth(double elapsedTime, 
			ArrayList<Shellfish> oldPopulationAL, int speciesMaxPopulation){
		int carryingCapacityPopulation = speciesMaxPopulation;
		int lastPopulation = oldPopulationAL.size();
		double A = (double) (carryingCapacityPopulation - lastPopulation) / (double) lastPopulation;
		double denominator = 1 + A * Math.exp(-relativeGrowthRate * elapsedTime);
		double rawPopulation = (double) carryingCapacityPopulation / denominator;
		// make it an int becuase we can't have (viable) fractions of SeaCreature
		int updatedPopulationNumber = (int) rawPopulation;
		// next line for debug
		//System.out.println(updatedPopulation);
		return updatedPopulationNumber - oldPopulationAL.size();
	}
	
	/**
	 * Instantiates the given number of cod and adds it to the codPopulation ArrayList
	 * @param numCodToAdd the number of cod to instantiate and add
	 */
	public void addABunchOfCod(int numCodToAdd){
		addABunchOfFish(codPopulation, FishSpecies.COD, 
				numCodToAdd);
	}
	
	/**
	 * Instantiates the given number of salmon and adds it to the salmonPopulation ArrayList
	 * @param numSalmonToAdd the number of salmon to instantiate and add
	 */
	public void addABunchOfSalmon(int numSalmonToAdd){
		addABunchOfFish(salmonPopulation, FishSpecies.SALMON, 
				numSalmonToAdd);
	}
	
	/**
	 * Instantiates the given number of tuna and adds it to the tunaPopulation ArrayList
	 * @param numTunaToAdd the number of tuna to instantiate and add
	 */
	public void addABunchOfTuna(int numTunaToAdd){
		addABunchOfFish(tunaPopulation, FishSpecies.TUNA, 
				numTunaToAdd);
	}

	/**
	 * Instantiates the given number of oyster and adds it to the oysterPopulation ArrayList
	 * @param numOysterToAdd the number of oyster to instantiate and add
	 */
	private void addABunchOfOyster(int numOysterToAdd){
		addABunchOfShellfish(oysterPopulation, ShellfishSpecies.OYSTER, 
				numOysterToAdd);
	}
	
	/**
	 * Instantiates the given number of lobster and adds it to the lobsterPopulation ArrayList
	 * @param numLobsterToAdd the number of lobster to instantiate and add
	 */
	private void addABunchOfLobster(int numLobsterToAdd){
		addABunchOfShellfish(lobsterPopulation, ShellfishSpecies.LOBSTER, 
				numLobsterToAdd);
	}
	
	/**
	 * Instantiates the given number of crab and adds it to the crabPopulation ArrayList
	 * @param numCrabToAdd the number of crab to instantiate and add
	 */
	private void addABunchOfCrab(int numCrabToAdd){
		System.out.println("adding "+numCrabToAdd);
		addABunchOfShellfish(crabPopulation, ShellfishSpecies.CRAB, 
				numCrabToAdd);
	}
	
	/**
	 * Instantiates the given num of the given species of Fish and adds it to the given
	 * Fish population
	 * @param fishPopulation the population to which the Fish are added
	 * @param species the species of Fish
	 * @param num the number of Fish that are instantiated and added
	 */
	private void addABunchOfFish(ArrayList<Fish> fishPopulation, FishSpecies species, int num)
	{
		for (int i = 0; i <= num - 1; i++)
		{
			addFish(fishPopulation, species);
		}
	}

	/**
	 * Instantiates the given num of the given species of Shellfish and adds it to the given
	 * Shellfish population
	 * @param shellfishPopulation the population to which the Shellfish are added
	 * @param species the species of Shellfish
	 * @param num the number of Shellfish that are instantiated and added
	 */
	private void addABunchOfShellfish(ArrayList<Shellfish> shellfishPopulation, ShellfishSpecies species, int num)
	{
		for (int i = 0; i <= num - 1; i++)
		{
			addShellfish(shellfishPopulation, species);
		}
	}

	/**
	 * Instantiates a Fish of the given species with an appropriate random weight
	 * and adds it to the given Fish population
	 * @param fishPopulation the population to add the Fish to
	 * @param species the species that will be instantiated
	 */
	private void addFish(ArrayList<Fish> fishPopulation, FishSpecies species)
	{
		fishPopulation.add(new Fish(species, 
				getRandomWeightForFish(species), getRandomSpeedFactorForFish(species)));
	}

	/**
	 * Instantiates a Shellfish of the given species with an appropriate random weight
	 * and adds it to the given Shellfish population
	 * @param shellfishPopulation the population to add the Shellfish to
	 * @param species the species that will be instantiated
	 */
	private void addShellfish(ArrayList<Shellfish> shellfishPopulation, ShellfishSpecies species)
	{
		shellfishPopulation.add(new Shellfish(species, 
				getRandomWeightForShellfish(species), getRandomSpeedFactorForShellFish(species)));
	}
	
	/**
	* Gets random weight for a Fish of the given species between 
	* the applicable min and max weights for that species
	* @return a random double between appropriate min and max
	*/
	 private double getRandomWeightForFish(FishSpecies species)
		{
			double weight = 0;
			switch (species)
			{
			case COD:
				weight = getRandomWeightForCod();
				break;
			case SALMON:
				weight = getRandomWeightForSalmon();
				break;
			case TUNA:
				weight = getRandomWeightForTuna();
				break;

			}
			return weight;
		}

	 	/**
		* Gets random weight for a Shellfish of the given species between 
		* the applicable min and max weights for that species
		* @return a random double between appropriate min and max
		*/
		private double getRandomWeightForShellfish(ShellfishSpecies species)
		{
			double weight = 0;
			switch (species)
			{
			case OYSTER:
				weight = getRandomWeightForOyster();
				break;
			case LOBSTER:
				weight = getRandomWeightForLobsetr();
				break;
			case CRAB:
				weight = getRandomWeightForCrab();
				break;

			}
			return weight;
		}

	// get random weight of each species

	/**
	* Gets random weight for Tuna between Constants.COD_INITIAL_WEIGHT_MIN 
	* and Constants.COD_INITIAL_WEIGHT_MAX)
	* @return a random double between Constants.COD_INITIAL_WEIGHT_MIN 
	* and Constants.COD_INITIAL_WEIGHT_MAX)
	*/
	private double getRandomWeightForCod()
	{
		return NumberUtilities.getRandomDouble(Constants.COD_INITIAL_WEIGHT_MIN, Constants.COD_INITIAL_WEIGHT_MAX);

	}

	/**
	 * Gets random weight for Salmon between Constants.SALMON_INITIAL_WEIGHT_MIN 
	 * and Constants.SALMON_INITIAL_WEIGHT_MAX)
	 * @return a random double between Constants.SALMON_INITIAL_WEIGHT_MIN 
	 * and Constants.SALMON_INITIAL_WEIGHT_MAX)
	 */
	private double getRandomWeightForSalmon()
	{
		return NumberUtilities.getRandomDouble(Constants.SALMON_INITIAL_WEIGHT_MIN, Constants.SALMON_INITIAL_WEIGHT_MAX);
	}

	/**
	 * Gets random weight for Tuna between Constants.TUNA_INITIAL_WEIGHT_MIN 
	 * and Constants.TUNA_INITIAL_WEIGHT_MAX)
	 * @return a random double between Constants.TUNA_INITIAL_WEIGHT_MIN 
	 * and Constants.TUNA_INITIAL_WEIGHT_MAX)
	 */
	private double getRandomWeightForTuna()
	{
		return  NumberUtilities.getRandomDouble(Constants.TUNA_INITIAL_WEIGHT_MIN, Constants.TUNA_INITIAL_WEIGHT_MAX);

	}

	/**
	 * Gets random weight for Crab between Constants.LOBSTER_INITIAL_WEIGHT_MIN 
	 * and Constants.LOBSTER_INITIAL_WEIGHT_MAX)
	 * @return a random double between Constants.LOBSTER_INITIAL_WEIGHT_MIN 
	 * and Constants.LOBSTER_INITIAL_WEIGHT_MAX)
	 */
	private double getRandomWeightForCrab()
	{
		return NumberUtilities.getRandomDouble(Constants.CRAB_INITIAL_WEIGHT_MIN, Constants.CRAB_INITIAL_WEIGHT_MAX);

	}

	/**
	 * Gets random weight for Lobster between Constants.LOBSTER_INITIAL_WEIGHT_MIN 
	 * and Constants.LOBSTER_INITIAL_WEIGHT_MAX)
	 * @return a random double between Constants.LOBSTER_INITIAL_WEIGHT_MIN 
	 * and Constants.LOBSTER_INITIAL_WEIGHT_MAX)
	 */
	private double getRandomWeightForLobsetr()
	{
		return NumberUtilities.getRandomDouble(Constants.LOBSTER_INITIAL_WEIGHT_MIN, Constants.LOBSTER_INITIAL_WEIGHT_MAX);
	}

	/**
	 * Gets random weight for Oyster between Constants.OYSTER_INITIAL_WEIGHT_MIN 
	 * and Constants.OYSTER_INITIAL_WEIGHT_MAX)
	 * @return a random double between Constants.OYSTER_INITIAL_WEIGHT_MIN 
	 * and Constants.OYSTER_INITIAL_WEIGHT_MAX)
	 */
	private double getRandomWeightForOyster()
	{
		return NumberUtilities.getRandomDouble(Constants.OYSTER_INITIAL_WEIGHT_MIN, Constants.OYSTER_INITIAL_WEIGHT_MAX);
		
	}
	
	private double getRandomSpeedFactorForFish(FishSpecies species)
	{
		double speedFactor = 0;
		switch (species)
		{
		case COD:
			speedFactor = getRandomSpeedFactorForCod();
			break;
		case SALMON:
			speedFactor = getRandomSpeedFactorForSalmon();
			break;
		case TUNA:
			speedFactor = getRandomSpeedFactorForTuna();
			break;

		}
		return speedFactor;
	}
	
	private double getRandomSpeedFactorForShellFish(ShellfishSpecies species)
	{
		double speedFactor = 0;
		switch (species)
		{
		case OYSTER:
			speedFactor = getRandomSpeedFactorForOyster();
			break;
		case LOBSTER:
			speedFactor = getRandomSpeedFactorForLobster();
			break;
		case CRAB:
			speedFactor = getRandomSpeedFactorForCrab();
			break;

		}
		return speedFactor;
	}
	
	private double getRandomSpeedFactorForCod(){
		return NumberUtilities.getRandomDouble(Constants.COD_MIN_SPEED_FACTOR, Constants.COD_MAX_SPEED_FACTOR);
	}
	
	private double getRandomSpeedFactorForSalmon(){
		return NumberUtilities.getRandomDouble(Constants.SALMON_MIN_SPEED_FACTOR, Constants.SALMON_MAX_SPEED_FACTOR);
	}
	
	private double getRandomSpeedFactorForTuna(){
		return NumberUtilities.getRandomDouble(Constants.TUNA_MIN_SPEED_FACTOR, Constants.TUNA_MAX_SPEED_FACTOR);
	}
	
	private double getRandomSpeedFactorForLobster(){
		return NumberUtilities.getRandomDouble(Constants.LOBSTER_MIN_SPEED_FACTOR, Constants.LOBSTER_MAX_SPEED_FACTOR);
	}
	
	private double getRandomSpeedFactorForCrab(){
		return NumberUtilities.getRandomDouble(Constants.CRAB_MIN_SPEED_FACTOR, Constants.CRAB_MAX_SPEED_FACTOR);
	}
	
	private double getRandomSpeedFactorForOyster(){
		return NumberUtilities.getRandomDouble(Constants.OYSTER_MIN_SPEED_FACTOR, Constants.OYSTER_MAX_SPEED_FACTOR);
	}
	
	/**
	 * Getter for the size of the cod array list.
	 * @return An int of the size of the current cod population 
	 */
	public int getCurrentCodPopulation()
	{
		return codPopulation.size();
	}

	/**
	 * Getter for the size of the salmon array list.
	 * @return An int of the size of the current salmon population 
	 */
	public int getCurrentSalmonPopulation()
	{
		return salmonPopulation.size();
	}

	/**
	 * Getter for the size of the tuna array list.
	 * @return An int of the size of the current tuna population 
	 */
	public int getCurrentTunaPopulation()
	{
		return tunaPopulation.size();
	}

	/**
	 * Getter for the size of the crab array list.
	 * @return An int of the size of the current crab population 
	 */
	public int getCurrentCrabPopulation()
	{
		return crabPopulation.size();
	}

	/**
	 * Getter for the size of the oyster array list.
	 * @return An int of the size of the current oyster population 
	 */
	public int getCurrentLobsterPopulation()
	{
		return lobsterPopulation.size();
	}

	/**
	 * Getter for the size of the oyster array list.
	 * @return An int of the size of the current oyster population 
	 */
	public int getCurrentOysterPopulation()
	{
		return oysterPopulation.size();
	}
}
