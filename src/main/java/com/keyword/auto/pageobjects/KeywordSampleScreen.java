package com.keyword.auto.pageobjects;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datadriven.auto.webfunctions.AutomationProperties;
import com.keyword.auto.webfunctions.KeyWordFunctions;

public class KeywordSampleScreen
{
   protected static final Logger log = LoggerFactory.getLogger(KeywordSampleScreen.class);
   protected ArrayList<ArrayList<String>> testCase = new ArrayList<ArrayList<String>>();
   protected ArrayList<String> headerName = new ArrayList<String>();
   protected ArrayList<String> testStep = new ArrayList<String>();
   protected KeyWordFunctions keyFunctions = null;
   protected String url, browser, testName, testDescription;
   protected AutomationProperties aProperties = new AutomationProperties();


   public void quit()
   {
      keyFunctions.quit();
      log.info("Closing the browser");
   }


   public void setTheFunction(ArrayList<ArrayList<String>> excelData)
   {
      testCase = excelData;
      for (ArrayList<String> step : testCase)
      {
         testStep = step;
         switch (step.get(0))
         {
            case "startbrowser":
               startBrowser(step);
               break;
            case "quit":
               keyFunctions.quit();
               log.info("Completed the test case, closed the instance");
               break;
            default:
               getTheFunction(step);
               break;
         }
         testStep = null;
      }
   }


   private void startBrowser(ArrayList<String> step)
   {
      url = step.get(1);
      browser = step.get(2);
      if (url == null)
      {
         url = getParameter("url");
      }
      if (browser == null)
      {
         browser = getParameter("browser");
      }
      keyFunctions = KeyWordFunctions.buildWebDriver(url, browser, setTestInfromation());
   }


   protected ArrayList<String> setTestInfromation()
   {
      ArrayList<String> testInfo = new ArrayList<String>();
      if ( !(testStep.get(3) == null))
      {
         testInfo.add(testStep.get(3));
      }
      else
      {
         testInfo.add("Please update the test case name in 2nd row 4th column");
      }
      if ( !(testStep.get(4) == null))
      {
         testInfo.add(testStep.get(4));
      }
      else
      {
         testInfo.add("Please update the test case description in 2nd row 5th column");
      }
      return testInfo;
   }


   protected String getParameter(String parameter)
   {
      return aProperties.getValue(parameter);
   }


