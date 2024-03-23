package Guru99BankTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.annotation.ParametersAreNonnullByDefault;

public class Customer {

    WebDriver driver;

    @Parameters({"url"})
    @BeforeClass
    public void setUp(String url) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }


    @Parameters ({"username", "userPassword"})
    @Test(priority = 100)
    public void logIn(String username, String upassword) {

        WebElement userId = driver.findElement(By.name("uid"));
        WebElement userPassword = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));

        userId.sendKeys(username);
        userPassword.sendKeys(upassword);
        loginButton.click();

        System.out.println("Login Successful");
    }


    @Parameters({"expectedTitle"})
    @Test(priority = 200)
    public void verifyLoginTitlePage(String expected) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expected);
    }


    @Parameters({"name","dob","addr","city","state","pinno","tel",
                "email","password"})
    @Test(priority = 300)
    public void AddCustomer(String sname, String sdob, String saddr, String scity, String sstate,
                            String spinno, String stel,
                            String semail, String spassword) {

        WebElement addCustomerLink = driver.findElement(By.linkText("New Customer"));
        addCustomerLink.click();

        //gender
        driver.findElement(By.xpath("//input[@value='f']")).click();

        driver.findElement(By.name("name")).sendKeys(sname);
        driver.findElement(By.name("dob")).sendKeys(sdob);
        driver.findElement(By.name("addr")).sendKeys(saddr);
        driver.findElement(By.name("city")).sendKeys(scity);
        driver.findElement(By.name("state")).sendKeys(sstate);
        driver.findElement(By.name("pinno")).sendKeys(spinno);
        driver.findElement(By.name("telephoneno")).sendKeys(stel);
        driver.findElement(By.name("emailid")).sendKeys(semail);
        driver.findElement(By.name("password")).sendKeys(spassword);

        driver.findElement(By.name("sub")).click();

    }


    @Test(priority = 400)
    public String GetCustomerid() {

        String customerId = driver.findElement(By.xpath("//table[@id='customer']/tbody/tr[4]/td[2]")).getText();

        System.out.println("Customer ID - "+ customerId);
        return customerId;
    }

    @Test(priority = 401)
    public void DisplayCust() {
        String CreCust = GetCustomerid();
        System.out.println("Customer ID created: " + CreCust);
    }

    @Parameters ({"eaddr","ecity","estate","epinno","etel",
            "eemail"})
    @Test(priority = 500)
    public void EditCustomer(String eaddr, String ecity, String estate, String epinno, String etel,
                             String eemail){
        WebElement EditCustomerLink = driver.findElement(By.linkText("Edit Customer"));
        EditCustomerLink.click();

        String cust = GetCustomerid();
        //String cust = "56857";


        driver.findElement(By.name("cusid")).sendKeys(cust);
        driver.findElement(By.name("AccSubmit")).click();

        driver.findElement(By.name("addr")).clear();
        driver.findElement(By.name("city")).clear();
        driver.findElement(By.name("state")).clear();
        driver.findElement(By.name("pinno")).clear();
        driver.findElement(By.name("telephoneno")).clear();
        driver.findElement(By.name("emailid")).clear();

        driver.findElement(By.name("addr")).sendKeys(eaddr);
        driver.findElement(By.name("city")).sendKeys(ecity);
        driver.findElement(By.name("state")).sendKeys(estate);
        driver.findElement(By.name("pinno")).sendKeys(epinno);
        driver.findElement(By.name("telephoneno")).sendKeys(etel);
        driver.findElement(By.name("emailid")).sendKeys(eemail);

        driver.findElement(By.name("sub")).click();
        driver.switchTo().alert().accept();

    }

    @Test(priority = 600)
    public void DeleteCustomer() {
        String CustIdDelete = GetCustomerid();
        System.out.println("Enter Delete Method" + CustIdDelete);

        WebElement DeleteCustomerLink = driver.findElement(By.linkText("Delete Customer"));
        DeleteCustomerLink.click();



        System.out.println("Entered Delete for " + CustIdDelete);

        driver.findElement(By.name("cusid")).sendKeys(CustIdDelete);
        driver.findElement(By.name("AccSubmit")).click();

        driver.switchTo().alert().accept();
        driver.switchTo().alert().accept();

        System.out.println("Customer Deleted: " + CustIdDelete);
    }

    @Test(priority = 700)

    public void tearDown() {
        driver.quit();
    }



}
