package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.time.Duration;

public class Driver {

    // Create a private static WebDriver object
    private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();
    // To make a singleton driver, make the constructor private, so that no objects can be created from outside classes.
    private Driver() { // Constructor Driver
    }

    // Create the getDriver method to create and initiate the driver instance
    public static WebDriver getDriver(){
        // This ensures that only one driver object is created.
        // If a driver was created before, 'driver' won't be null, and this if block won't execute.
        // It will return the existing (previously created) driver.

        if (driver.get() == null){
            switch (ConfigReader.getProperty("browser")){
                case "chrome":
                    driver.set(new ChromeDriver());
                    break;
                case "ie":
                    driver.set(new InternetExplorerDriver());
                    break;
                default:
                    driver.set(new ChromeDriver());
                    break;
            }
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get().manage().window().maximize();

        }
        return driver.get();
    }
    // create a closeDriver method to close the driver
    public static void closeDriver() {
        //   quit the driver id it is pointing chromedriver, firefoxdriver....
        driver.get().close();
        driver.set(null);

    }
}
