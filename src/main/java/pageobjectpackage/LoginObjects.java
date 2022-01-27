package pageobjectpackage;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import reusablepackage.ReusableFunction;

public class LoginObjects {
	WebDriver driver;
	ExtentTest logger;
	ReusableFunction ReusFun=new ReusableFunction(driver, logger);
	@FindBy(xpath="//button[@id='btnTerminalResetOk']")
	WebElement btn_terminal_yes;
	
	@FindBy(xpath="//select[@class='input' and @id='ddlTerminal']")
	WebElement slt_terminal;
	
	@FindBy(xpath="//input[@id='txtUserId']")
	WebElement inp_username;
	
	@FindBy(xpath="//input[@id='txtPwd']")
	WebElement inp_password;
	
	@FindBy(xpath="//button[@id='btnLogin']")
	WebElement btn_login;
	
	@FindBy(xpath="//a[text()='CUSTOMER SELECTION']")
	WebElement txt_CustomerSelection;
	
	@FindBy(xpath="//input[@id='txtCMobileNo']")
	WebElement txt_Mob_num;
	
	@FindBy(xpath="//button[@id='btnCusOK']")
	WebElement btn_ok;
	
	@FindBy(xpath="//h6[@id='lblCustomerName']")
	WebElement txt_name;
	
	@FindBy(xpath="//h6[@id='lblMobileNo']")
	WebElement txt_phonenum;
	
	public LoginObjects(WebDriver driver,ExtentTest logger) {
	this.driver = driver;
	this.logger = logger;
	PageFactory.initElements(driver, this);
	}

	
	public void login(String UserName,String Password) {
		/**Login with PS, SA, @stra****/
		ReusFun.SELECTBYVISIBLETEXT(slt_terminal, "PS");
		ReusFun.SENDKEYS(inp_username, UserName);
		ReusFun.SENDKEYS(inp_password, Password);
		btn_login.click();
		
		try {
			Thread.sleep(5000);
			btn_terminal_yes.click();
			Thread.sleep(5000);
			String actual_Alert_text = driver.switchTo().alert().getText();
			driver.switchTo().alert().accept();
			Thread.sleep(5000);
			login(UserName,Password);
		}
		catch(Exception e)
		{
		System.out.println("Terminal is already closed");	
		}
		String pageTitle=driver.getCurrentUrl();
		Assert.assertEquals(pageTitle, "http://cashwrap.expertsoftsys.com/Cashwrap/Index");
	}
	
