package Rest_Assured_Assignments;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.SQLOutput;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Assignment_002
{
    Response post_response;
    Response get_response;

    Response put_response;
    Response delete_response;
    @Parameters({"name","year","DOB","Address","Salary","Post_URL","New_Address"})
    @Test
    public void post_and_get_employee_details(String name,String year,String DOB,String Address,String Salary,String Post_URL,String New_Address)
    {
      String post_request_body = "{\n" +
              "    \"name\" : \""+name+"\",\n" +
              "    \"data\" :{\n" +
              "        \"year\" : \""+year+"\",\n" +
              "        \"DOB\"  : \""+DOB+"\",\n" +
              "        \"Address\" : \""+Address+"\",\n" +
              "        \"Salary\" : \""+Salary+"\"\n" +
              "    }\n" +
              "}";
      post_response = given().contentType(ContentType.JSON).body(post_request_body).when().post(Post_URL);
      System.out.println("Data Posted Successfully!");
      int post_status_code = post_response.statusCode();
      Assert.assertEquals(String.valueOf(post_status_code),"200");

      String get_url = Post_URL + "?id=" + post_response.getBody().jsonPath().getString("id");

      get_response = given().contentType(ContentType.JSON).when().get(get_url);
      System.out.println("Below Data is Received");
      System.out.println(get_response.getBody().asString());
      int get_status_code = get_response.statusCode();
      Assert.assertEquals(String.valueOf(get_status_code),"200");


      // Response Data Validation

      String get_name = get_response.getBody().jsonPath().getString("name[0]");
      String get_year = get_response.getBody().jsonPath().getString("data.year[0]");
      String get_DOB  = get_response.getBody().jsonPath().getString("data.DOB[0]");
      String get_address  = get_response.getBody().jsonPath().getString("data.Address[0]");
      String get_salary  = get_response.getBody().jsonPath().getString("data.Salary[0]");

      Assert.assertEquals(get_name,name);
      Assert.assertEquals(get_year,year);
      Assert.assertEquals(get_DOB,DOB);
      Assert.assertEquals(get_address,Address);
      Assert.assertEquals(get_salary,Salary);

      System.out.println("Assertion Validation Successful!");

      // Put Call

        String put_url = Post_URL + "/" + get_response.getBody().jsonPath().getString("id[0]");

        String put_request_body = "{\n" +
                "    \"name\" : \""+name+"\",\n" +
                "    \"data\" :{\n" +
                "        \"year\" : \""+year+"\",\n" +
                "        \"DOB\"  : \""+DOB+"\",\n" +
                "        \"Address\" : \""+New_Address+"\",\n" +
                "        \"Salary\" : \""+Salary+"\"\n" +
                "    }\n" +
                "}";
        put_response = given().contentType(ContentType.JSON).body(put_request_body).when().put(put_url);
        System.out.println("Address Modified Successfully!");
        System.out.println(put_response.getBody().asString());
        int put_status_code = put_response.statusCode();
        Assert.assertEquals(String.valueOf(put_status_code),"200");

        String put_address = put_response.getBody().jsonPath().getString("data.Address");
        Assert.assertEquals(put_address,New_Address);

        // GET CALL, VALIDATING WHETHER ADDRESS IS MODIFIED

        String get_url_new = Post_URL + "?id=" + put_response.getBody().jsonPath().getString("id");
        get_response = given().contentType(ContentType.JSON).when().get(get_url_new);
        String get_address_new  = get_response.getBody().jsonPath().getString("data.Address[0]");

        Assert.assertEquals(get_address_new,New_Address);
        System.out.println("Address matches!");

        //DELETING RECORD

        String delete_url = Post_URL + "/" + get_response.getBody().jsonPath().getString("id[0]");
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

        String get_url_final = Post_URL + "?id=" + delete_id;
        get_response = given().contentType(ContentType.JSON).when().get(get_url_final);
        System.out.println(get_response.getBody().asString());

    }
}
