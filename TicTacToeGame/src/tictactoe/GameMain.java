package tictactoe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The `GameMain` class represents the main UI and logic for the Tic-Tac-Toe game.
 * It extends `JPanel` to create a graphical user interface with mouse input handling.
 */
public class GameMain extends JPanel implements MouseListener {
    // Constants for the Tic-Tac-Toe game
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    // Game components
    private Board board;
    private enum GameState {
        Playing, Draw, Cross_won, Nought_won
    }
    private GameState currentState;
    private Player currentPlayer;
    private JLabel statusBar;

    // Store the selected row and column
    private int rowSelected, colSelected;

    // Constructor for the Tic-Tac-Toe game UI
    public GameMain() {
        addMouseListener(this);

        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        board = new Board();
        initGame(); // Initialize the game state
    }

    // Main method to start the Tic-Tac-Toe game
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.add(new GameMain());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    // Event handler for mouse clicks
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        rowSelected = mouseY / CELL_SIZE;
        colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            if (isValidMove(rowSelected, colSelected)) {
                // Make a move and update the game state
                board.cells[rowSelected][colSelected].content = currentPlayer;
                updateGame(currentPlayer, rowSelected, colSelected);
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            } else {
                // Invalid move, handle it (e.g., display a message)
                statusBar.setText("Invalid Move! Try again.");
                return;
            }
        } else {
            // Game over, restart
            initGame();
        }

        repaint(); // Redraw the UI
    }

    // Check if the move is valid (within the board and the selected cell is empty)
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS &&
               board.cells[row][col].content == Player.Empty;
    }

    // Custom painting codes for the Tic-Tac-Toe game UI
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        board.paint(g);

        // Display the current player's turn in the status bar
        statusBar.setText(currentPlayer == Player.Cross ? "'X' Turn" : "'O' Turn");

        // Additional painting codes for different game states
        if (currentState == GameState.Playing) {
            // Additional codes for playing state (if needed)
        } else if (currentState == GameState.Draw) {
            // Display draw message in the status bar
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            // Display 'X' won message in the status bar
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            // Display 'O' won message in the status bar
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    // Update the game state after each move
    private void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) {
            currentState = GameState.Draw;
        }
    }

    // Initialize the game state at the beginning or after a game ends
    private void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Player.Empty;
            }
        }
        currentState = GameState.Playing;
        currentPlayer = Player.Cross;
        statusBar.setText("Game started. 'X' goes first.");
    }

    // Auto-generated, events not used
    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }
}

