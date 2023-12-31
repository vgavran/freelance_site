package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.enabled
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import org.openqa.selenium.By
import java.time.Duration

class OrderPage: SelenideObjects(){

    fun getOrderId(): String {
        return elx("//*[@id = 'changeorder-oid']").getAttribute("value").toString()
    }

    fun startDiscuss() {
        el(By.partialLinkText("Обсудить")).shouldBe(visible, Duration.ofSeconds(10)).shouldBe(visible).click()
    }

    fun orderConfirm() {
        el(By.id("button-accept-order")).shouldBe(visible).click()
        if (el(By.id("input-code")).isDisplayed) {
            el(By.id("input-code")).value = "1111"
            el(By.id("button-accept-order")).shouldBe(visible).click()
        }
        el(By.cssSelector("span.__tservices_orders_feedback_submit_label")).shouldBe(visible).click()
    }

    fun notifyCompletedWork() {
        el(By.linkText("Уведомить о выполненной работе")).shouldBe(visible).click()
    }

    fun checkPaymentStatus() {
        el(By.cssSelector("#reservespayoutform > div.b-fon.b-fon_margbot_20")).isDisplayed
    }

    fun moveOrderToArbitrage(arbitrageText: String?) {
        el(By.linkText("обратиться в Арбитраж")).shouldBe(visible).click()
        el(By.name("message")).value = arbitrageText
        el(By.linkText("Начать Арбитраж")).shouldBe(visible).click()
    }

    fun makeDecisionArbitrage() {
        el(By.id("arbitrage_sum_frl")).value = "1000"
        el(By.id("arbitrage_apply")).shouldBe(visible).click()
        Selenide.confirm()
    }

    fun sendMessageByArbitr(textMessage: String?) {
        elx("//*[@id = 'quill-container']/div").value = textMessage
        elx("//a[text()='Отправить сообщение']").shouldBe(visible).click()
    }

    fun cancelingOrder() {
        el(By.linkText("Отменить заказ")).shouldBe(visible).click()
    }

    fun checkOrderCancellationModal() {
        elx("//*[@class = 'modal-body p-24']").shouldBe(Condition.visible)
    }

    fun checkOrderCancellationStatusByCustomer() {
        elx("//*[starts-with(@id, 'tservices_order_status')]//div[contains(text(),'Заказ с оплатой через Безопасную сделку — заказ отменен')]")
            .shouldBe(Condition.visible)
        elx("//*[starts-with(@id, 'tservices_order_status')]//div[contains(text(),'Сотрудничество по заказу отменено')]")
            .shouldBe(Condition.visible)
    }

    fun checkOrderCancellationStatusByFreel() {
        elx("//*[starts-with(@id, 'tservices_order_status')]//div[contains(text(),'Заказ с оплатой через Безопасную сделку — заказ отменен')]")
            .shouldBe(Condition.visible)
        elx("//*[starts-with(@id, 'tservices_order_status')]//div[contains(text(),'К сожалению, Заказчик отменил свой заказ')]")
            .shouldBe(Condition.visible)
    }

    fun checkCancelStatusByFreelInOldOrder() {
        elx("//*[starts-with(@id, 'tservices_order_status')]//div[contains(text(),'Сделка отменена, истек срок ожидания оплаты.')]")
            .shouldBe(Condition.visible)
    }

    @Throws(InterruptedException::class)
    fun receiveMoneyToCardFreel() {
        el(By.partialLinkText("Получить выплату за работу")).shouldBe(visible).click()
        if (elx("//*[text() = 'Добавить новую карту']").`is`(visible)) {
            elx("//*[text() = 'Добавить новую карту']").shouldBe(visible).click()
        }
        el(By.id("el-card")).value = "4200000000000000"
        el(By.id("el-smscode")).clear()
        el(By.id("el-smscode")).value = "1111"
        val code = el(By.id("el-smscode")).shouldBe(visible).value
        if (code == "") {
            el(By.id("el-smscode")).value = "1111"
        }
        el(By.id("el-btnsubmit")).shouldBe(visible).click()
    }

    @Throws(InterruptedException::class)
    fun receiveMoneyToCardFreelArbitrage() {
        el(By.partialLinkText("Подтвердить выплату суммы")).shouldBe(visible).click()
        if (elx("//*[text() = 'Добавить новую карту']").`is`(visible)) {
            elx("//*[text() = 'Добавить новую карту']").shouldBe(visible).click()
        }
        el(By.id("el-card")).value = "4200000000000000"
        Selenide.sleep(1000)
        el(By.id("el-btnsubmit")).shouldBe(visible).click()
    }

    @Throws(InterruptedException::class)
    fun receiveMoneyToUMFreel() {
        el(By.id("payout-confirm-checkbox")).shouldBe(visible).click()
        el(By.linkText("Получить выплату за работу")).shouldBe(visible).click()
        el(By.linkText("ЮMoney")).shouldBe(visible).click()
    }

    @Throws(InterruptedException::class)
    fun receiveMoneyToBankAccount() {
        el(By.id("payout-confirm-checkbox")).shouldBe(visible).click()
        el(By.linkText("Получить выплату за работу")).shouldBe(visible).click()
        elx("//*[contains(@class, 'b-button__pm_bank')]").shouldBe(visible).click()
    }

    fun checkSuccessfulPaymentToFreel() {
        elx("//*[contains(text(), 'Заказ с оплатой через Безопасную сделку — выплата сумм')]").shouldBe(visible)
        elx("//*[contains(text(), 'Оставить отзыв')]").shouldBe(visible)
    }

    fun checkDirectOrderCreation(projectName: String?) {
        elx("//h1[@class = 'b-page__title']").shouldBe(Condition.visible)
            .shouldHave(Condition.text(projectName))
    }

    fun payDirectOrder(){
        elx("//*[contains(text(), 'Зарезервировать')]").shouldBe(visible).click()
    }

    fun checkNeedFillFinance() {
        elx("//*[contains(text(), 'Перейти на страницу \"Финансы\"')]").shouldBe(visible).shouldBe(Condition.enabled)
    }
}