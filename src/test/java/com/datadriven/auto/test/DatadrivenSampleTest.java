package com.datadriven.auto.test;

import java.io.IOException;
/* =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 * Author: Nagaraju.Meka
 * File: SampleTest.java
 * Created: 11/25/16
 * Description: Class to help normalize startup and usage of
 * retrieving the objects from Jenkins/Properties file.
 * -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.datadriven.auto.base.AutomationBaseTest;
import com.datadriven.auto.expect.Expect;
import com.datadriven.auto.pageobjects.AutomationSampleScreen;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DatadrivenSampleTest extends AutomationBaseTest
{
   protected static final Logger log = LoggerFactory.getLogger(DatadrivenSampleTest.class);
   private final AutomationSampleScreen ss = new AutomationSampleScreen();
   ExtentReports report;
   ExtentTest logger;


   @BeforeMethod
   public void setBeforeTest(Method method)
   {
      aController.launchBrowser(method);
      ss.setWebFunctions(aController.getwebController());
      setWebFunctions(aController.getwebController());
      String reportNmae = this.getClass().getName() + aController.getwebController().getSystemTime("h:mm a");
      report = new ExtentReports("C:\\Reports\\" + reportNmae + ".html");
      logger = report.startTest(method.getName());
   }


   @AfterTest
   public void closeBrowser(ITestResult result)
      throws IOException
   {
      if (result.getStatus() == ITestResult.FAILURE)
      {
         String location = aController.getwebController().captureScreenshot(result.getTestName());
         String image = logger.addScreenCapture(location);
         logger.log(LogStatus.FAIL, result.getTestName(), image);
      }
      report.endTest(logger);
      report.flush();
      log.info("Closing the browser");
      aController.quitBrowser();
   }


   @Test(description = "Sample test case", groups = "sample-test", priority = 1)
   public void testSampleTest()
   {
      logger.log(LogStatus.INFO, "Browser Started");
      log.info("Alert present: {}", ss.checkAlert());
      assertTrue( !ss.checkAlert(), "");
      assertElementExist(AutomationSampleScreen.SEARCHMENU);
      Expect.expect("{Actual Message} contains {Expected Message}", "www.google.com", "www.google.com");
      logger.log(LogStatus.PASS, "Validation done");
   }
}
