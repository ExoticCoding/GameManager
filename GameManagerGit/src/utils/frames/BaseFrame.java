package utils.frames;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import games.ActionReader;
import utils.Utils;

public class BaseFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private final Image closeImg = new ImageIcon(getClass().getResource("/close.png")).getImage();
	
	public BaseFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setResizable(false);
		setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		addKeyListener(new ActionReader(){
			@Override
			public void keyPressed(KeyEvent ev) {
				if(ev.getKeyCode() == KeyEvent.VK_ESCAPE) close();
			}
		});
		addClose();
	}
	
	public void addClose(){
		JLabel closeLabel = new JLabel();
		closeLabel.addMouseListener(new ActionReader() {
			@Override
			public void mouseClicked(MouseEvent ev){
				close();
			}
		});
		closeLabel.setBounds(565, 11, 25, 25);
		closeLabel.setIcon(new ImageIcon(closeImg));
		getContentPane().add(closeLabel);
		
	}
	
	public void createLayout(){
		JLabel layout = new JLabel();
		layout.setBounds(0, 0, 600, 400);
		layout.setIcon(Utils.getBlack(600, 400));
		getContentPane().add(layout);
	}
	
	public void open(){
		setVisible(true);
	}
	
	public void close(){
		setVisible(false);
	}
	
}
