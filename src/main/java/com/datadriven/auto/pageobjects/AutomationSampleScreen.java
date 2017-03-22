package com.datadriven.auto.pageobjects;

/* =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 * Author: Nagaraju.Meka
 * File: SampleScreen.java
 * Created: 11/25/16
 * Description: Class to help normalize startup and usage of
 * retrieving the objects from Jenkins/Properties file.
 * -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datadriven.auto.webfunctions.WebFunctions;

public class AutomationSampleScreen
{
   protected static final Logger log = LoggerFactory.getLogger(AutomationSampleScreen.class);
   protected WebFunctions webController;
   public static final String SEARCHMENU = "css=input[title='Search']";


   public void setWebFunctions(WebFunctions webFunctions)
   {
      this.webController = webFunctions;
   }


   public boolean checkAlert()
   {
	   webController.click("");
      return webController.isAlertPresent();
   }
}
