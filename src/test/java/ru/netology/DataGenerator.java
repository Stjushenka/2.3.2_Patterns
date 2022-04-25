package ru.netology;



import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;


import java.util.Locale;

    public class DataGenerator {
        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        private static final Faker faker = new Faker(new Locale("en"));

        private DataGenerator() {
        }

        private static void sendRequest(registeredUser user) {
            RestAssured.given()
                    .spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static String getRandomLogin() {
            String login = faker.name().username();
            return login;
        }

        public static String getRandomPassword() {
            String password = faker.internet().password();
            return password;
        }

        public static class Registration {
            private Registration() {
            }

            public static registeredUser getUser(String status) {
                registeredUser user = new registeredUser(
                        getRandomLogin(),
                        getRandomPassword(),
                        status
                );
                return user;
            }

            public static registeredUser getRegisterUser(String status) {
                registeredUser registerUser = getRegisterUser(status);
                DataGenerator.sendRequest(registerUser);
                return registerUser;
            }
        }

        @Value
        public static class registeredUser {
            String login;
            String password;
            String status;
        }

    }
