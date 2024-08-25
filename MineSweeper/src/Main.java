import java.util.Random;
import java.util.Scanner;

public class Main {
    public String[][] field;
    public String[][] view;
    int numberOfMines;

    Main(int rows, int cols, int numberOfMines) {
        field = new String[rows][cols];
        view = new String[rows][cols];
        this.numberOfMines = numberOfMines;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter the number of rows (between 3 and 10): ");
        int rows = scanner.nextInt();
        while (rows < 3 || rows > 10) {
            System.out.println("Invalid input. Please enter a number between 3 and 10.");
            rows = scanner.nextInt();
        }

        System.out.print("Enter the number of columns (between 3 and 10): ");
        int cols = scanner.nextInt();
        while (cols < 3 || cols > 10) {
            System.out.println("Invalid input. Please enter a number between 3 and 10.");
            cols = scanner.nextInt();
        }

        int maxMines = (rows * cols) - 1;
        maxMines = Math.min(maxMines, (rows * cols) / 2);
        maxMines = Math.max(maxMines, 5);

        System.out.print("Enter the number of mines (between 5 and " + maxMines + "): ");
        int numberOfMines = scanner.nextInt();
        while (numberOfMines < 5 || numberOfMines > maxMines) {
            System.out.println("Invalid input. Please enter a number between 5 and " + maxMines + ".");
            numberOfMines = scanner.nextInt();
        }

        Main game = new Main(rows, cols, numberOfMines);
        int input1;
        int input2;
        System.out.println("Enter column number between 0-" + (cols - 1));
        input1 = scanner.nextInt();

        System.out.println("Enter row number between 0-" + (rows - 1));
        input2 = scanner.nextInt();

        game.processField(input2, input1);
        game.printField();

        game.reveal(input2, input1);

        game.printView();

        do {

            System.out.println("Enter column number between 0-" + (cols - 1));
            input1 = scanner.nextInt();

            System.out.println("Enter row number between 0-" + (rows - 1));
            input2 = scanner.nextInt();
            game.reveal(input2, input1);
            game.printView();
            game.printField();
            game.countDiscoveredFields();
        } while (!game.field[input2][input1].equals("X"));

    }

    public void printField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printView() {
        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[0].length; j++) {
                System.out.print(view[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void processField(int x, int y) {
        Random rand = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numberOfMines) {
            int f = rand.nextInt(field.length);
            int g = rand.nextInt(field[0].length);
            if ((f != x || g != y) && field[f][g] == null) {
                field[f][g] = "X";
                minesPlaced++;
            }
        }


        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == null) {
                    field[i][j] = "0";
                }
            }
        }

        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[0].length; j++) {
                view[i][j] = "*";
            }
        }
        field[x][y] = "0";
        view[x][y] = "0";
    }

    public void countDiscoveredFields() {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (!view[i][j].equals("*")) {
                    count++;
                }
            }
        }

        int totalCells = field.length * field[0].length;
        if (count == totalCells - numberOfMines) {
            System.out.println("Congratulations! You've won!");
            System.exit(0);
        }
    }

    public void reveal(int x, int y) {
        int count = 0;

        if (x > 0) {
            if (y > 0 && field[x - 1][y - 1].equals("X")) count++;
            if (field[x - 1][y].equals("X")) count++;
            if (y < field[0].length - 1 && field[x - 1][y + 1].equals("X")) count++;
        }


        if (y > 0 && field[x][y - 1].equals("X")) count++;
        if (y < field[0].length - 1 && field[x][y + 1].equals("X")) count++;

        if (x < field.length - 1) {
            if (y > 0 && field[x + 1][y - 1].equals("X")) count++;
            if (field[x + 1][y].equals("X")) count++;
            if (y < field[0].length - 1 && field[x + 1][y + 1].equals("X")) count++;
        }

        view[x][y] = Integer.toString(count);
    }}