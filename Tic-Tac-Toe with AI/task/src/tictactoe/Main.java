package tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;




public class Main {
    static Scanner input = new Scanner(System.in);
    static Player player = Player.X;
    static PlayerMode xMode = PlayerMode.USER;
    static PlayerMode oMode = PlayerMode.USER;
    static Random rd = new Random();
    enum Player {
        X('X'),
        O('O');

        public char c;

        Player(char c) {
            this.c = c;
        }
        public Player next() {
            if (this == X) return O;
            return X;
        }
    }
    enum PlayerMode {
        EASY("easy", new EasyMove()),
        MEDIUM("medium", new MediumMove()),
        HARD("hard", new HardMove()),
        USER("user", new UserMove());

        public String name;
        public PlayerMove moveType;

        PlayerMode(String user, PlayerMove moveType) {
            this.name = user;
            this.moveType = moveType;
        }
        public static PlayerMode getMode(String name) {
            for (PlayerMode mode : PlayerMode.values()) {
                if (mode.name.equals(name)) {
                    return mode;
                }
            }
            return null;
        }
        public static boolean isMode(String name) {
            return getMode(name) != null;
        }
    }
    public interface PlayerMove
    {
        public void move();
    }
    public static class UserMove implements PlayerMove {
        @Override
        public void move() {
            playerMove();
        }
    }
    public static class EasyMove implements PlayerMove {
        @Override
        public void move() {
            easyMove();
        }
    }
    public static class MediumMove implements PlayerMove {
        @Override
        public void move() {
            mediumMove();
        }
    }
    public static class HardMove implements PlayerMove {
        @Override
        public void move() {
            hardMove();
        }
    }

    public static void main(String[] args) {
        while(menuLoop()) {
            Grid.size = 3;
            Grid.initialize();
            Grid.printGrid();
            while (checkStatus()) {
                if (player == Player.O) {
                    oMode.moveType.move();
                } else {
                    xMode.moveType.move();
                }
                player = player.next();
                Grid.printGrid();
            }
        }


    }
    public static boolean menuLoop() {
        System.out.println("Input command");
        String[] args = input.nextLine().split(" ");
        if ("exit".equals(args[0])) {
            return false;
        }
        if (!"start".equals(args[0])) {
            System.out.println("Unknown command");
            return menuLoop();
        }
        if (args.length != 3) {
            System.out.println("Bad parameters!");
            return menuLoop();
        }
        if (PlayerMode.isMode(args[1]) && PlayerMode.isMode(args[2])) {
            xMode = PlayerMode.getMode(args[1]);
            oMode = PlayerMode.getMode(args[2]);
            player = Player.X;
            return true;
        }
        System.out.println("Bad parameters!");
        return menuLoop();
    }
    public static void easyMove() {
        System.out.println("Making move level \"easy\"");
        Cell[] emptyCells = Grid.cellsContaining(' ');
        Cell randomCell = emptyCells[rd.nextInt(emptyCells.length)];
        randomCell.contents = player.c;
    }
    public static void mediumMove() {
        System.out.println("Making move level \"medium\"");
        Cell[] choices = Grid.winningCells(player);
        if (choices.length == 0) {
            choices = Grid.winningCells(player.next());
            if (choices.length == 0) {
                choices = Grid.cellsContaining(' ');
            }
        }
        Cell randomCell = choices[rd.nextInt(choices.length)];
        randomCell.contents = player.c;
    }
    public static void hardMove() {
        System.out.println("Making move level \"hard\"");
        HashMap<Cell, Integer> values = Grid.moveValues(player, player);
        int max = values.values().stream().max(Integer::compare).get();
        ArrayList<Cell> choices = new ArrayList<>(0);
        for (Cell cell : values.keySet()) {
            if (values.get(cell) == max) {
                choices.add(cell);
            }
        }
        Cell randomCell = choices.get(rd.nextInt(choices.size()));
        randomCell.contents = player.c;

    }
    public static void playerMove() {
        int[] move = null;
        while (move == null) {
            move = checkMove();
            if (move != null) {
                makeMove(move[0], move[1]);
            }
        }
    }
    public static int[] checkMove() {
        System.out.println("Enter the coordinates:");
        int i;
        int j;
        String[] s1 = input.nextLine().split(" ");
        try {
            i = Integer.parseInt(s1[0]);
            j = Integer.parseInt(s1[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("You should enter numbers!");
            return null;
        }
        if (Grid.size < i || i < 1 || Grid.size < j || j < 1) {
            System.out.println("Coordinates should be from 1 to 3!");
            return null;
        }
        if (Grid.getContents(i, j) != ' ') {
            System.out.println("This cell is occupied! Choose another one!");
            return null;
        }
        return new int[]{i, j};
    }
    public static void makeMove(int i, int j) {
        Grid.setContents(i, j, player.c);
    }
    public static Boolean checkStatus() {
        int countX = Grid.cellsContaining('X').length;
        int countO = Grid.cellsContaining('O').length;
        int countempty = Grid.cellsContaining(' ').length;
        if (countempty + countO + countX != Grid.cells.length) {
            System.out.println("Impossible: invalid contents");
            return false;
        }
        if (Math.abs(countX - countO) >= 2) {
            System.out.println("Impossible: turn mismatch");
            return false;
        }
        boolean xWin = Grid.checkWin('X');
        boolean oWin =  Grid.checkWin('O');
        if (xWin && oWin) {
            System.out.println("Impossible: both players won");
            return false;
        }
        if (xWin) {
            System.out.println("X wins");
            return false;
        }
        if (oWin) {
            System.out.println("O wins");
            return false;
        }
        if (countempty == 0) {
            System.out.println("Draw");
            return false;
        }
        return true;
    }
}

