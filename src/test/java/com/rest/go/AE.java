package com.rest.go;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class AE {
    ResponseSpecification responseSpecification;
    private static final String email = "askomdch@gmail.com";
    private static final String access_token = "ya29.a0AfH6SMCCblJR_aABg_4VG9BOz4So-AXPGphsfHH0smNXMnex1DAx6LQFxFjPrx_Llbw_jA79JTJyVsSfcqC9bO4iiIdmFOfV9GciPpjbp5DFL9zj1DT3srV8eBcYkeptc5u8SK_nuMeafQGRmutabWUIYv7xxc18HMG0SCeQCZc";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://oauth2.googleapis.com").
                addHeader("Authorization", "Bearer " + access_token).
                //        setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                //                .appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                        setContentType(ContentType.JSON).
                        log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getUserProfile(){
        given().
                baseUri("https://gmail.googleapis.com").
                basePath("/gmail/v1").
                pathParam("emailId", email).
        when().
                get("/users/{emailId}/profile").
        then().spec(responseSpecification);
    }

    @Test
    public void postMessage(){
        String msg = "From: ask om <askomdch@gmail.com> \n" +
                "To: omprakash <omprakash.chavan@gmail.com> \n" +
                "Subject: Saying Hello \n" +
                "Date: Thu, 28 Jan 2021 09:55:06 -0600 \n" +
                "Message-ID: <1234@local.machine.example>\n" +
                "\n" +
                "This is a message just to say hello. So, \"Hello\".";
        String base64UrlString = Base64.getUrlEncoder().encodeToString(msg.getBytes());
        HashMap<String, String> payload = new HashMap<>();
        payload.put("raw", base64UrlString);

        given().
                baseUri("https://gmail.googleapis.com").
                basePath("/gmail/v1").
                pathParam("emailId", email).
                header("To", "omprakash.chavan@gmail.com").
                body(payload).
        when().
                post("/users/{emailId}/messages/send").
        then().spec(responseSpecification);
    }
}
