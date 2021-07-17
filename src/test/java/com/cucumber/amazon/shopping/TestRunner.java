package com.cucumber.amazon.shopping;


import org.junit.runner.RunWith;
import org.testng.annotations.Test;

import WebdriverBase.CustomAbstractTestNGCucumberRunner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class) //Use this to run with Junit
@CucumberOptions
(
	
		plugin = {"pretty","json:target/report.xml","json:target/cucumber.json","io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"},
		features = "src/main/java/feature", 
		glue = {"stepdefinition"},
		tags = "@smoke",  //"@sanity and @negative and @regression and not @smoke"
		monochrome = true
		)


@Test
public class TestRunner extends CustomAbstractTestNGCucumberRunner{

}

