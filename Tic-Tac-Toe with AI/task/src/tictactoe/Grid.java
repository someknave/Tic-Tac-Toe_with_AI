package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Grid {

    static Cell[] cells = {};
    static int size = 3;
    public static void initialize() {
        cells = new Cell[size * size];
        for (int index = 0; index < size * size; index++) {
            cells[index] = new Cell(index % size + 1, index / size + 1);
        }
    }
    public static Cell[] cellsContaining(char c) {
        ArrayList<Cell> result = new ArrayList<Cell>();
        for (Cell cell : cells) {
            if (cell.contents == c) {
                result.add(cell);
            }
        }
        return result.toArray(Cell[]::new);
    }
    public static char getContents(int i, int j) {
        return cells[size * j + i - size - 1].contents;
    }
    public static void setContents(int i, int j, char c) {
        cells[size * j + i - size - 1].contents = c;
    }
    public static Cell[] getRow(int x) {
        if (x <= 0) {
            return Arrays.stream(cells).filter(cell -> cell.i == cell.j).toArray(Cell[]::new);
        }
        if (x <= size) {
            return Arrays.stream(cells).filter(cell -> cell.i == x).toArray(Cell[]::new);
        }
        if (x <= 2 * size) {
            return Arrays.stream(cells).filter(cell -> cell.j == x - size).toArray(Cell[]::new);
        }
        return Arrays.stream(cells).filter(cell -> cell.i == (size + 1 - cell.j)).toArray(Cell[]::new);
    }
    public static boolean checkWin(char c) {
        boolean win = true;
        for (int r = 0; r<= 2 * size + 1; r++) {
            Cell[] row = getRow(r);
            for (Cell cell : row) {
                if (cell.contents != c) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
            win = true;
        }
        return false;
    }
    public static HashMap<Cell, Integer> moveValues(Main.Player currentPlayer, Main.Player aiPlayer ) {
        HashMap<Cell, Integer> values = new HashMap<>();
        Cell[] cells = cellsContaining(' ');
        if (cells.length == 0) {
            return null;
        }
        for (Cell cell : cells) {
            cell.contents = currentPlayer.c;
            if (checkWin(currentPlayer.c)) {
                if (currentPlayer == aiPlayer) {
                    values.put(cell, 10);
                } else {
                    values.put(cell, -10);
                }
                cell.contents = ' ';
                continue;
            }
            HashMap<Cell, Integer> nextMove = moveValues(currentPlayer.next(), aiPlayer);
            if (nextMove == null) {
                values.put(cell, 0);
                cell.contents = ' ';
                continue;
            }
            int val;
            if (currentPlayer == aiPlayer) {
                val = nextMove.values().stream().min(Integer::compare).get();
            } else {
                val = nextMove.values().stream().max(Integer::compare).get();
            }
            values.put(cell, val);
            cell.contents = ' ';
        }
        return values;
    }

    public static void printGrid() {
        String line = "---------";
        System.out.println(line);
        for (int j = size; j >= 1; j--) {
            System.out.print("| ");
            for (int i = 1; i <= size; i++) {
                System.out.print(getContents(i, j));
                System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println(line);
    }

    public static Cell[] winningCells(Main.Player player) {
        ArrayList<Cell> winning = new ArrayList<>(0);
        for (Cell cell : cellsContaining(' ')) {
            cell.contents = player.c;
            if (checkWin(player.c)) {
                winning.add(cell);
            }
            cell.contents = ' ';
        }

        return  winning.toArray(Cell[]::new);
    }
}
