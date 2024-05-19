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

public class get_pet_details
{
    Response get_response;
    Map<Integer,String> pet_deets_map = new HashMap<>();
    @Parameters({"get_url"})
    @Test()
    public void get_response_validation(String get_url) throws IOException {
        pet_deets_map = Store_XL_Data_in_Hashmap();
        for(Map.Entry<Integer,String> entry : pet_deets_map.entrySet())
        {
            int expected_petID = entry.getKey();
            String expected_petName = entry.getValue();
            get_response = given().contentType(ContentType.JSON).when().get(get_url+expected_petID);
            System.out.println(get_response.getBody().asString());
            int actual_pet_id = get_response.getBody().jsonPath().getInt("id");
            String actual_pet_name = get_response.getBody().jsonPath().getString("name");

            // VALIDATING PET_ID AND PET_NAME MATCHES BETWEEN EXCEL AND RESPONSE
            Assert.assertEquals(expected_petID,actual_pet_id);
            Assert.assertEquals(expected_petName,actual_pet_name);
        }
    }
}

