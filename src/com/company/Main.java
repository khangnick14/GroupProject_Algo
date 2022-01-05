package com.company;

import java.util.*;
import java.io.*;

public class Main {
    private static int maxSum = 0;
    private static Node maxLeaf = null;
    private static ArrayList<Integer> pathFromMaxToRoot = new ArrayList<>();
    private static ArrayList<String> path = new ArrayList<>();
    private static ArrayList<String> temp = new ArrayList<>();

    static class Node {
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
                    if (temp[j].equals(".")) {
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

    private static Node constructTree(int[][] maze, Node root, int row, int col) {
        if ((row < maze.length)
                && (col < maze[0].length)) {
            root = new Node(maze[row][col]);
            root.left = constructTree(maze, root.left, row, col + 1);
            root.right = constructTree(maze, root.right, row + 1, col);
        }
        return root;
    }

    private static void displayMaze(int[][] maze) {
        int row = maze.length;
        int col = maze[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void findMaximumPath(Node root, int currSum) {
        //base Case
        if (root == null) {
            return;
        }
        currSum += root.data;
        //check the stop condition
        if (root.left == null && root.right == null) {
            if (maxSum < currSum) {
                maxSum = currSum;
                maxLeaf = root;
            }
            return;
        }
        findMaximumPath(root.left, currSum);
        findMaximumPath(root.right, currSum);
    }

    private static void printPreOder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        if (node.left != null && node.left.data == -1) {
            printPreOder(node.right);
        } else if (node.right != null && node.right.data == -1) {
            printPreOder(node.left);
        } else {
            printPreOder(node.left);
            printPreOder(node.right);
        }

    }

    private static Boolean getPath(Node root, Node leaf) {
        if (root == null) return false;
        if (root == leaf || getPath(root.left,leaf) || getPath(root.right, leaf)) {
            pathFromMaxToRoot.add(root.data);
            return true;
        }
        return false;
    }

    private static void generatePath(Node root) {
        int size = pathFromMaxToRoot.size();
        if (root.data != pathFromMaxToRoot.get(pathFromMaxToRoot.size()-1)) return;
        for(int i = size - 2; i >= 0; i--) {
            if (root.left.data == pathFromMaxToRoot.get(i)) {
                path.add("R");
                root = root.left;
            } else if (root.right.data == pathFromMaxToRoot.get(i)) {
                path.add("D");
                root = root.right;
            }
        }
    }



    public static void main(String[] args) {
        int[][] maze = readFile(newFile);
        readFile(newFile);
        displayMaze(maze);
        System.out.println();
        //construct Tree
        Node root = new Node(maze[0][0]);
        root = constructTree(maze, root, 0, 0);

        //Display constructed tree
        printPreOder(root);
        System.out.println();

        //find maximum path and sum
        findMaximumPath(root, 0);
        System.out.println("Maximum gold: " + maxSum);
        getPath(root, maxLeaf);
        System.out.println("Total steps: " + (pathFromMaxToRoot.size() - 1));
        System.out.print("Path: ");

        for (int i : pathFromMaxToRoot) {
            System.out.print(i + " ");
        }
        System.out.println();
        generatePath(root);
        for(String s : path) {
            System.out.print(s + " ");
        }
    }
}

