package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;


public class DataGenerator {


    private static Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    private static void makeRegistration(RequestData registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    public static RequestData generateNewActiveValidUser() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        String status = "active";
        makeRegistration(new RequestData(login, password, status));
        return new RequestData(login, password, status);
    }

    public static RequestData generateNewBlockedUser() {
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new RequestData(login, password, "blocked"));
        return new RequestData(login, password, "blocked");
    }

    public static RequestData generateNewActiveUserInvalidLogin() {
        String password = faker.internet().password();
        String status = "active";
        makeRegistration(new RequestData("nastya", password, status));
        return new RequestData("login", password, status);
    }

    public static RequestData generateNewActiveInvalidPassword() {
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        makeRegistration(new RequestData(login, "password", status));
        return new RequestData(login, "123456", status);
    }

}
