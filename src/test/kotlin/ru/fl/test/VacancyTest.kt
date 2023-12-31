package ru.fl.test

import com.codeborne.selenide.Selenide.sleep
import com.codeborne.selenide.WebDriverRunner.url
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.model.UsersData.Users.*
import java.io.IOException
import java.sql.SQLException

class VacancyTest : TestBase() {
    @Test
    fun vacancyCreateAndOfferIndividual() {
        app.login().signIn(CustomerIndividualPRO)
        val name = "New vacancy wizard test " + randomLetters()
        app.create().vacancy(name, "standard")
        app.vacancyPage.paymentVacancy()
        app.pay().yooMoneyCard()
        val vacancy = url()
        app.vacancyPage.checkVacancyPublished()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.findProject(name)
        app.listAllWorkPage.applyFilter()
        app.vacancyPage.goToVacancy(name)
        val offerPhrase = "Отклик на вакансию" + randomLetters()
        app.create().offerVacancy(offerPhrase)
        app.vacancyPage.startDiscuss()
        app.chatPage.sendMessage("Сообщение от фрилансера по вакансии")
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().page(vacancy)
        app.vacancyPage.startDiscuss()
        app.chatPage.sendMessage("Сообщение от заказчика по вакансии")
        app.chatPage.closeChat()
        app.vacancyPage.checkVacancyOffer(offerPhrase)
    }

    @Test
    fun publicationVacancyEntityByAccount(){
        app.login().signIn(CustomerlLegalEntityPRO)
        val name = "New vacancy wizard test " + randomLetters()
        app.create().vacancy(name, "standard")
        app.vacancyPage.paymentVacancy()
        app.vacancyPage.checkVacancyPublished()
    }

    @Test
    fun publicationVacancyEntityWithTopUpAccount(){
        val loginCustomerEntityLS: String? = app.login().getLogin(CustomerEntityLS)
        app.db.setSumPersonalAccountLegalEntity(loginCustomerEntityLS, "0.000000")
        app.login().signIn(CustomerEntityLS)
        val name = "New vacancy wizard test " + randomLetters()
        app.create().vacancy(name, "standard")
        val vacancyPayment = url()
        app.vacancyPage.topUpLsClick()
        app.goTo().personalAccount()
        val numberLS: String = app.personalAccountPage.getLSNumber()
        app.login().signOut()
        app.login().signIn(Arbitr)
        app.goTo().adminLSPage()
        app.adminPanelBS.approveLS(numberLS, "2400")
        app.login().signOut()
        app.login().signIn(CustomerEntityLS)
        app.goTo().page(vacancyPayment)
        app.vacancyPage.chooseVacancyType("standard")
        app.vacancyPage.paymentVacancy()
        app.vacancyPage.checkVacancyPublished()
    }

    @Test
    fun premiumVacancyPublication() {
        app.login().signIn(CustomerIndividualPRO)
        val name = "New Premium vacancy wizard test " + randomLetters()
        app.create().vacancy(name, "premium")
        app.vacancyPage.paymentVacancy()
        app.pay().yooMoneyCard()
        app.vacancyPage.checkVacancyPublished()
    }

//    @Test
//    fun subscribeVacancyPublication() {
//        app.login().signIn(CustomerIndividualPRO)
//        val name = "New Premium vacancy wizard test " + randomLetters()
//        app.create().vacancy(name, "subscribe")
//        app.vacancyPage.paymentByCard()
//        app.pay().yooMoney()
//        app.vacancyPage.checkVacancyPublished()
//    }

    @Test
    fun vacancyRemoveFromPublication() {
        val name = "New vacancy wizard test " + randomLetters()
        val tokenCustomer = app.api().getToken(CustomerIndividualPRO)
        val vacancyId = app.api().createVacancy(tokenCustomer, name)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().vacancyEdit(vacancyId)
        app.vacancyPage.removeFromPublication()
        app.vacancyPage.checkVacancyNotPublished()
    }

    @Test
    fun buyAllVAS() {
        app.login().signIn(CustomerIndividualPRO)
        val name = "New vacancy wizard test " + randomLetters()
        app.goTo().createVacancy()
        app.fillForm().vacancy(name, "1000", "5000")
        app.vacancyPage.chooseVacancyType("standard")
        app.vacancyPage.addPinVacancyVAS()
        app.vacancyPage.addHighlightVacancyVAS()
        app.vacancyPage.addLabelUrgentVacancyVAS()
        app.vacancyPage.addHideVacancyVAS()
        app.vacancyPage.paymentVacancy()
        app.pay().yooMoneyCard()
        app.vacancyPage.checkPinVacancyVAS()
        app.vacancyPage.checkHighlightVacancyVAS()
        app.vacancyPage.checkLabelUrgentVacancyVAS()
        app.vacancyPage.checkHideVacancyVAS()
    }

