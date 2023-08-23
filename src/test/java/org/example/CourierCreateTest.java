package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.entity.ContentType;
import org.example.dto.CourierCreate;
import org.example.mother.CreateAndDeleteUserInBeforeAfterClass;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CourierCreateTest extends CreateAndDeleteUserInBeforeAfterClass {

    //нельзя создать двух одинаковых курьеров
    //если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void checkTwoCouriersCreation() {
        CourierCreate courierCreate = new CourierCreate(user.getLogin(), user.getPassword(), user.getFirstName());//создаем объект, который соответствует JSON схеме
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(courierCreate)
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", Matchers.equalTo("Этот логин уже используется"));
    }

    //если логина нет, запрос возвращает ошибку
    @Test
    @DisplayName("Создание курьера без логина")
    public void checkCourierCreationWithoutLogin() {
        CourierCreate courierCreate = new CourierCreate(null, user.getPassword(), user.getFirstName());//создаем объект, который соответствует JSON схеме без логина
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(courierCreate)
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));

    }

    //если пароля нет, запрос возвращает ошибку
    @Test
    @DisplayName("Создание курьера без пароля")
    public void checkCourierCreationWithoutPassword() {
        CourierCreate courierCreate = new CourierCreate(user.getLogin(), null, user.getFirstName());//создаем объект, который соответствует JSON схеме без пароля
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(courierCreate)
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));

    }

}