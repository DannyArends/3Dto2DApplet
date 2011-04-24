package nl.dannyarends.gameserver.game;

import nl.dannyarends.applets.gameApplet.events.ServerConnection;

public class Currency extends Item{
	private int amount;
	
	Currency(ServerConnection s) {
		super(s,1);
	}
	
	
	/// parseItemDetails: Overwitten parseItemDetails response from server, we expect 2 lines name first, then amount.
	// @param itemDetail String response from the server when called for an item=ID
	void parseItemDetails(String itemDetail){
		String[] item = itemDetail.split("\n");
		setCommonname(item[0]);
		setAmount(Integer.parseInt(item[1]));
		setWeight(1);
		setQuality(0);
	}


	public void setAmount(int a) {
		amount = a;
	}


	public int getAmount() {
		return amount;
	}
}
