package Rest_Assured_Assignments;

import com.beust.ah.A;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.sql.*;

import static io.restassured.RestAssured.get;

public class Assignment_004
{
    Response response;
    Connection connection;


    public static void main(String[] args)
    {
      Assignment_004 a_004 = new Assignment_004();
       //  a_004.get_company_details();
       // a_004.create_a_new_table();
       //  a_004.insert_new_records();
         a_004.update_records(5,"America","Gadgets");
    }

    public void get_company_details()
    {
        response = get("https://fake-json-api.mock.beeceptor.com/companies");
        System.out.println(response.getBody().asString());
    }
    public void create_a_new_table()
    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "tiger");
            Statement stmt = connection.createStatement();
            stmt.execute("use db_created_through_ij");
            String query = "create table companies_data(id int,name varchar(255),address varchar(255),zip varchar(255),country varchar(255),employeeCount bigint,industry varchar(255),marketCap varchar(255),domain varchar(255),logo varchar(255),ceoName varchar(255));";
            stmt.execute(query);
            System.out.println("Table Created successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insert_new_records()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "tiger");
            Statement stmt = connection.createStatement();
            stmt.execute("use db_created_through_ij");
            int record_count = response.getBody().jsonPath().getList("id").size();
            for(int a = 0;a < record_count; a++)
            {
                String id = response.getBody().jsonPath().getString("id["+a+"]");
                String name = response.getBody().jsonPath().getString("name["+a+"]");
                String address = response.getBody().jsonPath().getString("address["+a+"]");
                String zip = response.getBody().jsonPath().getString("zip["+a+"]");
                String country = response.getBody().jsonPath().getString("country["+a+"]");
                String employeeCount = response.getBody().jsonPath().getString("employeeCount["+a+"]");
                String industry = response.getBody().jsonPath().getString("industry["+a+"]");
                String marketCap = response.getBody().jsonPath().getString("marketCap["+a+"]");
                String domain = response.getBody().jsonPath().getString("domain["+a+"]");
                String logo = response.getBody().jsonPath().getString("logo["+a+"]");
                String ceoName = response.getBody().jsonPath().getString("ceoName["+a+"]");

                //Inserting Records one at a time
                String query = "insert into companies_data values ("+id+",\""+name+"\",\""+address+"\","+zip+",\""+country+"\","+employeeCount+",\""+industry+"\","+marketCap+",\""+domain+"\",\""+logo+"\",\""+ceoName+"\")";
                stmt.execute(query);
            }
                System.out.println(record_count + " Records Inserted successfully!!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void update_records(int id,String new_country,String new_industry)
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "tiger");
            Statement stmt = connection.createStatement();

            //Updating Values
             String update_query = "update db_created_through_ij.companies_data set country= \""+new_country+"\" ,industry= \""+new_industry+"\" where id= "+id+" ";
             stmt.executeUpdate(update_query);
             System.out.println("Update Query Executed Successfully");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
    }

}


