package ru.netology;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.getRegisterUser;
import static ru.netology.DataGenerator.Registration.getUser;
import static ru.netology.DataGenerator.getRandomLogin;
import static ru.netology.DataGenerator.getRandomPassword;

public class RegistrationTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    public void shouldSuccessfulLoginIfRegisteredActiveUser() {
        val registeredUser = getRegisterUser("active");
        $("[name='login']").setValue(registeredUser.getLogin());
        $("[type='password']").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".App_appContainer__3jRx1").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        val notRegisteredUser = getUser("active");
        $("[name='login']").setValue(notRegisteredUser.getLogin());
        $("[type='password']").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Ошибка")).shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        val blockedUser = getRegisterUser("blocked");
        $("[name='login']").setValue(blockedUser.getLogin());
        $("[type='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Ошибка")).shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        val registeredUser = getRegisterUser("active");
        var wrongLogin = getRandomLogin();
        $("[name='login']").setValue(DataGenerator.getRandomLogin());
        $("[type='password']").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Ошибка")).shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        val registeredUser = getRegisterUser("active");
        var wrongPassword = getRandomPassword();
        $("[name='login']").setValue(registeredUser.getLogin());
        $("[type='password']").setValue(DataGenerator.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Ошибка")).shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
