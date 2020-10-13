package com.example.demo.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MinesweeperService {

    public String execute(String input) {
        JsonArray inputArray = new Gson().fromJson(input, JsonArray.class);
        Map<Integer, JsonArray> initMap = new HashMap<>();
        int row = inputArray.size();
        for (int i = 0; i < row; i++) {
            JsonElement element = inputArray.get(i);
            JsonArray arr = element.getAsJsonArray();
            initMap.put(i, arr);
        }
        int column = initMap.get(0).size();
        Boolean[][] initMatrix = new Boolean[row][column];
        initMap.forEach((k, v) -> {
            for (int i = 0; i < v.size(); i++) {
                initMatrix[k][i] = v.get(i).getAsBoolean();
            }
        });
        int[][] result = new int[row][column];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column; c++) {
                result[r][c] = calculateMine(r, c, row, column, initMatrix);
            }
        }
        return new Gson().toJson(result);
    }

    private int calculateMine(int r, int c, int maxRow, int maxColumn, Boolean[][] initMatrix) {
        int count = 0;
        for (int row = r - 1; row < r + 2; row++) {
            for (int column = c - 1; column < c + 2; column++) {
                if (row >= 0 && row < maxRow && column >= 0 && column < maxColumn) {
                    if (!(row == r && column == c)) {
                        if (initMatrix[row][column] == Boolean.TRUE) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

}
