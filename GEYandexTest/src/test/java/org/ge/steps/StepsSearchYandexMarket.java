package org.ge.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.ge.pageobjects.yandex.MarketPage;
import org.ge.pageobjects.yandex.NotebookItem;
import org.ge.pageobjects.yandex.NotebookPage;
import org.ge.pageobjects.yandex.Yandex;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StepsSearchYandexMarket extends BaseSteps {

    private static final Logger logger = LoggerFactory.getLogger(StepsSearchYandexMarket.class);
    private Yandex homePage;
    private MarketPage marketPage;
    private NotebookPage notebookPage;

    @Given("I am on the main Yandex page")
    public void navigate_to_yandex() {
        webDriver.get("https://yandex.ru");
        homePage = new Yandex(webDriver, webDriverWait);
    }

    @Given("^I select to \"([^\"]*)\"$")
    public void i_select_to(String link) {
        marketPage.navigateTo(link);
    }

    @Given("^I navigate to Yandex Market$")
    public void i_navigate_to_Yandex_Market() {
        homePage.navigateTo("Маркет");
        marketPage = new MarketPage(webDriver, webDriverWait);
    }

    @Then("^I see \"([^\"]*)\" page$")
    public void i_see_page(String title) {
        notebookPage = new NotebookPage(webDriver, webDriverWait);
        Assert.assertTrue("Page title doesn't contain " + title, webDriver.getTitle().contains(title));
    }

    @Then("^I set lower price to \"([^\"]*)\"$")
    public void i_set_lower_price_to(String price) {
        notebookPage.setLowerPrice(price);
    }

    @Then("^I set upper price to \"([^\"]*)\"$")
    public void i_set_upper_price_to(String price) {
        notebookPage.setUpperPrice(price);
    }

    @Then("^I set manufacture to \"([^\"]*)\"$")
    public void i_set_manufacture_to(String arg1) throws Throwable {
        notebookPage.setManufacturer(arg1);
    }

    @Then("^I set ascending price order$")
    public void i_set_ascending_price_order() throws Throwable {
        notebookPage.clickPriceSorter();
    }

    @Then("^I should see only \"([^\"]*)\" notebooks with descending price$")
    public void i_should_see_only_notebooks_with_descending_price(String arg1) {
        List<NotebookItem> list = notebookPage.getItems();

        boolean sameManufacture = true;
        boolean isSorted = true;

        for (NotebookItem item : list) {
            if (!item.getTitle().contains(arg1)) {
                sameManufacture = false;
                logger.error("Error " + item.getTitle());
            }

//			logger.info(item.getTitle());
//			logger.info(item.getPrice());
        }

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).getPrice() > list.get(i).getPrice()) {
                isSorted = false;
            }
        }

        Assert.assertTrue("Some items are not manufactured by " + arg1, sameManufacture);
        Assert.assertTrue("Price order are not ascending", isSorted);
    }
}
