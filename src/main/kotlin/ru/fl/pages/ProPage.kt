package ru.fl.pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By

class ProPage: SelenideObjects(){

    fun selectOneMonthProFreel(){
        el(By.id("proSelectPicker")).shouldBe(Condition.visible).click()
        elx("//*[contains(text(), 'На 1 месяц')]").shouldBe(Condition.visible).click()
    }

    fun selectThreeMonthProFreel(){
        el(By.id("proSelectPicker")).shouldBe(Condition.visible).click()
        elx("//*[contains(text(), 'На 3 месяца')]").shouldBe(Condition.visible).click()
    }

    fun selectOneYearProFreel(){
        el(By.id("proSelectPicker")).shouldBe(Condition.visible).click()
        elx("//*[contains(text(), 'На 1 год')]").shouldBe(Condition.visible).click()
    }

    fun buyOneMonthProCustomer(){
        el(By.id("is_enough_15")).shouldBe(Condition.visible).click()
    }

    fun removeInsuranceCheckbox(){
        elx("//*[@for='customCheck-insurance']").shouldBe(Condition.visible).click()
    }

    fun buyProOneMonthClickFreel(){
        elx("//button[contains(text(), 'Купить PRO на 1 месяц')]").shouldBe(Condition.visible).click()
    }

    fun buyProThreeMonthClickFreel(){
        elx("//button[contains(text(), 'Купить PRO на 3 месяца')]").shouldBe(Condition.visible).click()
    }

    fun buyProOneYearClickFreel(){
        elx("//button[contains(text(), 'Купить PRO на 1 год')]").shouldBe(Condition.visible).click()
    }

    fun otherPaymentMethodsClick(){
        elx("//a[contains(text(), 'Другие способы оплаты')]").shouldBe(Condition.visible).click()
    }

    fun checkSuccessfulPurchaseCustomerPRO(){
        elx("//*[@id  = 'quickPaymentPopupProSuccessPopup']//*[contains(@class, 'title_pro')]")
            .shouldHave(Condition.text("Вы успешно купили аккаунт PRO"))
    }

    fun checkSuccessfulPurchaseFreelPRO(){
        elx("//*[@id  = 'quickPaymentPopupPaidMainSpec']//*[contains(@class, 'title_pro')]")
            .shouldHave(Condition.text("Вы успешно купили аккаунт PRO"))
    }
}