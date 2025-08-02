package com.amit.customer.cucumber;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/cucumber-reports/html,json:target/cucumber-reports/json/Cucumber.json,junit:target/cucumber-reports/junit/Cucumber.xml")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.amit.customer.cucumber")
public class CucumberTestRunner {
}
