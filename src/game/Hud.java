package game;

import events.ButtonControler;
import genetics.QTLdataset;
import genetics.QTLheatmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Hud {
	QTLdataset dataset;
	static private boolean printHelp=false;
	static private boolean printControls=false;
	static private boolean printAbout=false;
	private ButtonControler c = new ButtonControler();
	Font[] fonts = new Font[]{
			new Font ("Dialog", Font.BOLD,  12)
			,new Font("Dialog", Font.PLAIN,  10)
	    };
	
	public void addDataset(QTLdataset d){
		dataset=d;
	}
	
	int DatasetInfo(Graphics2D g,int x,int y){
		drawBox(g,x,y,250,96,Color.gray);
		g.setFont(fonts[0]);
		drawString(g,"--Dataset Overview--", x, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Traits: " + dataset.ntraits, x, y+24);
		drawString(g,"Chromosomes: " + dataset.nchromosomes, x, y+36);
	    String distances = "Lengths: ";
	    for (int c = 0; c < dataset.nchromosomes; c++) {
	      distances += dataset.chrlengths[c] + " ";
	    }
	    drawString(g,distances, x, y+48);
	    drawString(g,"Markers: " + dataset.nmarkers, x, y+60);
	    drawString(g,"Cut-off: " + ((float)Math.round(QTLheatmap.getCutoff()*10))/10.0, x, y+72);
	    drawString(g,"Objects: " + Scene.myobjects.size() + "/" + Scene.softmyobjectslimit, x, y+84);
	    return y+96;
	}
	
	public static void drawString(Graphics2D g, String s,int x,int y){
		g.setColor(Color.gray);
		g.drawString(s, x+1, y+1);
		g.setColor(Color.white);
		g.drawString(s, x, y);
	}
	
	public static void drawBox(Graphics2D g, int x, int y, int width,int height,Color c){
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
		g.fillRect(x, y+2, width-10, height);
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
		g.fillRect(x, y+4, width-15, height-2);
	}
	
	public void render(Graphics2D g){
		int l = 0;
		drawBox(g,5,36,250,80,Color.gray);
		g.setColor(Color.white);
		g.setFont(fonts[0]);
		drawString(g,"--Genetic Landscapes--", 15, 36);
		g.setFont(fonts[1]);
		drawString(g,"Press 'H' for Help", 15, 48);
		drawString(g,"Press 'C' for Controls", 15, 60);
		drawString(g,"Press 'A' for About", 15, 72);
		if(dataset!=null) l = DatasetInfo(g,Engine.width-200,84);
		if(printHelp) l= doPrintHelp(g,Engine.width-200,l);
		if(printControls) l= doPrintControls(g,l);
		if(printAbout) l= doPrintAbout(g,l);
		c.render(g);
	}

	public static void setPrintHelp(boolean p) {
		printHelp = p;
	}

	public static boolean isPrintHelp() {
		return printHelp;
	}

	public static boolean isPrintControls() {
		return printControls;
	}

	public static boolean isPrintAbout() {
		return printAbout;
	}

	public int doPrintHelp(Graphics2D g,int x, int y) {
		drawBox(g,x,y,250,72,Color.gray);
		g.setFont(fonts[0]);
		drawString(g,"--HELP--", x, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Open triangles: QTL at marker", x, y+36);
		drawString(g,"Filled triangles: Selected Cofactor at marker", x, y+48);
		drawString(g,"Triangle size: QTL effect/likelihood", x, y+60);
		return y+72;
	}

	public static void setPrintControls(boolean p) {
		printControls = p;
	}

	public int doPrintControls(Graphics2D g, int y) {
		drawBox(g,5,y,250,132,Color.gray);
		g.setFont(fonts[0]);
		drawString(g,"--CONTROLS--", 10, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Click and move mouse to look around", 10, y+36);
		drawString(g,"[Left]       step left", 10, y+48);
		drawString(g,"[Right]      step right", 10, y+60);
		drawString(g,"[Down]       step back", 10, y+72);
		drawString(g,"[Up]         step forward ", 10, y+84);
		drawString(g,"[Page Up]    float up", 10, y+96);
		drawString(g,"[M]          Toggle between model only view", 10, y+108);
		drawString(g,"[+]/[-]      Increade/Decrease LOD score Cut-off", 10, y+120);
		return y+132;
	}

	public static void setPrintAbout(boolean p) {
		printAbout = p;
	}

	public int doPrintAbout(Graphics2D g, int y) {
		drawBox(g,5,y,250,84,Color.gray);
		g.setFont(fonts[0]);
		drawString(g,"--ABOUT--", 10, y+12);
		g.setFont(fonts[1]);
		drawString(g,"QTL viewing applet", 10, y+36);
		drawString(g,"Part of the iqtl package", 10, y+48);
		drawString(g,"(c) 2010 Danny Arends - GBIC", 10, y+60);
		drawString(g,"https://github.com/DannyArends/3Dto2DApplet", 10, y+72);
		return y+84;
	}
}
