package nl.dannyarends.gameserver;

import java.io.File;
import java.util.ArrayList;

import nl.dannyarends.generic.Utils;
import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.GameOptions;
import nl.dannyarends.options.OptionsPackage;
import nl.dannyarends.options.WebOptions;
import nl.dannyarends.www.http.WWWServer;
import nl.dannyarends.www.http.servlets.FileServlet;




public class GameServer implements Runnable{
	private static String[] arguments;
	private static GameServer server;
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
		// TODO Auto-generated method stub
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
		Utils.log("-- Compiling Applets --",System.err);
		appletCompiler.compileAll(GameOptions.applets_source_folder, GameOptions.game_data);
		
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
