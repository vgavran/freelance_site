package ru.fl.pages

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import org.openqa.selenium.By

class ProfilePage: SelenideObjects(){
    fun createProject() {
        elx("//*[contains(text(), 'Разместить заказ')]").shouldBe(visible).click()
    }

    fun offerOrder() {
        elx("//*[contains(text(), 'Предложить заказ')]").shouldBe(visible).click()
    }

    fun selectNewOrderCreate() {
        el(By.id("newPersonalOrderProjectPopup")).shouldBe(visible)
        elx("//*[@id = 'newPersonalOrderProjectPopup']//*[contains(text(), 'Создать новый заказ')]").shouldBe(visible).click()
    }

    fun addSpecialization() {
        el(By.id("add-base-specialization")).shouldBe(visible).click()
    }

    fun selectSpecialization() {
        el(By.id("el-specialization")).shouldBe(visible).click()
        elx("//*[@id = 'paidmainspec']//*[contains(text(), 'Разработка игр')]").shouldBe(visible).click()
        elx("//*[@id = 'paidmainspec']//*[contains(text(), 'Программирование игр')]").shouldBe(visible).click()
    }

    fun checkSuccessfulPurchaseSpecialization(){
        elx("//*[@id  = 'quickPaymentPopupPaidMainSpecSuccessPopup']//*[contains(@class, 'title_pro')]")
            .shouldHave(text("Вы успешно купили основную специализацию"))
        el(By.id("b-close-icon-done")).shouldBe(visible).click()
    }

    fun setNameUser(){
        el(By.name("name")).value = "Зак"
    }

    fun setSurnameUser(){
        el(By.name("surname")).value = "Закович"
    }

    fun enterPasswordUser(pass: String){
        el(By.name("oldpwd")).value = pass
    }

    fun saveChangePersonalData(){
        el(By.name("btn")).shouldBe(visible).click()
    }

    fun checkChangePersonalDataSaved(login: String){
        el(By.id("profile-user-name")).shouldHave(text("Зак Закович [$login]"))
    }


}