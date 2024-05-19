package Rest_API_Assignment;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static Rest_API_Assignment.Read_Excel_File.Store_XL_Data_in_Hashmap;
import static io.restassured.RestAssured.given;


public class post_pet_details
{
 Response post_response;
 Response get_response;

 Map<Integer,String> pet_deets_map = new HashMap<>();
@Parameters({"post_url","expected_status_code","expected_status_text"})
@Test(groups = {"post"})
public void post_response_validation(String post_url,String expected_status_code,String expected_status_text) throws IOException
{
   pet_deets_map = Store_XL_Data_in_Hashmap();
   for(Map.Entry<Integer,String> entry : pet_deets_map.entrySet())
 {
     int petID = entry.getKey();
     String petName = entry.getValue();

     // POST RESPONSE
     String post_request_body = "{\n" +
             "    \"id\":\""+petID+"\",\n" +
             "    \"category\":{\"id\":0,\"name\":\"string\"},\n" +
             "    \"name\":\""+petName+"\",\n" +
             "    \"photoUrls\":[\"string\"],\n" +
             "    \"tags\":[{\"id\":0,\"name\":\"string\"}],\n" +
             "    \"status\":\"available\"\n" +
             "}";
     post_response = given().contentType(ContentType.JSON).body(post_request_body).when().post(post_url);
     System.out.println(post_response.getBody().asString());
     int actual_response_code = post_response.statusCode();
     String actual_status_text = post_response.getBody().jsonPath().getString("status");

     // VALIDATING STATUS CODE IS 200 AND STATUS TEXT IS "available"
     Assert.assertEquals(String.valueOf(actual_response_code),"200");
     Assert.assertEquals(actual_status_text,"available");
 }
}
}
