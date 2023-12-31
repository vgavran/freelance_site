package ru.fl.pages

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import org.openqa.selenium.By

class RegistrationPage: SelenideObjects(){

    fun email(email: String?) {
        el(By.id("ui-input-user-email")).value = email
    }

    fun pass(passNew: String?) {
        el(By.id("user-password")).value = passNew
    }

    fun submit() {
        el(By.id("qa-registration-button")).shouldBe(visible).click()
    }

    fun selectCustomer() {
        elx("//button[contains(text(),'Продолжить как заказчик')]").shouldBe(visible).click()
    }

    fun selectFreelancer() {
        elx("//button[contains(text(),'Продолжить как фрилансер')]").shouldBe(visible).click()
    }

    fun checkSuccessRegistration() {
        elx("//*[@class = 'fl-content-main']/h1").shouldHave(text("Вы зарегистрированы!"))
    }

    fun checkModalActivateMail() {
        elx("//*[@id='activate-reminder-modal']").shouldBe(visible)
    }

    fun checkSuccessActivated() {
        el(By.className("b-general-notification"))
            .shouldHave(text("Ваш аккаунт успешно активирован"))
    }

    fun emailOldModal(email: String?) {
        el(By.id("el-email")).value = email
    }

    fun passOldModal(passNew: String?) {
        el(By.id("el-passwd")).value = passNew
    }

    fun submitOldModal() {
        el(By.id("el-submit")).shouldBe(visible).click()
    }
}