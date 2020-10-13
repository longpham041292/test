package com.example.demo.controller;

import com.example.demo.model.ApiAnalyze;
import com.example.demo.service.LogsAnalyzeService;
import com.example.demo.service.MinesweeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private LogsAnalyzeService logsAnalyzeService;

    @Autowired
    MinesweeperService minesweeperService;

    @GetMapping(value = "/api/callLogs/analyze", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers="Content-Type=multipart/form-data")
    public ApiAnalyze logsAnalyze(@RequestParam("logs") String logs) {
        return logsAnalyzeService.logAnalyze(logs);
    }

    @GetMapping(value = "/api/minesweeper/execute", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers="Content-Type=multipart/form-data")
    public String minesweeper(@RequestParam("input") String input) {
        return minesweeperService.execute(input);
    }
}
