package pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class AbstractPage {
    private static final Logger logger = LogManager.getLogger(AbstractPage.class);
    protected WebDriver driver;
    protected String url;

    protected AbstractPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие страницы")
    public void open() {
        this.driver.get(url);
        takeScreenshot();
    }

    @Attachment(value = "Вложение Ashot", type = "image/png", fileExtension = ".png")
    public byte[] takeScreenshot() {
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(300)) //Прокручиваем с таймаутом 300 мс
                .takeScreenshot(driver);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(screenshot.getImage(), "png", byteArrayOutputStream);
        } catch (IOException e) {
            logger.info("Ошибка создания скриншота", e);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
