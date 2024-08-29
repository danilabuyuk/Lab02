package maze;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MazeGame
{
    private final static int HEIGHT = 19;
    private final static int WIDTH = 39;
    private final static int ROW = 0;
    private final static int COL = 1;
    private Scanner playerInput;
    private boolean[][] blocked = new boolean[HEIGHT][WIDTH];
    private boolean[][] visited = new boolean[HEIGHT][WIDTH];
    private int[] player = new int[2];
    private int[] goal = new int[2];
    private int[] start = new int[2];
    
    public void MazeGame(String mazeFile, Scanner playerInput) {
        this.playerInput = playerInput;
        loadMaze(mazeFile);
    }

    public void MazeGame(String mazeFile) {
        this.playerInput = new Scanner(System.in);
        loadMaze(mazeFile);
    }
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
    public boolean[][] copyTwoDimBoolArray(boolean[][] input) {
        boolean[][] output = new boolean[HEIGHT][WIDTH];
            for(int i = 0; i < HEIGHT; i++) {
                for(int j = 0; j < WIDTH; j++) {
                    output[i][j] = input[i][j];
                }
            }
        return output;
    }

    public void setPlayerInput(Scanner input) {
        playerInput = input;
    }
    public void setPlayerCol(int input) {
        if(input <= WIDTH) {
            player[COL] = input;
        }
    }
    public void setPlayerRow(int input) {
        if(input <= HEIGHT) {
            player[ROW] = input;
        }
    }
    public void setGoalCol(int input) {
        if(input <= WIDTH) {
            goal[COL] = input;
        }
    }
    public void setGoalRow(int input) {
        if(input <= HEIGHT) {
            goal[ROW] = input;
        }
    }
    public void setStartCol(int input) {
        if(input <= WIDTH) {
            start[COL] = input;
        }
    }
    public void setStartRow(int input) {
        if(input <= HEIGHT) {
            start[ROW] = input;
        }
    }
    public void setBlocked(boolean[][] input) {
        blocked = copyTwoDimBoolArray(input);
    }
    public void setVisited(boolean[][] input) {
        visited = copyTwoDimBoolArray(input);
    }

    public void prompt() {
        printMaze();
        System.out.print("Enter your move (up, down, left, right, or q to quit):");
    }
    public boolean playerAtGoal() {
        return player[ROW] == goal[ROW] && player[COL] == goal[COL];
    }
    public boolean valid(int row, int col) {
        return blocked[row][col] && row < ROW && col < COL;
    }
    public void visit(int row, int col) {
        visited[row][col] = true;
    }
    public void loadMaze(String mazeFile)
    {
    	blocked = new boolean[HEIGHT][WIDTH];
    	
     	File file = new File(mazeFile);
     	Scanner mapFile = null;
    	try
    	{	
            mapFile = new Scanner(new FileReader(file));
    	} 
    	catch (FileNotFoundException ioe)
    	{
            System.out.println("Could not find file.");
            System.exit(0);
    	}
    	while (mapFile.hasNext())
    	{
            for (int i = 0; i < HEIGHT; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    String mazeElement; 
                    mazeElement = mapFile.next();
    				
                    if (mazeElement.equals("1"))
                    {
                        blocked[i][j] = true;
                    }
                    else if (mazeElement.equals("S"))
                    {
                        blocked[i][j] = false;
                        setStartRow(i);
                        setStartCol(j);
                    }
                    else if (mazeElement.equals("G"))
                    {
                        blocked[i][j] = false;
                        setGoalRow(i);
                        setGoalCol(j);
                    }   				
                }
            }
        }
        mapFile.close();
    }
    public void printMaze()
    {
        // TODO   	
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
                else if (visited[i][j] == true && !(i == getStartRow() 
                		&& j == getStartRow()))
                {
                    System.out.print(".");
                }
                else if (i == getStartRow() && j == getStartCol())
                {
                    System.out.print("S");
                }
                else if (i == getGoalRow() && j == getGoalCol())
                {
                    System.out.print("G");
                }
                else if (blocked[i][j] == false)
                {
                    System.out.print(" ");
                }
                else if (blocked[i][j] == true)
                {
                    System.out.print("X");
                }
            }
            System.out.println("|");
        }
    	System.out.println("*---------------------------------------*");
    }
    public boolean makeMove(String move)
    {
        // TODO
        if (move.equals("up"))
    	{
            if (player[ROW] > 0 && blocked[player[ROW] - 1][player[COL]] == false)
            {
                player[ROW] -= 1;
                return true;
            }
            else 
            {
                return false;
            }
    	}
    	else if (move.equals("down"))
    	{
            if (player[ROW] < HEIGHT - 1 && blocked[player[ROW] + 1][player[COL]] == false)
            { 
                player[ROW] += 1;
                return true;
            }
            else 
            {
                return false;
            }
        }
    	else if (move.equals("left"))
        {
            if ((player[COL] - 1) >= 0 && blocked[player[ROW]][player[COL] - 1] == false)
            {
                player[COL] -= 1;
                return true;
            }
            else 
            {
                return false;
            }
        }
    	else if (move.equals("right"))
    	{
            if (player[COL] + 1 <= WIDTH - 1 
            		&& blocked[player[ROW]][player[COL] + 1] == false)
            {
                player[COL] += 1;
                return true;
            }
            else 
            {
                return false;
            }
    	}
    	else if (move.equals("quit"))
    	{
            return true;
    	}
    	    	
        return false;
    }
    public void playGame()
    {
        int countMoves = 0;
        int endGame = 0;
    	while (playerAtGoal() == false && endGame == 0)
        {
            printMaze();
            System.out.println("up, down, left, right or quit?");
            String input = playerInput.next();
            makeMove(input);
            if (input.equals("quit"))
            {
            	endGame += 1; 
            }
            countMoves++;
        }
    	System.out.print("Player took " + countMoves + " moves.");
    }
}