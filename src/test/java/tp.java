import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.concurrent.TimeUnit;

class Tp {
    WebDriver driver;
    JavascriptExecutor javascriptExecutor;
    final By input = By.className("new-todo");

    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void prepareDriver() {
        driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(2, TimeUnit.MINUTES);
        javascriptExecutor = (JavascriptExecutor) driver;
    }
    @ParameterizedTest
    @ValueSource(strings = {"Backbone.js","React","AngularJS"})
    public void todosTest(String framework)  {
        driver.get("https://todomvc.com");
        choice(framework);
        addTodo("Clean the car");
        addTodo("Buy Meat");
        addTodo("Meet a friend");
        remove(3);
        assertLeftItems(3);
    }


    private void choice(String framework) {
        WebElement element = driver.findElement(By.linkText(framework));
        element.click();
    }
    public void addTodo(String todo) {
        driver.findElement(this.input).sendKeys(todo);
        driver.findElement(this.input).sendKeys(Keys.RETURN);
    }
    private void remove(int position) {
        driver.findElement(By.cssSelector("li:nth-child(" + position + ") > div > input")).click();
    }
    private void assertLeftItems(int expected) {
        WebElement element = driver.findElement(By.xpath("//footer/*/span | //footer/span"));
        ExpectedConditions.textToBePresentInElement(element, String.format("%d %s left", expected, expected == 1 ? "item": "items"));
    }


}