package com.tiger.demoOpenCSV.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tiger.demoOpenCSV.logic.OpenCsvLogic;

@RestController
@RequestMapping(value = "/tiger-openCSV")
public class OpenCsvController {
	@Autowired
	OpenCsvLogic openCsvLogic;
	
	@GetMapping(value = "/demoCSV")
	public String demoCSV(@RequestParam("title") String title) throws IOException {
		openCsvLogic.csvWriter();
		return "done";
	}
}
