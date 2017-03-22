package com.keyword.auto.test;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.datadriven.auto.webfunctions.AutomationProperties;
import com.keyword.auto.pageobjects.KeywordSampleScreen;
import com.keyword.auto.webfunctions.ExcelTestReader;
import com.keyword.auto.webfunctions.ExcelTestSuiteReader;

public class KeywordSampleTest extends KeywordSampleScreen
{
   protected static final Logger log = LoggerFactory.getLogger(KeywordSampleTest.class);
   String excelReader = ("src/test/resources/TestSuite.xls");
   protected ExcelTestReader excelTest = new ExcelTestReader();
   protected ExcelTestSuiteReader excelTestSuite = new ExcelTestSuiteReader();
   protected ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
   protected ArrayList<ArrayList<String>> testCase = new ArrayList<ArrayList<String>>();
   protected ArrayList<String> headerName = new ArrayList<String>();
   protected String testScript, suite;
   AutomationProperties aProperties = new AutomationProperties();


   @BeforeTest
   public void beforeTest()
   {
      testCase = excelTestSuite.getTestCasesData(excelReader);
      suite = aProperties.getValue("suite");
   }


   @AfterTest
   public void afterTest()
   {
      log.info("Completed the Test Suite {}", excelReader);
      quit();
   }


   @Test
   public void testAllExcels()
   {
      if ((suite.equalsIgnoreCase("sanity") || (suite.equalsIgnoreCase("regression"))))
      {
         log.info("Executing the {} Test Suite", WordUtils.capitalizeFully(suite));
         runTest();
      }
      else
      {
         for (ArrayList<String> test : testCase)
         {
            if (test.get(2).toLowerCase().trim().equals("yes"))
            {
               testScript = test.get(1);
               testSampleTest();
            }
            else
            {
               log.info("=========================================================");
               log.info("Test: {}", test.get(0));
               log.info("Since { {} } test case run mode is { {} }, not executing the test case", test.get(0), WordUtils.capitalizeFully(test.get(2)));
               log.info("=========================================================");
            }
            headerName = new ArrayList<String>();
            data = new ArrayList<ArrayList<String>>();
         }
      }
   }


   private void runTest()
   {
      for (ArrayList<String> test : testCase)
      {
         if (test.get(3).trim().equalsIgnoreCase(suite))
         {
            if (test.get(2).toLowerCase().trim().equals("yes"))
            {
               testScript = test.get(1);
               testSampleTest();
            }
            else
            {
               log.info("=========================================================");
               log.info("Test: {}", test.get(0));
               log.info("Since { {} } test case run mode is: { {} }, not executing the test case", test.get(0), WordUtils.capitalizeFully(test.get(2)));
               log.info("=========================================================");
            }
            headerName = new ArrayList<String>();
            data = new ArrayList<ArrayList<String>>();
         }
      }
   }


   protected void testSampleTest()
   {
      String excelRun = testScript;
      headerName = excelTest.getHeadder(excelRun);
      data = excelTest.getExcelData(excelRun);
      setTheFunction(data);
   }
}
