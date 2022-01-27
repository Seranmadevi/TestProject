package pageobjectpackage;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;

import dataprovider.WriteExcelApiTest;
import reusablepackage.ReusableFunction;

public class NewCustomer {

	WebDriver driver;
	ExtentTest logger;
	ReusableFunction ReusFun=new ReusableFunction(driver, logger);
	
	@FindBy(xpath="//button[@id='btnitemView']")
	WebElement btn_view;
	
	@FindBy(xpath="//a[@id='lnkCr']")
	WebElement btn_new_cust;
	
	@FindBy(xpath="//input[@id='txtCustomerName']")
	WebElement inp_Cust_name;
	
	@FindBy(xpath="//input[@id='txtEmail']")
	WebElement inp_Email;
	
	@FindBy(xpath="//input[@id='txtCMobileNo']")
	WebElement txt_Mob_num;
	
	@FindBy(xpath="//button[@id='btnCusOK']")
	WebElement btn_ok;
	
	@FindBy(xpath="(//div[@class='blobselect-button'])[2]")
	WebElement arrow_slt_customer_group;
	
	@FindBy(xpath="(//div[@class='blobselect-item-search'])[2]")
	WebElement search_customer_group;
	
	
	@FindBy(xpath="//input[@id='txtMemberShipId']")
	WebElement inp_Mem_id;
	
	@FindBy(xpath="//input[@id='btnCustomer']")
	WebElement btn_save;
	
	
	public NewCustomer(WebDriver driver,ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
		}
	
	public void Customer_creation(String Phonenum1,String Name,String Email) throws Exception {
		
		//this.Phonenum=Phonenum1;
		StringBuffer Phonenumx=randomnum_generator();
	    String PhoneNum=String.valueOf(Phonenumx);
	    
		String excelLocation = System.getProperty("user.dir")+"/src/test/resources/"+"TestData.xlsx";
		//System.out.println(excelLocation);
		WriteExcelApiTest eat = new WriteExcelApiTest(excelLocation);
        eat.setCellData("Customer","PhoneNum",2,PhoneNum);
	    
		WebDriverWait wait = new WebDriverWait(driver,30);
	    wait.until(ExpectedConditions.visibilityOf(txt_Mob_num));
		ReusFun.SENDKEYS(txt_Mob_num, PhoneNum);
		btn_ok.click();
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		btn_new_cust.click();
		 wait.until(ExpectedConditions.visibilityOf(inp_Cust_name));
		ReusFun.SENDKEYS(inp_Cust_name,Name);
		wait.until(ExpectedConditions.visibilityOf(inp_Email));
		ReusFun.SENDKEYS(inp_Email,Email);
		arrow_slt_customer_group.click();
		
		Actions action = new Actions(driver); 
		//element = driver.findElement(By.linkText("Get started free"));
		action.moveToElement(search_customer_group).click().sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
		
		inp_Mem_id.sendKeys("43235");
		btn_save.click();
		Thread.sleep(10000);
		String expected_alert_text="New Customer Created Successfully.";
		String actual_Alert_text=driver.switchTo().alert().getText();
		//System.out.println("Actual alert text " + actual_Alert_text);
		Assert.assertEquals(expected_alert_text, actual_Alert_text);
		driver.switchTo().alert().accept();
		
		LoginObjects loginobj=new LoginObjects(driver,logger);
		loginobj.cust_details_Compare(PhoneNum);
	}
	
	static int Phonenum=0;
	public StringBuffer randomnum_generator()
	{
		/**10 digit Random number generator***/
		Random rnd = new Random();
	    int number = rnd.nextInt(99999999);
	   // System.out.println(number);
	     String Phonenum1=String.valueOf(number)+"1";  
	     StringBuffer str1 = new StringBuffer(Phonenum1);
	     int phonelength=Phonenum1.length();
	  //  System.out.println(phonelength);
	   
	    if(phonelength==10)
	    {
	    	str1 = new StringBuffer(Phonenum1);
	    }
	    else if(phonelength==9)
	    {
	    	str1 = new StringBuffer(Phonenum1);
	    	str1.append("1");
	    }
	    else if(phonelength==8)
	    {
	    	str1 = new StringBuffer(Phonenum1);
	    	str1.append("1");
	    	str1.append("1");
	    }
	    else if(phonelength==7)
	    {
	    	str1 = new StringBuffer(Phonenum1);
	    	str1.append("1");
	    	str1.append("1");
	    	str1.append("1");
	    	//Phonenum1=String.valueOf(number)+"1"+"1"+"1"+"2";
	    	// Phonenum=Integer.parseInt(Phonenum1);
	 	   // return Phonenum;
	    }
	    
	   // System.out.println(str1);
	return str1;
	}
	
	}
	

//WebElement cust_row=driver.findElement(By.xpath("//div/table[@id='tblVendorList']/tbody/tr[contains(@id,'"+Phonenum+"')]"));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[contains(@id,'"+Phonenum+"')])[1]")));
		//System.out.println(cust_row);
		
		//WebElement Phonenum1 = driver.findElement(By.xpath("//div/table[@class='tblTable']/tbody/tr[contains(@id,'"+Phonenum+"')]/td[1]"));
		//String PhoneNum=LoginObjects.getText(driver,Phonenum1);
		//System.out.println(PhoneNum);