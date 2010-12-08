package game;

import genetics.QTLdataset;

import java.awt.Color;
import java.awt.Graphics2D;

public class Hud {
	QTLdataset dataset;
	static private boolean printHelp=false;
	static private boolean printControls=false;
	static private boolean printAbout=false;
	
	Hud(){
		
	}
	
	public void addDataset(QTLdataset d){
		dataset=d;
	}
	
	int DatasetInfo(Graphics2D g,int y){
		g.drawString("Traits: " + dataset.ntraits, 10, y+12);
		g.drawString("Chromosomes: " + dataset.nchromosomes, 10, y+24);
	    String distances = "Lengths: ";
	    for (int c = 0; c < dataset.nchromosomes; c++) {
	      distances += dataset.chrlengths[c] + " ";
	    }
	    g.drawString(distances, 10, y+36);
	    g.drawString("Markers: " + dataset.nmarkers, 10, y+48);
	    return y+60;
	}
	
	public void render(Graphics2D g){
		int l = 0;
		g.setColor(Color.white);
		g.drawString("--QTL viewer--", 10, 36);
		g.drawString("Press 'H' for Help", 10, 48);
		g.drawString("Press 'C' for Controls", 10, 60);
		g.drawString("Press 'A' for About", 10, 72);
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
		g.drawString("--HELP--", 10, y+12);
		g.drawString(" ", 10, y+24);
		g.drawString(" ", 10, y+36);
		g.drawString(" ", 10, y+48);
		g.drawString(" ", 10, y+60);
		g.drawString(" ", 10, y+72);
		return y+84;
	}

	public static void setPrintControls(boolean p) {
		printControls = p;
	}

	public int doPrintControls(Graphics2D g, int y) {
		g.drawString("--CONTROLS--", 10, y+12);
		g.drawString(" ", 10, y+24);
		g.drawString(" ", 10, y+36);
		g.drawString(" ", 10, y+48);
		g.drawString(" ", 10, y+60);
		g.drawString(" ", 10, y+72);
		return y+84;
	}

	public static void setPrintAbout(boolean p) {
		printAbout = p;
	}

	public int doPrintAbout(Graphics2D g, int y) {
		g.drawString("--ABOUT--", 10, y+12);
		g.drawString(" ", 10, y+24);
		g.drawString(" ", 10, y+36);
		g.drawString(" ", 10, y+48);
		g.drawString(" ", 10, y+60);
		g.drawString(" ", 10, y+72);
		return y+84;
	}
}
