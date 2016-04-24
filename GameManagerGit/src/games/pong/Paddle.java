package games.pong;

import java.awt.Color;
import java.awt.Graphics2D;

public class Paddle{
	
	protected final boolean player;
	protected final Pong pong;
	protected final int width = 50, height = 150, speed = 10;
	protected int score, x, y;
	
	public Paddle(final boolean player, Pong pong) {
		this.player = player;
		this.pong = pong;
		this.x = player ? 0 : 950;
		this.y = 225;
	}
	
	public void move(boolean up){
		if(up) y = y - speed > 0 ? y -= speed : 0;
		else y = y + speed < 450 ? y += speed : 450;
	}
	
	public void returnMiddle(){
		for(int i = 3; i > 0; i--){
			if(y > 225) y -= i;
			if(y < 225) y += i;
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.BLUE); g.fillRect(x, y, width, height);
	}
}