package nl.dannyarends.gameserver.game;

import nl.dannyarends.applets.gameApplet.events.ServerConnection;

public class Experience extends GameObject{
	int id;
	private enum Experiences{harvesting,manufacturing,attack,defence};
	private int[] experience = new int[Experiences.values().length];
	
	Experience(ServerConnection s,int userid) {
		super(s);
		id=userid;
		parseXPDetails(s.commandToServer("xp="+id));
	}

	public void setExperience(int[] e) {
		experience = e;
	}

	public int[] getExperience() {
		return experience;
	}
	
	/// parseXPDetails: Parse response from server, we expect 2 lines weight first, then quality.
	// @param XPDetail String response from the server when called for an xp=ID
	void parseXPDetails(String XPDetail){
		String[] xp_item = XPDetail.split("\n");
		for(int x=0;x<Experiences.values().length;x++){
			experience[x]=Integer.parseInt(xp_item[x]);
		}
	}
}
