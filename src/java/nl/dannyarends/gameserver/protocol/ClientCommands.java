package nl.dannyarends.gameserver.protocol;

import nl.dannyarends.eventHandling.NetworkHandler;
import nl.dannyarends.gameserver.protocol.CommandList.fromServer;
import nl.dannyarends.gameserver.protocol.CommandList.toServer;

public class ClientCommands {
  static NetworkHandler server;
  
  public ClientCommands(NetworkHandler s){
    server=s;
    new CommandList();
  }
  
  public static String heatbeat(){
    return toServer.heatbeat.toString();
  }
  
  public static String login(String user,String pass){
    return toServer.login + user + toServer.login.sep + pass;
  }
  
  public static String chat(String text){
    return toServer.chat + text;
  }
  
  public static String move(int x, int y){
    return toServer.movement.toString() + x + toServer.movement.sep + y;
  }
  
  public CommandList.fromServer parseCommand(String command){
    for(fromServer s : fromServer.values()){
      if(command.startsWith(s.pre)){
        return s;
      }
    }
    return null;
  }
}
