package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import java.io.IOException

class FinanceTest : TestBase() {
    @Test
    //Сохранение фин данных в старом стеке для фрилансера физика, резидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveFreelancerIndividualResident() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeFreelIndividualResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в старом стеке для фрилансера физика, нерезидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveFreelancerIndividualNotResident() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeFreelIndividualNotResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в старом стеке для фрилансера юрика, резидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveFreelancerEntity() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeFreelancerEntityResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в старом стеке для заказчика физика, резидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveCustomerIndividual() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeCustomerIndividualResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в старом стеке для заказчика юрика, резидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveCustomerEntity() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeFreelancerEntityResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в старом стеке для фрилансера юрика, нерезидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveFreelancerEntityNotResident() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeEntityNotResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в старом стеке для заказчика юрика, нерезидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveCustomerEntityNotResident() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeEntityNotResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в старом стеке для заказчика физика, нерезидента.
    @Throws(IOException::class)
    fun testOldFinanceSaveCustomerIndividualNotResident() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().financePage(login)
        app.fillForm().financeCustomerIndividualNotResident()
        app.financeOldPage.checkSaveFinance()
    }

    @Test
    //Сохранение фин данных в новом стеке для заказчика юрика, резидента.
    @Throws(IOException::class)
    fun testNewFinanceSaveCustomerEntity() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        val finance = "${config.baseUrl}/user/profile/finances/legal-entity/"
        app.goTo().page(finance)
        app.fillForm().financeCustomerEntityResidentNew()
        app.financeNewPage.checkFinaceUnderReview()
    }

    @Test
    //Сохранение фин данных в новом стеке для заказчика юрика, резидента, поиск по ИНН.
    @Throws(IOException::class)
    fun testNewFinanceSaveCustomerEntityINN() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        val finance = "${config.baseUrl}/user/profile/finances/legal-entity/"
        app.goTo().page(finance)
        app.fillForm().financeCustomerEntityResidentInnNew()
        app.financeNewPage.checkFinaceUnderReview()
    }

    @Test
    //Сохранение фин данных в новом стеке для заказчика юрика, нерезидента.
    @Throws(IOException::class)
    fun testNewFinanceSaveCustomerEntityNotResident() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        val finance = "${config.baseUrl}/user/profile/finances/legal-entity/"
        app.goTo().page(finance)
        app.fillForm().financeCustomerEntityNotResidentNew()
        app.financeNewPage.checkFinaceUnderReview()
    }
}