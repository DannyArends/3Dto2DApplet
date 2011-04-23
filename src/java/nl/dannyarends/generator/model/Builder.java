package nl.dannyarends.generator.model;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Builder{
	private Model m = new Model();
	static JFrame frame;

	public Builder(){
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

    private static void createAndShowGUI() {
        //Create and set up the window.
    	frame = new JFrame("Model Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Model Builder");
        frame.getContentPane().add(label);
//        frame.getContentPane().add((JComponent)new Entity());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
    public Builder(String modelFile){
		this();
		try {
			m.loadModel(modelFile);
		} catch (IOException e) {
			System.err.println("Unable to load model from file: " + modelFile);
			e.printStackTrace();
		}
	}
	
	public boolean saveModel(String modelFile){
		try {
			return m.saveModel(modelFile);
		}catch (IOException e){
			System.err.println("Unable to save model to file: " + modelFile);
			return false;
		}
	}
	
	public Model getModel(){
		return m;
	}
	
}
