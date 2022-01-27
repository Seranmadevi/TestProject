package cbtsmoke;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import mainpackage.CBTClass;
import pageobjectpackage.ItemSelection;
import pageobjectpackage.LoginObjects;
import pageobjectpackage.NewCustomer;
import pageobjectpackage.Payment;

public class CBT extends CBTClass{
	
	@Test(dataProvider="LoginData", description = "CashWrap_Login", priority = 1, enabled = true)
	public void Login(String UserName,String Password) {
		logger = extent.startTest("Login");
		LoginObjects obj=new LoginObjects(driver,logger);
		obj.login(UserName,Password);
	}
	
	@Test(dataProvider="CustomerData", description = "NewCustomer", priority = 2, enabled = true)
	public void New_Cust_Creation(String PhoneNum,String Name,String Email) throws Exception {
		logger = extent.startTest("New CUstomer Creation");
		NewCustomer obj=new NewCustomer(driver,logger);
		obj.Customer_creation(PhoneNum,Name,Email);
		ItemSelection itemobj=new ItemSelection(driver,logger);
		itemobj.click_view();
	}
	
	@Test(dataProvider="ItemData", description = "Item_Selection", priority = 3, enabled = true)
	public void ItemSelection(String ItemNo,String ItemCode,String ItemName,String ItemQty,String ActualAmount) throws Exception {
		logger = extent.startTest("ItemSelection");
		ItemSelection itemobj=new ItemSelection(driver,logger);
		itemobj.item_Select_2(ItemNo,ItemCode,ItemName,ItemQty,ActualAmount);
		
	}
	

	@Test(dataProvider="PaymentData",description = "Payment_cash_card", priority = 4, enabled = true)
	public void PaymentType(String CardAmount) throws Exception {
		logger = extent.startTest("PaymentType");
		Payment pay=new Payment(driver,logger);
		pay.pay_Cash_Card(CardAmount);
	} 
	
	
	@Test(dataProvider="TotalData",description = "Payment_cash_card", priority = 5, enabled = false)
	public void TotalType(String Total,String ManualCARD,String AutoCalculation,String GetCardAMount,String GetCashAMount) throws InterruptedException {
		logger = extent.startTest("TotalType");
		System.out.println(Total);
	} 
	
	@DataProvider(name="TotalData")
	public Object[][] TotalData(){Object[][] data = getExcelData("TestData.xlsx", "Total");
		return data;
	}

	
	@DataProvider(name="LoginData")
	public Object[][] sampledata(){Object[][] data = getExcelData("TestData.xlsx", "Login");
		return data;
	}
	
	@DataProvider(name="CustomerData")
	public Object[][] CustomerData(){Object[][] data = getExcelData("TestData.xlsx", "Customer");
		return data;
	}
	
	@DataProvider(name="PaymentData")
	public Object[][] PaymentData(){Object[][] data = getExcelData("TestData.xlsx", "Payment");
		return data;
	}
	
	@DataProvider(name="ItemData")
	public Object[][] ItemData(){Object[][] data = getExcelData("TestData.xlsx", "Item");
		return data;
	}

}
