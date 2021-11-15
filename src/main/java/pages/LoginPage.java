package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage {


    // Обычный поиск элемента
//    private WebElement inputTextUser = driver.findElement(By.id("username"));
//    @FindBy(xpath = "//*[contains(@id, 'username')]")
        private WebElement inputTextUser = driver.findElement(By.xpath("//*[contains(@id, 'username')]"));

    // Поиск элемента через аннотацию
//    @FindBy(id = "password")
//    @FindBy(xpath = "//*[contains(@id, 'password')]")
//    private WebElement inputTextPassword;
    private WebElement inputTextPassword = driver.findElement(By.xpath("//*[contains(@id, 'password')]"));


    // Обычный поиск элемента
//    private WebElement inputSubmit = driver.findElement(By.xpath("//a[@type='submit']"));
    private WebElement inputSubmit = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div/div/div/div[2]/form/input[1]"));


    // todo: остальные элементы страницы

    public void login(String user, String password) {
        // todo
        inputTextUser.sendKeys(user);
        inputTextPassword.sendKeys(password);
        inputSubmit.click();
    }
}
