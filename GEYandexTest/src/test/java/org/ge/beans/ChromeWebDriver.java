package org.ge.beans;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class ChromeWebDriver extends EventFiringWebDriver {

    private static final WebDriver webDriver;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            webDriver.close();
        }
    };

    static {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        ChromeOptions capabilities = new ChromeOptions();
        webDriver = new ChromeDriver(capabilities);

        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public ChromeWebDriver() {
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
