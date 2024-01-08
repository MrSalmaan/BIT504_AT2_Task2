package tictactoe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Constants for game
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    private Board board;
    private enum GameState {
        Playing, Draw, Cross_won, Nought_won
    }
    private GameState currentState;
    private Player currentPlayer;
    private JLabel statusBar;

    private int rowSelected, colSelected;

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
        initGame();
    }

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

    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        rowSelected = mouseY / CELL_SIZE;
        colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            if (isValidMove(rowSelected, colSelected)) {
                board.cells[rowSelected][colSelected].content = currentPlayer;
                updateGame(currentPlayer, rowSelected, colSelected);
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            } else {
                // Invalid move, handle it (e.g., display a message)
                statusBar.setText("Invalid Move! Try again.");
                return;
            }
        } else {
            initGame();
        }

        repaint();
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS &&
               board.cells[row][col].content == Player.Empty;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        board.paint(g);

        statusBar.setText(currentPlayer == Player.Cross ? "'X' Turn" : "'O' Turn");

        if (currentState == GameState.Playing) {
            // Additional painting codes for playing state
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    private void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) {
            currentState = GameState.Draw;
        }
    }

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

    public void mousePressed(MouseEvent e) {
        // Auto-generated, event not used
    }

    public void mouseReleased(MouseEvent e) {
        // Auto-generated, event not used
    }

    public void mouseEntered(MouseEvent e) {
        // Auto-generated, event not used
    }

    public void mouseExited(MouseEvent e) {
        // Auto-generated, event not used
    }
}

