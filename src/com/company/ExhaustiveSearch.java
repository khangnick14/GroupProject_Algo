package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExhaustiveSearch {
    //Read file function
    private static int[][] readFile(File file) {
        try {
            Scanner scan = new Scanner(file);
            //Spit the first line in the file
            String[] arrRow = scan.nextLine().split(" ");

            //Set # of row(s) and column(s)
            int row = Integer.parseInt(arrRow[0]);
            int col = Integer.parseInt(arrRow[1]);

            //Declare 2D array
            int[][] maze = new int[row][col];

            //Get each row, read the whole line then split to each cell
            for (int i = 0; i < row; i++) {
                String line = scan.nextLine();
                String[] temp = line.split(" ");
                //Loop each cell and put each to the 2D array
                for (int j = 0; j < col; j++) {
                    if (temp[j].equals(".")) {
                        maze[i][j] = 0;
                    }
                    else if (temp[j].equals("x") || temp[j].equals("X")) {
                        maze[i][j] = -1;
                    }
                    //If number of gold is negative
                    else if (Integer.parseInt(temp[j]) < 0) {
                        System.out.println
                                ("There is a problem with the input file. Numbers of gold cannot be negative!!!");
                        return null;
                    } else maze[i][j] = Integer.parseInt(temp[j]);
                }
            }
            return maze;
        }
        //Throw errors messages when there are any problems with input file
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println("No file found!");
            return null;
        } catch (NumberFormatException numberFormatException) {
            System.out.println("There is a problem with the input file. Invalid number of gold");
            return null;
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("There is a problem with the input file. Invalid size of input matrix");
            return null;
        }
    }

    //Function to find maximum gold path
    public static Path findMaximumGoldPath(int[][] maze) {
        //Get the total number of row and column
        int row = maze.length;
        int column = maze[0].length;
        //Declare an empty solution path
        Path solutionPath = new Path(0, new ArrayList<>(), maze, 0, 0);

        //Make sure the starting point is not a rock
        if(maze[0][0] != -1) {
            //Maximum length of possible paths
            final int MAX_LENGTH = row + column - 2;
            for (int i = 1; i <= MAX_LENGTH; i++) {
                //For each number from 0 -> 2^i -1, generate bit strings
                for (long j = 0; j <= Math.pow(2.0, i) - 1; j++) {
                    boolean skipPath = false;
                    //Create a new path object
                    ArrayList<String> collectGoldPath = new ArrayList<>();
                    Path path = new Path(maze[0][0], collectGoldPath, maze, 0, 0);
                    for (int k = 0; k < i; k++) {
                        //if the current path is skipped
                        if (skipPath) {
                            continue;
                        }
                        long temp;
                        temp = (j >> k) & 1;
                        //go Right
                        if (temp == 1) {
                            //Check valid then update the path and gold
                            if (path.isRightStepValid()) {
                                path.updateGoldPath("R");
                            } else skipPath = true; //the path is valid, stop generate the next step
                        }
                        //go Down
                        else {
                            //Check valid then update the path and gold
                            if (path.isDownStepValid()) {
                                path.updateGoldPath("D");
                            } else skipPath = true; //the path is valid, stop generate the next step
                        }
                    }
                    //the new path is valid, compare with the current best path then update
                    if (!skipPath) {
                        if (path.getTotalGold() > solutionPath.getTotalGold()) {
                            solutionPath = path;
                        }
                    }
                }
            }
        }
        //return the result
        return solutionPath;
    }


    public static void main(String[] args) {
        long startTime = System.nanoTime(); //find the current time in nanoSeconds
        String dir = System.getProperty("user.dir"); //Get the path to current project folder
        File newFile = new File(dir + "\\src\\com\\company\\mapName.txt"); //Generate file path
        int[][] maze = readFile(newFile); //read the file then store to 2D array
        if (maze == null) return; //If there is an error, stop the process
        Path solutionPath = findMaximumGoldPath(maze); //find the best path

        //Display the result of the best path
        ArrayList<String> path = solutionPath.getCollectGoldPath();
        System.out.println("Total gold: " + solutionPath.getTotalGold());
        System.out.print("Path: ");
        for (String s : path) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("Total steps: " + (path.size()));
        System.out.println("Time elapsed: " + ((System.nanoTime() - startTime)) + " nanoseconds");// Measuring total time of algorithm

    }
}
