package com.company;

import java.util.ArrayList;

public class Path {
    private int totalGold;
    private ArrayList<String> collectGoldPath;
    private int[][] maze;

    Path() {
    }

    Path(int totalGold, ArrayList<String> collectGoldPath, int[][] maze) {
        this.totalGold = totalGold;
        this.collectGoldPath = collectGoldPath;
        this.maze = maze;
    }

    public Boolean isDownStepValid(int a, int b) {
        return a + 1 < maze.length && maze[a + 1][b] != -1;
    }

    public Boolean isRightStepValid(int a, int b) {
        return b + 1 < maze[0].length && maze[a][b + 1] != -1;
    }

    public void calculateGoldPath() {
        if (collectGoldPath.size() == 0) return;
        int i = 0, j = 0;
        for (String s : collectGoldPath) {
            if (s.equals("R")) {
                totalGold += maze[i][j + 1];
            } else
                totalGold += maze[i + 1][j];
        }
    }

    public void updateCurrentGold(int a, int b) {
        totalGold += maze[a][b];
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

}
