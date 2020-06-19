package facebook.com;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class TestForFacebookFirefox {
    WebDriver driver;
    Properties property = new Properties();

    @BeforeTest
    public void setupFirefox() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://facebook.com");
    }

    @Test
    public void loginToFacebook() {
        WebElement searchFieldEmail = driver.findElement(By.id("email"));
        searchFieldEmail.clear();
        searchFieldEmail.sendKeys("");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement searchFieldPassword = driver.findElement(By.id("pass"));
        searchFieldPassword.clear();
        searchFieldPassword.sendKeys("");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement buttonLogin = driver.findElement(By.id("u_0_b"));
        buttonLogin.click();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);

        assertTrue(driver.getPageSource().contains("Messenger"));
    }

    @Test
    public void navigateToFriendList() {
        WebElement searchFieldProfile = driver.findElement(By.xpath("//*[@id=\"mount_0_0\"]/div/div/div[1]/div[2]/div[4]/div[1]/div[4]/a"));
        searchFieldProfile.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement searchFieldFriend = driver.findElement(By.linkText("Друзья"));
        searchFieldFriend.click();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);

        assertTrue((driver.getPageSource().contains("Поиск")));
    }


    private void scroolToLastFriend() throws AWTException {
        Robot robot = new Robot();
        WebElement searchAnimation = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[3]/div/div/div[1]/div/div/div/div[4]/div/div/div/div/div/div[3]/div[10]/div"));

        try {
            do {
                robot.keyPress(KeyEvent.VK_PAGE_DOWN);
            }
            while (searchAnimation.isDisplayed());
        } catch (Exception ex) {
        }
    }

    @Test
    public void searchFriendsCount() throws AWTException {
        scroolToLastFriend();
        WebElement friedsCountElement = driver.findElement(
                By.xpath("//*[@id=\"mount_0_0\"]/div/div/div[1]/div[3]/div/div/div[1]/div/div/div/div[3]/div/div/div/div/div[1]/div/div/div[1]/div/div/div/div[1]/a[3]/div/span/span/div/div/span"));
        String friendsCountHtml = friedsCountElement.getAttribute("innerHTML");
        int expectedFriendsCount = Integer.parseInt(friendsCountHtml);
        List<WebElement> friendsElementsList = driver.findElements(By.xpath("/html/body/div[1]/div/div/div[1]/div[3]/div/div/div[1]/div/div/div/div[4]/div/div[1]/div/div/div/div[3]/*"));
        Assert.assertEquals(true, expectedFriendsCount == friendsElementsList.size() + 1);
    }

    @AfterAll
    public void closeBrowser() {
        driver.quit();
    }
}
