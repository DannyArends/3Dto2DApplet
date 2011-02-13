package game;

import events.ServerConnection;

public class Types extends GameObject{

	Types(ServerConnection s) {
		super(s);
	}

	static int[] IDs;
	
	static boolean IDExists(int buildingID){
		for(int y : IDs){
			if(y==buildingID) return true;
		}
		return false;
	}
	
	static int whichIndexIsID(int buildingID){
		int index=0;
		for(int y : IDs){
			if(y==buildingID) return index;
			index++;
		}
		return -1;
	}
}
