package pageobjectpackage;

import org.openqa.selenium.By;
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

public class ItemSelection {
	WebDriver driver;
	ExtentTest logger;
	ReusableFunction ReusFun = new ReusableFunction(driver, logger);

	@FindBy(xpath = "//button[@id='btnitemView']")
	WebElement btn_view;

	@FindBy(xpath = "//select[@id='ddlitem']")
	WebElement slt_value;

	@FindBy(xpath = "(//div[@class='blobselect-button'])[1]")
	WebElement slt_drp_down;

	@FindBy(xpath = "(//div[@class='blobselect-item-search'])[1]")
	WebElement slt_search;

	@FindBy(xpath = "//input[@id='txtQty']")
	WebElement inp_quantity;

	@FindBy(xpath = "//button[@id='btnProductAdd']")
	WebElement btn_add;

	@FindBy(xpath = "//tr[@onclick='RowItemList(this.id,0)']/td[2]")
	WebElement itemcode_1;

	@FindBy(xpath = "//tr[@id='IS13064_1']/td[3]")
	WebElement itemname_1;

	@FindBy(xpath = "//tr[@id='IS13064_1']/td[4]")
	WebElement itemqty_1;

	@FindBy(xpath = "//tr[@id='IS13064_1']/td[5]")
	WebElement itemrate_1;

	@FindBy(xpath = "//tr[@id='IS13064_1']/td[6]")
	WebElement itemamount_1;

	// @FindBy(xpath="//input[@id='txtRate']")
	@FindBy(xpath = "//input[@id='hdnRate']")
	WebElement actualrat;

	@FindBy(xpath = "(//div[@class='blobselect-button'])[1]")
	WebElement search_customer_item_arrow;

	@FindBy(xpath = "(//div[@class='blobselect-item-search'])[1]")
	WebElement search_customer_item_search;

	@FindBy(xpath = "(//div[@class='blobselect-button'])[2]")
	WebElement search_customer_group_arrow;

	public ItemSelection(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
		PageFactory.initElements(driver, this);
	}

