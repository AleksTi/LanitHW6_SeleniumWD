import model.pojo.TicketPojo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AbstractPage;
import pages.LoginPage;
import pages.TicketsPage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HelpdeskUITest {

    private WebDriver driver;

    @Before
    public void setup() throws IOException {
        // Читаем конфигурационный файл в System.properties
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        // Создание экземпляра драйвера
        driver = new ChromeDriver();
        // Устанавливаем размер окна браузера, как максимально возможный
        driver.manage().window().maximize();
        // Установим время ожидания для поиска элементов
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Установить созданный драйвер для поиска в веб-страницах
        AbstractPage.setDriver(driver);
    }

    //    Открыть страницу https://at-sandbox.workbench.lanit.ru/
    //    Создать ticket
    //    Выполнить логин (login: admin, pass: adminat)
    //    Через поиск найти созданный тикет и
    //    убедиться, что данные соответствуют введенным в п. 2.

    @Test
    public void createTicketTest() throws IOException, InterruptedException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
        driver.get(System.getProperty("site.url"));
        driver.get(System.getProperty("pageTicketsSubmit.url"));

        TicketsPage ticketsPage = new TicketsPage();
        TicketPojo ticket = new TicketPojo();
        ticket.setQueue("Some Product");
        ticket.setSummary("My homework");
        ticket.setDescription("Please help me make my homework");
        ticket.setPriority("3. Normal");
        ticket.setDateCreated(LocalDate.parse("2021-11-15"));
        ticket.setEmail("someadressl@mail.ru");
        ticketsPage.createTicket(ticket);

        driver.get(System.getProperty("pageLogin.url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(System.getProperty("user"), System.getProperty("password"));

        ticketsPage.openTicketPage(ticket);

        String summary = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]//table/thead/tr/th/h3")).getText();
        System.out.println("Found summary: " + summary);
        String description = driver.findElement(By.xpath("//*[@id=\"ticket-description\"]/p")).getText();
        System.out.println("Found description: " + description);
        String email = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div/div[1]/div/div/table/tbody/tr[2]/td[2]")).getText();
        System.out.println("Found email: " + email);
        String dateCreated = driver.findElement(By.xpath("//*[@id=\"content-wrapper\"]/div/div[1]/div/div/table/tbody/tr[1]/td[1]")).getText();
        LocalDate date = LocalDate.parse(dateCreated.substring(0, 13), DateTimeFormatter.ofPattern("MMM. dd, yyyy", Locale.ROOT));
        System.out.println("Found date: " + date);

        Assert.assertTrue("Summary does not match", summary.contains(ticket.getSummary()));
        Assert.assertTrue("Description does not match", description.contains(ticket.getDescription()));
        Assert.assertTrue("email does not match", email.toLowerCase().contains(ticket.getEmail().toLowerCase()));
        Assert.assertTrue(date.isEqual(ticket.getDateCreated()));
    }

    @After
    public void tierDown() {
        driver.quit();
    }
}
