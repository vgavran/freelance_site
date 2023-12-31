package ru.fl.test

import org.testng.annotations.Test

class SearchTest : TestBase() {
    @Test
    fun successSearchProject() {
        app.goTo().searchProject()
        app.searchPage.fillSearchField("Java")
        app.searchPage.checkSearchSuccessfulProject()
    }

    @Test
    fun successSearchFreelancer() {
        app.goTo().searchFreelancer()
        app.searchPage.fillSearchField("aegupova")
        app.searchPage.checkSearchSuccessfulFreel()
    }
}