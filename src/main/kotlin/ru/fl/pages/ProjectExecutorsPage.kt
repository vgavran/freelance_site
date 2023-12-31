package ru.fl.pages

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.sleep
import org.openqa.selenium.By
import java.time.Duration


class ProjectExecutorsPage: SelenideObjects() {

    fun startDiscuss() {
        elx("//*[contains(text(), 'Написать исполнителю')]").shouldBe(visible, Duration.ofSeconds(10)).click()
    }

    fun moveOrderToArbitrage(arbitrageText: String?) {
        el(By.linkText("обратиться в Арбитраж")).shouldBe(visible).click()
        el(By.name("message")).value = arbitrageText
        el(By.linkText("Начать Арбитраж")).shouldBe(visible).click()
    }

    fun reservePay() {
        sleep(3000)
        elx("//button[contains(text(), 'Зарезервировать оплату')]").shouldBe(visible).click()
        elx("//button[contains(text(), 'Подтвердить')]").shouldBe(visible).click()
    }

    fun checkSuccessPayment() {
        sleep(1000)
        elx("//*[@class = 'circle-loader']").shouldBe(disappear, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-order-commercial-block']").shouldBe(visible, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-card-header']").shouldHave(text("В работе"))
    }

    fun checkNeedFillFinance() {
        sleep(1000)
        elx("//*[@class = 'circle-loader']").shouldBe(disappear, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-order-commercial-block']").shouldBe(visible, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-card-header']").shouldHave(text("Заполните ваши финансовые данные"))
    }

    fun goToFillFinance() {
        elx("//*[contains(text(), 'Заполнить финансовые данные')]").shouldBe(visible).click()
    }

    fun closeOrder(text: String) {
        el(By.id("executors-count")).shouldBe(visible).click()
        elx("//button[contains(text(), 'Работа выполнена')]").shouldBe(visible).click()
        el(By.id("modal-sms-phone")).shouldBe(visible)
        el(By.id("input-code")).shouldBe(visible).value = "1111"
        el(By.cssSelector(".custom-control-label")).shouldBe(visible).click()
        el(By.cssSelector("button.text-nowrap")).shouldBe(visible).click()
        el(By.cssSelector(".display-5 > div:nth-child(1)")).shouldBe(visible, Duration.ofSeconds(10))
            .shouldHave(text(text))
        elx("//*[@id = 'comment' and  @placeholder = 'Оставьте отзыв']").value = "Хороший отзыв"
        el(By.cssSelector(".mt-48")).shouldBe(visible).click()
    }

    fun checkSuccessSelectionExecutor(name: String?) {
        el(By.id("executors-count")).shouldBe(visible).shouldHave(text("1"))
        elx("//*[@data-id = 'qa-card-user-title']").shouldBe(visible).shouldHave(text(name))
    }

    fun checkCancelStatusByCustomerInOldOrder() {
        sleep(1000)
        elx("//*[@class = 'circle-loader']").shouldBe(disappear, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-order-commercial-block']").shouldBe(visible, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-card-header']").shouldHave(text("Сделка отменена, истек срок ожидания оплаты"))
    }
}