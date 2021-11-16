package pages;

import model.pojo.TicketPojo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TicketUnitPage extends AbstractPage {

    public TicketUnitPage(WebDriver driver, String ticketUrl) {
        super(driver);
        this.url = ticketUrl;
    }

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]//table/thead/tr/th/h3")
    private WebElement foundSummary;

    @FindBy(xpath = "//*[@id=\"ticket-description\"]/p")
    private WebElement foundDescription;

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]/div/div[1]/div/div/table/tbody/tr[2]/td[2]")
    private WebElement foundEmail;

    @FindBy(xpath = "//*[@id=\"content-wrapper\"]/div/div[1]/div/div/table/tbody/tr[1]/td[1]")
    private WebElement foundDateCreated;

    public TicketPojo getFoundTicket(){
        this.open();
        TicketPojo ticket = new TicketPojo();
        ticket.setSummary(foundSummary.getText());
        ticket.setEmail(foundEmail.getText());
        ticket.setDateCreated(LocalDate.parse(foundDateCreated.getText().substring(0, 13),
                DateTimeFormatter.ofPattern("MMM. dd, yyyy", Locale.ROOT)));
        ticket.setDescription(foundDescription.getText());
        return ticket;
    }
}
