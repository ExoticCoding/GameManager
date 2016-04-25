package games.pong;

import java.awt.Color;
import java.awt.Font;
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
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, pong.width, pong.height);
		if(pong.ingame){
			drawInGame(g2);
		}else{
			drawPreGame(g2);
		}
		pong.drawBorder(g2, pong.width, pong.height);
	}
	
	private final void drawPreGame(Graphics2D g){
		g.setFont(new Font("Verdana", Font.BOLD, 50));
		g.setColor(pong.menuOption == 1 ? Color.CYAN : Color.BLUE);
		g.drawString("-> Play game", 20, 50);
		g.setColor(pong.menuOption == 2 ? Color.CYAN : Color.BLUE);
		g.drawString("-> Exit pong", 20, 150);
		g.setColor(pong.menuOption == 3 ? Color.CYAN : Color.BLUE);
		g.drawString("-> Edit settings", 20, 250);
		g.setColor(Color.GRAY);
		g.setFont(new Font("Verdana", Font.ITALIC, 20));
		for(int i = 0; i < pong.actions.size(); i++) g.drawString(pong.actions.get(i), 393, (i * 40) + 450);
		if(pong.winner != null){
			String[] strings = {
					"You have beaten the bot!", "You have been beaten by the bot...", "Congratulations!", "Better luck next time"
			};
			g.setColor(Color.MAGENTA);
			g.setFont(new Font("Times New Roman", Font.ITALIC, 40));
			g.drawString(strings[pong.winner.player ? 0:1], 20, 340);
			g.drawString(strings[pong.winner.player ? 2:3], 20, 380);
		}
	}
	
	private final void drawInGame(Graphics2D g){
		g.setColor(Color.RED);
		String string = "Rally: " + pong.rally;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
		if(pong.rally >= 10) g.drawString(string, 155 - (10f * string.length()), 40);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
		g.drawString(String.valueOf(pong.player.score), 225, 75);
		g.drawString(String.valueOf(pong.bot.score), 745, 75);
		g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 60)); 
		pong.ball.render(g);
		pong.player.render(g);
		pong.bot.render(g);
		g.setColor(Color.WHITE);
		if(!pong.paused && pong.countdown < 0) g.drawLine(pong.width / 2 - 15, 0, pong.width / 2 - 15, pong.height);
		if(pong.countdown >= 0){
			g.setColor(Color.RED);
			g.setFont(new Font("Times New Roman", Font.ITALIC, 80));
			if(pong.countdown > 80) g.drawString("3...", pong.width / 2-75, pong.height / 2 - 75);
			else if(pong.countdown > 40) g.drawString("2...", pong.width / 2-75, pong.height / 2 - 75);
			else g.drawString("1...", pong.width / 2-75, pong.height / 2 - 75);
			pong.countdown--;
		}
		if(pong.paused){
			g.setColor(Color.RED);
			g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
			g.drawString("Game Paused", 400, 175);
			g.drawString("Press space to continue.", 340, 225);
		}
	}
	
}