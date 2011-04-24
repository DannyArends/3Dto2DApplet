package nl.dannyarends.gameserver.game;

import nl.dannyarends.applets.gameApplet.events.ServerConnection;

public class GameObject {
	ServerConnection connection;
	
	GameObject(ServerConnection s){
		connection=s;
	}
}
