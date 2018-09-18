Catch!.jar is the Finished product. Use this to play the game!

We have included the necessary SQLite JDBC Jar.

For networking, see CatchServer in package catchgame, and FishingActivity also in package catchgame.

For databasing, see DAO, UserDAO, and LeaderBoardDAO.
	—The UserDAO handles one table and the LeaderBoardDAO handles 
	another.
	—The UserDAO lets you search for a specific user by username.
	—The LeaderBoardDAO can sort data stored in the table by various 	values.

For abstract resource classes, see our two types Equipment and SeaCreature.

For subclasses, Equipment has subclasses Boat, LobsterTrap, and SimpleFishingItem, and SeaCreature has subclasses Fish and Shellfish.

For the JavaFX UI, check out our userinterface package, our graphicclasses package, and the animation that occurs in ClientSubOceanAnimator.

For multi-threading, we have a timer that calls a function repeatedly in ocean, a timer that calls a function repeatedly in market, a thread for establishing connections with clients in CatchServer, a HandleServerSideGameControl thread for every client, and a timeline for the animation.
