package ru.fl.appmanager

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner.url

class PurchaseHelper(val app: ApplicationManager){

    fun kassaCom() {
        app.paymentPage.cardNumberKassaCom()
        app.paymentPage.cardExpireKassaCom()
        app.paymentPage.cardCvvKassaCom()
        app.paymentPage.submitPaymentKassaCom()
        app.paymentPage.returnToFLKassaCom()
    }

    fun yooMoneyCard() {
        val cards = app.paymentPage.cardsYooKassa
        for (card in cards) {
            app.paymentPage.cardNumberYooMoney(card)
            app.paymentPage.cardExpireYooMoney()
            app.paymentPage.cardCvсYooMoney()
            val url = url()
            app.paymentPage.submitPaymentYooMoney()
            if (app.paymentPage.cardLimited()){
                app.goTo().page(url)
                app.paymentPage.tryAgain()
            }else break
        }
        app.paymentPage.returnToFLYooMoney()
    }

    fun yooKassaTest(){
        app.paymentPage.setCardTypePayment()
        app.paymentPage.testPayment()
    }

    fun paymentCompetition() {
        app.paymentPage.setTypePayment()
        app.paymentPage.competitionPayment()
    }

    fun dealByTestPayment(orderId: String) {
        app.goTo().page("${Configuration.baseUrl}/internal_testing/order/$orderId/pay/")
    }
}