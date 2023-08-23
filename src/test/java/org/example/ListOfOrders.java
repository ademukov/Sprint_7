package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.entity.ContentType;
import org.example.mother.UserLoginInBeforeClass;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ListOfOrders extends UserLoginInBeforeClass { //Проверь, что в тело ответа возвращается список заказов

    @Test
    @DisplayName("Проверка ручки получения списка заказов")
    public void courierAuth() {

        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders", Matchers.notNullValue());
    }
}
