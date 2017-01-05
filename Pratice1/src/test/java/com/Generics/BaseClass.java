package com.Generics;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import java.io.File;
import java.lang.reflect.Method;

public class BaseClass
{
	protected WebDriver driver;
	ExtentReports report;
	public static ExtentTest logger;
	String path=System.getProperty("user.dir").replace("\\", "\\");
	@BeforeSuite
	public void createExtentFile()
	{
		File f=new File(path+"\\Reports");
		if(f.exists())
		{
			try
			{
				FileUtils.deleteDirectory(f);
			}
			catch(Exception e)
			{}
		}
		f.mkdir();
		f=new File(path+"\\Reports\\report.html");
		try
		{
			f.createNewFile();
		}
		catch(Exception e)
		{}
	}
	@BeforeClass
	public void beforeClass()
	{
		report=new ExtentReports(path+"\\Reports\\report.html",false);
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("http://www.seleniumeasy.com/");
		
		Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
		report.addSystemInfo("Browser_Name", cap.getBrowserName());
		report.addSystemInfo("Browser_Version", cap.getVersion());
	}
	@BeforeMethod
	public void beforeMethod(Method method)
	{
		String categoryName = this.getClass().getCanonicalName().split("basic.")[1].split("\\.")[0];
		String testClassName=this.getClass().getSimpleName();
		String testMethodName = method.getName();
		String testDescription = null;
		try
		{
			testDescription = this.getClass().getDeclaredField("description").get(this).toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		logger=report.startTest(testClassName+"_"+testMethodName, testDescription);
		logger.assignCategory(categoryName);
	}
	@AfterMethod(alwaysRun=true)
	public void afterMethod()
	{
		report.endTest(logger);
		report.flush();
	}
	@AfterClass(alwaysRun=true)
	public void afterClass()
	{
		driver.quit();
	}
	@AfterSuite
	public void afterSuite()
	{
		report.close();
	}
	public WebDriver getDriver()
	{
		return driver;
	}
}
