package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.switchTo

class AdminPanelBS: SelenideObjects(){
    fun approveReservation(numberBS: String?) {
        elx("//*[contains(text(), '$numberBS')]").shouldBe(Condition.visible).click()
        switchTo().window(1)
        elx("//input[contains(@value, 'Подтвердить резерв')]").click()
    }

    fun approveLS(numberLS: String, billSum: String?) {
        elx("//input[contains(@name, '$numberLS')]").shouldBe(Condition.visible).setValue(billSum).pressEnter()
    }

    fun changeOldOrderData(orderId: String) {
        elx("//*[@role='main']/form[2]/input[2]").setValue(orderId)
        elx("//*[@role='main']/form[2]/button[contains(text(), 'Отправить')]").shouldBe(Condition.visible).click()
    }

    fun runCommandForAutocloseOldOrder() {
        elx("//*[@role='main']/form[5]/button[contains(text(), 'Запустить')]").shouldBe(Condition.visible).click()
    }

}