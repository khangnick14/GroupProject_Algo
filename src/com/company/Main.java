package com.company;

import java.util.*;
import java.io.*;

public class Main {
    class Node
    {
        int data;
        Node left = null, right = null;

        Node(int data) {
            this.data = data;
        }
    }


    //get the maze file
    private static File newFile = new File("..\\GroupProject_Algo\\src\\com\\company\\mapName.txt");

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
                    if(temp[j].equals(".")) {
                        maze[i][j] = 0;
                    } else if (temp[j].equals("x")) {
                        maze[i][j] = -1;
                    } else {
                        maze[i][j] = Integer.parseInt(temp[j]);
                    }
                }
            }
            return maze;
        } catch (FileNotFoundException e) {
            System.out.println("No file found!");
            return null;
        }
    }


    public static void main(String[] args) {
        int[][] maze = readFile(newFile);
        int row = maze.length;
        int col = maze[0].length;
        readFile(newFile);
        //display maze
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(maze[i][j]+" ");
            }
            System.out.println();
        }
    }
}

