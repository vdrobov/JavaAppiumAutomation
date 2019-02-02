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
    public void testCancelledSearchResult()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Java";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );

//        List<WebElement> elements = driver.findElementsById("org.wikipedia:id/page_list_item_container");
//        int amount = elements.size();
//        assertTrue("Less than one element find",amount > 1);

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_empty_message"),
                "Cannot find empty search message",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Search results are still present on the page",
                5
        );
    }

    @Test
    public void testSaveTwoArticlesToMyList()
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

        MainPageObject.waitForElementPresent(
                By.id(view_title_text),
                "Cannot find article title",
                30
        );

        String menu_more_options = "//android.widget.ImageView[@content-desc='More options']";

        MainPageObject.waitForElementAndClick(
                By.xpath(menu_more_options),
                "Cannot find button to open article options",
                5
        );

        String menu_add_to_list = "//*[@text='Add to reading list']";

        MainPageObject.waitForElementAndClick(
                By.xpath(menu_add_to_list),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        String text_input = "org.wikipedia:id/text_input";

        MainPageObject.waitForElementAndClear(
                By.id(text_input),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Folder";

        MainPageObject.waitForElementAndSendKeys(
                By.id(text_input),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/menu_page_search"),
                "Cannot press menu search button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Java']"),
                "Cannot press recent search button",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Island of Indonesia']"),
                "Cannot find 'Island of Indonesia' topic",
                5
        );

        String title_before_saving = MainPageObject.waitForElementAndGetAttribute(
                By.id(view_title_text),
                "text",
                "Cannot find title of article",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(menu_more_options),
                "Cannot find button to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(menu_add_to_list),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        String first_saved_list = "//*[@text='object-oriented programming language']";

        MainPageObject.swipeElementToLeft(
                By.xpath(first_saved_list),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath(first_saved_list),
                "Cannot delete saved article",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='island of Indonesia']"),
                "Cannot find 'island of Indonesia' saved list",
                5
        );

        String title_after_saving = MainPageObject.waitForElementAndGetAttribute(
                By.id(view_title_text),
                "text",
                "Cannot find title of article",
                15
        );

        assertEquals(
                "Article title have been changed after adding to reading list",
                title_before_saving,
                title_after_saving
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
