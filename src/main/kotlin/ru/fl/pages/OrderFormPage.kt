package ru.fl.pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By

class OrderFormPage: SelenideObjects(){

    fun orderName(name: String) {
        el(By.id("el-title")).value = name
    }

    fun orderDescription(description: String) {
        el(By.id("el-description")).value = description
    }

    fun orderTerm(term: String) {
        el(By.id("el-order_days")).value = term
    }

    fun orderPrice(budget: String) {
        el(By.id("el-order_price")).value = budget
    }

    fun selectNotSafeDeal() {
        el(By.id("el-pay")).selectOptionContainingText("Незащищенная сделка")
    }

    fun acceptRisks() {
        el(By.name("agree")).shouldBe(Condition.visible).click()
    }

    fun checkDefaultPaymentType(payment: String) {
        el(By.id("el-pay")).shouldHave(Condition.text(payment))
    }

    fun checkPaymentTypeAvailability(text1: String, text2: String) {
        el(By.id("el-pay")).click()
        el(By.cssSelector("#el-pay > option:nth-child(1)")).shouldHave(Condition.text(text1))
        el(By.cssSelector("#el-pay > option:nth-child(2)")).shouldHave(Condition.text(text2))
    }

    fun buttonOfferOrders() {
        el(By.id("el-btnsubmit")).shouldBe(Condition.visible).click()
    }

    fun clickSubmitBtn() {
        el(By.id("el-btnsubmit")).shouldBe(Condition.visible).click()
    }

    fun clickSubmitBtnWithReg() {
        elx("//button[contains(text(), 'Зарегистрироваться и предложить заказ')]").shouldBe(Condition.visible).click()
    }

    fun goToAuth() {
        elx("//*[contains(text(), 'перейти на страницу авторизации')]").shouldBe(Condition.visible).click()
        el(By.name("username")).shouldBe(Condition.visible)
    }
}