package assignment.drawing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int centerX = getWidth() / 2;
		int centerY = getWidth() / 2;
		int radius = 50;
		
		g.setColor(Color.BLACK);
		g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Circle Drawing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Drawing());
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}
