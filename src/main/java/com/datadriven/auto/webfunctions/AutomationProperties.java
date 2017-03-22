package com.datadriven.auto.webfunctions;

/* =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 * Author: Nagaraju.Meka
 * File: AutomationProperties.java
 * Created: 11/25/16
 * Description: Class to help normalize startup and usage of
 * retrieving the objects from Jenkins/Properties file.
 * -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutomationProperties
{
   protected static final Logger log = LoggerFactory.getLogger(AutomationProperties.class);
   protected InputStream inputStream;
   protected Properties pro = new Properties();


   /**
    * Returns the runtime value from defined properties file, parameter value
    *
    * @param browser - type of browser to automate
    * @param application - application name to automate
    * @param url - application url to automate
    * @return String, value
    */
   public String getValue(String propertiesFileName, String parameter)
   {
      String value = System.getProperty(parameter);
      if (value == null || value.isEmpty())
      {
         try
         {
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
            pro.load(inputStream);
            value = pro.getProperty(parameter);
            inputStream.close();
         } catch (Exception e)
         {}
      }
      return value;
   }


   /**
    * Returns the runtime value from src/test/resources/project.properties file, parameter value
    *
    * @param browser - type of browser to automate
    * @param application - application name to automate
    * @param url - application url to automate
    * @return String, value
    */
   public String getValue(String parameter)
   {
      String value = System.getProperty(parameter);
      if (value == null || value.isEmpty())
      {
         try
         {
            inputStream = getClass().getClassLoader().getResourceAsStream("project.properties");
            pro.load(inputStream);
            value = pro.getProperty(parameter);
            inputStream.close();
         } catch (Exception e)
         {}
      }
      return value;
   }
}