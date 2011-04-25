package nl.dannyarends.gameserver.game;

import nl.dannyarends.eventHandling.ServerConnection;

public class GameObject {
	ServerConnection connection;
	
	GameObject(ServerConnection s){
		connection=s;
	}
}
