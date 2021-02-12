package begin;

import io.restassured.path.json.JsonPath;

public class ReuseableMethods {
//   **** When a method is main static no need to create object in the main class***
    public static JsonPath getRequiredDetails(String string) {
        JsonPath js = new JsonPath(string);//for parsing jason used for extraction of information
        return js;
    }
}
