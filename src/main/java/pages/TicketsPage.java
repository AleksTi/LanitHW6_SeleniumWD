package pages;

import model.pojo.TicketPojo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class TicketsPage extends AbstractPage {
    private static final String URL_TICKET = System.getProperty("pageTicketsSubmit.url");

    public TicketsPage(WebDriver driver) {
        super(driver);
        this.url = URL_TICKET;
    }

    public void createTicket(TicketPojo ticket){
        Select selectQueue = new Select(driver.findElement(By.xpath("//*[@id=\"id_queue\"]")));
        selectQueue.selectByVisibleText(ticket.getQueue());
        driver.findElement(By.xpath("//*[@id=\"id_title\"]")).sendKeys(ticket.getSummary());
        driver.findElement(By.xpath("//*[@id=\"id_body\"]")).sendKeys(ticket.getDescription());
        new Select(driver.findElement(By.xpath("//*[@id=\"id_priority\"]"))).selectByVisibleText(ticket.getPriority());
        driver.findElement(By.xpath("//*[@id=\"id_due_date\"]")).sendKeys(ticket.getDateCreated().toString());
        driver.findElement(By.xpath("//*[@id=\"id_submitter_email\"]")).sendKeys(ticket.getEmail());
        driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div/div/div/div[2]//form/button")).click();
    }

    public void openTicket(TicketPojo ticket){
        driver.findElement(By.xpath("//*[@id=\"search_query\"]")).sendKeys(ticket.getSummary());
        driver.findElement(By.xpath("//*[@id=\"searchform\"]/div/div/button")).click();
        driver.get(driver.findElement(By.xpath("//a[contains(text(), '" + ticket.getSummary() + "')]")).getAttribute("href"));
    }
}
