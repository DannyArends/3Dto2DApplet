package nl.dannyarends.gameserver.game;

import nl.dannyarends.eventHandling.ServerConnection;

public class Inventory extends GameObject{
	private int id;
	private Item[][] items = new Item[5][5];
	
	Inventory(ServerConnection s,int userid) {
		super(s);
		id=userid;
		parseItemsList(connection.commandToServer("function=get_inventory&p1"+id));
	}

//	public int getGold(){
//		return gold;
//	}
//	
//	/// pay: Ask the server to subtract amount
//	// @param amount String response from the server when called for an inventory=ID
//	// @return True/Talse if the transaction was succesfull
//	public boolean pay(int amount){
//		if(amount <= gold){
//			if(Integer.parseInt(connection.commandToServer("inventory="+id+"&pay="+amount))==1){
//				gold -= amount;
//				return true;
//			}
//		}
//		return false;
//	}

	public Item[][] getItems() {
		return items;
	}
	
	/// parseItemsList: Parse response from server, we expect 26 lines gold first, then Item ID's.
	// @param itemsList String response from the server when called for an inventory=ID
	void parseItemsList(String itemsList){
		String[] server_items = itemsList.split("\n");
		for(int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				items[x][y] = new Item(connection,Integer.parseInt(server_items[1+x+y]));
			}
		}
	}
}
