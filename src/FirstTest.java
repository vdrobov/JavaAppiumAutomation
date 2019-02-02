import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase{

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testCompareSearchResultMatching()
    {
        String textForSearching = "Java";

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                textForSearching,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find search result",
                15
        );

        List<WebElement> elementsList = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));

        int counter = 0;

        for (WebElement element: elementsList)
        {
            if (element.getAttribute("text").contains(textForSearching))
                counter += 1;
            else
                System.out.println("Searched text is not presented in all search result");
        }

        assertTrue(
                "Searched text " + textForSearching + "is not presented in all search result",
                elementsList.size() == counter
        );
    }

    @Test
    public void testCompareSearchText() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Cannot find search text field",
                5
        );
    }

    @Test
    public void testAssertTitle()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String view_title_text = "org.wikipedia:id/view_page_title_text";

        MainPageObject.assertElementPresent(
                By.xpath(view_title_text),
                "Cannot find any titles"
        );
    }
}
