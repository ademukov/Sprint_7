package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.entity.ContentType;
import org.example.dto.Order;
import org.example.mother.FatherOfMother;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class OrderCreate extends FatherOfMother { //Чтобы протестировать создание заказа, нужно использовать параметризацию
    private final List<String> color;

    public OrderCreate(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {null}
        };

    }

    @Test
    @DisplayName("Проверка создания заказа")
    public void createOrder() {
        Order createOrderCheck = new Order();
        createOrderCheck.setFirstName("father");
        createOrderCheck.setLastName("Mat");
        createOrderCheck.setAddress("doma");
        createOrderCheck.setMetroStation("blizhnee");
        createOrderCheck.setPhone("89898989898");
        createOrderCheck.setRentTime(100);
        createOrderCheck.setDeliveryDate("01.01.2007");
        createOrderCheck.setComment("olo");
        createOrderCheck.setColor(color);
        given()
                .contentType(ContentType.APPLICATION_JSON.getMimeType()) // заполнили header
                .body(createOrderCheck)
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .body("track", Matchers.notNullValue());

    }


}
