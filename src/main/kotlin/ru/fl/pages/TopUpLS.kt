package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import org.openqa.selenium.By
import org.openqa.selenium.Keys

class TopUpLS: SelenideObjects(){

    fun byBankAccount(billSum: String) {
        Selenide.sleep(2000)
        el(By.id("account-refill-btn")).shouldBe(Condition.visible).click()
        val b = Keys.BACK_SPACE.toString()
        el(By.id("bill-history-fill-input")).sendKeys(b + b + b + b + billSum)
        elx("//button[contains(text(),'Скачать счет на оплату')]")
            .shouldBe(Condition.visible, Condition.enabled).click()
    }

    fun byCard(billSum: String) {
        Selenide.sleep(2000)
        elx("//*[contains(text(), 'Пополнить счет')]").shouldBe(Condition.visible).click()
        el(By.id("modal-account-refill")).shouldBe(Condition.visible)
        val b = Keys.BACK_SPACE.toString()
        elx("//input[@type = 'number']").sendKeys(b + b + b + b + billSum)
        elx("//button[contains(text(),'Пополнить')]").shouldBe(Condition.visible).click()
    }
}