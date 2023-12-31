package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import org.openqa.selenium.By
import java.time.Duration

class PaymentPage: SelenideObjects(){

    val cardsYooKassa = listOf(
        "4111111111111111",
        "5555555555554444",
        "6759649826438453",
        "2202474301322987",
        "370000000000002",
        "3528000700000000",
        "36700102000000"
    )

    fun testPaymentOrder(orderId: String){
        open("${Configuration.baseUrl}/internal_testing/order/$orderId/pay/")
    }

    fun cardNumberKassaCom() {
        el(By.id("card_cardNumber")).shouldBe(Condition.visible, Duration.ofSeconds(10)).value = "4200000000000000"
    }

    fun cardExpireKassaCom() {
        el(By.id("card_cardExpire")).shouldBe(Condition.visible, Duration.ofSeconds(10)).value = "1123"
    }

    fun cardCvvKassaCom() {
        el(By.id("card_cardCvv")).shouldBe(Condition.visible, Duration.ofSeconds(10)).value = "432"
    }

    fun submitPaymentKassaCom() {
        el(By.cssSelector(".js-card-submit")).shouldBe(Condition.visible, Duration.ofSeconds(10)).click()
    }

    fun returnToFLKassaCom() {
        el(By.cssSelector(".pf__action-button")).shouldBe(Condition.visible, Duration.ofSeconds(10)).click()
    }

    fun cardNumberYooMoney(cardNumber: String) {
        el(By.name("skr_card-number")).shouldBe(Condition.visible).value =
            cardNumber
    }

    fun cardExpireYooMoney() {
        el(By.name("skr_month")).shouldBe(Condition.visible).value = "12"
        el(By.name("skr_year")).shouldBe(Condition.visible).value = "25"
    }

    fun cardCvсYooMoney() {
        el(By.name("skr_cardCvc")).shouldBe(Condition.visible).value = "000"
    }

    fun submitPaymentYooMoney() {
        el(By.className("button__text")).shouldBe(Condition.visible, Duration.ofSeconds(10)).click()
    }

    fun returnToFLYooMoney() {
        elx("//h1").shouldBe(Condition.visible, Duration.ofSeconds(10))
        elx("//*[@role = 'link']").shouldBe(Condition.visible, Duration.ofSeconds(10)).click()
    }

    fun setCardTypePayment(){
        elx("//*[@data-quick-payment-type = 'dolcard']").shouldBe(Condition.visible).click()
    }

    fun testPayment(){
        el(By.name("success")).shouldBe(Condition.visible).click()
        el(By.linkText("Вернуться в магазин")).shouldBe(Condition.visible).click()
    }

    fun buySpecialization() {
        elx("//*[@id = 'paidmainspec']//*[@data-quick-payment-type = 'dolcard']").shouldBe(Condition.visible).click()
    }

    fun setTypePayment() {
        elx("//*[contains (@class, 'b-button__pm_card')]").shouldBe(Condition.visible).click()
    }

    fun competitionPayment(){
        elx("//*[@id = 'quick_pro_block_1']//*[contains(@class, 'b-button__pm_card')]").shouldBe(Condition.visible).click()
        elx("//input[@value='Успешно оплатить']").shouldBe(Condition.visible).click()
        el(By.linkText("Вернуться в магазин")).shouldBe(Condition.visible).click()
    }

    fun cardLimited(): Boolean{
        elx("//h1[contains(@class, 'title')]").shouldBe(Condition.visible, Duration.ofSeconds(15))
        return elx("//h1[contains(text(), 'Карта достигла лимита')]").`is`(Condition.visible)
    }

    fun tryAgain() {
        elx("//*[contains(text(), 'Попробовать снова')]").shouldBe(Condition.visible).click()
    }
}