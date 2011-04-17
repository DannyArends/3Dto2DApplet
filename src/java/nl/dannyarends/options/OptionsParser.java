package nl.dannyarends.options;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OptionsParser {
	HashMap<String, Field> field_map = new HashMap<String, Field>();
	
	public OptionsParser(){

	}
	
	public OptionsParser(OptionsPackage options){
		try {
			parse(options);
		} catch (Exception e) {
			System.err.println("Unable to parse options, loading defaults");
		}
	}
	
	public void parse(OptionsPackage options) throws Exception{
		Properties properties = options.properties;
		Field[] fields = options.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++){
			if (fields[i].isAnnotationPresent(Option.class)){
				Option opt = fields[i].getAnnotation(Option.class);
				field_map.put(opt.name(), fields[i]);
			}
		}
		for (Map.Entry<Object, Object> property : properties.entrySet()){
			String name = property.getKey().toString();
			Field field = field_map.get(name);
			if (field != null){
				Option opt = field.getAnnotation(Option.class);
				String result = "";
				if (opt.type() == Option.Type.NO_ARGUMENT){
					result = "true";
				}else if (opt.type() == Option.Type.OPTIONAL_ARGUMENT){
					result = property.getValue().toString().trim();
				}else if (opt.type() == Option.Type.REQUIRED_ARGUMENT){
					if (property.getValue().toString().length() == 0) throw new Exception("Insufficient number of options.");
					result = property.getValue().toString().trim();
				}
				try {
					switch (opt.param()) {
			            case BOOLEAN: field.set(options, Boolean.parseBoolean(result)); break;
			            case INTEGER: field.set(options, Integer.parseInt(result)); break;
			            case DOUBLE: field.set(options, Double.parseDouble(result)); break;
			            case STRING: field.set(options, result); break;
			            case COLLECTION:
			            	//NOTE: I am aware of the unchecked cast, but that what you get when you play with runtime objects
			            	@SuppressWarnings("unchecked")
							ArrayList<String> collection = (ArrayList<String>) field.get(options);
							for (String v : result.toString().split(",")){
								collection.add(v);
							}
			            	break;
			            case FILEPATH: field.set(options, result); break;
			            case DIRPATH: field.set(options, result.endsWith("/")?result:result+"/"); break;
			            default: field.set(options, result); break;
					}
				} catch (Exception ex) {
                    throw new Exception("Bad cast when trying to read options:\n" + ex.toString());
				}
			}
		}
	}
	
}
