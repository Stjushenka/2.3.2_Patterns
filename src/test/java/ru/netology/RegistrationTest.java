package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;


public class RegistrationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLogInIfUserIsValid (){
        RequestData user = DataGenerator.generateNewActiveValidUser();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Личный кабинет")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotLogInIfBlockedUser() {
        RequestData user = DataGenerator.generateNewBlockedUser();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Пользователь заблокирован")).waitUntil(Condition.visible, 15000);
    }

    @Test

    void shouldNotLogInIfInvalidLogin() {
        RequestData user = DataGenerator.generateNewActiveUserInvalidLogin();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotLogInIfInvalidPassword() {
        RequestData user = DataGenerator.generateNewActiveInvalidPassword();
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }
}