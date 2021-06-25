package com.cucumber.amazon.shopping;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions
(
		//plugin = {"pretty", "html:target/cucumber"},
		plugin = {"pretty","json:target/report.xml","json:target/cucumber.json","html:target/cucumber"},
		features = "src/main/java/feature", 
		glue = {"stepdefinition"},
		tags = "@smoke",
		monochrome = true
	//	tags = "@sanity and @negative and @regression and not @smoke"
		)

public class TestRunner {

}

