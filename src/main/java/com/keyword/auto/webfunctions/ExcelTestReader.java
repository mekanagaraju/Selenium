package com.keyword.auto.webfunctions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelTestReader
{
   protected static final Logger log = LoggerFactory.getLogger(ExcelTestReader.class);


   public ArrayList<ArrayList<String>> getExcelData(String fileNameXLS)
   {
      ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
      ArrayList<String> rowData = new ArrayList<String>();
      Workbook wb;
      try
      {
         wb = Workbook.getWorkbook(new File(fileNameXLS));
         Sheet sh = wb.getSheet(0);
         int totalNoOfRows = sh.getRows();
         int totalNoOfCol = sh.getColumns();
         for (int row = 1; row < totalNoOfRows; row++)
         {
            boolean flag = false;
            cool: for (int col = 0; col < 5; col++)
            {
               String s = sh.getCell(col, row).getContents().toString().trim();
               if ((col == 0) && (s.equals("")))
               {
                  flag = true;
                  break cool;
               }
               if (s.equals(""))
               {
                  s = null;
               }
               rowData.add(s);
            }
            if ( !flag)
            {
               data.add(rowData);
               rowData = new ArrayList<String>();
            }
         }
      } catch (BiffException | IOException e)
      {}
      return data;
   }


   public ArrayList<String> getHeadder(String fileNameXLS)
   {
      ArrayList<String> headerName = new ArrayList<String>();
      Workbook wb;
      try
      {
         wb = Workbook.getWorkbook(new File(fileNameXLS));
         Sheet sh = wb.getSheet(0);
         for (int col = 0; col < 5; col++)
         {
            headerName.add(sh.getCell(col, 0).getContents().toString().trim());
         }
      } catch (BiffException | IOException e)
      {}
      return headerName;
   }
}
