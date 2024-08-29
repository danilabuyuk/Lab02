package maze;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MazeGame
{
    //FIELDS
    public final static int HEIGHT = 19;
    public final static int WIDTH = 39;
    private final static int ROW = 0;
    private final static int COL = 1;
    private Scanner playerInput;
    private boolean[][] blocked = new boolean[HEIGHT][WIDTH];
    private boolean[][] visited = new boolean[HEIGHT][WIDTH];
    private int[] player = new int[2];
    private int[] goal = new int[2];
    private int[] start = new int[2];
    //FIELDS

    //CONSTRUCTORS
    public MazeGame(String mazeFile, Scanner playerInput) throws FileNotFoundException{
        this.playerInput = playerInput;
        loadMaze(mazeFile);
    }

    public MazeGame(String mazeFile) throws FileNotFoundException{  
        this.playerInput = new Scanner(System.in); 
        new MazeGame(mazeFile, playerInput);
    }
    //CONSTRUCTORS

    //METHODS
    private boolean[][] copyTwoDimBoolArray(boolean[][] input) {
        boolean[][] output = new boolean[HEIGHT][WIDTH];
            for(int i = 0; i < HEIGHT; i++) {
                for(int j = 0; j < WIDTH; j++) {
                    output[i][j] = input[i][j];
                }
            }
        return output;
    }
    private void prompt() {
        printMaze();
        System.out.print("Enter your move (up, down, left, right, or q to quit): ");
    }
    private boolean playerAtGoal() {
        return player[ROW] == goal[ROW] && player[COL] == goal[COL];
    }
    private boolean valid(int row, int col) {
        return blocked[row][col] && 
        row < HEIGHT && 
        col < WIDTH &&
        row >= 0 &&
        col >= 0; 
    }
    private void visit(int row, int col) {
        visited[row][col] = true;
    }
    private void loadMaze(String mazeFile) throws FileNotFoundException {
    	blocked = new boolean[HEIGHT][WIDTH];
        visited = new boolean[HEIGHT][WIDTH];
        player = new int[2];
        start = new int[2];
        goal = new int[2];
    	
     	File file = new File(mazeFile);
     	Scanner mapFile = new Scanner(new FileReader(file));

    	while (mapFile.hasNext())
    	{
            for (int i = 0; i < HEIGHT; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
    				switch (mapFile.next()) {
                        case "1":
                            blocked[i][j] = true;
                            break;
                        case "S":
                            blocked[i][j] = false;
                            setStartRow(i);
                            setStartCol(j);
                            setPlayerRow(i);
                            setPlayerCol(j);
                            break;
                        case "G":
                            blocked[i][j] = false;
                            setGoalRow(i);
                            setGoalCol(j);
                            break;
                        case "0":
                            blocked[i][j] = false;
                            break;
                        default:
                            break;
                    }				
                }
            }
        }
        mapFile.close();
    }
    public void printMaze()
    {  	
    	System.out.println("*---------------------------------------*");
    	
    	for (int i = 0; i < HEIGHT; i++)
    	{
            System.out.print("|");
            for (int j = 0; j < WIDTH; j++)
            {
                if (i == player[ROW] && j == player[COL])
                {
                    System.out.print("@");
                    visited[i][j] = true;
                }
                else if (i == getStartRow() && j == getStartCol())
                {
                    System.out.print("S");
                }
                else if (i == getGoalRow() && j == getGoalCol())
                {
                    System.out.print("G");
                }
                else if (visited[i][j] == true && !(i == getStartRow() && j == getStartCol()))
                {
                    System.out.print(".");
                }
                else if (blocked[i][j] == true)
                {
                    System.out.print("X");
                }
                else if (blocked[i][j] == false)
                {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
    	System.out.println("*---------------------------------------*");
    }
    private boolean makeMove(String move)
    {
        char e = move.toLowerCase().charAt(0);
        switch (e) {
            case 'u':
                if(valid(player[0] - 1, player[1])) {
                    player[ROW] -= 1;
                    visit(player[0] - 1, player[1]);
                    if(playerAtGoal()) {
                        return true;
                    }
                    return false;
            }
                break;
            case 'd':
                if(valid(player[0] + 1, player[1])) {
                    player[ROW] += 1;
                    visit(player[0] + 1, player[1]);
                    if(playerAtGoal()) {
                        return true;
                    }
                    return false;
            }
                break;
            case 'l':
                if(valid(player[0], player[1] - 1)) {
                    player[COL] -= 1;
                    visit(player[0], player[1] - 1);
                    if(playerAtGoal()) {
                        return true;
                    }
                    return false;
                }
                break;
            case 'r':
                if(valid(player[0], player[1] + 1)) {
                    player[COL] += 1;
                    visit(player[0], player[1] + 1);
                    if(playerAtGoal()) {
                        return true;
                    }
                    return false;
                }
                break;
            case 'q':
                return true;
            default:
                break;
        }   	    	
        return false;
    } 
    public void playGame()
    {
        String input;
        do {
            prompt();
            input = playerInput.next();
        }
        while(makeMove(input));
        if(playerAtGoal()) {
            System.out.println("You Won!");
        }
        else {
            System.out.println("Goodbye!");
        }
    }
    //METHODS

    //GETTERS
    public Scanner getPlayerInput() {
        return this.playerInput;
    }
    public int getPlayerCol() {
        return this.player[COL];
    }
    public int getPlayerRow() {
        return this.player[ROW];
    }
    public int getGoalCol() {
        return this.goal[COL];
    }
    public int getGoalRow() {
        return this.goal[ROW];
    }
    public int getStartCol() {
        return this.start[COL];
    }
    public int getStartRow() {
        return this.start[ROW];
    }
    public boolean[][] getBlocked() {
        return copyTwoDimBoolArray(blocked);
    }
    public boolean[][] getVisited() {
        return copyTwoDimBoolArray(visited);
    }
    //GETTERS

    //SETTERS
    public void setPlayerInput(Scanner input) {
        playerInput = input;
    }
    public void setPlayerCol(int input) {
        if(input <= WIDTH && input >= 0) {
            player[COL] = input;
        }
    }
    public void setPlayerRow(int input) {
        if(input <= HEIGHT && input >= 0) {
            player[ROW] = input;
        }
    }
    public void setGoalCol(int input) {
        if(input <= WIDTH && input >= 0) {
            goal[COL] = input;
        }
    }
    public void setGoalRow(int input) {
        if(input <= HEIGHT && input >= 0) {
            goal[ROW] = input;
        }
    }
    public void setStartCol(int input) {
        if(input <= WIDTH && input >= 0) {
            start[COL] = input;
        }
    }
    public void setStartRow(int input) {
        if(input <= HEIGHT && input >= 0) {
            start[ROW] = input;
        }
    }
    public void setBlocked(boolean[][] input) {
        blocked = copyTwoDimBoolArray(input);
    }
    public void setVisited(boolean[][] input) {
        visited = copyTwoDimBoolArray(input);
    }
    //SETTERS
}    