package ru.fl.pages

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import org.openqa.selenium.By
import java.time.Duration

class LoginPage: SelenideObjects(){
    fun loginByOK() {
        elx("//*[contains(@class, 'bg-orange-ok')]").shouldBe(visible).click()
    }

    fun checkOpenOKLoginPage() {
        elx("//*[@class = 'widget-oauth']").shouldBe(visible, Duration.ofSeconds(10))
    }

    fun loginByVK() {
        elx("//*[contains(@class, 'bg-blue-vk')]").shouldBe(visible).click()
    }

    fun checkOpenVKLoginPage() {
        elx("//*[@class = 'oauth_form']").shouldBe(visible)
    }

    fun loginByFB() {
        elx("//*[contains(@class, 'bg-blue-fb')]").shouldBe(visible).click()
    }

    fun checkOpenFBLoginPage() {
        elx("//*[@id = 'loginform']").shouldBe(visible, Duration.ofSeconds(10))
    }

    fun login(username: String?) {
        el(By.name("username")).value = username
    }

    fun pass(pass: String?) {
        el(By.name("password")).value = pass
    }

    fun signIn() {
        el(By.id("submit-button")).shouldBe(visible).click()
    }

    fun checkSignOut() {
        el(By.cssSelector(".pr-0")).shouldBe(visible)
    }

    fun goToRegistrationPage() {
        elx("//*[text() = 'Регистрация']").shouldBe(visible).click()
    }

    fun checkRegistrationPage() {
        elx("//button[contains(text(), 'Продолжить как фрилансер')]").shouldBe(visible)
        elx("//button[contains(text(), 'Продолжить как заказчик')]").shouldBe(visible)
    }

    fun checkLoginPassError() {
        elx("//*[contains(@class, 'invalid-feedback')]").shouldHave(text("Неверный логин/пароль"))
    }

    fun forgotPass() {
        elx("//*[@href = '/remind/']").shouldBe(visible)
    }

    fun remindEmail(email: String?) {
        elx("//*[@id = 'remind_email']").shouldBe(visible).value = email
        elx("//*[@id = 'remind_button_email']").shouldBe(visible).click()
    }

    fun checkSendingPassRecovery() {
        elx("//*[@id = 'remind_ok_email']").shouldBe(visible)
    }

    fun checkLoginError() {
        el(By.id("el-login-error-text")).shouldBe(visible)
    }
}