package games;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface Actions extends KeyListener, ActionListener{
	public void keyPressed(KeyEvent arg0);
	public void keyReleased(KeyEvent arg0);
	public void keyTyped(KeyEvent arg0);
	public void actionPerformed(ActionEvent arg0);	
}
