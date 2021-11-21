package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {
    private static final String URL_LOGIN = System.getProperty("pageLogin.url");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.url = URL_LOGIN;
    }

    @FindBy(xpath = "//*[contains(@id, 'username')]")
    private WebElement inputTextUser;

    @FindBy(xpath = "//*[contains(@id, 'password')]")
    private WebElement inputTextPassword;

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]//form/input[@type=\"submit\"]")
    private WebElement inputSubmit;

    @Step("Выполнение входа для пользователя: {user}")
    public void login(String user, String password) {
        this.open();
        inputTextUser.sendKeys(user);
        inputTextPassword.sendKeys(password);
        inputSubmit.click();
        takeScreenshot();
    }
}
