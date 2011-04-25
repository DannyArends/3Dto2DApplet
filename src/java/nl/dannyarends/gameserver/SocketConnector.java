package nl.dannyarends.gameserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import nl.dannyarends.options.GameOptions;



public class SocketConnector implements Runnable{
	private ServerSocket socket;
	
	SocketConnector(){
		
		try {
			InetAddress address = InetAddress.getByName(GameOptions.host);
			InetSocketAddress bindAddr = new InetSocketAddress(address,GameOptions.port);
			socket = new ServerSocket();
			socket.bind(bindAddr);
		}catch (IOException e) {
			System.err.println("Cannot resolve/bind to "+GameOptions.host+":"+GameOptions.port);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
