package nl.dannyarends.gameserver;

import java.io.File;
import java.util.ArrayList;

import nl.dannyarends.gameserver.game.Time;
import nl.dannyarends.gameserver.protocol.ServerCommands;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.GameOptions;
import nl.dannyarends.options.OptionsPackage;
import nl.dannyarends.options.WebOptions;
import nl.dannyarends.www.http.WWWServer;
import nl.dannyarends.www.http.servlets.FileServlet;



/**
 * \brief Main game server class, doing (1) Connections (2) World management<br>
 *
 * Main game server class, doing (1) Connections (2) World management
 * TODO: World management
 * bugs: none found<br>
 */
public class GameServer implements Runnable{
	private static String[] arguments;
	private static GameServer server;
	static SocketConnector connections;
	ServerCommands commandStack;
  public Time serverTime = new Time();
	private static String localPath;
	
	public boolean running = true;
	ArrayList<OptionsPackage> options = new ArrayList<OptionsPackage>();
	AppletCompiler appletCompiler = new AppletCompiler();;
	
	public static void main(String[] args) {
		arguments=args;
		Utils.log("-- Starting GameServer " + setLocalPath() + "--",System.err);
		server = new GameServer();
		new Thread(server).start();
	}

	@Override
	public void run() {
		initServer((arguments.length > 1)?arguments[0]:"settings/game.properties");
		while(server.running){
			mainLoop();
		}
	}

	private void mainLoop() {
		// mainLoop of the server, purely for updating the game world
	  Utils.idle(100);
	}

	private void initServer(String gameoptions) {
		Utils.log("-- Parsing properties --",System.err);
		options.add(new GameOptions(gameoptions));
		options.add(new DatabaseOptions("settings/db.properties"));
		Utils.log("-- Going to monitor for incomming connections --",System.err);
    connections = new SocketConnector(server);
    commandStack = new ServerCommands(server);
		new Thread(connections).start();
    Utils.log("-- Compiling Applets --",System.err);
    server.appletCompiler.compileOne(GameOptions.applets_source_folder,"clientApplet", GameOptions.game_data);
		if(GameOptions.webserver){
			options.add(new WebOptions("settings/www.properties"));
			Utils.log("-- Starting WebServer " + setLocalPath() + "--",System.err);
			WWWServer webserver = new WWWServer();
			webserver.addServlet("/data", new FileServlet("Game"));
			webserver.addServlet("/", new ServerServlet(server));
			new Thread(webserver).start();
		}
	}
	
	static String setLocalPath(){
		File f = new File("");
		localPath = f.getAbsolutePath();
		return localPath;
	}

}
