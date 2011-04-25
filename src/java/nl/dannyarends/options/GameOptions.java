package nl.dannyarends.options;

import nl.dannyarends.generator.model.Value;

public class GameOptions extends OptionsPackage{

	public GameOptions(String propertiesFile) {
		super(propertiesFile);
	}

	@Option(name = "host", param = Value.Type.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "hostname")
	public static String host = "game.dannyarends.nl";
	
	@Option(name = "port", param = Value.Type.INTEGER, type = Option.Type.REQUIRED_ARGUMENT, usage = "port to be used")
	public static int port = 2000;
	
	@Option(name = "applets_source_folder", param = Value.Type.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "source location of applets")
	public static String applets_source_folder = "src\\java\\nl\\dannyarends\\applets";

	@Option(name = "max_clients", param = Value.Type.INTEGER, type = Option.Type.REQUIRED_ARGUMENT, usage = "maximum number of connected clients")
	public static int max_clients = 350;
	
	@Option(name = "webserver", param = Value.Type.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Use the webserver")
	public static boolean webserver = true;
	
	@Option(name = "game_data", param = Value.Type.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of game data")
	public static String game_data = "websites/game/";

	@Option(name = "user_data", param = Value.Type.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "location of user data")
	public static String user_data = "websites/game/users/";
	
	@Option(name = "users_dir", param = Value.Type.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "maximum number of connected clients")
	public static String users_dir = "websites/game/users/";
}
