package utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Utils {
	
	private static Image black = new ImageIcon(Utils.class.getResource("/black.png")).getImage();
	
	public static ImageIcon getBlack(int width, int height){
		return new ImageIcon(black.getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}
	
}
