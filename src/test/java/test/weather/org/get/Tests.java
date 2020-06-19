package test.weather.org.get;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static org.hamcrest.core.IsEqual.equalTo;

public class Tests {
    @Test
    public void validRequestTownShouldBeExpected(){
        RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid=d1a48f454cc591091a820997dbf20d58").
                then().
                assertThat().
                body("name",
                        equalTo("Kyiv"));
    }

    @Test
    public void validRequestShouldReturnOk(){
        RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid=d1a48f454cc591091a820997dbf20d58").
                then().
                assertThat().statusCode(200);
    }

    @Test
    public void invalidTownShouldReturnNotFound(){
        RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=K&appid=d1a48f454cc591091a820997dbf20d58").
                then().
                assertThat().statusCode(404);
    }

    @Test
    public void invalidApiKeyShouldReturnNotAuthorized(){
        RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid=da48f454cc591091a820997dbf20d58").
                then().
                assertThat().statusCode(401);
    }

    @Test
    public void emptyCityShouldReturnBadRequest(){
        RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=&appid=d1a48f454cc591091a820997dbf20d58").
                then().
                assertThat().statusCode(400);
    }
    @Test
    public void emptyApiKeyShouldReturnNotAuthorized(){
        RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid=").
                then().
                assertThat().statusCode(401);
    }
    @Test
    public void emptyParamsShouldReturnBadRequest(){
        RestAssured.get("http://api.openweathermap.org/data/2.5/weather?q=&appid=").
                then().
                assertThat().statusCode(401);
    }
}
