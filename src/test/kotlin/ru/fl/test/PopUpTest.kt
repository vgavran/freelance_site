package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import java.io.IOException

class PopUpTest : TestBase() {
    @Test
    @Throws(IOException::class)
    fun testPopUpNewUser() {
        val newEmail = randomNumberLetter() + "@fl.com"
        app.api().registration(newEmail, "freelancer")
        app.login().signIn(newEmail, app.getProperty("passNew"))
    }
}