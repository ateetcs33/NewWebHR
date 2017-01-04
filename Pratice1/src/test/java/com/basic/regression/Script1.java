package com.basic.regression;


import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.Generics.BaseClass;
import com.relevantcodes.extentreports.LogStatus;


public class Script1 extends BaseClass
{
	public String description="Executing Script1";
	@Test
	public void test()
	{
		logger.log(LogStatus.INFO, "Script1 is started executing");
		logger.log(LogStatus.PASS, "Script1 is passed");
		logger.log(LogStatus.INFO, "Script1 is ended executing");
		driver.findElement(By.id("dfdfdd"));
	}
}
