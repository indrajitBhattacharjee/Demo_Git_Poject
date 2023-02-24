package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import data.BrowserFactory;
import data.DataReader;
import data.ExcelDataReader;

public class LoginTest {
    private DataReader dataReader;
    private WebDriver driver;

    public LoginTest(String browserType) {
        this.dataReader = new ExcelDataReader();
        this.driver = BrowserFactory.getDriver(browserType);
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String browserType, int attempt) {
        // Navigate to the login page
        driver.get("http://leaftaps.com/opentaps/control/main");

        // Enter the username and password
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("decorativeSubmit")).click();

        // Verify that the login was successful
        Assert.assertTrue(driver.getTitle().contains("Leaftaps"));
    }

    @Test(dataProvider = "loginData")
    public void testInvalidLogin(String username, String password, String browserType, int attempt) {
        // Navigate to the login page
        driver.get("http://leaftaps.com/opentaps/control/main");

        // Enter the invalid username and password
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("decorativeSubmit")).click();

        // Verify that the login failed
        Assert.assertTrue(driver.getTitle().contains("Welcome"));
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String[][] credentials = dataReader.readData("credentials.xlsx");
        String browserType = "chrome"; // Default browser type

        // Read the browser type from the config file, if available
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("config.properties");
            properties.load(fis);
            browserType = properties.getProperty("browser.type", "chrome");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object[][] data = new Object[credentials.length][4];
        for (int i = 0; i < credentials.length; i++) {
            data[i][0] = credentials[i][0]; // username
            data[i][1] = credentials[i][1]; // password
            data[i][2] = browserType; // browser type
            data[i][3] = i + 1; // attempt number
        }

        return data;
    }
}
