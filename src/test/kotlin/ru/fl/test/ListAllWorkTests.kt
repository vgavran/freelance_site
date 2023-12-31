package ru.fl.test

import org.testng.annotations.Test

class ListAllWorkTests : TestBase() {
    @Test
    fun filterBySpecialization() {
        var specialization = "Разработка сайтов"
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.selectSpecialization(specialization)
        app.listAllWorkPage.applyFilter()
        app.listAllWorkPage.checkFiltration(specialization)
    }
}