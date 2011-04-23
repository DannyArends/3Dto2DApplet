package nl.dannyarends.options;

import nl.dannyarends.generator.model.Value;

public class GeneratorOptions extends OptionsPackage{
	public GeneratorOptions(String propertiesFile) {
		super(propertiesFile);
	}
	
	@Option(name = "templates", param = Value.Type.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of the templates")
	public static String templates = "templates";
	
	@Option(name = "output", param = Value.Type.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of the generated files")
	public static String output = "generated";
}
