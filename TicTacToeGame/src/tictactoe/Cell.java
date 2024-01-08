package tictactoe;
import java.awt.*;

public class Cell {
    // Enum representing the content of the cell (empty, cross, nought)
    Player content;
    // Row and column of this cell
    int row, col;

    // Constructor to initialize this cell with the specified row and col
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear(); // Initialize the cell content to EMPTY
    }

    // Paint the cell on the graphics canvas based on its content
    public void paint(Graphics g) {
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
        int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;

        if (content == Player.Cross) {
            // If content is Cross, draw a red cross symbol
            graphic2D.setColor(Color.RED);
            int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            graphic2D.drawLine(x1, y1, x2, y2);
            graphic2D.drawLine(x2, y1, x1, y2);
        } else if (content == Player.Nought) {
            // If content is Nought, draw a blue circular symbol
            graphic2D.setColor(Color.BLUE);
            graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
        }
    }

    // Set this cell's content to EMPTY
    public void clear() {
        content = Player.Empty;
    }
}
