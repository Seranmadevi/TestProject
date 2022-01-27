package pageobjectpackage;

import java.util.ArrayList;

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

import dataprovider.WriteExcelApiTest;
import mainpackage.MainClass;
import reusablepackage.ReusableFunction;

public class Payment {
	WebDriver driver;
	ExtentTest logger;
	ReusableFunction ReusFun = new ReusableFunction(driver, logger);

	@FindBy(xpath = "//a[@id='btnPayment']")
	WebElement btn_pay;

	@FindBy(xpath = "//button[@id='btnAmtCancel']")
	WebElement btn_pay_Cancel;

	@FindBy(xpath = "//button[@id='CD']")
	WebElement btn_slt_Card;

	@FindBy(xpath = "//span[text()='CARD']")
	WebElement lbl_mop_card;

	@FindBy(xpath = "//span[text()='CASH']")
	WebElement lbl_mop_cash;

	@FindBy(xpath = "//input[@id='code']")
	WebElement inp_amount_enter;

	@FindBy(xpath = "//input[@value='OK' and @id='btnPaymentProcess']")
	WebElement btn_pay_ok;

	@FindBy(xpath = "//td[text()='CARD']")
	WebElement tab_txt_Card;

	@FindBy(xpath = "//tr[@id='CD_1']/td[@id='CD_Amt']")
	WebElement txt_card_amt;

	@FindBy(xpath = "//tr[@id='CA_1']/td[@id='CA_Amt']")
	WebElement txt_cash_amt;

	@FindBy(xpath = "//span[@id='lblTender']")
	WebElement txt_tendered;

	@FindBy(xpath = "//button[@id='CA']")
	WebElement btn_slt_Cash;

	@FindBy(xpath = "//button[text()='FINISH']")
	WebElement btn_pay_finish;

	@FindBy(xpath = "//button[@id='btnPayCustomerClose']")
	WebElement btn_close_cust_Selec;

	@FindBy(xpath = "(//a[@class='waves-effect waves-light'])[3]")
	WebElement btn_store_admin;

	@FindBy(xpath = "//li[@id='mnuLogout']")
	WebElement list_logout;

	public Payment(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
		
	}

	public void pay_Cash_Card(String CardAmount) throws Exception {
		String excelLocation = System.getProperty("user.dir") + "/src/test/resources/" + "TestData.xlsx";
		//System.out.println(excelLocation);
		 WriteExcelApiTest eat = new WriteExcelApiTest(excelLocation);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		btn_pay.click();
		btn_pay_Cancel.click();
		btn_slt_Card.click();

		/*** Get card amount from excel, Eg:200 and pass to payment ****/
		String actualcard = lbl_mop_card.getText();
		String expectedcard = "CARD";
		Assert.assertEquals(actualcard, expectedcard);
		System.out.println("Actual Card Text"+actualcard);
		ReusFun.SENDKEYS(inp_amount_enter, CardAmount);
		btn_pay_ok.click();
		boolean y = tab_txt_Card.isDisplayed();
		// System.out.println(y);
		Assert.assertTrue(y);
		String screenshotPath = MainClass.getScreenshot(driver, "Card displayed");
		logger.log(LogStatus.PASS, logger.addScreenCapture(screenshotPath));
		/** Get the card amount displayed ***/

		String act_txt_card_amt1 = txt_card_amt.getText();
		String act_txt_card_amt[] = act_txt_card_amt1.split(" ");
		float act_txt_card = Float.parseFloat(act_txt_card_amt[1]);
		
		System.out.println("Actual Card Amount"+act_txt_card_amt[1]);
		
		/**Compare card amount entered from excelsheet and amount displayed while payment**/
		String excel_card_actual = act_txt_card_amt[1];
		System.out.println(" Excel Card actual "+excel_card_actual);
		Assert.assertEquals(CardAmount, act_txt_card_amt[1]);
		eat.setCellData("Total","GetCardAMount",2,excel_card_actual);

		/*** Pay reamining amount in cash ***/

		btn_slt_Cash.click();
		String actualcash = lbl_mop_cash.getText();
		String expectedcash = "CASH";
		System.out.println("Actual Cash Text"+actualcash);
		Assert.assertEquals(actualcash, expectedcash);
		btn_pay_ok.click();

		
		/** Get the cash amount displayed ***/
		String act_txt_cash_amt1 = txt_cash_amt.getText();
		String act_txt_cash_amt[] = act_txt_cash_amt1.split(" ");
		float act_txt_cash = Float.parseFloat(act_txt_cash_amt[1]);
		String excel_cash_actual = String.valueOf(act_txt_cash);
		System.out.println("excel_cash_actual "+act_txt_cash_amt[1]);
		eat.setCellData("Total","GetCashAMount",2,excel_cash_actual);

		/*** See the tender amount***/
		String tender = txt_tendered.getText();
		String tnder1[] = tender.split(" ");
		//System.out.println("Tender 1 " + tnder1[1]);
		float tendervalue = Float.parseFloat(tnder1[1]);

		/*** Calculate cash & Card *****/
		float totalamtcash_Card1 = act_txt_card + act_txt_cash;
		System.out.println("Total amt cash card " + totalamtcash_Card1);
		
		String totalamtcash_Car = String.valueOf(totalamtcash_Card1);
		eat.setCellData("Total","Calculationfromweb",2,totalamtcash_Car);
	//	System.out.println("Total amt cash card " + totalamtcash_Car);

		
		/***Compare total amount and check with tender total***/
		float totalamt_Cash_Car = (float) totalamtcash_Card1;
		System.out.println("Total tender" + tendervalue);
		Assert.assertEquals(tendervalue, totalamt_Cash_Car);

		screenshotPath = MainClass.getScreenshot(driver, "Total amount,Tender");
		logger.log(LogStatus.PASS, logger.addScreenCapture(screenshotPath));
		
		btn_pay_finish.click();
		Thread.sleep(5000);
		
		/***Message for successful payment***/
		String expected_alert_text = "Payment Successfully completed.";
		String actual_Alert_text = driver.switchTo().alert().getText();
		//System.out.println(actual_Alert_text);
		Assert.assertEquals(expected_alert_text, actual_Alert_text);
		driver.switchTo().alert().accept();
		Thread.sleep(5000);
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		screenshotPath = MainClass.getScreenshot(driver, "Tender slip");
		logger.log(LogStatus.PASS, logger.addScreenCapture(screenshotPath));
		driver.close();
		driver.switchTo().window(tabs2.get(0));

		
		/***CLose the payment and driver***/
		Thread.sleep(5000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		wait.until(ExpectedConditions.visibilityOf(btn_close_cust_Selec));
		// js.executeScript("arguments[0].click();", btn_close_cust_Selec);
		btn_close_cust_Selec.click();
		Thread.sleep(2000);
		btn_store_admin.click();
		list_logout.click();

	}

}
