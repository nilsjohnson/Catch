package catchgametest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import catchgame.Ocean;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import resources.Boat;
import resources.BoatTypes;
import resources.Fish;
import resources.FishSpecies;
import resources.LobsterTrap;
import resources.Shellfish;
import resources.ShellfishBushel;
import resources.ShellfishSpecies;
import resources.SimpleFishingItem;
import resources.SimpleFishingItemType;
import resources.Usage;

public class ResourceTest
{

	public static void main(String[] args)
	{
		System.out.println("Manual Testing: 'resouces'\n");

		System.out.println("Making and testing new boats...");
		Boat skiff = new Boat(BoatTypes.FISHING_SKIFF);
		System.out.println("'getType()': " + skiff.getType());
		System.out.println("'getUsage()': " + skiff.getUsage() + "\n");
		
		Boat trawler = new Boat(BoatTypes.TRAWLER);
		System.out.println("'getType()': " + trawler.getType());
		System.out.println("'getUsage()': " + trawler.getUsage()+ "\n");
		
		Boat commericalTrawler = new Boat(BoatTypes.COMMERCIAL_TRAWLER);
		System.out.println("'getType()': " + commericalTrawler.getType());
		System.out.println("'getUsage()': " + commericalTrawler.getUsage()+ "\n");
		
		System.out.println("Making and testing new SimpleFishingItems ...");
		SimpleFishingItem net = new SimpleFishingItem(SimpleFishingItemType.FISHING_POLE);
		System.out.println("'getType()': " + net.getType());
		System.out.println("'getUsage()': " + net.getUsage()+ "\n");
		
		SimpleFishingItem largeNet = new SimpleFishingItem(SimpleFishingItemType.FISHING_POLE);
		System.out.println("'getType()': " + largeNet.getType());
		System.out.println("'getUsage()': " + largeNet.getUsage()+ "\n");
		
		System.out.println("Making and testing new a new LobsterTrap ...");
		LobsterTrap lobsterTrap = new LobsterTrap(Usage.LOBSTER_TRAPPING);
		System.out.println("'getType()': " + lobsterTrap.getType());
		System.out.println("'getUsage()': " + lobsterTrap.getUsage()+ "\n");
		
		System.out.println("Making and testing new a new Fish and Shellfish ...");
		// make Fish of type COD
        Fish fish = new Fish(FishSpecies.COD, 12);
        System.out.println("fish.getWeight(): " + fish.getWeight());
        System.out.println("fish.getSpecies(): " + fish.getSpecies() + "\n");
        
        // make another one of type SALMON
        Fish fish2 = new Fish(FishSpecies.SALMON, 17);
        System.out.println("fish2.getWeight(): " + fish2.getWeight());
        System.out.println("fish2.getSpecies(): " + fish2.getSpecies() + "\n");
     
        
        // make a Shellfish of type LOBSTER
        Shellfish Shellfish2 = new Shellfish(ShellfishSpecies.LOBSTER, 1.4);
        System.out.println("Shellfish2.getWeight(): " + Shellfish2.getWeight());
        System.out.println("Shellfish2.getSpecies(): " + Shellfish2.getSpecies() + "\n");
        
        // Shellfish logically go in Bushesl.
        // make a Bushel to hold Crabs
        ShellfishBushel bushel = new ShellfishBushel(ShellfishSpecies.CRAB);
        System.out.println("'bushel.getBushelContent()': " + bushel.getBushelContent());
        System.out.println("'bushel.getWeight()': " + bushel.getWeight() + "\n");
        
        // Add a Shellfish of type OYSTER to the Bushel for type CRAB
        bushel.add(new Shellfish(ShellfishSpecies.OYSTER, 1.4));
        System.out.println("'bushel.getWeight()' after attempting to add an OYSTER: " + bushel.getWeight() + "\n");
        
        // Try it with a CRAB
        bushel.add(new Shellfish(ShellfishSpecies.CRAB, 1.3));
        System.out.println("'bushel.getWeight()' after adding 1 crab: " + bushel.getWeight() + "\n");
        
        // Add more CRAB
        bushel.add(new Shellfish(ShellfishSpecies.CRAB, 1.3));
        bushel.add(new Shellfish(ShellfishSpecies.CRAB, 1.9));
        bushel.add(new Shellfish(ShellfishSpecies.CRAB, 1.2));
        System.out.println("'bushel.getWeight()' after adding 3 crabs: " + bushel.getWeight() + "\n");
        
        // Testing the Ocean class
        System.out.println("Testing the Ocean Class...");
        Ocean ocean = new Ocean();
        
        System.out.println("'ocean.getCurrentCodPopulation()': " + ocean.getCurrentCodPopulation());
        System.out.println("'ocean.getCurrentSalmonPopulation()': " + ocean.getCurrentSalmonPopulation());
        System.out.println("'ocean.getCurrentTunaPopulation()': " + ocean.getCurrentTunaPopulation());
        System.out.println("'ocean.ocean.getCurrentCrabPopulation()': " + ocean.getCurrentCrabPopulation());
        System.out.println("'ocean.ocean.getCurrentLobsterPopulation()': " + ocean.getCurrentLobsterPopulation());
        System.out.println("'ocean.getCurrentOysterPopulation()': " + ocean.getCurrentOysterPopulation());
        
       
      /*  URI uri;
		try
		{
			uri = new URI("water.wav");
	
        
		Media media = new Media(uri.toString());

		MediaPlayer a = new MediaPlayer(media);
		a.setOnEndOfMedia(new Runnable()
		{
			public void run()
			{
				a.seek(Duration.ZERO);
			}
		});
		a.play();
		}
		catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        
        
        /**/
	}

}