    @Test
    fun buyPinVacancyVAS() {
        val name = "New vacancy wizard test " + randomLetters()
        val tokenCustomer = app.api().getToken(CustomerIndividualPRO)
        val vacancyId = app.api().createVacancy(tokenCustomer, name)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().vacancyManage(vacancyId)
        app.vacancyPage.pinVacancyVasInPublishedVacancy()
        app.vacancyPage.goToPaymentVAS()
        app.pay().yooMoneyCard()
        app.vacancyPage.checkPinVacancyVAS()
    }

    @Test
    fun buyHighlightVacancyVAS() {
        val name = "New vacancy wizard test " + randomLetters()
        val tokenCustomer = app.api().getToken(CustomerIndividualPRO)
        val vacancyId = app.api().createVacancy(tokenCustomer, name)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().vacancyManage(vacancyId)
        app.vacancyPage.highlightVacancyVasInPublishedVacancy()
        app.vacancyPage.goToPaymentVAS()
        app.pay().yooMoneyCard()
        app.vacancyPage.checkHighlightVacancyVAS()
    }

    @Test
    fun buyLabelUrgentVacancyVAS() {
        val name = "New vacancy wizard test " + randomLetters()
        val tokenCustomer = app.api().getToken(CustomerIndividualPRO)
        val vacancyId = app.api().createVacancy(tokenCustomer, name)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().vacancyManage(vacancyId)
        app.vacancyPage.labelUrgentVacancyVasInPublishedVacancy()
        app.vacancyPage.goToPaymentVAS()
        app.pay().yooMoneyCard()
        app.vacancyPage.checkLabelUrgentVacancyVAS()
    }

    @Test
    fun buyHideVacancyVAS() {
        val name = "New vacancy wizard test " + randomLetters()
        val tokenCustomer = app.api().getToken(CustomerIndividualPRO)
        val vacancyId = app.api().createVacancy(tokenCustomer, name)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().vacancyManage(vacancyId)
        app.vacancyPage.hideVacancyVasInPublishedVacancy()
        app.vacancyPage.goToPaymentVAS()
        app.pay().yooMoneyCard()
        app.vacancyPage.checkHideVacancyVAS()
    }

    @Test
    fun editOffer() {
        val name = "New vacancy wizard test " + randomLetters()
        val tokenCustomer = app.api().getToken(CustomerIndividualPRO)
        val vacancyId = app.api().createVacancy(tokenCustomer, name)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().vacancy(vacancyId)
        val offerPhrase = "Отклик на вакансию " + randomLetters()
        app.create().offerVacancy(offerPhrase)
        app.vacancyPage.editOfferVacancy()
        val changeOffer = "Редактирую отклик на вакансию " + randomLetters()
        app.edit().offerVacancy(changeOffer)
        app.vacancyPage.checkVacancyOffer(changeOffer)
    }

    @Test
    //Успешная публикация вакансии и перенос ее в проект
    fun publishVacancyAndMoveToProject() {
        val name = "New vacancy moved to project " + randomLetters()
        val tokenCustomer = app.api().getToken(CustomerIndividualPRO)
        val vacancyId = app.api().createVacancy(tokenCustomer, name)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(Arbitr)
        app.goTo().vacancy(vacancyId)
        app.vacancyPage.editVacancyByAdmin()
        app.vacancyPage.selectProjectTypeAndSave()
        app.projectPage.checkMovedToProject()
    }

    @Test //Пополнение персонального счета с банковского счета новорегом
    @Throws(IOException::class, SQLException::class)
    fun creatingVacancyWithTopUpPersonalAccountNewregByBankAccount() {
        val vacancyName = "vacancy with top  up personal account " + randomLetters()
        val login = RandomUtils.randomNumberLetter()
        val email = "$login@fl.com"
        val pass = app.getProperty("passNew")
        app.api().registration(email, "customer")
        app.login().signIn(email, pass)
        app.goTo().personalAccount()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.create().vacancy(vacancyName)
        val urlVacancy = url()
        app.vacancyPage.goToFillFinances()
        app.fillForm().financeCustomerEntityResidentNew()
        app.login().signOut()
        app.login().signIn(Moderator)
        app.goTo().financePage(login)
        app.financeOldPage.acceptFinance()
        app.login().signOut()
        app.login().signIn(email, pass)
        app.goTo().page(urlVacancy)
        app.vacancyPage.topUpLsClick()
        sleep(500)
        val billSum = app.vacancyPage.getPaymentAmount()
        app.goTo().personalAccount()
        val numberbillLS: String = app.personalAccountPage.getLSNumber()
        app.login().signOut()
        app.login().signIn(Moderator)
        app.goTo().adminLSPage()
        app.adminPanelBS.approveLS(numberbillLS, billSum)
        app.login().signOut()
        app.login().signIn(email, pass)
        app.goTo().page(urlVacancy)
        app.vacancyPage.paymentVacancy()
        app.vacancyPage.checkVacancyPublished()
    }
}