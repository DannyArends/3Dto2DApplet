package game;

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
	Font[] fonts = new Font[]{
			new Font ("Dialog", Font.BOLD,  12)
			,new Font("Dialog", Font.PLAIN,  10)
	    };
	
	public void addDataset(QTLdataset d){
		dataset=d;
	}
	
	int DatasetInfo(Graphics2D g,int y){
		g.setFont(fonts[0]);
		drawString(g,"--Dataset Overview--", 10, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Traits: " + dataset.ntraits, 10, y+24);
		drawString(g,"Chromosomes: " + dataset.nchromosomes, 10, y+36);
	    String distances = "Lengths: ";
	    for (int c = 0; c < dataset.nchromosomes; c++) {
	      distances += dataset.chrlengths[c] + " ";
	    }
	    drawString(g,distances, 10, y+48);
	    drawString(g,"Markers: " + dataset.nmarkers, 10, y+60);
	    drawString(g,"Cut-off: " + ((float)Math.round(QTLheatmap.getCutoff()*10))/10.0, 10, y+72);
	    return y+84;
	}
	
	public void drawString(Graphics2D g, String s,int x,int y){
		g.setColor(Color.gray);
		g.drawString(s, x+1, y+1);
		g.setColor(Color.white);
		g.drawString(s, x, y);
	}
	
	public void render(Graphics2D g){
		int l = 0;
		g.setColor(Color.white);
		g.setFont(fonts[0]);
		drawString(g,"--Genetic Landscapes--", 10, 10);
		g.setFont(fonts[1]);
		drawString(g,"-- --", 10, 36);
		drawString(g,"Press 'H' for Help", 10, 48);
		drawString(g,"Press 'C' for Controls", 10, 60);
		drawString(g,"Press 'A' for About", 10, 72);
		if(dataset!=null) l = DatasetInfo(g,84);
		if(printHelp) l= doPrintHelp(g,l);
		if(printControls) l= doPrintControls(g,l);
		if(printAbout) l= doPrintAbout(g,l);
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

	public int doPrintHelp(Graphics2D g, int y) {
		g.setFont(fonts[0]);
		drawString(g,"--HELP--", 10, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Open triangles: QTL at marker", 10, y+36);
		drawString(g,"Filled triangles: Selected Cofactor at marker", 10, y+48);
		drawString(g,"Triangle size: QTL effect/likelihood", 10, y+60);
		return y+72;
	}

	public static void setPrintControls(boolean p) {
		printControls = p;
	}

	public int doPrintControls(Graphics2D g, int y) {
		g.setFont(fonts[0]);
		drawString(g,"--CONTROLS--", 10, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Click and move mouse to look around", 10, y+36);
		drawString(g,"[Left]       step left", 10, y+48);
		drawString(g,"[Right]      step right", 10, y+60);
		drawString(g,"[Down]       step back", 10, y+72);
		drawString(g,"[Up]         step forward ", 10, y+84);
		drawString(g,"[Page Up]    float up", 10, y+96);
		drawString(g,"[Page Down]  float down", 10, y+108);
		return y+120;
	}

	public static void setPrintAbout(boolean p) {
		printAbout = p;
	}

	public int doPrintAbout(Graphics2D g, int y) {
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
