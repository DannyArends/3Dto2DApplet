package nl.dannyarends.gameserver.protocol;

import nl.dannyarends.gameserver.GameServer;
import nl.dannyarends.gameserver.protocol.CommandList.fromServer;
import nl.dannyarends.gameserver.protocol.CommandList.toServer;

public class ServerCommands {
  static GameServer server;
  
  public ServerCommands(GameServer s){
    server=s;
  }
  
  public static String timestamp(){
    return fromServer.timestamp + server.serverTime.getTime() + fromServer.timestamp.sep + server.serverTime.getDate();
  }
  
  public static String login(int id, String message){
    return fromServer.login.toString() + id + fromServer.login.sep + message;
  }
  
  public static String chat(String message){
    return fromServer.chat + message;
  }
  
  public static String player(String name,int x,int y){
    return fromServer.players + name + fromServer.players.sep + x + fromServer.players.sep + y;
  }
  
  public static String item(String name){
    return fromServer.items + name;
  }
  
  public static String move(int x, int y){
    return fromServer.movement.toString() + x + fromServer.movement.sep + y;
  }
  
  public static String object(String name){
    return fromServer.objects + name;
  }
  
  public toServer parseCommand(String command){
    for(toServer s : toServer.values()){
      if(command.startsWith(s.pre)){
        return s;
      }
    }
    return null;
  }
}
