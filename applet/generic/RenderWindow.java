package generic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;

/// Abstract class to render to Applet graphics and/or JFrame
//<p>
//TODO
//</p>
//

public abstract interface RenderWindow extends ImageObserver {
	public abstract void paint(Graphics g);
	public abstract Image createImage(int width, int height);
	public abstract Dimension getSize();
	public abstract URL getDocumentBase();
	public abstract URL	getCodeBase();
	public abstract Graphics getGraphics();
	public abstract void repaint();
	public abstract void showStatus(String message);
	public abstract Image createImage(ImageProducer memoryImageSource);
}
