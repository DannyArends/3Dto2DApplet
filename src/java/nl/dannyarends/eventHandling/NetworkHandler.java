package nl.dannyarends.eventHandling;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import nl.dannyarends.gameserver.protocol.ClientCommands;
import nl.dannyarends.gameserver.protocol.CommandList;
import nl.dannyarends.gameserver.protocol.CommandList.fromServer;
import nl.dannyarends.generic.Utils;

public class NetworkHandler implements Runnable{
  private DataInputStream is;
  private DataOutputStream os;
  private Socket s;
  private ArrayList<String> commandsToServer = new ArrayList<String>();
  private boolean connected = true;
  private int runtime = 0;
  public TimeHandler serverTime = new TimeHandler();
  private ClientCommands commandStack;

  public NetworkHandler(String host,int port) {
    commandStack=new ClientCommands(this);
    try{

      s = new Socket(host, port);
      is = new DataInputStream(s.getInputStream());
      os = new DataOutputStream(s.getOutputStream());
    }catch (Exception e){
        System.err.println("No connection");
        e.printStackTrace();
        connected=false;
    }
  }
  
  void handleCommand(CommandList.fromServer type, String payload){
    switch(type){
      case timestamp: 
        serverTime.set(payload.split(fromServer.timestamp.sep));
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
      handleCommand(commandStack.parseCommand(s),s.substring(2));
    }
    }catch (Exception e){
      e.printStackTrace();
    }
  }
  
  public void sendCommands(int n){
    int cnt =0;
    try{
    while(n > 0 && commandsToServer.size() > 0){
      os.writeBytes(commandsToServer.get(0));
      commandsToServer.remove(0);
      cnt++;
    }
    }catch (Exception e){
      e.printStackTrace();
    }
    if(cnt > 0)Utils.console("Send "+cnt+"/"+n+" commands to server");
  }

  @Override
  public void run() {
    while(connected){
      long s = System.currentTimeMillis();
      receiveCommands();
      sendCommands(5);
      Utils.idle(45);
      runtime += System.currentTimeMillis()-s;
      if(runtime > 5000){
        commandsToServer.add(ClientCommands.heatbeat());
        runtime=0;
      }
    }
  }

}