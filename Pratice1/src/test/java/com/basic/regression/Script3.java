package com.basic.regression;


import org.testng.annotations.Test;

import com.Generics.BaseClass;
import com.relevantcodes.extentreports.LogStatus;


public class Script3 extends BaseClass
{
	public String description="Executing Script3";
	@Test
	public void test()
	{
		logger.log(LogStatus.INFO, "Script3 is started executing");
		//logger.log(LogStatus.SKIP, "Script3 is skipped");
		logger.log(LogStatus.INFO, "Script3 is ended executing");
	}
}
