package ru.fl.appmanager

import com.codeborne.selenide.Selenide.clearBrowserCookies
import com.codeborne.selenide.Selenide.refresh
import com.codeborne.selenide.WebDriverRunner
import org.openqa.selenium.Cookie
import ru.fl.model.UsersData.Users

class LoginHelper(val app: ApplicationManager){

    fun getEmail(user: Users): String? {
        return app.usersData.userData(user)["email"]
    }

    fun getLogin(user: Users): String? {
        return app.usersData.userData(user)["username"]
    }

    fun getName(user: Users): String? {
        return app.usersData.userData(user)["name"]
    }

    fun getSurname(user: Users): String? {
        return app.usersData.userData(user)["surname"]
    }

    fun getPhone(user: Users?): String? {
        return app.usersData.userData(user)["phone"]
    }

    fun getPass(user: Users?): String? {
        return app.usersData.userData(user)["pass"]
    }

    fun getUID(user: Users?): String? {
        return app.usersData.userData(user)["uid"]
    }

    fun acceptCookies() {
        val ca = Cookie("cookies_accepted", "1")
        WebDriverRunner.getWebDriver().manage().addCookie(ca)
        refresh()
    }

    fun signIn(user: Users) {
        val username: String? = app.usersData.userData(user)["username"]
        val pass: String? = app.usersData.userData(user)["pass"]
        app.goTo().auth()
        acceptCookies()
        app.loginPage.login(username)
        app.loginPage.pass(pass)
        app.loginPage.signIn()
    }

    fun signIn(username: String?, pass: String?) {
        app.goTo().auth()
        acceptCookies()
        app.loginPage.login(username)
        app.loginPage.pass(pass)
        app.loginPage.signIn()
    }

    fun signOut() {
        app.headerPage.signOut()
        clearBrowserCookies()
        refresh()
    }
}