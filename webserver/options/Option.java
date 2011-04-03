package options;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Option {
	public enum Type{
		NO_ARGUMENT,
		REQUIRED_ARGUMENT,
		OPTIONAL_ARGUMENT
	};
	
	public enum Param{
		BOOLEAN,
		INTEGER,
		DOUBLE,
		STRING,
		COLLECTION,
		FILEPATH,
		DIRPATH
	};
		
	String name();
	Param param();
	Type type();
	String usage();
}
