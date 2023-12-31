package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import ru.fl.model.UsersData.Users.CustomerIndividualPRO
import ru.fl.model.UsersData.Users.FreelIndividualPRO
import java.io.IOException

class ProProfiTest : TestBase() {

    @Test
    //Покупка аккаунта PRO фрилансера на 1 месяц в старом аккаунте
    fun freelBuyPro1Month() {
        app.login().signIn(FreelIndividualPRO)
        app.headerPage.goToPRO()
        app.proPage.selectOneMonthProFreel()
        app.proPage.buyProOneMonthClickFreel()
        app.proPage.otherPaymentMethodsClick()
        app.pay().yooKassaTest()
        app.proPage.checkSuccessfulPurchaseFreelPRO()
    }

    @Test
    //Покупка аккаунта PRO фрилансера на 3 месяца в старом аккаунте
    fun freelBuyPro3Month() {
        app.login().signIn(FreelIndividualPRO)
        app.headerPage.goToPRO()
        app.proPage.selectThreeMonthProFreel()
        app.proPage.buyProThreeMonthClickFreel()
        app.proPage.otherPaymentMethodsClick()
        app.pay().yooKassaTest()
        app.proPage.checkSuccessfulPurchaseFreelPRO()
    }

    @Test
    //Покупка аккаунта PRO фрилансера на 1 год в старом аккаунте
    fun freelBuyPro1Year() {
        app.login().signIn(FreelIndividualPRO)
        app.headerPage.goToPRO()
        app.proPage.selectOneYearProFreel()
        app.proPage.buyProOneYearClickFreel()
        app.proPage.otherPaymentMethodsClick()
        app.pay().yooKassaTest()
        app.proPage.checkSuccessfulPurchaseFreelPRO()
    }

    @Test
    //Покупка аккаунта PRO заказчика на 1 месяц
    fun customerBuyPro1Month() {
        app.login().signIn(CustomerIndividualPRO)
        app.headerPage.goToPRO()
        app.proPage.buyOneMonthProCustomer()
        app.proPage.otherPaymentMethodsClick()
        app.pay().yooKassaTest()
        app.proPage.checkSuccessfulPurchaseCustomerPRO()
    }

    @Test
    //Покупка аккаунта PRO фрилансера на 1 месяц + страховка (новый аккаунт)
    @Throws(IOException::class)
    fun proInsuranceBuyNewRegFreel() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.headerPage.goToPRONewReg()
        app.proPage.buyProOneMonthClickFreel()
        app.pay().yooKassaTest()
        app.goTo().personalAccount()
        app.accountPage.checkBuyInsurance()
    }

    @Test
    //Покупка аккаунта PRO фрилансера на 1 месяц, без страховки (новый аккаунт)
    @Throws(IOException::class)
    fun proWithoutInsuranceBuyNewRegFreel() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.headerPage.goToPRONewReg()
        app.proPage.removeInsuranceCheckbox()
        app.proPage.buyProOneMonthClickFreel()
        app.pay().yooKassaTest()
        app.goTo().personalAccount()
        app.accountPage.checkNotBuyInsurance()
    }
}