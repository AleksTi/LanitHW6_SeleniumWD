import io.qameta.allure.*;
import model.pojo.TicketPojo;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class HelpdeskUITest {
    private WebDriver driver;

    @Before
    public void setup() throws IOException, ClassNotFoundException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
        System.setProperty("webdriver.chrome.driver", System.getProperty("webdriver.chrome.driver"));
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
    public void createTicketTest() throws IOException {
        TicketPojo ticket = new TicketPojo();
        ticket.setQueue("Some Product");
        ticket.setSummary("My homework");
        ticket.setDescription("Please help me make my homework");
        ticket.setPriority("3. Normal");
        ticket.setDateCreated(LocalDate.parse("2021-11-17"));
        ticket.setEmail("someadressl@mail.ru");

        openMainPage();
        new TicketSubmitPage(driver).createTicket(ticket);
        takeScreenshot();
        new LoginPage(driver).login(System.getProperty("user"), System.getProperty("password"));
        TicketPojo foundTicket = new TicketUnitPage(driver, new TicketListPage(driver).findTicketUrl(ticket)).getFoundTicket();

        Assert.assertTrue("Summary does not match", foundTicket.getSummary().contains(ticket.getSummary()));
        Assert.assertTrue("Description does not match", foundTicket.getDescription().contains(ticket.getDescription()));
        Assert.assertTrue("email does not match", foundTicket.getEmail().toLowerCase().contains(ticket.getEmail().toLowerCase()));
        Assert.assertTrue("Date of creation does not match", foundTicket.getDateCreated().isEqual(ticket.getDateCreated()));
    }

    @Step
    public void openMainPage() throws IOException {
        new MainPage(driver).open();
        takeScreenshot();
    }

    @Test
    @Description("Тест для наполнения отчёта и графика Allure")
    public void runEmptyOkTestForAllureReport() {
        Assert.assertEquals("Double is not Ok..!!", 1.0, 1.0, 0.0);
    }

    @Test
    @Description("Тест для наполнения отчёта и графика Allure")
    public void runEmptyNokTestForAllureReport() {
        Assert.assertEquals("Double is not Ok..!!", 1.0, 2.0, 0.0);
    }

    @Step("Проверка суммы числа {num1} и числа {num2}")
    public void checkSumStep(int num1, int num2, int expectedSum) throws IOException {
        Assert.assertEquals("Sum is wrong", expectedSum, num1 + num2);
        //Добавление вложения через аннотацию
        getBytes("images/img.png");
        //Добавление вложения при помощи статического метода
        Allure.addAttachment("Результат", "text/plain", "https://yandex.ru");
    }

    @Test
    public void checkSumTest() throws IOException {
        checkSumStep(3, 2, 5);
        checkSumStep(5, 4, 9);
    }

    @Attachment(value = "Вложение статичной картинки", type = "image/png", fileExtension = ".png")
    public byte[] getBytes(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources", resourceName));
    }

    @Attachment(value = "Вложение Ashot", type = "image/png", fileExtension = ".png")
    public byte[] takeScreenshot() throws IOException {
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(300)) //Прокручиваем с таймаутом 300 мс
                .takeScreenshot(driver);
        BufferedImage bufferedImage = screenshot.getImage();

        //Сохранение скриншота в папку resources/images/screenshots
        String currentTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm").format(LocalDateTime.now());
        File file = new File("src/main/resources/images/screenshots/" + currentTime + ".png");
        ImageIO.write(bufferedImage, "png", file);

        //Сохрание скриншота как вложение в Allure
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }

    @After
    public void tierDown() {
        driver.quit();
    }
}
