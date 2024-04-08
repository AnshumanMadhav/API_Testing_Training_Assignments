package stepdef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;

public class Rest_Assured_Assignments
{
    Response response;
    public XmlPath xml_path_obj;
    public String c_temp;
    public String laptop_name;
    public String year;
    public String price;
    public String request_body_001;
    public String request_body_002;
    public String url_003;
    public String url_004;

    // Assignment_001
    @Given("User gives temperature in Celsius as {string}")
    public void user_gives_temperature_in_celsius_as(String expected_c_temp)
    {
        c_temp = expected_c_temp;
    }
    @When("User creates temperature conversion request body")
    public void user_creates_temperature_conversion_request_body()
    {
       request_body_001 = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
               "  <soap12:Body>\n" +
               "    <CelsiusToFahrenheit xmlns=\"https://www.w3schools.com/xml/\">\n" +
               "      <Celsius>"+c_temp+"</Celsius>\n" +
               "    </CelsiusToFahrenheit>\n" +
               "  </soap12:Body>\n" +
               "</soap12:Envelope>";

    }

    @When("User sends post call with url as {string}")
    public void user_sends_post_call_with_url_as(String url)
    {
     response = given().contentType(ContentType.XML).header("Content-Type","text/xml; charset=utf-8")
                .body(request_body_001).when().post(url);
    }
    @Then("User sees status code value as {string}")
    public void user_sees_status_code_value_as(String expected_status)
    {
        int actual_status = response.statusCode();
        Assert.assertEquals(String.valueOf(actual_status),expected_status);
    }

    @Then("User sees temperature in fahrenheit as {string}")
    public void user_sees_temperature_in_fahrenheit_as(String expected_f_temp)
    {
        xml_path_obj = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        String actual_f_temp = xml_path_obj.getString("soap:Envelope.soap:Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult");
        Assert.assertEquals(actual_f_temp,expected_f_temp);
    }
    // Assignment_002
    @Given("User gives Laptop Details as {string},{string} & {string}")
    public void user_gives_laptop_details_as(String given_laptop_name,String given_year,String given_price)
    {
        laptop_name = given_laptop_name;
        year = given_year;
        price = given_price;
    }

    @When("User creates Laptop Details request body")
    public void user_creates_laptop_details_request_body()
    {
        request_body_002 = "{\n" +
                "  \"name\": \""+laptop_name+"\",\n" +
                "  \"data\": {\n" +
                "            \"year\": "+year+",\n" +
                "            \"price\": "+price+",\n" +
                "            \"CPU Model\": \"Intel Core i9\",\n" +
                "            \"Hard disk size\": \"1 TB\"\n" +
                "           }\n" +
                "}";
    }

    @When("User sends post call for Laptop Details with url as {string}")
    public void user_sends_post_call_for_laptop_details_with_url_as(String url)
    {
        response = given().contentType(ContentType.JSON).body(request_body_002).when().post(url);
    }

    @Then("User can see that status code value is {string}")
    public void user_can_see_that_status_code_value_is(String expected_status)
    {
        int actual_status = response.statusCode();
        Assert.assertEquals(String.valueOf(actual_status),expected_status);
    }

    @Then("User sees Year as {string}")
    public void user_sees_year_as(String expected_year)
    {
        String actual_year = response.getBody().jsonPath().getString("data.year");
        Assert.assertEquals(actual_year,expected_year);

    }

    @Then("User sees Price as {string}")
    public void user_sees_price_as(String expected_price)
    {
        String actual_price = response.getBody().jsonPath().getString("data.price");
        Assert.assertEquals(actual_price,expected_price);

    }

    @Then("User validates that createdAt is not null")
    public void user_validates_that_created_at_is_not_null()
    {
        String createAt = response.getBody().jsonPath().getString("createdAt");
        Assert.assertNotNull(createAt);
    }
    // Assignment_003
    @Given("User enters the {string}")
    public void user_enters_the(String base_url)
    {
       url_003 = base_url;
    }

