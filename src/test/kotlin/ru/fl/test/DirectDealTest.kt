package ru.fl.test

import com.codeborne.selenide.WebDriverRunner.url
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.model.UsersData.Users.*

class DirectDealTest : TestBase() {
    @Test
    @Throws(InterruptedException::class)
    fun testArbitrage() {
        val loginFreelancer = app.login().getLogin(FreelIndividualPRO)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        val projectName = "selenide test " + randomLetters() + "Test order individual for arbitrage"
        app.create().directOrderSafeDeal(projectName, "Тестовый заказ для арибитража", "3450", "6")
        val order: String = url()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(order)
        app.orderPage.orderConfirm()
        app.login().signOut()
        app.login().signIn(CustomerIndividualPRO);
        app.goTo().page(order);
        app.orderPage.payDirectOrder()
        app.orderPage.moveOrderToArbitrage("Заказчик обращается в арбитраж");
        app.login().signOut()
        app.login().signIn(Arbitr)
        app.goTo().page(order)
        app.orderPage.makeDecisionArbitrage()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(order)
        app.orderPage.receiveMoneyToCardFreelArbitrage()
        app.orderPage.checkPaymentStatus()
    }

    @Test
    /* Создание прямой сделки с выбором оплаты через банковскую карту */
    @Throws(InterruptedException::class)
    fun testDirectOrderCreate() {
        val loginFreelancer = app.login().getLogin(FreelIndividualPRO)
        app.login().signIn(CustomerIndividualPRO)
        val projectName = "selenide test " + randomLetters() + " order individual"
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.fillForm().directOrder(projectName, "order from individual to individual", "1", "2000")
        app.orderFormPage.checkDefaultPaymentType("С банковской карты физ. лица")
        app.orderFormPage.checkPaymentTypeAvailability(
            "С банковской карты физ. лица",
            "Незащищенная сделка"
        )
        app.orderFormPage.buttonOfferOrders()
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test
    /* Создание прямой сделки юр лицом */
    @Throws(InterruptedException::class)
    fun testDirectOrderCreateYurik() {
        val loginFreelancer = app.login().getLogin(FreelIndividualPRO)
        app.login().signIn(CustomerEntityLS)
        val projectName = "selenide test " + randomLetters() + " order yurik"
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.fillForm().directOrder(projectName, "order from individual to individual yurik", "1", "1000")
        app.orderFormPage.checkDefaultPaymentType("Счет для юридических лиц")
        app.orderFormPage.checkPaymentTypeAvailability(
            "Счет для юридических лиц",
            "Незащищенная сделка"
        )
        app.orderFormPage.buttonOfferOrders()
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test
    @Throws(InterruptedException::class)
    fun cancelSafeDealDirectOrder() {
        val loginFreelancer = app.login().getLogin(FreelIndividualPRO)
        app.login().signIn(CustomerIndividualPRO)
        app.headerPage.goToFreelancersCatalogOld()
        app.freelancersPage.search(loginFreelancer)
        app.freelancersPage.goToProfile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.create().directOrderSafeDeal(
            "Test order individual for canceling",
            "Тестовый заказ который будет отменен. (Из профиля, Касса.ком)", "1500", "5"
        )
        val order: String = url()
        app.orderPage.cancelingOrder()
        app.orderPage.checkOrderCancellationStatusByCustomer()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(order)
        app.orderPage.checkOrderCancellationStatusByFreel()
    }
}