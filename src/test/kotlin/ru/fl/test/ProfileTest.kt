package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import ru.fl.model.UsersData
import java.io.IOException

class ProfileTest : TestBase() {
    @Test
    @Throws(IOException::class)
    fun changeProfileInfo() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val pass = app.getProperty("passNew")
        app.api().registration(email, "customer")
        app.goTo().mainPage()
        app.login().signIn(login, pass)
        app.headerPage.openSettings()
        app.profilePage.setNameUser()
        app.profilePage.setSurnameUser()
        app.profilePage.enterPasswordUser(pass)
        app.profilePage.saveChangePersonalData()
        app.profilePage.checkChangePersonalDataSaved(login)
    }

    @Test
    fun purchaseSpecialization() {
        app.login().signIn(UsersData.Users.FreelIndividualPRO)
        app.headerPage.openSettings()
        app.profilePage.addSpecialization()
        app.profilePage.selectSpecialization()
        app.paymentPage.buySpecialization()
        app.paymentPage.testPayment()
        app.profilePage.checkSuccessfulPurchaseSpecialization()
    }
}