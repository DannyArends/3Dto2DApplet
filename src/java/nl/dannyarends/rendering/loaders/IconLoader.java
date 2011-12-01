package nl.dannyarends.rendering.loaders;

import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.rendering.Engine;
import nl.dannyarends.rendering.Scene;

/**
 * \brief Lazy loader to load icons via HTTP<br>
 *
 * Lazy loader to load icons via HTTP
 * bugs: none found<br>
 */
public class IconLoader {
  RenderWindow window;
  Engine engine;
  Scene scene;
  
	/**
	 * IconLoader constructor: Gets a list of 2D icons and initializes empty icon models
	 * 
	 */	
	IconLoader(RenderWindow w, Engine e, Scene s){
	  window=w;
	  engine=e;
	  scene=s;   
	}
	
}
