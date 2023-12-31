package ru.fl.pages

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.refresh
import org.openqa.selenium.By


class PersonalAccountPage: SelenideObjects(){
    fun closeOldInvoice(){
        if (elx("//*[@class = 'fl-billing-personal-block-item-close']").isDisplayed) {
            elx("//*[@class = 'fl-billing-personal-block-item-close']").shouldBe(visible).click()
        }
    }

    fun topUpAccountClick() {
        elx("//button[contains(text(),'Пополнить счет')]").shouldBe(visible).click()
    }

    fun topUpAccountWithBillInvoice(billSum: String) {
        el(By.id("bill-history-fill-input")).value = billSum
        elx("//button[contains(text(),'Скачать счет на оплату')]").shouldBe(visible).click()
        elx("//*[@data-id = 'qa-ui-button-icon']").shouldBe(visible).click()
    }

    fun topUpAccountWithCard(billSum: String) {
        el(By.id("bill-history-fill-input")).value = billSum
        elx("//*[@id = 'modal-bill-history']//button[contains(text(),'Пополнить')]").shouldBe(visible).click()
    }

    fun getSumAccount() : Int{
        val text = elx("//*[@id = 'qa-bill-history-pay-block']//span").text.replace("[^0-9]".toRegex(), "")
        return text.toInt()
    }

    fun getSumFreelancerAccount() : Int{
        val text = elx("//span[contains(text(), '.00 руб.')]")
            .text.replace("[^0-9]".toRegex(), "")
        val textSum = text.dropLast(2)
        return textSum.toInt()
    }

    fun waitingGenerateBill() {
        //Ждем когда сформируется счет и исчезнет спиннер
        elx("//div[@class='b-layout b-layout_waiting']").should(disappear)
    }

    fun getLSNumber(): String {
        elx("//*[contains(@class, 'fl-billing-personal-block-item-ls')]").shouldBe(visible)
        refresh()
        elx("//*[contains(@class, 'fl-billing-personal-block-item-ls')]").shouldBe(visible)
        val text = elxs("//*[contains(@class, 'fl-billing-personal-block-item-ls')]").first().text
        return text.substring(8)
    }

    fun getFreelLSNumber(): String {
        elx("//*[@id='bill_invoice_remove']//a[contains(text(),'Счет')]").shouldBe(visible)
        val text = elxs("//*[@id='bill_invoice_remove']//a[contains(text(),'Счет')]").first().text.substring(10)
        return text
    }

    fun topUpAccountWithCardByFreelancer(billSum: String) {
        elx("//a[@data-popup='quick_payment_account']").shouldBe(visible).click()
        el(By.id("account_price")).value = billSum
        elx("//*[@data-quick-payment-type = 'dolcard']//span[@class='b-button__txt']").shouldBe(visible).click()
        el(By.name("success")).shouldBe(visible).shouldBe(visible).click()
        el(By.linkText("Вернуться в магазин")).shouldBe().shouldBe(visible).click()
    }

    fun topUpAccountWithBillByFreelancer(billSum: String) {
        elx("//a[@data-popup='quickPaymentPopupBillInvoice']").shouldBe(visible).click()
        el(By.id("el-sum")).value = billSum
        elx("//a[@data-quick-payment-type = 'bank']").shouldBe(visible).click()
        elx("//div[@class='b-layout b-layout_waiting']").should(disappear)
    }

    fun selectCardType() {
        elx("//button[contains(text(), 'с банковской карты')]").scrollTo().shouldBe(visible).click()
    }

    fun selectAccountType() {
        elx("//button[contains(text(), 'с расчетного счета')]").scrollTo().shouldBe(visible).click()
    }

    fun confirmSelectedType() {
        elx("//button[contains(text(), 'Подтвердить выбор')]").shouldBe(visible).click()
    }

    fun checkEntitySelected() {
        elx("//*[@id = 'status-ip']").shouldHave(selected)
        elx("//*[@id = 'status-fiz']").shouldNotBe(visible)
    }

    fun checkIndividualSelected() {
        elx("//*[@id = 'status-fiz']").shouldHave(selected)
        elx("//*[@id = 'status-ip']").shouldNotBe(visible)
    }

    fun checkIndividualSaved() {
        elx("//*[text() = 'Юридический статус']/../../td[2]//*").shouldHave(text("физическое лицо"))
    }

    fun checkEntitySaved() {
        elx("//*[text() = 'Юридический статус']/../../td[2]//*")
            .shouldHave(text("ИП или юридическое лицо"))
    }

    fun acceptPhone(phone: String) {
        elx("//*[@id = 'modal-sms-phone']//input[@class = 'form-control']").value = phone
        elx("//*[@id = 'modal-sms-phone']//button[contains(text(), 'Получить СМС')]").shouldBe(enabled).shouldBe(visible).click()
        el(By.id("input-code")).shouldBe(visible).value = "1111"
    }
}