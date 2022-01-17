package com.rest;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FA {
    SessionFilter filter;

    @BeforeClass
    public void beforeClass(){
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri("https://localhost:8081").
                setRelaxedHTTPSValidation().build();
    }

    @Test
    public void form_authentication_csrf() {
/*        filter = new SessionFilter();
        Response response = given().
                baseUri("https://localhost:8081").
                relaxedHTTPSValidation().
                filter(filter).
                log().all().
        when().
                get("/login").
        then().
                log().all().
                assertThat().
                statusCode(200).
                extract().response();

 //       Cookie cookie = response.getDetailedCookie("JSESSIONID");
        String CSRF = response.htmlPath().getString("html.body.div.div.div.div.form.input.@value");

//        System.out.println(cookie.toString());
        System.out.println(CSRF);*/

  //      String cookie = "JSESSIONID=AEF21C7BE07A696B0CD6B5563F118585; Path=/; Secure; HttpOnly";

        SessionFilter filter1 = new SessionFilter();

        given().
      //          contentType(ContentType.URLENC).
      //          formParam("_csrf", CSRF).
     //           formParam("txtUsername", "dan").
     //           formParam("txtPassword", "dan123").
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword").withAutoDetectionOfCsrf()).
                filter(filter1).
                log().all().
        when().
                get("/login").
        then().
                log().all().
                assertThat().
                statusCode(200);

        given().
                sessionId(filter1.getSessionId()).
    //            cookie("JSESSIONID=" + filter1.getSessionId() + ";Path=/;Secure;HttpOnly").
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
}
