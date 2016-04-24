package utils.frames;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class SubFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public SubFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setSize(new Dimension(600, 600));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public SubFrame(int width, int height) {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void hideMouse(){
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			    cursorImg, new Point(0, 0), "blank cursor");
		getContentPane().setCursor(blankCursor);
	}
	
	@Override
	public String getName(){
		System.out.println("Error -> SubFrames don't have names.");
		return "ERROR";
	}
	
	@Override
	public void setName(String str){
		System.out.println("Error -> You can't set the name of SubFrames.");
	}
	
}
