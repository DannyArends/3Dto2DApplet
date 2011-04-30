package nl.dannyarends.gameserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import nl.dannyarends.generic.Utils;
import nl.dannyarends.options.GameOptions;



public class SocketConnector implements Runnable{
	private ServerSocket socket;
	private GameServer server;
  private ArrayList<GameClient> clients = new ArrayList<GameClient>();
	
	SocketConnector(GameServer s){
	  server=s;
		try {
			InetAddress address = InetAddress.getByName(GameOptions.host);
			InetSocketAddress bindAddr = new InetSocketAddress(address,GameOptions.port);
			socket = new ServerSocket();
			socket.bind(bindAddr);
			socket.setSoTimeout(1000);
		}catch (IOException e) {
			System.err.println("Cannot resolve/bind to "+GameOptions.host+":"+GameOptions.port);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
	  Utils.console("Game SocketConnector @t "+GameOptions.host+":"+GameOptions.port+" Waiting For A Connection......");
	  while(server.running){
	    try{    
	      GameClient temp = new GameClient(socket.accept(),server);
	      new Thread(temp).start();
	      clients.add(temp);
      }catch (IOException e){
        server.serverTime.addSecond();
      }
	  }
	}
	
  public ArrayList<GameClient> getClients() {
    return clients;
  }

}
