package TicTacToe;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyDrawPanel extends JPanel {
	public void paintComponent (Graphics g) {
		Graphics2D G2d = (Graphics2D) g;

		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);
		Color startColor = new Color (red,green,blue);
		
		 red = (int) (Math.random() * 255);
		 green = (int) (Math.random() * 255);
		 blue = (int) (Math.random() * 255);
		Color endColor = new Color (red,green,blue);
		
		GradientPaint gradient = new GradientPaint(0,0,startColor,600,600, endColor);
		G2d.setPaint(gradient);
		G2d.fillRect(0,0,600,600);
	
	
	 }
}
