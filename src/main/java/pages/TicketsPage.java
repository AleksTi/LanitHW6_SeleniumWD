package pages;

import model.pojo.TicketPojo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TicketsPage extends AbstractPage {
    private static final String URL_TICKET = System.getProperty("pageTicketsSubmit.url");

    public TicketsPage(WebDriver driver) {
        super(driver);
        this.url = URL_TICKET;
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

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]/div/div/div/div[2]//form/button")
    private WebElement submit;

    @FindBy(xpath = "//*[@id=\"search_query\"]")
    private WebElement searchField;

    @FindBy(xpath = "//*[@id=\"searchform\"]/div/div/button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"ticketTable\"]/tbody/tr[1]/td[2]/div/a")
    private WebElement ticketLink;

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]//table/thead/tr/th/h3")
    private WebElement foundSummary;

    @FindBy(xpath = "//*[@id=\"ticket-description\"]/p")
    private WebElement foundDescription;

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]/div/div[1]/div/div/table/tbody/tr[2]/td[2]")
    private WebElement foundEmail;

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]/div/div[1]/div/div/table/tbody/tr[1]/td[1]")
    private WebElement foundDateCreated;

    public void createTicket(TicketPojo ticket){
        this.open();
        new Select(queue).selectByVisibleText(ticket.getQueue());
        summary.sendKeys(ticket.getSummary());
        description.sendKeys(ticket.getDescription());
        new Select(priority).selectByVisibleText(ticket.getPriority());
        dueOnDate.sendKeys(ticket.getDateCreated().toString());
        email.sendKeys(ticket.getEmail());
        submit.click();
    }

    public void openTicket(TicketPojo ticket){
        this.open();
        searchField.sendKeys(ticket.getSummary());
        searchButton.click();
        driver.get(ticketLink.getAttribute("href"));
    }

    public TicketPojo getOpenedTicket(){
        TicketPojo ticket = new TicketPojo();
            ticket.setSummary(foundSummary.getText());
            ticket.setEmail(foundEmail.getText());
            ticket.setDateCreated(LocalDate.parse(foundDateCreated.getText().substring(0, 13),
                    DateTimeFormatter.ofPattern("MMM. dd, yyyy", Locale.ROOT)));
            ticket.setDescription(foundDescription.getText());
        return ticket;
    }
}
