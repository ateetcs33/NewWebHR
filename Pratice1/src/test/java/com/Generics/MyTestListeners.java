package com.Generics;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.LogStatus;


public class MyTestListeners implements ITestListener,ISuiteListener
{
	String path=System.getProperty("user.dir").replace("\\", "\\");
	File backup;
	String currentStamp;
	String currentTimeStamp;
	
	public String getDate()
	{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}
	public String getDateStamp()
	{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("HH-mm-ss");
		return sdf.format(date);
	}
	public int getCurrentDate()
	{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		sdf.format(date);
		return date.getDate();
	}
	public String getDateSetup()
	{
		int sysMon=0;
		String reportDate;
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("-MM-yyyy");
		return sdf.format(date);
	}
	public void deletePrevoiousReports(String backupPath)
	{
		String monthTime = getDateSetup();
		File pathToBackup=new File(backupPath);
		String[] reportsFolders = pathToBackup.list();
		if(reportsFolders.length>0)
		{
			for(int i=0;i<reportsFolders.length;i++)
			{
				if(!reportsFolders[i].contains(monthTime))
				{
					String date = reportsFolders[i].split("-")[0].split("_")[1];
					int backupReportsDate=Integer.parseInt(date);
					if(backupReportsDate<getCurrentDate())
					{
						try
						{
							FileUtils.deleteDirectory(new File(backupPath+"\\"+reportsFolders[i]));
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Test is passed");
		String passScreenPath=path+"\\backUp\\Reports_"+currentStamp+"\\ScreenShots\\Pass_Tests";
		String timeStamp = getDateStamp();
		Object currentClass = result.getInstance();
		File screenshots = new File(passScreenPath);
		if(!screenshots.exists())
		{
			screenshots.mkdir();
		}
		File f = ((TakesScreenshot)((BaseClass)currentClass).getDriver()).getScreenshotAs(OutputType.FILE);
		try
		{
			FileUtils.copyFile(f, new File(passScreenPath+"\\screenshot_"+timeStamp+".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		String image = BaseClass.logger.addScreenCapture(passScreenPath+"\\screenshot_"+timeStamp+".png");
		BaseClass.logger.log(LogStatus.PASS, "Test Script is passed");
		BaseClass.logger.log(LogStatus.INFO, image);
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Test is failed");
		String failScreenPath=path+"\\backUp\\Reports_"+currentStamp+"\\ScreenShots\\Fail_Tests";
		String timeStamp = getDateStamp();
		//String screen="../backUp/Reports_"+currentStamp+"/ScreenShots/Fail_Tests/screenshot_"+timeStamp+".png\" || \"../Reports_"+currentStamp+"/ScreenShots/Fail_Tests/screenshot_"+timeStamp+".png";
		Object currentClass = result.getInstance();
		String exceptionInfo = result.getThrowable().getMessage();
		String exceptionCause = result.getThrowable().getCause().toString();
		File screenshots = new File(path+"\\ScreenShots\\Fail_Tests");
		if(!screenshots.exists())
		{
			screenshots.mkdir();
		}
		File f = ((TakesScreenshot)((BaseClass)currentClass).getDriver()).getScreenshotAs(OutputType.FILE);
		try
		{
			FileUtils.copyFile(f, new File(failScreenPath+"\\screenshot_"+timeStamp+".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		String image = BaseClass.logger.addScreenCapture(failScreenPath+"\\screenshot_"+timeStamp+".png");
		BaseClass.logger.log(LogStatus.FAIL, "REASON FOR TEST SCRIPT FAIL:"+exceptionInfo);
		BaseClass.logger.log(LogStatus.FAIL, exceptionCause);
		BaseClass.logger.log(LogStatus.INFO, image);
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("Test is skipped");
		String failScreenPath=path+"\\backUp\\Reports_"+currentStamp+"\\ScreenShots\\Skip_Tests";
		String timeStamp = getDateStamp();
		Object currentClass = result.getInstance();
		File screenshots = new File(path+"\\Reports\\ScreenShots\\Skip_Tests");
		if(!screenshots.exists())
		{
			screenshots.mkdir();
		}
		File f = ((TakesScreenshot)((BaseClass)currentClass).getDriver()).getScreenshotAs(OutputType.FILE);
		try
		{
			//System.out.println(path+"\\ScreenShots\\Fail_Tests"+"screenShot.png");
			FileUtils.copyFile(f, new File(failScreenPath+"\\screenshot_"+timeStamp+".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		String image = BaseClass.logger.addScreenCapture(failScreenPath+"\\screenshot_"+timeStamp+".png");
		BaseClass.logger.log(LogStatus.SKIP, "Test Script is skipped");
		BaseClass.logger.log(LogStatus.INFO, image);
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ISuite suite) 
	{
		String backupPath=path+"\\backUp";
		deletePrevoiousReports(backupPath);
		currentStamp = getDate();
		currentTimeStamp = getDateStamp();
		File screenshots = new File(path+"\\backUp\\Reports_"+currentStamp+"\\ScreenShots");
		if(!screenshots.exists())
		{
			screenshots.mkdir();
		}
		backup=new File(path+"\\backUp\\Reports_"+currentStamp+"\\Execution_Report_"+currentTimeStamp);
		if(!backup.exists())
		{
			backup.mkdir();
		}
	}

	public void onFinish(ISuite suite) 
	{
		try 
		{
			FileUtils.copyDirectory(new File(path+"\\Reports"),new File(path+"\\backUp\\Reports_"+currentStamp+"\\Execution_Report_"+currentTimeStamp));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}