package pages;

import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {
    private static final String URL_MAIN = System.getProperty("site.url");

    public MainPage(WebDriver driver) {
        super(driver);
        this.url = URL_MAIN;
    }


}
