package Rest_API_Assignment;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Read_Excel_File
{
    public static Map<Integer,String> Store_XL_Data_in_Hashmap() throws IOException
    {
        FileInputStream file = new FileInputStream("C:/Users/anshumanm/Downloads/Pet_Details.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sh = wb.getSheet("Sheet1");
        Map<Integer, String> pet_details_map = new HashMap<>();
        for (int i = 1; i <= sh.getLastRowNum();i++)
        {
            int Pet_ID = (int)sh.getRow(i).getCell(0).getNumericCellValue();
            String Pet_Name = sh.getRow(i).getCell(1).getStringCellValue();
            pet_details_map.put(Pet_ID,Pet_Name);
        }
        return pet_details_map;
    }

}
