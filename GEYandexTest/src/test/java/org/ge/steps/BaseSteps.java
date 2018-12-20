package org.ge.steps;

import org.ge.configuration.WebDriverConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {WebDriverConfiguration.class})
@PropertySource("classpath:application.properties")
public class BaseSteps {

    @Autowired
    protected WebDriver webDriver;

    @Autowired
    protected WebDriverWait webDriverWait;
}
