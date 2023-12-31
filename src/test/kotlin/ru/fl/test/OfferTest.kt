package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.model.UsersData.Users
import ru.fl.model.UsersData.Users.CustomerIndividualPRO
import ru.fl.model.UsersData.Users.FreelIndividualPRO

class OfferTest : TestBase() {

    @Test
    fun offerProjectWithRegistration() {
        val projectName = "Project for offer with registration " + randomLetters()
        val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerText = "Response to the project with registration " + randomLetters()
        val emailFreel = RandomUtils.randomNumberLetter() + "@fl.com"
        val pass = app.getProperty("passNew")
        app.goTo().project(projectId)
        app.login().acceptCookies()
        app.offerPage.pressAnswerButton(projectId)
        app.offerPage.offerDescriptionNotAuth(offerText)
        app.offerPage.termOffer("5")
        app.offerPage.totalCost("1500")
        app.offerPage.pressSubmitAnswerButtonNotAuth()
        app.registration().freelOldModal(emailFreel, pass)
        app.offerPage.checkSaveOffer()
        app.offerPage.closeModalOfferSaving()
    }
    /** Не работает - похоже отклик фрила при авторизации не сохраняется
    @Test
    fun offerProjectWithAuthorization() {
    val projectName = "Project for offer with authorization " + randomLetters()
    val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
    val projectId: String = app.api().createProject(tokenCustomer, projectName)
    val offerText = "Response to the project with authorization " + randomLetters()
    app.goTo().project(projectId)
    app.offerPage.pressAnswerButton(projectId)
    app.offerPage.offerDescriptionNotAuth(offerText)
    app.offerPage.termOffer("2")
    app.offerPage.totalCost("1200")
    app.offerPage.pressSubmitAnswerButtonNotAuth()
    app.login().signIn(Users.FreelIndividualPRO)
    app.offerPage.checkSaveOffer()
    app.offerPage.closeModalOfferSaving()
    }
     **/

    @Test
    fun offerProjectForEveryone() {
        val projectName = "Project for everyone " + randomLetters()
        val offerText = "Response to the project for everyone " + randomLetters()
        val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.login().signIn(Users.CustomerIndividualPRO)
        app.goTo().projectManage(projectId)
        app.projectManagePage.buyForEveryoneVAS()
        app.pay().yooMoneyCard()
        app.login().signOut()
        app.login().signIn(Users.FreelIndividualNotPRO)
        app.goTo().project(projectId)
        app.create().offerProject(offerText)
        app.login().signOut()
        app.login().signIn(Users.CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferWasSuccessfully(offerText)
    }

    @Test
    fun moreThen5OffersWithoutPRO() {
        val projectName = "Project for offer without PRO " + randomLetters()
        val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(Users.FreelIndividualNotPRO)
        app.goTo().project(projectId)
        app.offerPage.pressAnswerButton(projectId)
        app.offerPage.checkNeedBuyPRO()
    }

    @Test
    fun offerByNewregFreelWithBuyPRO() {
        val loginFreel = RandomUtils.randomNumberLetter()
        val emailFreel = "$loginFreel@fl.com"
        app.api().registration(emailFreel, "freelancer")
        val projectName = "Project for offer without PRO with offer saving" + randomLetters()
        val offerText = "Response to the project without PRO with offer saving" + randomLetters()
        val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(emailFreel, app.getProperty("passNew"))
        app.goTo().project(projectId)
        app.create().offerProject(offerText)
        app.offerPage.checkSaveOffer()
        app.offerPage.clickBuyPRO()
        app.pay().yooKassaTest()
        app.login().signOut()
        app.login().signIn(Users.CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferWasSuccessfully(offerText)
    }

    @Test
    // Отклик на заказ с окончательной стоимостью
    fun projectOfferWithDeadlineAndBudget() {
        val projectName = "project with deadline and budget " + randomLetters()
        val offerText = "Response to the project for everyone " + randomLetters()
        val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerBudget: String  = (1200..25000).random().toString()
        val offerDeadline: String = (10..22).random().toString()
        app.login().signIn(Users.FreelIndividualPRO)
        app.goTo().project(projectId)
        app.create().offerProject(offerText, offerDeadline, offerBudget)
        app.login().signOut()
        app.login().signIn(Users.CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferWasSuccessfully(offerText)
        app.projectOffersPage.checkOfferBudgetAndDeadline(offerBudget)
    }

    @Test
    // Отклик на заказ с предварительной стоимостью
    fun projectOfferWithPreliminaryCost() {
        val projectName = "project for offer with preliminary cost " + randomLetters()
        val offerText = "Response to the project for offer with preliminary cost " + randomLetters()
        val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerBudget: String  = (1200..25000).random().toString()
        val offerDeadline: String = (10..22).random().toString()
        app.login().signIn(Users.FreelIndividualPRO)
        app.goTo().project(projectId)
        app.create().offerProjectWithPreliminaryCost(offerText, offerDeadline, offerBudget)
        app.projectOffersPage.checkOfferWithPreliminaryCoast()
        app.login().signOut()
        app.login().signIn(Users.CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferWasSuccessfully(offerText)
        app.projectOffersPage.checkOfferBudgetAndDeadline(offerBudget)
    }

    @Test
// Отклик фрилансера с существующими примерами работ
    fun projectOfferWithExamplesOfWork() {
        val projectName = "project with examples of work " + randomLetters()
        val offerText = "Response to the project with examples of work " + randomLetters()
        val tokenCustomer: String = app.api().getToken(Users.CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerBudget: String  = (1200..25000).random().toString()
        val offerDeadline: String = (10..22).random().toString()
        app.login().signIn(Users.FreelIndividualPRO)
        app.goTo().project(projectId)
        app.create().offerProjectWithExamplesOfWork(offerText, offerDeadline, offerBudget)
        app.login().signOut()
        app.login().signIn(Users.CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferWasSuccessfully(offerText)
        app.projectOffersPage.checkOfferBudgetAndDeadline(offerBudget)
        app.projectOffersPage.checkOfferWithExamplesOfWork()
    }

    @Test
// Отклик фрилансера с загрузкой примера работ
    fun projectOfferWithSampleWorkUpload() {
        val projectName = "project with sample work upload " + randomLetters()
        val offerText = "Response to the project with sample work upload " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerBudget: String  = (1200..25000).random().toString()
        val offerDeadline: String = (10..22).random().toString()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().project(projectId)
        app.create().offerProjectWithSampleWorkUpload(offerText, offerDeadline, offerBudget)
        app.offerPage.checkFileIsLoaded()
        app.login().signOut()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferWasSuccessfully(offerText)
        app.projectOffersPage.checkOfferBudgetAndDeadline(offerBudget)
        app.projectOffersPage.checkOfferWithExamplesOfWork()
    }
}