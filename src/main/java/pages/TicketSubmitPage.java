package pages;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import model.pojo.TicketPojo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class TicketSubmitPage extends AbstractPage {
    private static final String URL_TICKET_SUBMIT = System.getProperty("pageTicketSubmit.url");

    public TicketSubmitPage(WebDriver driver) {
        super(driver);
        this.url = URL_TICKET_SUBMIT;
    }

    @FindBy(xpath = "//*[@id=\"id_queue\"]")
    private WebElement queue;

    @FindBy(xpath = "//*[@id=\"id_title\"]")
    private WebElement summary;

    @FindBy(xpath = "//*[@id=\"id_body\"]")
    private WebElement description;

    @FindBy(xpath = "//*[@id=\"id_priority\"]")
    private WebElement priority;

    @FindBy(xpath = "//*[@id=\"id_due_date\"]")
    private WebElement dueOnDate;

    @FindBy(xpath = "//*[@id=\"id_submitter_email\"]")
    private WebElement email;

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]//form/button")
    private WebElement submit;

    @Step("Создание ticket {ticket.summary}")
    public void createTicket(TicketPojo ticket){
        this.open();
        new Select(queue).selectByVisibleText(ticket.getQueue());
        summary.sendKeys(ticket.getSummary());
        description.sendKeys(ticket.getDescription());
        new Select(priority).selectByVisibleText(ticket.getPriority());
        dueOnDate.sendKeys(ticket.getDateCreated().toString());
        email.sendKeys(ticket.getEmail());
        submit.click();
        takeScreenshot();
    }
}
