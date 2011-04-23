package nl.dannyarends.db;

import java.sql.Connection;
import java.sql.DriverManager;

import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.OptionsParser;

public class Database {
	Connection connection;
	DatabaseOptions options;
	
	public Database(){
		options = new DatabaseOptions("db.properties");
		new OptionsParser(options);
	}
	
	public Connection getDatabase(){
	    if(connection==null){
	    	try {
	    		Class.forName(DatabaseOptions.db_driver);
	    		connection = DriverManager.getConnection(DatabaseOptions.db_uri, DatabaseOptions.db_user, DatabaseOptions.db_pass);
	    	} catch (Exception e) {
	    		System.out.println("JAVA_ERROR: failed to load " + DatabaseOptions.db_driver + " driver.");
	    		e.printStackTrace();
	    	}
	    }
	    return connection;
	}
}
