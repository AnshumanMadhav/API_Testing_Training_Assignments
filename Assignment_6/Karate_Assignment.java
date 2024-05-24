package support;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Karate_Assignment
{

public static int get_pet_id(String Runtime_TC_ID) throws IOException
{
    int xl_pet_id = 0;
    FileInputStream fis = new FileInputStream(new File("C:/Users/anshumanm/Downloads/Pet_Details_Karate_Assignment.xlsx"));
    XSSFWorkbook wb = new XSSFWorkbook(fis);
    XSSFSheet sheet = wb.getSheetAt(0);
    for (Row row : sheet)
    {
     if (row.getRowNum() != 0)
     {
       String XL_TC_ID = row.getCell(0).getStringCellValue();
       if(XL_TC_ID.equals(Runtime_TC_ID))
       {
           xl_pet_id = (int) row.getCell(1).getNumericCellValue();
           break;
       }
     }
    }
    return xl_pet_id;
}

    public static String get_pet_name(String Runtime_TC_ID) throws IOException
    {
        String xl_pet_name = null;
        FileInputStream fis = new FileInputStream(new File("C:/Users/anshumanm/Downloads/Pet_Details_Karate_Assignment.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        for (Row row : sheet)
        {
            if (row.getRowNum() != 0)
            {
                String XL_TC_ID = row.getCell(0).getStringCellValue();
                if(XL_TC_ID.equals(Runtime_TC_ID))
                {
                    xl_pet_name = row.getCell(2).getStringCellValue();
                    break;
                }
            }
        }
        return xl_pet_name;
    }
public static String post_pet_details(int petID,String petName)
{
    String post_body = null;
    try
    {
     post_body= "{\n" +
                "    \"id\":\""+petID+"\",\n" +
                "    \"category\":{\"id\":0,\"name\":\"string\"},\n" +
                "    \"name\":\""+petName+"\",\n" +
                "    \"photoUrls\":[\"string\"],\n" +
                "    \"tags\":[{\"id\":0,\"name\":\"string\"}],\n" +
                "    \"status\":\"available\"\n" +
                "}";
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return post_body;
}
}
