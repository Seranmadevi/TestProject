package reusablepackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

public class ReusableFunction {
	WebDriver driver;
	ExtentTest logger;
	
	public ReusableFunction(WebDriver driver,ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
		}

	
	public void SELECTBYVISIBLETEXT1(String selectname,String visibleText)
	{
		try {
			WebElement selectElement=driver.findElement(By.xpath(selectname));
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectname)));
		Select selElement=new Select(selectElement);
		selElement.selectByVisibleText(visibleText);
		}
		catch(Exception e)
		{
			System.out.println("Select element not found - SELECTBYVISIBLETEXT");
		}
	}
	
	
	public void SELECTBYVISIBLETEXT(WebElement element,String visibleText)
	{
		try {
			//WebDriverWait wait = new WebDriverWait(driver,30);
			//wait.until(ExpectedConditions.visibilityOf(element));
		Select selElement=new Select(element);
		selElement.selectByVisibleText(visibleText);
		}
		catch(Exception e)
		{
			System.out.println("Select element not found - SELECTBYVISIBLETEXT");
		}
	}
	public void SELECTBYVALUE(WebElement element,String value)
	{
		try {
			//WebDriverWait wait = new WebDriverWait(driver,30);
			//wait.until(ExpectedConditions.visibilityOf(element));
		Select selElement=new Select(element);
		selElement.selectByValue(value);
		}
		catch(Exception e)
		{
			
			e.printStackTrace();			//System.out.println("Select element not found - SELECTBYVALUE");
		}
	}

	
	public void SENDKEYS(WebElement element,String sendText)
	{
		try {
			element.click();
			element.sendKeys(sendText);
		}
		catch(Exception e)
		{
			System.out.println("Element not found - SendKeys");
		}
	}
	public void ISDISPLAYED(WebElement element)
	{
		try {
			if(element.isDisplayed())
			System.out.println("Element displayed - ISDISPLAYED");
		}
		catch(Exception e)
		{
			System.out.println("Element not found - ISDISPLAYED");
		}
	}
	
}
