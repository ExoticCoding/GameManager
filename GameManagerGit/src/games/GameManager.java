package games;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import games.pong.Pong;
import games.tictactoe.TicTacToe;
import utils.Utils;

public class GameManager{

	public static final GameManager window = new GameManager();
	public static void main(String[] args){}
	
	public final JFrame frame = new JFrame();
	
	private final ImageIcon closeImg = new ImageIcon(getClass().getResource("/close.png")),
	minimizeImg = new ImageIcon(getClass().getResource("/minimize.png")),
	pongImg = new ImageIcon(getClass().getResource("/Pong.png")),
	tttImg = new ImageIcon(getClass().getResource("/ttt.png"));
	
	public GameManager() {
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.addKeyListener(close(false));
		createButtons();
		frame.setVisible(true);
	}
	
	private final void createButtons() {
		JLabel pongLabel=new JLabel(), tttLabel=new JLabel(), closeLabel=new JLabel(), minimize=new JLabel(), layout=new JLabel();
		
		pongLabel.addMouseListener(openPong());
		tttLabel.addMouseListener(openTTT());
		closeLabel.addMouseListener(close(true));
		minimize.addMouseListener(minimize());

		pongLabel.setBounds(10, 11, 176, 70); tttLabel.setBounds(10, 92, 319, 60);
		closeLabel.setBounds(565, 11, 25, 25); minimize.setBounds(530, 11, 25, 25);
		layout.setBounds(0, 0, 600, 400);
		
		pongLabel.setIcon(pongImg); tttLabel.setIcon(tttImg);
		closeLabel.setIcon(closeImg); minimize.setIcon(minimizeImg);
		layout.setIcon(Utils.getBlack(600, 400));
		
		frame.getContentPane().add(pongLabel); frame.getContentPane().add(tttLabel); 
		frame.getContentPane().add(closeLabel); frame.getContentPane().add(minimize);
		frame.getContentPane().add(layout);
	}
	
	private final ActionReader openPong(){
		return new ActionReader() { 
			@Override
			public void mouseClicked(MouseEvent ev){
				Pong.startGame();
			}
		};
	}
	
	private final ActionReader openTTT(){
		return new ActionReader(){
			@Override
			public void mouseClicked(MouseEvent ev){
				TicTacToe.startGame();
			}
		};
	}
	
	private final ActionReader close(boolean click){
		if(!click){
			return new ActionReader(){
				@Override
				public void keyPressed(KeyEvent ev) {
					if(ev.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
				} 
			};
		}
		return new ActionReader(){
			@Override
			public void mouseClicked(MouseEvent ev){
				System.exit(0);
			}
		};
	}
	
	private final ActionReader minimize(){
		return new ActionReader(){
			@Override
			public void mouseClicked(MouseEvent ev){
				frame.setState(Frame.ICONIFIED);
			}
		};
	}
	
}