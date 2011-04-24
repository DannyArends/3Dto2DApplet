package nl.dannyarends.gameserver.game;

import nl.dannyarends.applet.events.ServerConnection;

public class GameObject {
	ServerConnection connection;
	
	GameObject(ServerConnection s){
		connection=s;
	}
}
