package org.ge.pageobjects.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NotebookItem {

    private String title;
    private Integer price;

    public NotebookItem(WebElement webElement) {
        title = webElement.findElement(By.xpath(".//div[@class='n-snippet-card2__title']")).getText();
        String textPrice = webElement.findElement(By.xpath(".//div[@class='n-snippet-card2__main-price']")).getText();
        price = Integer.parseInt(textPrice.replaceAll("[\\D]", ""));
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

}