   private boolean getTheFunction(ArrayList<String> step)
   {
      boolean flag = false;
      String testStep = step.get(0);
      String element = step.get(1);
      String sendValue = step.get(2);
      String expectedResult = step.get(3);
      String otherInfo = step.get(4);
      switch (testStep.toLowerCase().replaceAll(" ", ""))
      {
         case "wait":
            int delay = delay(element, sendValue);
            keyFunctions.delay(delay);
            log.info("Hard delay upto {} second(s)", delay);
            break;
         case "iselementpresent":
            flag = keyFunctions.isElementPresent(element);
            log.info("{} Element located on the page: {}", element, String.valueOf(flag).toUpperCase());
            break;
         case "ischecked":
            flag = keyFunctions.isChecked(element);
            if ( !flag)
            {
               log.info("{} Check box state is {} --> Unchecked", element, String.valueOf(flag).toUpperCase());
            }
            else
            {
               log.info("{} Check box state is {} --> Checked", element, String.valueOf(flag).toUpperCase());
            }
            break;
         case "isvisible":
            flag = keyFunctions.isVisible(element);
            log.info("{} Element is visible on the page: {}", element, String.valueOf(flag).toUpperCase());
            break;
         case "skipiecertificate":
            if (keyFunctions.getBrowserName().equals("internetexplorer"))
            {
               keyFunctions.overrideIECertificateError();
               log.info("Skipped Certificate Warning");
            }
            else
            {
               log.info("Cannot Skip Certificate Warning, since {} is running", keyFunctions.getBrowserName());
            }
            break;
         case "pageloadwait":
            log.info("Waiting for the page to load");
            keyFunctions.waitForPageLoad();
            break;
         case "acceptalert":
            flag = keyFunctions.acceptAlert();
            log.info("Accepted the alert: {}", String.valueOf(flag).toUpperCase());
            break;
         case "check":
            keyFunctions.check(element);
            log.info("Selected the check box {}", element);
            break;
         case "clearfield":
            keyFunctions.clearField(element);
            log.info("Cleared the field {}", element);
            break;
         case "rightclick":
            keyFunctions.rightClick(element);
            log.info("Right clicked on {}", element);
            break;
         case "click":
            keyFunctions.click(element);
            log.info("Clicked on {}", element);
            break;
         case "clickbyaction":
            keyFunctions.clickByAction(element);
            log.info("Clicked on {}", element);
            break;
         case "clickusingjavascript":
            keyFunctions.clickUsingJavascript(element);
            log.info("Clicked on {}", element);
            break;
         case "quit":
            try
            {
               keyFunctions.quit();
               log.info("Closed the instance");
            } catch (Exception e)
            {
               log.info("Closing the instance");
               keyFunctions.quit();
            }
            break;
         case "closeslowly":
            keyFunctions.closeSlowly();
            log.info("Closing the instance slowly");
            break;
         case "closeallchildwindows":
            keyFunctions.closeAllChildWindows();
            log.info("Closed all child windows");
            break;
         case "delay":
            delay = delay(element, sendValue);
            keyFunctions.delay(delay);
            log.info("Hard delay upto {} second(s)", delay);
            break;
         case "dismissalert":
            keyFunctions.dismissAlert();
            log.info("Dismissed the alert");
            break;
         case "doubleclick":
            keyFunctions.doubleClick(element);
            log.info("Dobule clicked the element {}", element);
            break;
         case "firejsevent":
            keyFunctions.fireJsEvent(element, sendValue);
            break;
         case "focus":
            keyFunctions.focus(element);
            log.info("Shifted the focus to {}", element);
            break;
         case "goback":
            keyFunctions.goBack();
            log.info("Navigated to one step back");
            break;
         case "gototop":
            keyFunctions.goToTop();
            log.info("Scrolled to top of the current page");
            break;
         case "jsclick":
            keyFunctions.jsClick(element);
            log.info("Clicked on {}", element);
            break;
         case "keypress":
            keyFunctions.keyPress(element, sendValue);
            log.info("Value {} sent to the locator {}", sendValue, element);
            break;
         case "makeelementvisible":
            keyFunctions.makeElementVisible(element);
            log.info("{} element is visible now", element);
            break;
         case "draganddropanelement":
            keyFunctions.dragAndDropAnElement(element, sendValue, 30);
            log.info("Dragged the element from {} and dropped to {}", element, sendValue);
            break;
         case "movetoandclick":
            keyFunctions.moveToAndClick(element);
            log.info("Moved to {} element and clicked");
            break;
         case "open":
            keyFunctions.open(element);
            log.info("Opened the url, {}", element);
            break;
         case "overrideiecertificateerror":
            if (keyFunctions.getBrowserName().equals("internetexplorer"))
            {
               keyFunctions.overrideIECertificateError();
               log.info("Skipped Certificate Warning");
            }
            else
            {
               log.info("Cannot Skip Certificate Warning, since {} is running", keyFunctions.getBrowserName());
            }
            break;
         case "overrideedgecertificateerror":
            if (keyFunctions.getBrowserName().contains("edge"))
            {
               keyFunctions.overrideEdgeCertificateError();
               log.info("Skipped Certificate Warning");
            }
            else
            {
               log.info("Cannot Skip Certificate Warning, since {} is running", keyFunctions.getBrowserName());
            }
            break;
         case "reload":
            keyFunctions.reload();
            log.info("Reloaded the browser");
            break;
         case "scrollhorizontal":
            int a = Integer.parseInt(element);
            int b = Integer.parseInt(sendValue);
            keyFunctions.scrollHorizontal(a, b);
            log.info("Scrolled from {} to {}", a, b);
            break;
         case "scrollvertical":
            a = Integer.parseInt(element);
            b = Integer.parseInt(sendValue);
            keyFunctions.scrollVertical(a, b);
            log.info("Scrolled from {} to {}", a, b);
            break;
         case "select":
            keyFunctions.select(element, sendValue);
            log.info("Selected the value: {} from the locator {}", sendValue, element);
            break;
         case "selectframe":
            keyFunctions.selectFrame(element);
            log.info("Switched to frame {}", element);
            break;
         case "selectwindow":
            keyFunctions.selectWindow(element);
            log.info("Switched to window {}", element);
            break;
         case "sendkeys":
            keyFunctions.sendKeys(element, sendValue);
            log.info("Value {} sent to the locator {}", sendValue, element);
            break;
         case "setcheckboxstate":
            keyFunctions.setCheckBoxState(element, Boolean.valueOf(sendValue));
            log.info("{} Check box state is {}", element, Boolean.valueOf(sendValue));
            break;
         case "submit":
            keyFunctions.submit(element);
            log.info("Submitted the {}", element);
            break;
         case "switchtoframe":
            keyFunctions.switchToFrame(element);
            log.info("Switched to {} frame", element);
            break;
         case "switchtonewlyopenedwindow":
            keyFunctions.switchToNewlyOpenedWindow();
            log.info("Switched to newly opened window");
            break;
         case "switchtoparentwindow":
            keyFunctions.switchToParentWindow();
            log.info("Switched to parent window");
            break;
         case "type":
            keyFunctions.type(element, sendValue);
            log.info("Clearing locator field and Setting the locator {} with value: {}", element, sendValue);
            break;
         case "typewithoutclearing":
            keyFunctions.typeWithoutClearing(element, sendValue);
            log.info("Setting the locator {} with value: {}", element, sendValue);
            break;
         case "typeusingjavascript":
            break;
         case "typeusingrobot":
            break;
         case "uncheck":
            keyFunctions.uncheck(element);
            log.info("Deselecting the check box {}", element);
            break;
         case "waitforalert":
            delay = eDelay(sendValue);
            keyFunctions.waitForAlert(delay);
            log.info("Waited for alert on the screen for {} second(s)", delay);
            break;
         case "waitforelementfound":
            delay = eDelay(sendValue);
            keyFunctions.waitForElementFound(element, delay);
            log.info("Waited for {} to found on the screen", element);
            break;
         case "waitforelementnotfound":
            delay = eDelay(sendValue);
            keyFunctions.waitForElementNotFound(element, delay);
            log.info("Waited for {} to not found on the screen", element);
            break;
         case "waitforelementvisible":
            delay = eDelay(sendValue);
            keyFunctions.waitForElementVisible(element, delay);
            log.info("Waited for {} to become visible", element);
            break;
         case "windowmaximize":
            keyFunctions.windowMaximize();
            log.info("{} Window is Maximized", keyFunctions.getBrowserName());
            break;
         case "waitforelementclickable":
            delay = eDelay(sendValue);
            keyFunctions.waitForElementClickable(element, delay);
            log.info("Waited for clickable element {} on the screen", element);
            break;
         case "switchtodefaultcontent":
            keyFunctions.switchToDefaultContent();
            log.info("Switeched to default content");
            break;
         case "selectrandomitemfromdropdown":
            keyFunctions.selectRandomItemFromDropDown(element);
            log.info("Selected the random item from the drop down {}", element);
            break;
         case "selectlikeitemfromdropdown":
            keyFunctions.selectLikeItemFromDropDown(element, sendValue);
            log.info("Selected the item like {} from the drop down {}", sendValue, element);
            break;
         case "selectitemfromdropdown":
            keyFunctions.selectItemFromDropDown(element, sendValue);
            log.info("Selected the item {} from the drop down {}", sendValue, element);
            break;
         case "selectitemstartwithfromdropdown":
            keyFunctions.selectItemStartWithFromDropDown(element, sendValue);
            log.info("Selected the item starts with {} from the drop down {}", sendValue, element);
            break;
         case "close":
            keyFunctions.close();
            log.info("Closed the current window");
            break;
         case "scrolltoend":
            keyFunctions.scrollToEnd();
            log.info("Scrolled the page to end");
            break;
         case "scrolltotop":
            keyFunctions.scrollTotop();
            log.info("Scrolled the page to top");
            break;
         case "assertelementexist":
            keyFunctions.assertElementExist(element);
            break;
         case "asserttrue":
            String text = keyFunctions.getText(element).trim();
            keyFunctions.assertTrue(expectedResult, text, otherInfo);
            break;
         case "assertsame":
            int value = Integer.parseInt(keyFunctions.getText(element));
            int eValue = Integer.parseInt(expectedResult);
            keyFunctions.assertSame(eValue, value, otherInfo);
            break;
         case "assertequals":
            text = keyFunctions.getText(element).trim();
            keyFunctions.assertEquals(expectedResult, text, otherInfo);
            break;
         case "gettext":
            log.info("{} ", keyFunctions.getText(element).trim());
            break;
         case "gettitle":
            log.info("{} ", keyFunctions.getTitle());
            break;
         case "getcurrenturl":
            log.info("{}", keyFunctions.getCurrentUrl());
            break;
         case "getelementscount":
            log.info("{}", keyFunctions.getElementsCount(element));
            break;
         case "iselementpresentwithwait":
            delay = eDelay(sendValue);
            flag = keyFunctions.isElementPresentWithWait(element, delay);
            log.info("{} Element present on the screen after waiting for {} seconds: {}", element, delay, String.valueOf(flag).toUpperCase());
            break;
         case "isenabled":
            flag = keyFunctions.isEnabled(element);
            log.info("{} Element is enabled on the screen: {}", element, String.valueOf(flag).toUpperCase());
            break;
         case "istextpresent":
            break;
         case "istextpresentelement":
            flag = keyFunctions.isTextPresentElement(element, sendValue);
            log.info("Text { {} } present on the screen: {}", sendValue, String.valueOf(flag).toUpperCase());
            break;
         case "iselementvisiblewithwait":
            break;
         case "waitfortext":
            break;
         case "getnumberofopenwindows":
            break;
         case "isalertpresent":
            break;
         default:
            break;
      }
      return flag;
   }


   private int delay(String element, String sendValue)
   {
      Integer seconds = 1;
      try
      {
         if (element == null)
         {
            if (sendValue == null)
            {
               seconds = 1;
            }
            else
            {
               seconds = Integer.valueOf(sendValue);
            }
         }
         else
         {
            seconds = Integer.valueOf(element);
         }
      } catch (Exception e)
      {
         seconds = 1;
      }
      return seconds;
   }


   private int eDelay(String sendValue)
   {
      int seconds = 30;
      try
      {
         if ((sendValue == null) || (sendValue.equals("")))
         {
            seconds = 30;
         }
         else
         {
            seconds = Integer.valueOf(sendValue);
         }
      } catch (Exception e)
      {
         seconds = 30;
      }
      return seconds;
   }
}
