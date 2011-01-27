package game;

import events.ServerConnection;

public class GameObject {
	ServerConnection connection;
	
	GameObject(ServerConnection s){
		connection=s;
	}
}
