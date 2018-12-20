package org.ge.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageObject {

    protected WebDriver webDriver;

    protected WebDriverWait webDriverWait;

    public PageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.webDriver = driver;
    }

    public PageObject(WebDriver driver, WebDriverWait webDriverWait) {
        PageFactory.initElements(driver, this);
        this.webDriver = driver;
        this.webDriverWait = webDriverWait;
    }

    public PageObject navigateTo(String nameLink) {
        List<WebElement> elements = webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(nameLink)));
        for (WebElement element : elements)
            if (element.isDisplayed()) {
                element.click();
                break;
            }

        return this;
    }
}
