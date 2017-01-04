package com.basic.smoke;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.Generics.BaseClass;
import com.relevantcodes.extentreports.LogStatus;


public class Script2 extends BaseClass
{
	public String description="Executing Script2";
	@Test
	public void test()
	{
		logger.log(LogStatus.INFO, "Script2 is started executing");
		//logger.log(LogStatus.FAIL, "Script2 is failed");
		logger.log(LogStatus.INFO, "Script2 is ended executing");
		//Assert.fail();
		
	}
}
