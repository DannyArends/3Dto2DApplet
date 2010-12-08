package game;

import genetics.QTLdataset;

import java.awt.Color;
import java.awt.Graphics2D;

public class Hud {
	QTLdataset dataset;
	
	Hud(){
		
	}
	
	public void addDataset(QTLdataset d){
		dataset=d;
	}
	
	void DatasetInfo(Graphics2D g,int y){
		g.drawString("Traits: " + dataset.ntraits, 10, y+12);
		g.drawString("Chromosomes: " + dataset.nchromosomes, 10, y+24);
	    String distances = "Lengths: ";
	    for (int c = 0; c < dataset.nchromosomes; c++) {
	      distances += dataset.chrlengths[c] + " ";
	    }
	    g.drawString(distances, 10, y+36);
	    g.drawString("Markers: " + dataset.nmarkers, 10, y+48);
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.white);
		g.drawString("--QTL viewer--", 10, 36);
		g.drawString("Press 'H' for Help", 10, 48);
		g.drawString("Press 'C' for Controls", 10, 60);
		g.drawString("Press 'A' for About", 10, 72);
		if(dataset!=null) DatasetInfo(g,84);
	}
}
