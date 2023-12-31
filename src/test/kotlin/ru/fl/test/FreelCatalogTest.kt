package ru.fl.test

import org.testng.annotations.Test
import ru.fl.model.UsersData.Users.FreelIndividualNotPRO
import ru.fl.model.UsersData.Users.FreelIndividualPRO


class FreelCatalogTest : TestBase(){
    @Test
    fun searchForFreel() {
        val login = app.login().getLogin(FreelIndividualNotPRO)
        app.login().signIn(FreelIndividualPRO)
        app.headerPage.goToFreelancersCatalogOld()
        app.freelCatalogPage.findFreel(login)
        app.freelCatalogPage.checkFreelFind(login)
    }
}