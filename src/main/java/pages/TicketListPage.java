package pages;

import model.pojo.TicketPojo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TicketListPage extends AbstractPage {
    private static final String URL_TICKET_LIST = System.getProperty("pageTicketList.url");

    public TicketListPage(WebDriver driver) {
        super(driver);
        this.url = URL_TICKET_LIST;
    }

    @FindBy(xpath = "//*[@id=\"search_query\"]")
    private WebElement searchField;

    @FindBy(xpath = "//*[@id=\"searchform\"]/div/div/button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"ticketTable\"]/tbody/tr[1]/td[2]/div/a")
    private WebElement ticketLink;

    public String findTicketUrl(TicketPojo ticket) {
        this.open();
        searchField.sendKeys(ticket.getSummary());
        searchButton.click();
        return ticketLink.getAttribute("href");
    }
}
