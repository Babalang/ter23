package seleniumTests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;


public class BaseForTests {
    static WebDriver driver;
    String serverAdress="http://localhost:8080";

    protected WebDriverWait wait;


    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.gecko.driver","/home/e20210002499/tmp/geckodriver");
        //WebDriverManager.chromiumdriver().setup();
    }

    @BeforeEach
    void init() {
        FirefoxOptions options=new FirefoxOptions();
        FirefoxBinary binary=new FirefoxBinary(new File("/usr/lib/firefox/firefox"));
        options.setBinary(binary);
        driver=new FirefoxDriver(options);
//        driver = WebDriverManager.chromiumdriver().create();
        Duration d = Duration.ofSeconds(1);
        wait = new WebDriverWait(driver, d);

    }

    public void waitElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element) {
        waitElement(element);
        element.click();
    }

    public void write(WebElement element, String text) {
        waitElement(element);
        element.sendKeys(text);

    }

    public String read(WebElement element) {
        waitElement(element);
        return element.getText();
    }

    public  void screenshot(String fileName)
            throws IOException
    {
        File File = ((TakesScreenshot)driver)
                .getScreenshotAs(OutputType.FILE);
        String home = System.getenv("HOME");
        FileUtils.copyFile(File,
                new File(home+"/tmp/selenium/"+ this.getClass().getName()+"-"+fileName + ".jpeg"));
    }

    void login(String userName, String password)  {
        driver.get(serverAdress+"/login");
        WebElement userNameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("[type=submit]"));

        write(userNameField, userName);
        write(passwordField, password);
        click(loginButton);
    }

    String Acces_List_Teacher(){
        Acces_List();
        String test = driver.getPageSource();
        WebElement button1 = driver.findElement(By.partialLinkText("Add Teachers"));
        click(button1);
        return test;
    }
    String GetSource(){
        return driver.getPageSource();
    }
    void Retourner(){
        WebElement button = driver.findElement(By.partialLinkText("Retourner à l'accueil"));
        click(button);
    }
    void Acces_List(){
        WebElement button = driver.findElement(By.partialLinkText("Teachers List"));
        click(button);
    }
}
