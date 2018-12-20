package org.ge.beans;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class FirefoxWebDriver extends EventFiringWebDriver {

    private static final WebDriver webDriver;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            webDriver.close();
        }
    };

    static {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");

        FirefoxOptions capabilities = new FirefoxOptions();
        webDriver = new FirefoxDriver(capabilities);

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public FirefoxWebDriver() {
        super(webDriver);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            return;
        }
        super.close();
    }

}
