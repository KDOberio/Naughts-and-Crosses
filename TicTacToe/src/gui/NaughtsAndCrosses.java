package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


@SuppressWarnings("serial")
public class NaughtsAndCrosses extends JPanel{
	
	private static final int ROWS = 3;
	private static final int COLS = 3;
	
	private enum GameStatus { NAUGHT_WON, CROSS_WON, IN_PROGRESS, DRAW };
	
	// Values to be used for button text within a board. 
	private static String NAUGHT = "0";
	private static String CROSS = "X";
	private static String BLANK = " ";
	private static String BEGIN_GAME_MESSAGE = "Start new game. Cross to start.";
	
	// A 2 dimensional array of JButton objects. Each button is intended to 
	// display the text NAUGHT, CROSS or BLANK (see above). 
	private JButton[][] _board;
	private boolean _naughtsTurn = false;
	private JButton _newGame;
	private JLabel _gameStatus;
	
	public NaughtsAndCrosses() {
		// Build the GUI.
		buildGUI();
		
		// TO DO: set up event handlers.
		_newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_gameStatus.setText(BEGIN_GAME_MESSAGE);
				setBoardAll(BLANK);
			}
		});
		
		for(JButton[] row: _board) {
			for(JButton button: row) {
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!(button.getText().equals(BLANK)) | (getGameStatus() != GameStatus.IN_PROGRESS)) {
							return;
						}
						
						if (_naughtsTurn) {
							button.setText(NAUGHT);
						} else {
							button.setText(CROSS);
						}
						
						switch(getGameStatus()) {
						case NAUGHT_WON:
							_gameStatus.setText("NAUGHTS WON!");
							break;
						case CROSS_WON:
							_gameStatus.setText("CROSSES WON!");
							break;
						case DRAW:
							_gameStatus.setText("Game drawn. Click 'New Game' to start new game");
							break;
							
						default:
							_gameStatus.setText("Game in progress.");
							_naughtsTurn = !(_naughtsTurn);
						}
					}
				});
			}
		}
	}
	
	private void buildGUI() {
		// Initialise the board.
		_board = new JButton[ROWS][COLS];
		Panel boardPanel = new Panel(new GridLayout(3, 3));
		
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				_board[row][col] = new JButton(BLANK);
				boardPanel.add(_board[row][col]);
			}
		}

		// TO DO: create other GUI components and lay them out as appropriate.
		// Note that _board only stores 9 JButton objects using a 3x3 array. 
		// The buttons still need to be added to the GUI - use GridLayout to 
		// add and layout the JButtons on a JPanel.
		_newGame = new JButton("New Game");
		_gameStatus = new JLabel(BEGIN_GAME_MESSAGE, SwingConstants.CENTER);
		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(_newGame, BorderLayout.SOUTH);
		add(_gameStatus, BorderLayout.NORTH);


	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TO DO: Create and configure a JFrame to display the GUI.
				// Make class NaughtsAndCrosses extend JPanel so that a 
				// NaughtsAndCrosses object can be added to the JFrame.
				JFrame gui = new JFrame("Naughts and Crosses");
				gui.add(new NaughtsAndCrosses());
				
				gui.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
				
				gui.pack();
				gui.setLocationRelativeTo(null);
				gui.setVisible(true);
			}
		});
	}

	// Helper method to determine the status of a Naughts and Crosses game. 
	// This method processes the 2 dimensional array of JButton objects, and 
	// uses the text value (NAUGHT, CROSS or BLANK) of each JButton to 
	// calculate the game's state of play.
	private GameStatus getGameStatus() {
		GameStatus status = GameStatus.DRAW;
		
		List<String> lines = new ArrayList<String>();
		
		// Top row
		lines.add(_board[0][0].getText() + _board[0][1].getText() + _board[0][2].getText());
		
		// Middle row
		lines.add(_board[1][0].getText() + _board[1][1].getText() + _board[1][2].getText());
		
		// Bottom row
		lines.add(_board[2][0].getText() + _board[2][1].getText() + _board[2][2].getText());
		
		// Left col
		lines.add(_board[0][0].getText() + _board[1][0].getText() + _board[2][0].getText());
		
		// Middle col
		lines.add(_board[0][1].getText() + _board[1][1].getText() + _board[2][1].getText());
		
		// Right col
		lines.add(_board[0][2].getText() + _board[1][2].getText() + _board[2][2].getText());
		
		// Diagonals
		lines.add(_board[0][0].getText() + _board[1][1].getText() + _board[2][2].getText());
		lines.add(_board[0][2].getText() + _board[1][1].getText() + _board[2][0].getText());
		
		// Check to see if there's any cell without a NAUGHT or a CROSS.
		for(String line : lines) {
			System.out.println("Looking at line: |" + line + "|");
			if(line.contains(BLANK)) {
				status = GameStatus.IN_PROGRESS;
				System.out.println("In progress");
				break;
			}
		}
		
		// Check to see if there's any line of NAUGHTs or CROSSes.
		if(lines.contains(CROSS + CROSS + CROSS)) {
			status = GameStatus.CROSS_WON; 
		} else if (lines.contains(NAUGHT + NAUGHT + NAUGHT)) {
			status = GameStatus.NAUGHT_WON;
		}
		

		return status;
	}
	
	private void setBoardAll(String changeTo) {
		for(int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				_board[row][col].setText(changeTo);
			}
		}
		
		_naughtsTurn = false;
	}
	
}
