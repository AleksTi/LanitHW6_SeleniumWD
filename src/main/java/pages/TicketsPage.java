package pages;

import model.pojo.TicketPojo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class TicketsPage extends AbstractPage {

    public void createTicket(TicketPojo ticket){
        Select selectQueue = new Select(driver.findElement(By.xpath("//*[@id=\"id_queue\"]")));
        selectQueue.selectByVisibleText(ticket.getQueue());
//        просмотр options Select
//        selectQueue.getOptions().forEach(option ->{
//            System.out.println("Value = " + option.getAttribute("value") + "; Text = " + option.getText());
//        });
        driver.findElement(By.xpath("//*[@id=\"id_title\"]")).sendKeys(ticket.getSummary());
        driver.findElement(By.xpath("//*[@id=\"id_body\"]")).sendKeys(ticket.getDescription());
        new Select(driver.findElement(By.xpath("//*[@id=\"id_priority\"]"))).selectByVisibleText(ticket.getPriority());
        driver.findElement(By.xpath("//*[@id=\"id_due_date\"]")).sendKeys(ticket.getDateCreated().toString());
        driver.findElement(By.xpath("//*[@id=\"id_submitter_email\"]")).sendKeys(ticket.getEmail());
        driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div/div/div/div[2]/form/button")).click();
    }


    public void openTicketPage(TicketPojo ticket){
        driver.findElement(By.xpath("//*[@id=\"search_query\"]")).sendKeys(ticket.getSummary());
        driver.findElement(By.xpath("//*[@id=\"searchform\"]/div/div/button")).click();
        driver.get(driver.findElement(By.xpath("//a[contains(text(), '" + ticket.getSummary() + "')]")).getAttribute("href"));
    }
}
