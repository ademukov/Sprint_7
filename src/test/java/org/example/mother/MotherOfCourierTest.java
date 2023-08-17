package org.example.mother;

import org.apache.http.entity.ContentType;
import org.example.dto.CourierLogin;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;
import static org.example.constant.UserConstant.*;

public class MotherOfCourierTest extends FatherOfMother {
    protected Integer courierId;

    @Before
    public void setUp() {
        CourierLogin createCourierForLoginCheck = new CourierLogin(LOGIN, PASSWORD, FIRSTNAME);//создаем объект для создания курьера
        //создаем курьера
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(createCourierForLoginCheck)
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));
        CourierLogin loginCourierForLoginCheck = new CourierLogin(LOGIN, PASSWORD, null);//создаем объект для логина курьера
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
