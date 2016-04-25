package games.pong;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utils.frames.BaseFrame;

public class SettingsFrame extends BaseFrame{
	
	private static final long serialVersionUID = 1L;
	protected final Pong pong;
	protected final Image scoreImg = new ImageIcon(getClass().getResource("/Pong/maxscore.png")).getImage(),
			plus = new ImageIcon(getClass().getResource("/Pong/+.png")).getImage(),
			subt = new ImageIcon(getClass().getResource("/Pong/-.png")).getImage(),
			ballImg = new ImageIcon(getClass().getResource("/Pong/gamespeed.png")).getImage();
	
	public SettingsFrame(Pong pong) {
		this.pong = pong;
		final JLabel score = new JLabel() , scoreNumb = new JLabel() , subtScore = new JLabel() , addScore = new JLabel(),
				speed = new JLabel() , speedNumb = new JLabel() , subtSpeed = new JLabel() , addSpeed = new JLabel();
		int[][] bounds=
			{{10,11,278,70, 345,11,75,49, 298,11,37,49, 430,11,47,49},{10,92,320,70, 375,92,52,49, 335,92,40,49, 430,92,47,49}};
		setupLabels(score,scoreNumb,subtScore,addScore,true,bounds[0]);
		setupLabels(speed,speedNumb,subtSpeed,addSpeed,false,bounds[1]);
		createLayout();
	}
	
	public void setupLabels(JLabel lab1, JLabel lab2, JLabel lab3, JLabel lab4, boolean score, int[] bnds){
		lab1.setIcon(new ImageIcon(score ? scoreImg : ballImg)); lab2.setIcon(getImage(get(score)));
		lab3.setIcon(new ImageIcon(subt)); lab4.setIcon(new ImageIcon(plus));
		lab1.setBounds(bnds[0], bnds[1], bnds[2], bnds[3]); lab2.setBounds(bnds[4], bnds[5], bnds[6], bnds[7]);
		lab3.setBounds(bnds[8], bnds[9], bnds[10], bnds[11]); lab4.setBounds(bnds[12], bnds[13], bnds[14], bnds[15]);
		getContentPane().add(lab1); getContentPane().add(lab2); getContentPane().add(lab3); getContentPane().add(lab4);
		lab3.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent arg0) { 
				if(get(score) > 1) 
					set(score, get(score)-1, lab2);
			}});
		lab4.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent arg0) { 
				if(get(score) < (score ? 25 : 5)) 
					set(score, get(score)+1, lab2);
			}});	
	}
	
	public void set(boolean score, int val, JLabel lab){
		if(score) pong.maxScore = val;
		else pong.gameSpeed = val;
		lab.setIcon(getImage(get(score)));
	}
	
	public int get(boolean score){
		if(score) return pong.maxScore;
		else return pong.gameSpeed;
	}

	public ImageIcon getImage(final int numb){
		return new ImageIcon(getClass().getResource("/numbers/" + numb + ".png"));
	}	
}