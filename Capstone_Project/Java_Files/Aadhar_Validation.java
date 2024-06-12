package Capstone_Project;

import My_Learning.Read_Property_File_Data;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Capstone_Project.read_data_from_property_file.read_properties;
import static Capstone_Project.post_request_body.aadhar_details;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class Aadhar_Validation
{
  Connection connection;
  Response post_aadhar_response;
  String url = read_properties("url");
  String user = read_properties("user");
  String password = read_properties("password");

  @BeforeClass
  public void connect_to_db() throws SQLException
  {
    connection = DriverManager.getConnection(url,user,password);
    System.out.println("DB Connection Established!");
  }
  @Test(priority = 1)
  public void insert_records() throws IOException, SQLException
  {
      System.out.println("Aadhar Insert Records Validation!!");
      Statement stmt = connection.createStatement();
      FileInputStream fis = new FileInputStream(new File("C:/Users/anshumanm/OneDrive - Maveric Systems Limited/Desktop/API TESTING TRAINING/Capstone_Project/Aadhar_Details.xlsx"));
      XSSFWorkbook wb = new XSSFWorkbook(fis);
      XSSFSheet sheet = wb.getSheetAt(0);
      for (Row row : sheet)
      {
          if(row.getRowNum() != 0)
          {
              String Fname = row.getCell(0).getStringCellValue();
              String Lname = row.getCell(1).getStringCellValue();
              long aadhar_no = (long)row.getCell(2).getNumericCellValue();
              String address = row.getCell(3).getStringCellValue();
              long phone_no = (long) row.getCell(4).getNumericCellValue();
              String query = "insert into maven_practise.aadhar_details values (\""+Fname+"\",\""+Lname+"\","+aadhar_no+",\""+address+"\","+phone_no+")";
              stmt.execute(query);
              Reporter.log("Values: (\""+Fname+"\",\""+Lname+"\","+aadhar_no+",\""+address+"\","+phone_no+") Inserted Successfully in table");
          }
      }
      System.out.println("Records Inserted Successfully");
  }
  @Test(priority = 2)
  public void aadhar_finder()
  {
      try
      {
          System.out.println("Aadhar Finder Test Starts!!");
          String test_aadhar = Read_Property_File_Data.read_properties("aadhar_no");
          Statement stmt =connection.createStatement();
          ResultSet result =stmt.executeQuery("select distinct Aadhar_No from maven_practise.aadhar_details;");
          ArrayList<String> aadhar_nos = new ArrayList<>();
          while(result.next())
          {
              aadhar_nos.add(result.getString("Aadhar_No"));
          }
          if (aadhar_nos.contains(test_aadhar))
          {
              System.out.println("Aadhar Exists");
              Reporter.log("Aadhar Number: "+test_aadhar+ " is a valid Aadhar." );
              get_aadhar_details(test_aadhar);
          }
          else
          {
              System.out.println("Aadhar does not Exists");
              Reporter.log("Aadhar Number: "+test_aadhar+ " is an invalid Aadhar.",true);
          }
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }
  public void get_aadhar_details(String aadhar_no)
{
    try
    {
        String post_url = "https://reqres.in/api/users";
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd");
        Statement stmt =connection.createStatement();
        ResultSet result =stmt.executeQuery("select * from maven_practise.aadhar_details where Aadhar_No = "+aadhar_no+";");
        while(result.next())
        {
            // Storing Values from Database
            String firstname_db =result.getString("Fname");
            String lastname_db =result.getString("Lname");
            long aadhar_db =result.getLong("Aadhar_No");
            String address_db = result.getString("Address");
            long phone_db = result.getLong("Phone");

            // Sending DB Values to Post Request Body
            String post_request_body = aadhar_details(firstname_db,lastname_db,aadhar_db,address_db,phone_db);
            post_aadhar_response = given().contentType(ContentType.JSON).body(post_request_body).when().post(post_url);
            System.out.println(post_aadhar_response.body().asString());

            // Storing Values from Post Response
            String first_name_api = post_aadhar_response.getBody().jsonPath().getString("Fname");
            String last_name_api = post_aadhar_response.getBody().jsonPath().getString("Lname");
            long aadhar_api = post_aadhar_response.getBody().jsonPath().getLong("Aadhar_No");
            String address_api = post_aadhar_response.getBody().jsonPath().getString("Address");
            long phone_api = post_aadhar_response.getBody().jsonPath().getLong("Phone");
            String id_api = post_aadhar_response.getBody().jsonPath().getString("id");
            String created_at_api = date_extractor(post_aadhar_response.getBody().jsonPath().getString("createdAt"));

            // Validating Post Response against DB Values
            Assert.assertEquals(firstname_db,first_name_api);
            Assert.assertEquals(lastname_db,last_name_api);
            Assert.assertEquals(aadhar_db,aadhar_api);
            Assert.assertEquals(address_db,address_api);
            Assert.assertEquals(phone_db,phone_api);

            Reporter.log("For the field First Name==> DB value: " + firstname_db + " API Value: " + first_name_api+" || Value matching.");
            Reporter.log("For the field Last Name==> DB value: " + lastname_db + " API Value: " + last_name_api+" || Value matching.");
            Reporter.log("For the field Aadhar No==> DB value: " + aadhar_db + " API Value: " + aadhar_api+" || Value matching.");
            Reporter.log("For the field Address==> DB value: " + address_db + " API Value: " + address_api+" || Value matching.");
            Reporter.log("For the field Phone No==> DB value: " + phone_db + " API Value: " + phone_api+" || Value matching.");

            //Validating if Account ID and CreatedAt Fields are not empty
            Assert.assertNotNull(id_api);
            Assert.assertNotNull(created_at_api);

            Reporter.log("For the field id ==> API Value: " +id_api);
            Reporter.log("For the createdAt==> API Value: " +created_at_api);

            //Validating if Account ID is numeric
            Assert.assertTrue(id_api.matches("[0-9]+"));

            //Validating if CreatedAt Field contains current Date
            Date date = new Date();
            String current_date = sf.format(date);
            Assert.assertEquals(current_date,created_at_api);
            Reporter.log("For the field createdAt==> Current-Date: " + current_date + " API Value: " + created_at_api+" || Value matching.");
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}
 public String date_extractor(String created_at)
 {
   String[] extracted_date = created_at.split("T");
   return extracted_date[0];
 }

 @AfterClass
 public void validation_end_message()
 {
   System.out.println("Aadhar Validation Completed!");
 }
}

