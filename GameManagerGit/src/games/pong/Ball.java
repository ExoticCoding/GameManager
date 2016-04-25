package games.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Ball{
	
	protected int x, y, width = 20, height = 20, YMotion, XMotion;
	protected Random random = new Random();;
	protected final Pong pong;
	protected final Paddle player, bot;
	
	public Ball(Pong pong, Paddle player, Paddle bot) {
		this.pong = pong;
		this.player = player;
		this.bot = bot;
		resetBall();
	}
	public void resetBall(){
		x = pong.width/2-25;
		y = pong.width/2-250;
		XMotion = -8;
		YMotion = Math.random()*100 < 50 ? -random.nextInt(4)-4 : random.nextInt(4)+4;
	}
	public void update(){
		if(y + YMotion <= 0 || y + YMotion >=580) YMotion = -YMotion;
		y += YMotion; 
		x += XMotion;
		switch(checkCollision(player)){
		case 2: score(bot);
		case 1: hit(true);
		}
		switch(checkCollision(bot)){
		case 2: score(player);
		case 1: hit(false);
		}
	}
	public void hit(boolean player){
		pong.rally++;
		XMotion = player ? 8 : -8;
		YMotion = Math.random()*100 < 50 ? -random.nextInt(4)-4 : random.nextInt(4)+4;
	}
	public int checkCollision(Paddle paddle){
		if (x < paddle.x + paddle.width && x + width > paddle.x && y < paddle.y + paddle.height && y + height > paddle.y) return 1;
		else if ((paddle.x > x && paddle.player) || (paddle.x < x - width && !(paddle.player))) return 2;
		else return 0;
	}
	public void score(Paddle paddle){
		resetBall();
		bot.y = 225; player.y = 225;
		pong.rally = 0;
		paddle.score++;
		if(paddle.score >= pong.maxScore) pong.endGame(paddle);
	}
	public void render(Graphics2D g){
		g.setColor(Color.BLUE); g.fillOval(x, y, width, height);
	}
}