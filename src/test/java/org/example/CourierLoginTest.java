package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.entity.ContentType;
import org.example.dto.CourierLogin;
import org.example.mother.CreateAndDeleteUserInBeforeAfterClass;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CourierLoginTest extends CreateAndDeleteUserInBeforeAfterClass {

    //    курьер может авторизоваться;
    @Test
    @DisplayName("Логин зарегистрированного курьера. Курьер зарегистирован в системе")
    public void courierAuth() {
        CourierLogin loginCourierForLoginCheck = new CourierLogin(user.getLogin(), user.getPassword(), null);//создаем объект для логина курьера
        //    для авторизации нужно передать все обязательные поля;
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(loginCourierForLoginCheck)
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .body("id", Matchers.notNullValue());
    }


    //  проверяем, что нельзя залогиниться без заполнения логина;
    @Test
    @DisplayName("Логин зарегистрированного курьера. Логин не заполнен")
    public void courierLoginWithoutFillingLoginField() {
        CourierLogin loginCourierForLoginCheck = new CourierLogin(null, user.getPassword(), null);//создаем объект для логина курьера
        //  создаем курьера в before
        //  проверяем, что нельзя залогиниться без заполнения логина;
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(loginCourierForLoginCheck)
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин зарегистрированного курьера. Пароль не заполнен")
    public void courierLoginWithoutFillingPasswordField() {
        CourierLogin loginCourierForLoginCheck = new CourierLogin(user.getLogin(), null, null);//создаем объект для логина курьера

        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(loginCourierForLoginCheck)
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    //    система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("Логин зарегистрированного курьера. Несуществующий логин")
    public void courierLoginWithWrongLogin() {
        CourierLogin loginCourierForLoginCheck = new CourierLogin("gggddd12345q", user.getPassword(), null);//создаем объект для логина курьера

        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(loginCourierForLoginCheck)
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин зарегистрированного курьера. Несуществующий пароль")
    public void courierLoginWithWrongPassword() {
        CourierLogin loginCourierForLoginCheck = new CourierLogin(user.getLogin(), "gggddd12345q", null);//создаем объект для логина курьера

        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(loginCourierForLoginCheck)
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }
}
