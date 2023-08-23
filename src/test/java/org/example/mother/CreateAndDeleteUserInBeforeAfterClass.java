package org.example.mother;

import com.github.javafaker.Faker;
import org.apache.http.entity.ContentType;
import org.example.dto.CourierLogin;
import org.example.dto.User;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class CreateAndDeleteUserInBeforeAfterClass extends UserLoginInBeforeClass {
    protected Integer courierId;
    protected User user;

    @Before
    public void setUp() {
        Faker faker = new Faker();
        user = new User(faker.name().fullName(), faker.internet().password(), faker.name().firstName());
        CourierLogin createCourierForLoginCheck = new CourierLogin(user.getLogin(), user.getPassword(), user.getFirstName());//создаем объект для создания курьера
        //создаем курьера
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(createCourierForLoginCheck)
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));
        CourierLogin loginCourierForLoginCheck = new CourierLogin(user.getLogin(), user.getPassword(), null);//создаем объект для логина курьера
        this.courierId = given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(loginCourierForLoginCheck)
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .path("id");
    }

    //    если какого-то поля нет, запрос возвращает ошибку;
    //    если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    //    успешный запрос возвращает id
    @After
    public void clearingPreconditions() {
        //  удаляем курьера;
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .pathParam("id", this.courierId)
                .delete("/api/v1/courier/{id}")
                .then()
                .statusCode(200)
                .body("ok", Matchers.equalTo(true));
    }
}
