package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";
    private static final String search_line = "Java";

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.clickAddArticleToMyList();
            ArticlePageObject.fillMyListName(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.closeSyncPopup();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            String name_of_folder = "Folder";
            ArticlePageObject.clickAddArticleToMyList();
            ArticlePageObject.fillMyListName(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.closeSyncPopup();
        }

        ArticlePageObject.moveToSearchPage();

        if (Platform.getInstance().isAndroid()){
            SearchPageObject.clickByRecentSearchResultWithSubstring(search_line);
        }

        SearchPageObject.waitForSearchResult("Island of Indonesia");
        SearchPageObject.clickByArticleWithSubstring("Island of Indonesia");

        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.clickAddArticleToMyList();
            ArticlePageObject.clickByFolderWithSubstring(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        List<String> listOfTitlesBeforeDelete = MyListsPageObject.getArticlesList();

        MyListsPageObject.swipeByArticleToDelete(article_title);
        MyListsPageObject.waitForArticleToDisappearByTitle(article_title);

        List<String> listOfTitlesAfterDelete = MyListsPageObject.getArticlesList();

        assertEquals(
                "Number of articles is wrong",
                listOfTitlesAfterDelete.size(),
                1
        );

        assertEquals(
                "Number of articles is wrong",
                listOfTitlesAfterDelete.get(0),
                listOfTitlesBeforeDelete.get(0)
        );
    }
}
