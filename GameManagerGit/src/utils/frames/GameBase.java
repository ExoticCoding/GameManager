package utils.frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import games.ActionReader;
import games.GameManager;

public class GameBase extends ActionReader{
	public SubFrame createFrame(int width, int height){
		SubFrame frame = new SubFrame(width, height);
		frame.addKeyListener(this);
		frame.hideMouse();
		return frame;
	}
	public SubFrame createFrame(){
		SubFrame frame = new SubFrame();
		frame.addKeyListener(this);
		frame.hideMouse();
		return frame;
	}
	public void drawBorder(Graphics2D g, int width, int height){
		g.setStroke(new BasicStroke(5f));
		g.setColor(Color.RED);
		g.drawRect(0, 0, width, height);
	}
	public void closeFrame(JFrame frame, Timer timer){
		timer.stop();
		frame.dispose();
		GameManager.window.frame.setVisible(true);
	}
	public void checkESC(KeyEvent ev, JFrame frame, Timer timer){
		int id = ev.getKeyCode();
		if(id == KeyEvent.VK_ESCAPE) closeFrame(frame, timer);
	}
}