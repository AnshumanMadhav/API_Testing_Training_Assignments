package Rest_Assured_Assignments;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class Assignment_003 {
    Assignment_003_Support obj;
    Response post_response;
    Response get_response;
    Response get_response_new;
    Response put_response;
    Response delete_response;

    public static void main(String[] args) throws IOException {
        Assignment_003 assignment003 = new Assignment_003();
        assignment003.put_and_get_data();
    }

    public void put_and_get_data() throws IOException {
        FileInputStream fis = new FileInputStream(new File("C:/Users/anshumanm/Downloads/Bank_Employee_Details.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
        for (Row row : sheet) {
            if (row.getRowNum() != 0)
            {
              System.out.println("**** Starting Iteration number:" +row.getRowNum()+ "****");
              String name = row.getCell(0).getStringCellValue();
              int year = (int)row.getCell(1).getNumericCellValue();
              String dob = df.format(row.getCell(2).getDateCellValue());
              String address = row.getCell(3).getStringCellValue();
              int salary = (int)row.getCell(4).getNumericCellValue();
              String new_address = row.getCell(5).getStringCellValue();

              String post_request_body = "{\n" +
                        "    \"name\" : \""+name+"\",\n" +
                        "    \"data\" :{\n" +
                        "        \"year\" : \""+year+"\",\n" +
                        "        \"DOB\"  : \""+dob+"\",\n" +
                        "        \"Address\" : \""+address+"\",\n" +
                        "        \"Salary\" : \""+salary+"\"\n" +
                        "    }\n" +
                        "}";
              String post_url = "https://api.restful-api.dev/objects";
              post_response = given().contentType(ContentType.JSON).body(post_request_body).when().post(post_url);
              System.out.println("Data Posted Successfully!");
              int post_status_code = post_response.statusCode();
              Assert.assertEquals(String.valueOf(post_status_code),"200");
              String get_url = post_url + "?id=" + post_response.getBody().jsonPath().getString("id");

              get_response = given().contentType(ContentType.JSON).when().get(get_url);
              System.out.println("Below Data is Received");
              System.out.println(get_response.getBody().asString());
              int get_status_code = get_response.statusCode();
              Assert.assertEquals(String.valueOf(get_status_code),"200");

              // Response Data Validation

              String get_name = get_response.getBody().jsonPath().getString("name[0]");
              String get_year = get_response.getBody().jsonPath().getString("data.year[0]");
              String get_DOB  = get_response.getBody().jsonPath().getString("data.DOB[0]");
              String get_address = get_response.getBody().jsonPath().getString("data.Address[0]");
              String get_salary = get_response.getBody().jsonPath().getString("data.Salary[0]");

              Assert.assertEquals(get_name,name);
              Assert.assertEquals(get_year,String.valueOf(year));
              Assert.assertEquals(get_DOB,dob);
              Assert.assertEquals(get_address,address);
              Assert.assertEquals(get_salary,String.valueOf(salary));

              System.out.println("Assertion Validation Successful!");

              // Put Call
                String put_url = post_url + "/" + get_response.getBody().jsonPath().getString("id[0]");
                String put_request_body = "{\n" +
                        "    \"name\" : \""+name+"\",\n" +
                        "    \"data\" :{\n" +
                        "        \"year\" : \""+year+"\",\n" +
                        "        \"DOB\"  : \""+dob+"\",\n" +
                        "        \"Address\" : \""+new_address+"\",\n" +
                        "        \"Salary\" : \""+salary+"\"\n" +
                        "    }\n" +
                        "}";
                put_response = given().contentType(ContentType.JSON).body(put_request_body).when().put(put_url);
                System.out.println("Address Modified Successfully!");
                System.out.println(put_response.getBody().asString());
                int put_status_code = put_response.statusCode();
                Assert.assertEquals(String.valueOf(put_status_code),"200");

                String put_address = put_response.getBody().jsonPath().getString("data.Address");
                Assert.assertEquals(put_address,new_address);

                // GET CALL, VALIDATING WHETHER ADDRESS IS MODIFIED
                String get_url_new = post_url + "?id=" + put_response.getBody().jsonPath().getString("id");
                get_response = given().contentType(ContentType.JSON).when().get(get_url_new);
                String get_address_new  = get_response.getBody().jsonPath().getString("data.Address[0]");

                Assert.assertEquals(get_address_new,new_address);
                System.out.println("Address matches!");

                //DELETING RECORD
                String delete_url = post_url + "/" + get_response.getBody().jsonPath().getString("id[0]");
                String delete_id = get_response.getBody().jsonPath().getString("id[0]");
                delete_response = given().contentType(ContentType.JSON).when().delete(delete_url);
                System.out.println("Record Deleted Successfully");
                String expected_delete_body = "{\"message\":\"Object with id = "+delete_id+" has been deleted.\"}";
                String actual_delete_body = delete_response.getBody().asString();
                System.out.println(expected_delete_body);
                Assert.assertEquals(expected_delete_body,actual_delete_body);
                System.out.println("Delete Body matching as expected");
                int delete_status_code = delete_response.statusCode();
                Assert.assertEquals(String.valueOf(delete_status_code),"200");

                // VALIDATING GET-CALL RESPONSE CONTAINS NO DATA
                String get_url_final = post_url + "?id=" + delete_id;
                get_response = given().contentType(ContentType.JSON).when().get(get_url_final);
                System.out.println(get_response.getBody().asString());

                System.out.println("**** Ending Iteration number:" +row.getRowNum()+ "****");

            }


        }

    }
}
