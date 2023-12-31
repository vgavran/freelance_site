package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import java.io.IOException

class RegistrationTest : TestBase() {
    @Test
    //Регистрация фриланeсера без онбординга
    @Throws(IOException::class)
    fun registrationFreelancer() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.goTo().mainPage()
        app.registration().freel(email, app.getProperty("passNew"))
        app.onboardingPage.cancelRegistration()
        app.headerPage.checkIsAccountFreelancer()
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.registrationPage.checkSuccessActivated()
    }

    @Test
    //Регистрация фрилансера с онбордингом
    @Throws(IOException::class)
    fun registrationFreelancerOnboarding() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.goTo().registrationFreelancer()
        app.registration().freel(email, app.getProperty("passNew"))
        app.registration().onbording()
        app.registrationPage.checkSuccessRegistration()
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.registrationPage.checkSuccessActivated()
    }

    @Test
    //Регистрация заказчика (новая форма)
    @Throws(IOException::class)
    fun registrationCustomer() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val pass = app.getProperty("passNew")
        app.goTo().mainPage()
        app.registration().customer(email, pass)
        app.headerPage.checkIsAccountCustomer()
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.registrationPage.checkSuccessActivated()
    }

    @Test
    @Throws(IOException::class)
    fun switchingFreelancerToCreatedCustomer() {
        //Регистрация фрилансера и создание смежного аккаунта заказчика
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(login, app.getProperty("passNew"))
        app.headerPage.goTolinkedAccount()
        app.headerPage.checkIsAccountCustomer()
    }

    @Test
    @Throws(IOException::class)
    fun switchingCustomerToCreatedFreelancer() {
        //Регистрация заказчика и создание смежного аккаунта фрилансера
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(login, app.getProperty("passNew"))
        app.headerPage.goTolinkedAccount()
        app.headerPage.checkIsAccountFreelancer()
    }
}