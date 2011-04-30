package nl.dannyarends.gameserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import nl.dannyarends.gameserver.protocol.CommandList.toServer;
import nl.dannyarends.gameserver.protocol.ServerCommands;
import nl.dannyarends.generic.Utils;

public class GameClient implements Runnable {
  DataInputStream is;
  DataOutputStream os;
  InetAddress cip;
  Socket client;
  int runtime = 0;
  boolean online = true;
  int missedbeats = 0;
  GameServer server;
  
  GameClient(Socket c, GameServer s){
    client = c;
    server = s;
    cip=client.getInetAddress();
    System.out.println("Client IP Address:"+cip);
    try{
      is=new DataInputStream(client.getInputStream());
      os=new DataOutputStream(client.getOutputStream());
    }catch (IOException e){
      e.printStackTrace();
    }
  }
  
  void handleCommand(toServer s, String payload){
    switch(s){
      case heatbeat: 
        missedbeats=0;
        break;
      default:
        Utils.console("Strange command");
        break;
    }
  }
  
  public void receiveCommands(){
    try{
    int i;
    if((i = is.available()) > 0){
      byte[] b = new byte[i];
      is.read(b);
      String s = new String(b);
      Utils.console(s);
      handleCommand(server.commandStack.parseCommand(s),s.substring(2));
    }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    while(online){
    try{
    long s = System.currentTimeMillis();
    receiveCommands();
    Utils.idle(45);
    runtime += System.currentTimeMillis()-s;
    if(runtime > 1000){
      missedbeats++;
      if(missedbeats > 25)online=false;
      os.writeBytes(ServerCommands.timestamp());
      runtime=0;
    }
    }catch (IOException e){
      online = false;
    }
    }
    System.out.println("Client disconnected");
    
  }
}
