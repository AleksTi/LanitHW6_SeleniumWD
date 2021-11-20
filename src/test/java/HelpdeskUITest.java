import io.qameta.allure.*;
import model.pojo.TicketPojo;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class HelpdeskUITest {
    private WebDriver driver;

    @Before
    public void setup() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    //    Открыть страницу https://at-sandbox.workbench.lanit.ru/
    //    Создать ticket
    //    Выполнить логин (login: admin, pass: adminat)
    //    Через поиск найти созданный тикет и
    //    убедиться, что данные соответствуют введенным в п. 2.

    @Test
    @Description("Тест для создания и проверки Ticket")
    public void createTicketTest() {
        TicketPojo ticket = new TicketPojo();
            ticket.setQueue("Some Product");
            ticket.setSummary("My homework");
            ticket.setDescription("Please help me make my homework");
            ticket.setPriority("3. Normal");
            ticket.setDateCreated(LocalDate.parse("2021-11-17"));
            ticket.setEmail("someadressl@mail.ru");

        new MainPage(driver).open();
        new TicketSubmitPage(driver).createTicket(ticket);
        new LoginPage(driver).login(System.getProperty("user"), System.getProperty("password"));
        TicketPojo foundTicket = new TicketUnitPage(driver, new TicketListPage(driver).findTicketUrl(ticket)).getFoundTicket();

        Assert.assertTrue("Summary does not match", foundTicket.getSummary().contains(ticket.getSummary()));
        Assert.assertTrue("Description does not match", foundTicket.getDescription().contains(ticket.getDescription()));
        Assert.assertTrue("email does not match", foundTicket.getEmail().toLowerCase().contains(ticket.getEmail().toLowerCase()));
        Assert.assertTrue("Date of creation does not match", foundTicket.getDateCreated().isEqual(ticket.getDateCreated()));
    }

    @Test
    public void runEmptyOkTestForAllureReport() {
        Assert.assertEquals("Double is not Ok..!!", 1.0, 1.0, 0.0);
    }


    @Test
    public void runEmptyNokTestForAllureReport() {
        Assert.assertEquals("Double is not Ok..!!", 1.0, 2.0, 0.0);
    }

    @Step("Проверка суммы числа {num1} и числа {num2}")
    public void checkSumStep(int num1, int num2, int expectedSum) throws IOException {
        Assert.assertEquals("Sum is wrong", expectedSum, num1 + num2);
        getBytes("images/img.png");
        //Добавление вложения при помощи статического метода
        Allure.addAttachment("Результат", "text/plain", "https://yandex.ru");
    }

    @Test
    public void checkSumTest() throws IOException {
        checkSumStep(3, 2, 5);
        checkSumStep(5, 4, 9);
    }

    @Attachment(value = "Вложение", type = "image/png", fileExtension = ".png")
    public byte[] getBytes(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources", resourceName));
    }

    @After
    public void tierDown() {
        driver.quit();
    }
}
