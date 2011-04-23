package nl.dannyarends.options;

import nl.dannyarends.generator.model.Value;

public class DatabaseOptions extends OptionsPackage {
	
	public DatabaseOptions(String propertiesFile) {
		super(propertiesFile);
	}
	
	@Option(name = "db_driver", param = Value.Type.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "Database back end driver for the web server")
	public static String db_driver = "org.hsqldb.jdbcDriver";
	
	@Option(name = "db_user", param = Value.Type.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "Username for the database")
	public static String db_user = "sa";
	
	@Option(name = "db_pass", param = Value.Type.STRING, type = Option.Type.OPTIONAL_ARGUMENT, usage = "Password for the database")
	public static String db_pass = "";
	
	@Option(name = "db_uri", param = Value.Type.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "URI of the database back end for the web server")
	public static String db_uri = "jdbc:hsqldb:file:database/DJWServer;shutdown=true";
}
