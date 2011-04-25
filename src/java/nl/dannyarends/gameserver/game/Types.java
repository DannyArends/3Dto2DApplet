package nl.dannyarends.gameserver.game;

import nl.dannyarends.eventHandling.ServerConnection;

public class Types extends GameObject{

	Types(ServerConnection s) {
		super(s);
	}

	int[] IDs;
	
	boolean IDExists(int ID){
		for(int i : IDs){
			if(i==ID) return true;
		}
		return false;
	}
	
	int whichIndexIsID(int ID){
		int index=0;
		for(int i : IDs){
			if(i==ID) return index;
			index++;
		}
		return -1;
	}
	
	public int[] returnAllIndices(){
		return IDs;
	}
}
