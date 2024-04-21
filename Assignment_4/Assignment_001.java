package Rest_Assured_Assignments;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;

public class Assignment_001
{

    Response response;
    private ExtentSparkReporter spark;
    private ExtentReports extent;
    private ExtentTest logger;

    @BeforeClass
    public void report_setup()
    {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/Report/Phone_Details_Validation.html");
        spark.config().setDocumentTitle("Phone Details Validation");
        spark.config().setReportName("Phone_Details");
        spark.config().setTheme(Theme.DARK);
        logger = extent.createTest("Validate Phone Details");
        extent.attachReporter(spark);
        extent.setSystemInfo("QA_Name","Anshuman");
        extent.setSystemInfo("Build_Name","Version_1.0");
        extent.setSystemInfo("Environment_Name","QA");
    }

    @Parameters({"title","price","brand","image_count"})
    @Test()
    public void display_details(String expected_title,String expected_price,String expected_brand,String expected_image_count)
    {
        response = get("https://dummyjson.com/products/1");
        ArrayList<String> actual_image = new ArrayList<>();
        String actual_title = response.getBody().jsonPath().getString("title");
        String actual_price = response.getBody().jsonPath().getString("price");
        String actual_brand = response.getBody().jsonPath().getString("brand");
        int actual_image_count =  response.getBody().jsonPath().getList("images").size();
        int actual_status_code = response.statusCode();
        //Assert.assertEquals(expected_title,actual_title);
        //Assert.assertEquals(expected_price,actual_price);
        //Assert.assertEquals(expected_brand,actual_brand);
        //Assert.assertEquals(expected_image_count,String.valueOf(actual_image_count));
        if(actual_status_code == 200)
        {
          logger.pass("Status Code is populating correctly as " +actual_status_code);
        }
        else
        {
          logger.fail("Status Code is populating as " +actual_status_code);
        }
        if(expected_title.equals(actual_title))
        {
            logger.pass("Title is populating as " +actual_title+ " as expected");
        }
        else
        {
            logger.fail("Title mismatch! Expected Title is: " +expected_title+ " But Actual Title is: " +actual_title);
        }
        if(expected_price.equals(actual_price))
        {
            logger.pass("Price is populating as " +actual_price+ " as expected");
        }
        else
        {
            logger.fail("Price mismatch! Expected Price is: " +expected_price+ " But Actual Price is: " +actual_price);
        }
        if(expected_brand.equals(actual_brand))
        {
            logger.pass("Brand is populating as " +actual_brand+ " as expected");
        }
        else
        {
            logger.fail("Brand mismatch! Expected Brand is: " +expected_brand+ " But Actual Brand is: " +actual_brand);
        }
        if(expected_image_count.equals(String.valueOf(actual_image_count)))
        {
            logger.pass("Image Count is " +actual_image_count+ " as expected");
        }
        else
        {
            logger.fail("Image Count mismatch! Expected Count is: " +expected_image_count+ " But Actual Count is: " +actual_image_count);
        }

        for(int a = 0;a < actual_image_count; a++)
        {
         actual_image.add(response.getBody().jsonPath().getString("images["+a+"]"));
        }
        for (String image : actual_image)
        {
            if(image.endsWith(".jpg"))
            {
                logger.pass("Image "+ image +" has extension as .jpg");
            }
            else
            {
                logger.fail("Image "+ image +" does not have extension as .jpg");
            }
        }
    }
    @AfterClass
    public void report_generation()
    {
        System.out.println("All Tests Executed. Report is generated");
        extent.flush();
    }
}
