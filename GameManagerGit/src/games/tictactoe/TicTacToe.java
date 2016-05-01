package games.tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import games.GameManager;
import utils.Utils;

public final class TicTacToe {
	public static final void startGame() {
		GameManager.window.frame.setVisible(false);
		new TicTacToe();
	}

	private boolean player1 = true;

	private final Font font = new Font("Serif", Font.BOLD | Font.ITALIC, 30);
	private final String[][] board = new String[3][3];
	private final JFrame frame = new JFrame();
	private final JLabel playerLabel = new JLabel("Player 1");
	private final JButton slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9,
			safeClose = new JButton("Close");
	private final List<JButton> slots;

	private TicTacToe() {
		frame.setSize(565, 350);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setUndecorated(true);

		slots = Arrays.asList(slot1 = new JButton(), slot2 = new JButton(), slot3 = new JButton(),
				slot4 = new JButton(), slot5 = new JButton(), slot6 = new JButton(), slot7 = new JButton(),
				slot8 = new JButton(), slot9 = new JButton());

		setupButton(slot1, 0, 0);
		setupButton(slot2, 0, 1);
		setupButton(slot3, 0, 2);
		setupButton(slot4, 1, 0);
		setupButton(slot5, 1, 1);
		setupButton(slot6, 1, 2);
		setupButton(slot7, 2, 0);
		setupButton(slot8, 2, 1);
		setupButton(slot9, 2, 2);

		playerLabel.setBounds(10, 269, 118, 39);
		safeClose.setBounds(380, 269, 175, 39);

		playerLabel.setFont(font);
		safeClose.setFont(font);

		playerLabel.setForeground(Color.RED);
		safeClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				GameManager.window.frame.setVisible(true);
			}
		});
		frame.getContentPane().add(playerLabel);
		frame.getContentPane().add(safeClose);
		JLabel layout = new JLabel();
		layout.setBounds(0, 0, 565, 350);
		layout.setIcon(Utils.getBlack(565, 350));
		frame.getContentPane().add(layout);
		frame.setVisible(true);
	}

	private final void setupButton(JButton button, int row, int col) {
		final int[] bnds = { 11, 97, 183, 10, 195, 380 };
		final Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 30);
		button.addActionListener(buttonCheck(row, col, button));
		button.setBounds(bnds[col + 3], bnds[row], 175, 75);
		button.setFont(font);
		frame.getContentPane().add(button);
	}

	private final ActionListener buttonCheck(int row, int col, JButton button) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (board[row][col] != null)
					return;
				String val = player1 ? "X" : "O";
				board[row][col] = val;
				button.setText(val);
				player1 = !player1;
				boolean full = checkFull(), win = checkSpaces(val);
				if (full && !win) {
					resetGame(null);
					return;
				}
				if (win) {
					resetGame(player1 ? "Player 2" : "Player 1");
					return;
				}
				playerLabel.setText(player1 ? "Player 1" : "Player 2");
			}
		};
	}

	private final void resetGame(String winner) {
		String string = winner == null ? "The game was a draw!" : winner + " has won the game!";
		JOptionPane.showMessageDialog(frame, string, "Game Ended", JOptionPane.DEFAULT_OPTION);
		for (int a = 0; a < 3; a++)
			for (int b = 0; b < 3; b++)
				board[a][b] = null;
		player1 = true;
		playerLabel.setText("Player 1");
		slots.forEach(e -> e.setText(""));
	}

	private final boolean checkFull() {
		for (int a = 0; a < 3; a++)
			for (int b = 0; b < 3; b++)
				if (board[a][b] == null)
					return false;
		return true;
	}

	private final boolean checkSpaces(String value) {
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < 3 && board[b][a] == value; b++)
				if (b == 2)
					return true;
			for (int b = 0; b < 3 && board[a][b] == value; b++)
				if (b == 2)
					return true;
		}
		for (int a = 0; a < 3 && board[a][a] == value; a++)
			if (a == 2)
				return true;
		return (board[0][2] == value && board[1][1] == value && board[2][0] == value);
	}
}