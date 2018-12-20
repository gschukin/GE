package org.ge.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.ge.beans.ChromeWebDriver;
import org.ge.beans.FirefoxWebDriver;

@Configuration
@PropertySource("classpath:application.properties")
public class WebDriverConfiguration {
	
	@Value("${selenium.browser}")
	private String browser;
	
	@Bean(destroyMethod="close")
	public WebDriver getWebDriver() {
		WebDriver webDriver = null;

		switch (browser) {
			case "firefox":
				webDriver = new FirefoxWebDriver();
				break;
			case "chrome":
				webDriver = new ChromeWebDriver();
				break;
		}

		webDriver.manage().window().maximize();

		return webDriver;
	}
	
	@Bean
	public WebDriverWait getWebDriverWait() {
		return new WebDriverWait(getWebDriver(), 5);
	}

}
