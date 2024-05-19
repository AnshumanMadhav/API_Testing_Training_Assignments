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

public class delete_pet
{
 Response delete_response;
 Response get_response;
 Map<Integer,String> pet_deets_map = new HashMap<>();

 @Parameters({"delete_url","expected_message"})
 @Test()
 public void delete_response_validation(String delete_url,String expected_message) throws IOException {
     pet_deets_map = Store_XL_Data_in_Hashmap();
     for (Map.Entry<Integer, String> entry : pet_deets_map.entrySet())
     {
         int petID = entry.getKey();
         System.out.println("Delete URL IS:" +delete_url+petID);
         delete_response = given().contentType(ContentType.JSON).when().delete(delete_url+petID);
         System.out.println(delete_response.getBody().asString());
         //AFTER DELETING VALIDATING IF WE ARE GETTING PET NOT FOUND MESSAGE AFTER WE HIT GET URL
         System.out.println(delete_url+petID);
         get_response = given().contentType(ContentType.JSON).when().get(delete_url+petID);
         System.out.println(get_response.getBody().asString());
         String actual_message = get_response.getBody().jsonPath().getString("message");

         Assert.assertEquals(expected_message,actual_message);
     }
 }


}
