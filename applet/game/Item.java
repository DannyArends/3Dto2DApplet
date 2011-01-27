package game;

import events.ServerConnection;

public class Item extends GameObject{
	private int id=0;
	private String commonname;
	private int weight = 0;
	private int quality = 0;
	
	Item(ServerConnection s,int itemid) {
		super(s);
		id=itemid;
		parseItemDetails(connection.commandToServer("item="+id));
	}

	public String getCommonname() {
		return commonname;
	}

	public int getWeight() {
		return weight;
	}

	public int getQuality() {
		return quality;
	}

	/// parseItemDetails: Parse response from server, we expect 2 lines weight first, then quality.
	// @param itemDetail String response from the server when called for an item=ID
	void parseItemDetails(String itemDetail){
		String[] item = itemDetail.split("\n");
		setCommonname(item[0]);
		setWeight(Integer.parseInt(item[1]));
		setQuality(Integer.parseInt(item[2]));
	}

	public void setCommonname(String commonname) {
		this.commonname = commonname;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

}
