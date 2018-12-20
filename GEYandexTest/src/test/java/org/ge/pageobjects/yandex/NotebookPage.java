package org.ge.pageobjects.yandex;

import org.ge.pageobjects.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NotebookPage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(NotebookPage.class);

    @FindBy(xpath = "//legend[.='Производитель']/../..//label")
    List<WebElement> manufacturers;

    @FindBy(xpath = "//*[@id=\"glpricefrom\"]")
    private WebElement lowerPrice;

    @FindBy(xpath = "//*[@id=\"glpriceto\"]")
    private WebElement upperPrice;

    @FindBy(linkText = "по цене")
    private WebElement priceSort;


    public NotebookPage(WebDriver driver, WebDriverWait webDriverWait) {
        super(driver, webDriverWait);
    }

    public void setLowerPrice(String price) {
        lowerPrice.sendKeys(price);
        waitForLoading();
    }

    public void setUpperPrice(String price) {
        upperPrice.sendKeys(price);
        waitForLoading();
    }

    public void setManufacturer(String name) {
        for (WebElement manufacturer : manufacturers) {
            if (manufacturer.getText().equals(name)) {
                manufacturer.click();
                waitForLoading();
            }
        }
    }

    public void clickPriceSorter() {
        priceSort.click();
        waitForLoading();
    }

    public List<NotebookItem> getItems() {
        List<NotebookItem> itemList = new ArrayList<>();
        List<WebElement> items = webDriver.findElements(By.xpath("//div[starts-with(@data-id, 'model-')]"));

        for (WebElement item : items) {
//            logger.info(item.getText());
            itemList.add(new NotebookItem(item));
        }

        return itemList;
    }

    private void waitForLoading() {
        By paranja = By.xpath("//div[@class='preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja']");
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(paranja));
            webDriverWait.until(ExpectedConditions.stalenessOf(webDriver.findElement(paranja)));
        } catch (Exception e) {
        }
    }
}