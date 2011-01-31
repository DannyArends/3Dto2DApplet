package game;

import events.ServerConnection;

public class Item extends GameObject{
	private int id=0;
	private String commonname;
	private int weight = 0;
	private int quality = 0;
	private char itemclass = 'U';
	
	Item(ServerConnection s,int itemid) {
		super(s);
		id=itemid;
		parseItemDetails(connection.commandToServer("function=item_stats&itemid="+id));
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

	/// parseItemDetails: Parse response from server, we expect name and weight.
	// @param itemDetail String response from the server when called for an item=ID
	void parseItemDetails(String itemDetail){
		String[] item = itemDetail.split("\n");
		if(item.length == 3){
			setCommonname("Unknown");
			setWeight(0);
		}else{
			setCommonname(item[3].split(": ")[1]);
			setWeight(Integer.parseInt(item[4].split(": ")[1]));
			if(item.length > 4){
				setItemclass(item[5].split(": ")[1].charAt(0));
			}
		}
	}

	public void setCommonname(String name) {
		commonname = name;
	}

	public void setWeight(int w) {
		weight = w;
	}

	public void setQuality(int q) {
		quality = q;
	}

	public void setItemclass(char itemclass) {
		this.itemclass = itemclass;
	}

	public char getItemclass() {
		return itemclass;
	}

}
