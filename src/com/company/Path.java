package com.company;

import java.util.ArrayList;

public class Path {
    private int totalGold;
    private ArrayList<String> collectGoldPath;
    private int[][] maze;
    private int currRow, currColumn;


    Path() {
    }

    Path(int totalGold, ArrayList<String> collectGoldPath, int[][] maze, int currRow, int currColumn) {
        this.totalGold = totalGold;
        this.collectGoldPath = collectGoldPath;
        this.maze = maze;
        this.currRow = currRow;
        this.currColumn = currColumn;
    }

    public Boolean isDownStepValid() {
        return currRow + 1 < maze.length && maze[currRow + 1][currColumn] != -1;
    }

    public Boolean isRightStepValid() {
        return currColumn + 1 < maze[0].length && maze[currRow][currColumn + 1] != -1;
    }

    public void updateGoldPath(String s) {
        if (s.equals("R")) {
            collectGoldPath.add("R");
            totalGold += maze[currRow][currColumn+1];
            currColumn+=1;
        } else if (s.equals("D")) {
            collectGoldPath.add("D");
            totalGold += maze[currRow+1][currColumn];
            currRow+=1;
        }
    }

    public void updateGold() {
        totalGold+=maze[currRow][currColumn];
    }

    public int getTotalGold() {
        return totalGold;
    }

    public ArrayList<String> getCollectGoldPath() {
        return collectGoldPath;
    }

    public int[][] getMaze() {
        return maze;
    }

    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
    }

    public int getCurrRow() {
        return currRow;
    }

    public void setCurrRow(int currRow) {
        this.currRow = currRow;
    }

    public int getCurrColumn() {
        return currColumn;
    }

    public void setCurrColumn(int currColumn) {
        this.currColumn = currColumn;
    }
}
