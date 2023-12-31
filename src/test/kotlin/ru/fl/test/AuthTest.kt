package ru.fl.test

import org.testng.annotations.Test
import ru.fl.model.UsersData.Users.FreelIndividualPRO

class AuthTest : TestBase() {

    @Test
    fun validationEmailMask() {
        val login = "ifdevice@gmail"
        val pass = app.getProperty("passOld")
        app.login().signIn(login, pass)
        app.loginPage.checkLoginPassError()
    }

    @Test
    fun incorrectPass() {
        val email = app.login().getEmail(FreelIndividualPRO)
        val pass = "12345ASd"
        app.login().signIn(email, pass)
        app.loginPage.checkLoginPassError()
    }

    @Test
    fun successAuthByLogin() {
        app.login().signIn(FreelIndividualPRO)
        app.headerPage.checkIsAccountFreelancer()
    }

    @Test
    fun successAuthByEmail() {
        val email = app.login().getEmail(FreelIndividualPRO)
        val pass = app.login().getPass(FreelIndividualPRO)
        app.login().signIn(email, pass)
        app.headerPage.checkIsAccountFreelancer()
    }

    @Test
    fun successAuthByPhone() {
        val phone = app.login().getEmail(FreelIndividualPRO)
        val  pass = app.login().getPass(FreelIndividualPRO)
        app.login().signIn(phone, pass)
        app.headerPage.checkIsAccountFreelancer()
    }

    @Test
    fun successLogOut() {
        app.login().signIn(FreelIndividualPRO)
        app.login().signOut()
        app.loginPage.checkSignOut()
    }

    @Test
    fun authByOK() {
        app.goTo().auth()
        app.loginPage.loginByOK()
        app.loginPage.checkOpenOKLoginPage()
    }

    @Test(enabled = false)
    fun authByVK() {
        app.goTo().auth()
        app.loginPage.loginByVK()
        app.loginPage.checkOpenVKLoginPage()
    }

    @Test(enabled = false)
    fun authByFB() {
        app.goTo().auth()
        app.loginPage.loginByFB()
        app.loginPage.checkOpenFBLoginPage()
    }

    @Test
    fun goToRegistrationPage() {
        app.goTo().auth()
        app.loginPage.goToRegistrationPage()
        app.loginPage.checkRegistrationPage()
    }

    //    @Test НУЖНО ОТКЛЮЧЕНИЕ КАПЧИ
//    public void passwordRecovery() {
//        String email = app.login().getEmail(FreelEmail);
//        app.loginPage.forgotPass();
//        app.loginPage.remindEmail(email);
//        app.loginPage.checkSendingPassRecovery();
//    }
}
