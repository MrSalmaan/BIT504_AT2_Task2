package tictactoe;

import java.awt.*;

public class Board {
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    Cell[][] cells;

    public Board() {
        initializeCells();
    }

    private void initializeCells() {
        cells = new Cell[GameMain.ROWS][GameMain.COLS];
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public boolean isDraw() {
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].content == Player.Empty) {
                    return false; // If any cell is empty, the game is not a draw
                }
            }
        }
        return true; // All cells are filled, indicating a draw
    }

    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        // Check if the player has 3 in that row
        if (cells[playerRow][0].content == thePlayer &&
            cells[playerRow][1].content == thePlayer &&
            cells[playerRow][2].content == thePlayer) {
            return true;
        }

        // Check if the player has 3 in that column
        if (cells[0][playerCol].content == thePlayer &&
            cells[1][playerCol].content == thePlayer &&
            cells[2][playerCol].content == thePlayer) {
            return true;
        }

        // Check 3-in-the-diagonal
        if (playerRow == playerCol) {
            if (cells[0][0].content == thePlayer &&
                cells[1][1].content == thePlayer &&
                cells[2][2].content == thePlayer) {
                return true;
            }
        }

        // Check 3-in-the-opposite-diagonal
        if (playerRow + playerCol == 2) {
            if (cells[0][2].content == thePlayer &&
                cells[1][1].content == thePlayer &&
                cells[2][0].content == thePlayer) {
                return true;
            }
        }

        // No winner yet
        return false;
    }

    public void paint(Graphics g) {
        // Drawing the grid lines
        g.setColor(Color.gray);
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,
                    GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                    GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Drawing the cells
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}
