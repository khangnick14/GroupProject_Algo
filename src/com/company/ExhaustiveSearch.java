package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ExhaustiveSearch {
    static ArrayList<Path> allPath = new ArrayList<>();
    private static File newFile = new File("..\\GroupProject_Algo\\src\\com\\company\\mapName.txt");
    private static HashMap<Path, Integer> hm = new HashMap<>();

    private static int[][] readFile(File file) {
        try {
            Scanner scan = new Scanner(file);
            String[] arrRow = scan.nextLine().split(" ");
            int row = Integer.parseInt(arrRow[0]);
            int col = Integer.parseInt(arrRow[1]);
            int[][] maze = new int[row][col];
            for (int i = 0; i < row; i++) {
                String line = scan.nextLine();
                String[] temp = line.split(" ");
                for (int j = 0; j < col; j++) {
                    if (temp[j].equals(".")) {
                        maze[i][j] = 0;
                    } else if (temp[j].equals("x") || temp[j].equals("X")) {
                        maze[i][j] = -1;
                    } else {
                        maze[i][j] = Integer.parseInt(temp[j]);
                    }
                }
            }
            return maze;
        } catch (NullPointerException | FileNotFoundException e) {
            System.out.println("No file found!");
            return null;
        }
    }

    public static Path helper(int[][] maze) {
        int row = maze.length;
        int column = maze[0].length;
        int count = 0;
        Path solutionPath = new Path(maze[0][0], new ArrayList<String>(), maze, 0, 0);
        //Maximum length of path
        final int MAX_LENGTH = row + column - 2;
        for (int i = 1; i <= MAX_LENGTH; i++) {
            for (int j = 0; j <= Math.pow(2.0, i) - 1; j++) {
                boolean skipPath = false;
                ArrayList<String> collectGoldPath = new ArrayList<>();
                Path path = new Path(maze[0][0], collectGoldPath, maze, 0, 0);
                for (int k = 0; k < i; k++) {
                    if(skipPath) {
                        continue;
                    }
                    int temp;
                    temp = (j >> k) & 1;
                    //go Right
                    if (temp == 1) {
                        if (path.isRightStepValid()) {
                            path.updateGoldPath("R");
                            } else skipPath = true;
                        }
                    //go Down
                    else {
                        if (path.isDownStepValid()) {
                            path.updateGoldPath("D");
                        } else skipPath = true;
                    }
                }
                if(!skipPath) {
                    System.out.println(path.getCollectGoldPath());
                    if (path.getTotalGold() > solutionPath.getTotalGold()) {
                        solutionPath = path;
//                    System.out.println(count + ". UPDATED new Solution!!!");
                        count++;
                    }
                }
            }
        }
        return solutionPath;
    }


    public static void main(String[] args) {
        int[][] maze = readFile(newFile);
        Path solutionPath = helper(maze);
        ArrayList<String> path = solutionPath.getCollectGoldPath();
        System.out.println("Total gold: " + solutionPath.getTotalGold());
        System.out.print("Path: ");
        for (String s : path) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("Total steps: " + (path.size()));
    }
}
