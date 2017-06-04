import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class CalcTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/");
    }

    @AfterClass
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void additionTest() {
        Assert.assertEquals("12.0", calculation("7", "5", "+"));
    }

    @Test
    public void subtractionTest() {
        Assert.assertEquals("7.0", calculation("9", "2", "-"));
    }

    @Test
    public void multiplicationTest() {
        Assert.assertEquals("56.0", calculation("7", "8", "*"));
    }

    @Test
    public void divisionTest() {
        Assert.assertEquals("3.0", calculation("9", "3", "/"));
    }

    private String calculation(String firstElem, String secondElem, String symbol) {
        WebElement buttonC = findButtonByCaption("C");
        WebElement buttonFirstElement = findButtonByCaption(firstElem);
        WebElement buttonSymbol = findButtonByCaption(symbol);
        WebElement buttonSecondElement = findButtonByCaption(secondElem);
        WebElement buttonResult = findButtonByCaption("=");
        buttonC.click();
        buttonFirstElement.click();
        buttonSymbol.click();
        buttonSecondElement.click();
        buttonResult.click();
        return driver.findElement(By.id("inputData")).getAttribute("value");
    }

    private WebElement findButtonByCaption(String caption) {
        final List<WebElement> buttons =
                driver.findElements(By.className("v-button"));
        for (final WebElement button : buttons) {
            if (button.getText().equals(caption)) {
                return button;
            }
        }
        assertTrue("Not found button " + caption + " on the page. Check the operation of the web server and the location of the web elements on the page", false);
        return null;
    }

}
