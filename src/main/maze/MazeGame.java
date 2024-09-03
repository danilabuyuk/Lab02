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
    private boolean[][] blocked;
    private boolean[][] visited;
    private int[] player;
    private int[] goal;
    private int[] start;
    String output = "";
    String temp = "";
    //FIELDS

    //CONSTRUCTORS
    public MazeGame(String mazeFile, Scanner playerInput) throws FileNotFoundException{
        this.playerInput = playerInput;
        loadMaze(mazeFile);
    }

    public MazeGame(String mazeFile) throws FileNotFoundException{  
        playerInput = new Scanner(System.in); 
        new MazeGame(mazeFile, playerInput);
        loadMaze(mazeFile);
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
        return player[0] == goal[0] && player[1] == goal[1];
    }
    private boolean valid(int row, int col) {
        return row < HEIGHT && 
        col < WIDTH &&
        row >= 0 &&
        col >= 0 && 
        !blocked[row][col]; 
    }
    private void visit(int row, int col) {
        this.visited[row][col] = true;
    }
    private void loadMaze(String mazeFile) throws FileNotFoundException {
    	blocked = new boolean[HEIGHT][WIDTH];
        visited = new boolean[HEIGHT][WIDTH];
        player = new int[2];
        start = new int[2];
        goal = new int[2];
    	
     	File file = new File(mazeFile);
     	Scanner mapFile = new Scanner(new FileReader(file));
            for (int i = 0; i < HEIGHT; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    temp = mapFile.next();
                    switch (temp) {
                        case "1":
                            setBlockedVal(true, i, j);
                            //System.out.print("1 ");
                            break;
                        case "S":
                            setBlockedVal(false, i, j);
                            setStartRow(i);
                            setStartCol(j);
                            setPlayerRow(i);
                            setPlayerCol(j);
                            //System.out.println(getStartRow());
                            //System.out.println(getStartCol());
                            //System.out.println(getPlayerRow());
                            //System.out.println(getPlayerCol());
                            //System.out.print("S\n");
                            break;
                        case "G":
                            setBlockedVal(false, i, j);
                            setGoalRow(i);
                            setGoalCol(j);
                            //System.out.println(getGoalRow());
                            //System.out.println(getGoalCol());
                            //System.out.print("G\n");
                            break;
                        case "0":
                            setBlockedVal(false, i ,j);
                            //System.out.print("0 ");
                            break;
                        default:
                            break;
                    }				
                }
            
        }
        mapFile.close();
        
    }
    public void printMaze()
    {  	
        
    	output +="*---------------------------------------*\n";
    	
    	for (int i = 0; i < HEIGHT; i++)
    	{
            output +="|";
            for (int j = 0; j < WIDTH; j++)
            {
                if (i == player[ROW] && j == player[COL])
                {
                    output +="@";
                    //this.visited[i][j] = true;
                }
                else if (i == getStartRow() && j == getStartCol())
                {
                    output +="S";
                }
                else if (i == goal[ROW] && j == goal[COL])
                {
                    output +="G";
                }
                else if ( this.visited[i][j] == true && !(i == getStartRow() && j == getStartCol()))
                {
                    output +=".";
                }
                else if ( getBlockedVal(i, j) == true)
                {
                    output +="X";
                }
                else if ( getBlockedVal(i, j) == false)
                {
                    output +=" ";
                }
            }
            output +="|\n";
        }
    	output +="*---------------------------------------*";
        System.out.println(output);
        output = "";
    }
    private boolean makeMove(String move)
    {
        char e = move.toLowerCase().charAt(0);
        switch (e) {
            case 'u':
                if(valid(player[0] - 1, player[1])) {
                    player[ROW] -= 1;
                    visit(player[0], player[1]);
                    return playerAtGoal();
            }
                break;
            case 'd':
                if(valid(player[0] + 1, player[1])) {
                    player[ROW] += 1;
                    visit(player[0], player[1]);
                    return playerAtGoal();
            }
                break;
            case 'l':
                if(valid(player[0], player[1] - 1)) {
                    player[COL] -= 1;
                    visit(player[0], player[1]);
                    return playerAtGoal();
                }
                break;
            case 'r':
                if(valid(player[0], player[1] + 1)) {
                    player[COL] += 1;
                    visit(player[0], player[1]);
                    return playerAtGoal();
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
        while(!makeMove(input));
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
        return playerInput;
    }
    public int getPlayerCol() {
        return player[COL];
    }
    public int getPlayerRow() {
        return player[ROW];
    }
    public int getGoalCol() {
        return goal[COL];
    }
    public int getGoalRow() {
        return goal[ROW];
    }
    public int getStartCol() {
        return start[COL];
    }
    public int getStartRow() {
        return start[ROW];
    }
    public boolean[][] getBlocked() {
        return copyTwoDimBoolArray(blocked);
    }
    public boolean[][] getVisited() {
        return copyTwoDimBoolArray(visited);
    }
    public boolean getBlockedVal(int i, int j) {
        return this.blocked[i][j];
    }
    public boolean getVisitedVal(int i, int j) {
        return this.visited[i][j];
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
    public void setBlockedVal(boolean input, int i, int j) {
        this.blocked[i][j] = input;
    }
    public void setVisitedVal(boolean input, int i, int j) {
        this.visited[i][j] = input;
    }
    //SETTERS

    //TESTING
    public void testVars() {
        System.out.println(getGoalCol());
        System.out.println(getGoalRow());
        System.out.println(getPlayerCol());
        System.out.println(getPlayerRow());
        System.out.println(getStartCol());
        System.out.println(getStartRow());
    }
    public void testBlocked() {
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                if(this.blocked[i][j]) {
                    System.out.println("true"); return;
                }
            }
        }
        System.out.println("EMPTY");
    }

    public static void main(String[] args) {
        System.out.println("Testing: One Arg Constructor");
        try {new MazeGame("C:\\Users\\danil\\Desktop\\Lab02\\Lab02\\bin\\data\\hard.txt"); }
        catch(FileNotFoundException e) {
            System.out.println("Boy if you dont learn how to type istg.");
            System.exit(1);
        }
    }
}    