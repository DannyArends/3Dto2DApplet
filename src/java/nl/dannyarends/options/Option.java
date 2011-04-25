package nl.dannyarends.options;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import nl.dannyarends.generator.model.Value;



@Retention(RetentionPolicy.RUNTIME)
public @interface Option {
	public enum Type{
		NO_ARGUMENT,
		REQUIRED_ARGUMENT,
		OPTIONAL_ARGUMENT
	};
		
	String name();
	Value.Type param();
	Type type();
	String usage();
}
