package com.cucumber.amazon.shopping;

import org.testng.annotations.Test;
import WebdriverBase.CustomAbstractTestNGCucumberRunner;
import io.cucumber.junit.CucumberOptions;


@CucumberOptions
(
	
		plugin = {"pretty","json:target/report.xml","json:target/cucumber.json","html:target/cucumber"},
		features = "src/main/java/feature", 
		glue = {"stepdefinition"},
		tags = "@smoke",
		monochrome = true
	//	tags = "@sanity and @negative and @regression and not @smoke"
		)

@Test
public class TestRunner extends CustomAbstractTestNGCucumberRunner{

}

