package ru.fl.test

import com.codeborne.selenide.Selenide
import org.testng.annotations.Test
import ru.fl.model.UsersData.Users.*
import ru.fl.appmanager.RandomUtils.Companion.randomLetters


class CompetitionTest: TestBase(){
    @Test
    //Публикация Конкурса без ВАСа
    fun competitionCreateWithoutVasAndOfferIndividual () {
        val competitionName = "Competition test " + randomLetters()
        val endDate = (1..25).random()
        val winDate = endDate + 3
        app.login().signIn(CustomerIndividualPRO)
        app.create().competition(competitionName, "I want to find some participants",endDate, winDate)
        app.pay().paymentCompetition()
        app.competitionPage.goToCompetition()
        app.competitionPage.checkSuccessfulCreateCompetition()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().allWork()
        app.competitionPage.findCompetition(competitionName)
        val offerPhrase = "Отклик на Конкурс " + randomLetters()
        app.competitionPage.respondCompetition()
        app.create().offerCompetition(offerPhrase)
        app.login().signOut()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().allWork()
        app.competitionPage.findCompetition(competitionName)
        app.competitionPage.checkCompetitionOffer(offerPhrase)
        app.db.editDateCompetition(competitionName)
        app.competitionPage.clickWinner()
        Selenide.switchTo().alert().accept()
        Selenide.refresh()
        app.competitionPage.determineWinner()
        app.competitionPage.clickDefinitedWinner()
        app.competitionPage.checkWinner()
}

    @Test
    fun competitionCreateWithVas () {
        val competitionName = "Competition test " + randomLetters()
        val endDate = (1..25).random()
        val winDate = endDate + 3
        app.login().signIn(CustomerIndividualPRO)
        app.create().competition(competitionName, "I want to find some participants",endDate, winDate)
        app.pay().paymentCompetition()
        app.competitionPage.goToCompetition()
        app.competitionPage.addPinCompetitionVAS()
        app.competitionPage.addHighlightCompetitionVAS()
        app.competitionPage.addLabelUrgentCompetitionVAS()
        app.competitionPage.addHideCompetitionyVAS()
        app.competitionPage.buyVusCompetition()
        app.pay().paymentCompetition()
        app.competitionPage.closePopUpVas()
        app.competitionPage.checkPinCompetitionVas()
        app.competitionPage.checkHighlightCompetitionVas()
        app.competitionPage.checkLabelUrgentCompetitionVas()
        app.competitionPage.checkHideCompetitionVas()
    }
}