package ru.fl.pages

import com.codeborne.selenide.Condition

class AccountPage: SelenideObjects(){
    fun checkBuyInsurance() {
        elx("//*[text()='Страхование услуги ПРО аккаунта']").shouldBe(Condition.visible)
    }

    fun checkNotBuyInsurance() {
        elx("//*[text()='Страхование услуги ПРО аккаунта']").shouldNotBe(Condition.visible)
    }
}