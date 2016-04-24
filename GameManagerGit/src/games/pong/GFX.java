package games.pong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GFX extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private final Pong pong;
	public GFX(Pong pong){
		this.pong = pong;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		pong.render((Graphics2D) g);
	}
}