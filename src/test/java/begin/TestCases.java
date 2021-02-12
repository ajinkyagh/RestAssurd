package begin;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class TestCases {

    //        Given-sll input details
//        When-Submit the API -resource,http method
//        Then-validate the response




    @Test
        public void firstTestCase() {
            RestAssured.baseURI="https://rahulshettyacademy.com";

//        ADD PLACE
            String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                    .body("""
                            {
                              "location": {
                                "lat": -38.383494,
                                "lng": 33.427362
                              },
                              "accuracy": 50,
                              "name": "Ajinkya Ghorpade",
                              "phone_number": "(+91) 983 893 3937",
                              "address": "29, side layout, cohen 09",
                              "types": [
                                "shoe park",
                                "shop"
                              ],
                              "website": "http://google.com",
                              "language": "French-IN"
                            }
                            """).when().post("maps/api/place/add/json")
                    .then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("server","Apache/2.4.18 (Ubuntu)").extract().asString();

            System.out.println(response);


            /*JsonPath js=new JsonPath(response);//for parsing jason used for extraction of information
            String placeId= js.getString("place_id");*/
            JsonPath path=ReuseableMethods.getRequiredDetails(response);
            String placeId= path.getString("place_id");
            System.out.println(placeId);

            //        UDATE PLACE
            String newAddress="70 Summer walk, USA";
            given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body("{\n" +
                    "\"place_id\":\""+placeId+"\",\n" +
                    "\"address\":\""+newAddress+"\",\n" +
                    "\"key\":\"qaclick123\"\n" +
                    "}").
                    when().put("maps/api/place/update/json").
                    then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));

//        GET PLACE

            String getPlaceResponse=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId).
                    when().get("maps/api/place/get/json").
                    then().assertThat().statusCode(200).extract().response().asString();

            //JsonPath js1=new JsonPath(getPlaceResponse);
            JsonPath path1=ReuseableMethods.getRequiredDetails(getPlaceResponse);
            String actualAddress=path1.getString("address");
            System.out.println(actualAddress);
            Assert.assertEquals(actualAddress,newAddress);


        }
}

