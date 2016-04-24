package games.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import games.ActionReader;
import games.GameManager;
import utils.frames.GameBase;

public class Pong extends GameBase{
	
	public static void startGame() {
		GameManager.window.frame.setVisible(false);
		new Pong();
	}
	
	protected int maxScore = 1, menuOption = 1, gameSpeed = 1, botCooldown, countdown, rally;
	protected final int width = 1000, height = 600;
	
	private final Timer timer;
	private final GFX gfx;
	private final SettingsFrame settings; 
	private final JFrame frame;
	private final Ball ball;
	private final Paddle player, bot;
	private final List<String> actions = Arrays.asList(
					"UP / W -> Moves paddle up (or previous option on menu)", 
					"DOWN / S -> Moves paddle down (or next option on menu)", 
					"Space -> Pauses game until pressed again.");
	
	private boolean ingame = false, paused = false, up = false, down = false;
	private Paddle winner;
	
	public Pong() {
		frame = createFrame(width, height);
		settings = new SettingsFrame(this);
		frame.addMouseListener(new ActionReader() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				settings.close();
			}
		});
		player = new Paddle(true, this);
		bot = new Paddle(false, this);
		ball = new Ball(this, player, bot);
		timer = new Timer(20, this);
		gfx = new GFX(this);
		
		JLabel layout = new JLabel();
		layout.setBounds(0, 0, 1000, 600);
		frame.add(layout);
		
		frame.add(gfx);
		timer.start();
	}
	
	public void updateBoard(){
		for(int i = 0; i < gameSpeed; i++){
			if(up) player.move(true);
			if(down) player.move(false);
			ball.update();
			updateBot();
		}
	}
	public void updateBot(){
		if(botCooldown > 0) botCooldown--;
		else{
			if(ball.x < width / 2){
				if(bot.y != 225) bot.returnMiddle();
				return;
			}
			if(bot.y + bot.height / 2 + bot.speed < ball.y) bot.move(false);
			else if (bot.y + bot.height / 2 + bot.speed> ball.y+5) bot.move(true);
			botCooldown = 1;
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		if(ingame){
			g.setColor(Color.RED);
			String string = "Rally: " + rally;
			g.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
			if(rally >= 10) g.drawString(string, 155 - (10f * string.length()), 40);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
			g.drawString(String.valueOf(player.score), 225, 75);
			g.drawString(String.valueOf(bot.score), 745, 75);
			g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 60)); 
			ball.render(g);
			player.render(g);
			bot.render(g);
			g.setColor(Color.WHITE);
			if(!paused && countdown < 0) g.drawLine(width / 2 - 15, 0, width / 2 - 15, height);
			if(countdown >= 0){
				g.setColor(Color.RED);
				g.setFont(new Font("Times New Roman", Font.ITALIC, 80));
				if(countdown > 80) g.drawString("3...", width / 2-75, height / 2 - 75);
				else if(countdown > 40) g.drawString("2...", width / 2-75, height / 2 - 75);
				else g.drawString("1...", width / 2-75, height / 2 - 75);
				countdown--;
			}
			if(paused){
				g.setColor(Color.RED);
				g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
				g.drawString("Game Paused", 400, 175);
				g.drawString("Press space to continue.", 340, 225);
			}
		}else{
			g.setFont(new Font("Verdana", Font.BOLD, 50));
			g.setColor(menuOption == 1 ? Color.CYAN : Color.BLUE);
			g.drawString("-> Play game", 20, 50);
			g.setColor(menuOption == 2 ? Color.CYAN : Color.BLUE);
			g.drawString("-> Exit pong", 20, 150);
			g.setColor(menuOption == 3 ? Color.CYAN : Color.BLUE);
			g.drawString("-> Edit settings", 20, 250);
			g.setColor(Color.GRAY);
			g.setFont(new Font("Verdana", Font.ITALIC, 20));
			for(int i = 0; i < actions.size(); i++) g.drawString(actions.get(i), 393, (i * 40) + 500);
			if(winner != null){
				String[] strings = {
						"You have beaten the bot!", "You have been beaten by the bot...", "Congratulations!", "Better luck next time"
				};
				g.setColor(Color.MAGENTA);
				g.setFont(new Font("Times New Roman", Font.ITALIC, 40));
				g.drawString(strings[winner.player ? 0:1], 20, 340);
				g.drawString(strings[winner.player ? 2:3], 20, 380);
			}
		}
		drawBorder(g, width, height);
	}
	public void endGame(Paddle winner){
		ball.resetBall();
		player.y = 225;
		bot.y = 225;
		player.score = 0;
		bot.score = 0;
		paused = false;
		ingame = false;
		this.winner = winner;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gfx.repaint();
		if(!paused && ingame && countdown < 0){
			updateBoard();
		}
	}
	@Override
	public void checkESC(KeyEvent ev, JFrame frame, Timer timer){
		int id = ev.getKeyCode();
		if(id == KeyEvent.VK_ESCAPE) endGame(null);
	}
	public void beginGame(){
		rally = 0;
		ingame = true;
		countdown = 120;
	}
	@Override
	public void keyPressed(KeyEvent ev) {
		int id = ev.getKeyCode();
		if(ingame){
			checkESC(ev, frame, timer);
			if(id == KeyEvent.VK_SPACE) paused = paused || countdown > 0 ? false : true;
			if(id == KeyEvent.VK_UP || id == KeyEvent.VK_W) up = true;
			if(id == KeyEvent.VK_DOWN || id == KeyEvent.VK_S) down = true;
			return;
		}
		if(id == KeyEvent.VK_DOWN || id == KeyEvent.VK_S){
			if(menuOption < 3) menuOption++;
			else menuOption = 1;
		}else if(id == KeyEvent.VK_UP || id == KeyEvent.VK_W){
			if(menuOption > 1) menuOption--;
			else menuOption = 3;
		}else if(id == KeyEvent.VK_ENTER){
			switch(menuOption){
			case 1: beginGame(); return;
			case 2: closeFrame(frame, timer); return;
			case 3: settings.open(); return;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent ev) {
		int id = ev.getKeyCode();
		if(id == KeyEvent.VK_UP || id == KeyEvent.VK_W) up = false;
		if(id == KeyEvent.VK_DOWN || id == KeyEvent.VK_S) down = false;
	}	
}