	public void cust_Select(String Phonenum) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,30);
	    wait.until(ExpectedConditions.visibilityOf(txt_Mob_num));
		ReusFun.SENDKEYS(txt_Mob_num, Phonenum);
		btn_ok.click();
		Thread.sleep(5000);
		cust_details_Compare(Phonenum);
	}

	
	public void cust_details_Compare(String Phonenum) throws InterruptedException
	{
		
		//try {
			Thread.sleep(5000);
			WebDriverWait wait = new WebDriverWait(driver,30);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement cust_row=driver.findElement(By.xpath("//div/table[@id='tblVendorList']/tbody/tr[contains(@id,'"+Phonenum+"')]"));
		wait.until(ExpectedConditions.visibilityOf(cust_row));
		//System.out.println(cust_row);
		
		/***** Phone num ********/
		WebElement Phonenum1 = driver.findElement(By.xpath("//div/table[@class='tblTable']/tbody/tr[contains(@id,'"+Phonenum+"')]/td[1]"));
		String PhoneNum=LoginObjects.getText(driver,Phonenum1);
		//System.out.println("Phone num :"+PhoneNum);
		
		/***Name ****/
		WebElement name1 = driver.findElement(By.xpath("//div/table[@class='tblTable']/tbody/tr[contains(@id,'"+Phonenum+"')]/td[2]"));
		String name=LoginObjects.getText(driver,name1);
		//System.out.println("Name :"+name);
		
		/****Email ******/
		WebElement email1 = driver.findElement(By.xpath("//div/table[@class='tblTable']/tbody/tr[contains(@id,'"+Phonenum+"')]/td[3]"));
		String email=LoginObjects.getText(driver,email1);
		//System.out.println("Email :"+email);
		
		js.executeScript("arguments[0].click();", cust_row);
		
		String cashname=txt_name.getText();
		Assert.assertEquals(name, cashname);
		
		String cashPhonenum=txt_phonenum.getText();
		Assert.assertEquals(PhoneNum, cashPhonenum);
		/*
		
		}
		catch(Exception e)
		{
			System.out.println("Customer details not compared successfully");
		}*/
	}
	
	public static String getText(WebDriver driver, WebElement element){
	    return (String) ((JavascriptExecutor) driver).executeScript(
	        "return jQuery(arguments[0]).text();", element);
	}
	public static void extracode(){
		//wait.until(ExpectedConditions.elementToBeClickable(cust_row));
				//cust_row.click();
		
		/*
		 //String script = "return   document.getElementByXPath('//div/table[@class='tblTable']/tbody/tr[contains(@id,'8610663647')]/td[1]').getText();";
		 String script = "return   document.getElementByXPath('//div/table[@class='tblTable']/tbody/tr[contains(@id,'8610663647')]/td[1]')";
		 js.executeScript("arguments[0].text();", script);
		 String telno1 = ((JavascriptExecutor) driver).executeScript(script).toString();
		 System.out.println(telno1);
		*/
		/*
		WebElement element = driver.findElement(By.xpath("//div/table[@class='tblTable']/tbody/tr[contains(@id,'8610663647')]/td[1]"));
		String script="return arguments[0].text;";
		
		String telno1 = ((JavascriptExecutor) driver).executeScript(script).toString();
		 System.out.println(telno1);*/
	//	js.executeScript("return arguments[0].text", element);
		//System.out.println(element.getText());
		//js.executeScript("return arguments[0].text", element);
		/*
		WebElement mytable = driver.findElement(By.xpath("//div/table[@id='tblVendorList']/tbody"));
    	List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
    	int rows_count = rows_table.size();
    	for (int row = 0; row < rows_count; row++) {
    	    List < WebElement > Columns_row = rows_table.get(row).findElements(By.tagName("td"));
    	    System.out.println(Columns_row);
    	    int columns_count = Columns_row.size();
    	    System.out.println("Number of cells In Row " + row + " are " + columns_count);
    	    for (int column = 0; column < columns_count; column++) {
    	        String celtext = Columns_row.get(column).getText();
    	        System.out.println("Cell Value of row number " + row + " and column number " + column + " Is " + celtext);
    	    }}
    	    */
		/*	String xyz=driver.switchTo().alert().getText();
		System.out.println(xyz);*/
		//String cust_Elem='"'+"//tr[contains(@id,'"+Phonenum+"')]/ancestor::table[@id='tblVendorList']"+'"';
		//	"//tr[contains(@id,'8610663647')]/ancestor::table[@id='tblVendorList']"
			//String cust_Elem='"'+"(//tr[contains(@id,'"+Phonenum+"')])[1]"+'"';
			//System.out.println(cust_Elem);
		//tr[@id='RC0063_SERAN_8610663647_0']
		//driver.switchTo().alert().sendKeys("Text");
		
		/*String MainWindow=driver.getWindowHandle();		
 		
	        // To handle all new opened window.				
	            Set<String> s1=driver.getWindowHandles();		
	        Iterator<String> i1=s1.iterator();		
	        		
	        while(i1.hasNext())			
	        {		
	            String ChildWindow=i1.next();		
	            		
	            if(!MainWindow.equalsIgnoreCase(ChildWindow))			
	            {    		
	                 
	                    // Switching to Child window
	                    driver.switchTo().window(ChildWindow);	                                                                                                           
	                    driver.findElement(By.xpath("//input[@id='txtCMobileNo']"))
	                    .sendKeys(Phonenum);                			
	                    
	                    //driver.findElement(By.name("btnLogin")).click();			
	                                 
				// Closing the Child Window.
	                        driver.close();		
	            }		
	        }		
	        // Switching to Parent window i.e Main Window.
	            driver.switchTo().window(MainWindow);*/
		
		
	}

}