    @And("User hits get the call request")
    public void user_hits_get_the_call_request()
    {
        response = get(url_003);
    }

    @Then("User gets status code as {string}")
    public void user_gets_status_code_as(String expected_status)
    {
        int actual_status = response.statusCode();
        Assert.assertEquals(String.valueOf(actual_status),expected_status);
    }

    @When("Response id is {string}")
    public void response_id_is(String expected_id)
    {
        int id_path_count = response.getBody().jsonPath().getList("id").size();
        for (int a=0;a<id_path_count;a++)
        {
         String actual_id = response.getBody().jsonPath().getString("id["+a+"]");
         if(actual_id.equals(expected_id))
            {
                break;
            }
        }
    }
    @Then("Mobile Name is {string}")
    public void mobile_name_is(String expected_name)
    {
        int id_path_count = response.getBody().jsonPath().getList("id").size();
        for (int a=0;a<id_path_count;a++)
        {
            String actual_name = response.getBody().jsonPath().getString("name["+a+"]");
            if(actual_name.equals(expected_name))
            {
                break;
            }
        }
    }
    // Assignment_004
    @Given("User enters the currency URL {string}")
    public void user_enters_the_currency_url(String base_url)
    {
        url_004 = base_url;
    }
    @And("User hits get the call request for viewing currency details")
    public void user_hits_get_the_call_request_for_viewing_currency_details()
    {
        response = get(url_004);
        xml_path_obj = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        // Capturing Currency Details
        ArrayList<String> currencies = new ArrayList<>();
        int number_of_currencies = xml_path_obj.get("wsdl:definitions.wsdl:types.s:schema.s:simpleType[1].s:restriction.s:enumeration.size()");
        System.out.println("Number of Currencies: "+number_of_currencies);
        for(int i = 0;i < number_of_currencies;i++)
        {
            currencies.add(xml_path_obj.getString("wsdl:definitions.wsdl:types.s:schema.s:simpleType[1].s:restriction.s:enumeration["+i+"].@value"));
        }
        System.out.println(currencies.toString());
        // Capturing Forward Types
        ArrayList<String> forward_types = new ArrayList<>();
        int number_of_fwd_types = xml_path_obj.get("wsdl:definitions.wsdl:types.s:schema.s:simpleType[2].s:restriction.s:enumeration.size()");
        System.out.println("Number of Forward Types: "+number_of_fwd_types);
        for(int i = 0;i < number_of_fwd_types;i++)
        {
            forward_types.add(xml_path_obj.getString("wsdl:definitions.wsdl:types.s:schema.s:simpleType[2].s:restriction.s:enumeration["+i+"].@value"));
        }
        System.out.println(forward_types.toString());
    }
    @Then("User will be able to view that status code {string}")
    public void user_will_be_able_to_view_that_status_code(String expected_status)
    {
        int actual_status = response.statusCode();
        Assert.assertEquals(String.valueOf(actual_status),expected_status);
    }
    @Then("User will be able to see that total number of Outcome Types are {string}")
    public void user_will_be_able_to_see_that_total_number_of_outcome_types_are(String expected_outcome_no)
    {
        int actual_outcome_no = xml_path_obj.get("wsdl:definitions.wsdl:types.s:schema.s:simpleType[0].s:restriction.s:enumeration.size()");
        Assert.assertEquals(String.valueOf(actual_outcome_no),expected_outcome_no);

    }
    @Then("One of the Outcome is {string}")
    public void one_of_the_outcome_is(String expected_outcome)
    {
        int actual_outcome_no = xml_path_obj.get("wsdl:definitions.wsdl:types.s:schema.s:simpleType[0].s:restriction.s:enumeration.size()");
        ArrayList<String> outcome_types = new ArrayList<>();
        for(int i = 0;i < actual_outcome_no;i++)
        {
            outcome_types.add(xml_path_obj.getString("wsdl:definitions.wsdl:types.s:schema.s:simpleType[0].s:restriction.s:enumeration["+i+"].@value"));
        }
        Assert.assertTrue(outcome_types.contains(expected_outcome));
    }

}