	public void item_Select(String selectValue) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		btn_view.click();
		// ReusFun.SELECTBYVALUE(slt_value, selectValue);
		slt_drp_down.click();
		slt_search.sendKeys("IS13064");
		slt_search.sendKeys(Keys.ENTER);
		inp_quantity.sendKeys("2");
		btn_add.click();

	}

	public void new_cust_item_Select(String selectValue, String PhoneNum) throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);

			wait.until(ExpectedConditions.visibilityOf(btn_view));
			btn_view.click();
			// ReusFun.SELECTBYVALUE(slt_value, selectValue);
			wait.until(ExpectedConditions.visibilityOf(slt_drp_down));

			slt_drp_down.click();
			slt_search.sendKeys("IS13064");
			slt_search.sendKeys(Keys.ENTER);
			inp_quantity.sendKeys("2");
			btn_add.click();
			// slt_drp_down.click();

			// Actions action = new Actions(driver);
			// action.moveToElement(search_customer_item_arrow).click().moveToElement(search_customer_item_search).click().sendKeys("IS13064").sendKeys(Keys.ENTER).build().perform();

			// action.moveToElement(slt_drp_down).click().sendKeys("IS13064").sendKeys(Keys.ENTER).build().perform();
			// action.sendKeys(Keys.TAB).sendKeys(Keys.ARROW_DOWN).sendKeys("IS13064").sendKeys(Keys.ENTER).build().perform();

			// slt_search.sendKeys("IS13064");
			// slt_search.sendKeys(Keys.ENTER);
			// inp_quantity.sendKeys("2");
			// btn_add.click();

			/*
			 * String actualcode=itemcode_1.getText(); String expectedcode="IS13064";
			 * Assert.assertEquals(actualcode, expectedcode);
			 * 
			 * String actualname=itemname_1.getText(); String
			 * expectedname="ON & ON BDY BTR CRM"; Assert.assertEquals(actualname,
			 * expectedname);
			 */
			String actualqty = itemqty_1.getText();
			String expectedqty = "2";
			Assert.assertEquals(actualqty, expectedqty);
			float qty = Float.parseFloat(actualqty);

			String actualrate1 = itemrate_1.getText();
			String actualrate2[] = actualrate1.split(" ");
			for (String name : actualrate2) {
				System.out.println(name);
			}

			String actualrate = actualrate2[1];
			System.out.println("String actual rate" + actualrate);
			// String expectedrate="600.00";

			// Assert.assertEquals(actualrate, expectedrate);
			float rate = Float.parseFloat(actualrate.trim());

			System.out.println("Integer rate :" + rate);
			float expectedamount = qty * rate;

			String actualamount1 = itemamount_1.getText();
			String actualamount2[] = actualamount1.split(" ");
			// String expectedamount="195.00";
			// Assert.assertEquals(actualamount, expectedamount);

			float actualamt = Float.parseFloat(actualamount2[1]);
			System.out.println(actualamt);
			Assert.assertEquals(actualamt, expectedamount);

		} catch (Exception e) {
			System.out.println("Item not selected successfully");
		}
	}

	public void click_view() {
		/** Click view button ***/
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(btn_view));
		btn_view.click();
	}

	public void item_Select_2(String ItemNo, String ItemCode, String ItemName, String ItemQty, String ActualAmount)
			throws Exception {
		
		/***** Select items from drop down*****/
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(slt_drp_down));
		slt_drp_down.click();
		Thread.sleep(1500);
		slt_search.sendKeys(ItemCode);
		Thread.sleep(1700);
		slt_search.sendKeys(Keys.ENTER);
		inp_quantity.sendKeys(ItemQty);
		String expectedrate = actualrat.getAttribute("value");
		// System.out.println("Expected rate "+expectedrate);
		btn_add.click();

		/**** COmpare ItemNo, ItemName,ItemQty,ExpectedRate *********/
		item_Verify(ItemNo, ItemName, ItemCode, ItemQty, expectedrate, ActualAmount);

	}

	public void item_Verify(String ItemNo, String ItemName, String ItemCode, String ItemQty, String expectedrate,
			String ActualAmount) throws Exception {
		/**** COmpare ItemNo, ItemName,ItemQty,ExpectedRate *********/
		int Itemno1 = Integer.parseInt(ItemNo.trim());
		// Itemno1=Itemno1-1;
		// String Itemno2=String.valueOf(Itemno1);

		/*** Item COde ***/
		WebElement itemcode_1 = driver.findElement(By.xpath("//tr[@id='" + ItemCode + "_" + ItemNo + "']/td[2]"));
		// System.out.println(itemcode_1);
		String actualcode = itemcode_1.getText();
		Assert.assertEquals(actualcode, ItemCode);

		/*** Item Name ***/
		WebElement itemname_1 = driver.findElement(By.xpath("//tr[@id='" + ItemCode + "_" + ItemNo + "']/td[3]"));
		String actualname = itemname_1.getText();
		Assert.assertEquals(actualname, ItemName);

		/*** Item Quantity ****/
		WebElement itemqty_1 = driver.findElement(By.xpath("//tr[@id='" + ItemCode + "_" + ItemNo + "']/td[4]"));
		String actualqty = itemqty_1.getText();
		// System.out.println("String actual Qty :"+actualqty);
		Assert.assertEquals(actualqty, ItemQty);
		float qty = Float.parseFloat(actualqty);

		/***** Item Rate ****/
		WebElement itemrate_1 = driver.findElement(By.xpath("//tr[@id='" + ItemCode + "_" + ItemNo + "']/td[5]"));
		String actualrate1 = itemrate_1.getText();
		String actualrate2[] = actualrate1.split(" ");
		/*
		 * for(String name : actualrate2){ System.out.println(name); }
		 * 
		 */
		String actualrate = actualrate2[1];
		// System.out.println("String actual rate :"+actualrate);
		Assert.assertEquals(actualrate, expectedrate);
		float rate = Float.parseFloat(actualrate.trim());

		/**** Item amt calculation ***/
		// System.out.println("Integer rate :"+rate);
		float expectedamount = qty * rate;
		WebElement itemamount_1 = driver.findElement(By.xpath("//tr[@id='" + ItemCode + "_" + ItemNo + "']/td[6]"));
		String actualamount1 = itemamount_1.getText();
		String actualamount2[] = actualamount1.split(" ");

		/** Getting values from the screen ****/
		float actualamt = Float.parseFloat(actualamount2[1]);
		// System.out.println("Actual AMount :"+actualamt);
		Assert.assertEquals(actualamt, expectedamount);

		String excelactual = String.valueOf(actualamt);
		int rownum; // =Integer.parseInt(ItemNo);

		rownum = Itemno1 + 1;
		//System.out.println("Rownum " + rownum);
		//System.out.println("excelactual " + excelactual);
		String excelLocation = System.getProperty("user.dir") + "/src/test/resources/" + "TestData.xlsx";
		// System.out.println(excelLocation);
		WriteExcelApiTest eat = new WriteExcelApiTest(excelLocation);
		eat.setCellData("Item", "ActualAmount", rownum, excelactual);

	}
}

//WebElement cust_row=driver.findElement(By.xpath("//div/table[@id='tblVendorList']/tbody/tr[contains(@id,'"+Phonenum+"')]"));

// tr[@onclick='RowItemList(this.id,0)']
// WebElement
// itemcode_1=driver.findElement(By.xpath("//tr[@onclick='RowItemList(this.id,"+Itemno2+")']/td[2]"));
// tr[@id='IS13064_1']