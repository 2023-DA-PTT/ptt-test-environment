package com.ptt.boundary;

import com.ptt.entity.Session;
import com.ptt.entity.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class End2EndTest {
    private final String USERNAME = "darius";
    private final String PASSWORD = "password";
    private final Long WAIT_TIME_IN_SECONDS = 5L; // waiting time set for endpoint
    private final Long ERROR_MARGIN_WAIT_TIME = 1L; // time needed for requests to be sent back and forth

    @Test
    public void basicTestCase() {
        Session s = signUpAndGetSession();

        var startTime = System.currentTimeMillis();

        var sleeperEndpoint = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get("/sleep/"+s.token()+"/"+WAIT_TIME_IN_SECONDS)
                .thenReturn();

        var timeDiff = (System.currentTimeMillis() - startTime)/1000;

        assertThat(timeDiff, lessThanOrEqualTo(WAIT_TIME_IN_SECONDS+ERROR_MARGIN_WAIT_TIME));
        assertThat(timeDiff, greaterThanOrEqualTo(WAIT_TIME_IN_SECONDS));
        assertThat(sleeperEndpoint.statusCode(), equalTo(200));
    }

    @Test
    public void signUpAndLoginWithWrongPassword() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(new User(USERNAME, PASSWORD))
                .when()
                .post("/sign-up")
                .then()
                .statusCode(200)
                .body(is(JsonObject.mapFrom(new User(USERNAME, PASSWORD)).toString()));

        var login = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(new User(USERNAME, "wrongpassword"))
                .when()
                .post("/login")
                .thenReturn();

        assertThat(login.statusCode(), equalTo(403));
    }

    @Test
    public void testWrongToken() {
        var sleeperEndpoint = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get("/sleep/asdf/"+WAIT_TIME_IN_SECONDS)
                .thenReturn();

        assertThat(sleeperEndpoint.statusCode(), equalTo(403));
    }

    @Test
    public void testUnvalidTime() {
        Session s = signUpAndGetSession();

        var sleeperEndpoint = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get("/sleep/" + s.token().toString() + "/asdf")
                .thenReturn();

        assertThat(sleeperEndpoint.statusCode(), equalTo(404));
    }

    private Session signUpAndGetSession() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(new User(USERNAME, PASSWORD))
                .when()
                .post("/sign-up")
                .then()
                .statusCode(200)
                .body(is(JsonObject.mapFrom(new User(USERNAME, PASSWORD)).toString()));

        var login = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(new User(USERNAME, PASSWORD))
                .when()
                .post("/login")
                .thenReturn();

        assertThat(login.statusCode(), equalTo(200));

        Session s = login.body().as(Session.class);

        assertThat(s, notNullValue());

        return s;
    }
}
