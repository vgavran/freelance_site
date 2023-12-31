package ru.fl.test

import com.codeborne.selenide.Selenide.switchTo
import com.codeborne.selenide.WebDriverRunner.url
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.model.UsersData.Users.*
import java.io.IOException
import java.sql.SQLException

class BS_ReservationIndividualTest : TestBase() {
    @Test
    //Проверка резервирования по БС, зак физик, на счете достаточно денег для оплаты полной суммы сделки.
    @Throws(IOException::class, SQLException::class)
    fun paymentDealByLSIndividual() {
        val projectName = "project " + randomLetters() + " safe deal pay personal account"
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.checkSuccessPayment()
    }

    @Test
    //Проверка резервирования по БС, зак физик,  на счете достаточно денег для частичной оплаты сделки.
    @Throws(IOException::class, SQLException::class)
    fun paymentDealByLSAndCardIndividual() {
        val loginCustomer: String = app.login().getLogin(CustomerIndividualLS).toString()
        app.db.setChoisePersonalAccount(loginCustomer)
        app.db.setSumPersonalAccountIndividual(loginCustomer, "1350.000000")
        val projectName = "project " + randomLetters() + " safe deal pay personal account + card"
        val tokenCustomer: String = app.api().getToken(CustomerIndividualLS)
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName, "1895")
        app.api().offerProject(tokenFreelancer, projectId, "1895")
        app.login().signIn(CustomerIndividualLS)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.topUpLS.byCard("545")
        switchTo().window(1)
        app.pay().kassaCom()
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.checkSuccessPayment()
    }

    @Test
    //Проверка резервирования по БС, зак физик,  на счете нет денег для оплаты сделки.
    @Throws(IOException::class, SQLException::class)
    fun paymentDealByCardIndividual() {
        val loginCustomer: String = app.login().getLogin(CustomerIndividualLS).toString()
        app.db.setChoisePersonalAccount(loginCustomer)
        app.db.setSumPersonalAccountIndividual(loginCustomer, "0.000000")
        val projectName = "project " + randomLetters() + " safe deal pay card"
        val tokenCustomer: String = app.api().getToken(CustomerIndividualLS)
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(CustomerIndividualLS)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.topUpLS.byCard("4234")
        switchTo().window(1)
        app.pay().kassaCom()
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.checkSuccessPayment()
    }

    @Test
    //Проверка резервирования по БС, зак физик (без фин. данных), на счете нет денег для оплаты сделки
    @Throws(SQLException::class, IOException::class)
    fun paymentDealByCardAnonym() {
        val loginCustomer: String = app.login().getLogin(CustomerAnonymLS).toString()
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectName = "project " + randomLetters() + " safe deal pay card"
        val budget = "1500"
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.db.setPersonalAccountTypeAnonym(loginCustomer)
        app.db.setSumPersonalAccountIndividual(loginCustomer, "0.000000")
        app.login().signIn(CustomerAnonymLS)
        app.headerPage.goToAllWork()
        app.listAllWorkPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName)
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.api().offerProject(tokenFreelancer, projectId, budget)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.topUpLS.byCard(budget)
        switchTo().window(1)
        app.pay().kassaCom()
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.checkSuccessPayment()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testSecureDealCard() {
        app.login().signIn(CustomerIndividualPRO)
        val projectName = "selenide project " + randomLetters() + " БС"
        app.goTo().mainPage()
        app.create().project(projectName)
        app.projectPage.publicationProject()
        val project: String = url()
        val projectId = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(project)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        app.projectOffersPage.waitLoadOrder()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(project)
        app.projectPage.goToOrder()
        app.orderPage.orderConfirm()
        val order: String = url()
        app.login().signOut()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.closeOrder("Работа выполнена")
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(order)
        app.orderPage.receiveMoneyToCardFreel()
        app.orderPage.checkPaymentStatus()
    }

    @Test
    //Проверка автоотмены сделки, если истек срок резервирования бюджета (30 дней после согласия исполнителя)
    @Throws(InterruptedException::class)
    fun testCancelSecureDealAfter30DaysInactive() {
        app.login().signIn(CustomerIndividualPRO)
        val projectName = "Project BS cancel after 30 days innactive " + randomLetters()
        app.goTo().mainPage()
        app.create().project(projectName)
        app.projectPage.publicationProject()
        val project: String = url()
        val projectId = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(project)
        app.create().offerProject()
        app.login().signOut()

        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        app.projectOffersPage.waitLoadOrder()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(project)
        app.projectPage.goToOrder()
        app.orderPage.orderConfirm()
        val order: String = url()
        app.login().signOut()

        app.login().signIn(CustomerIndividualPRO)
        app.goTo().autoCancelInternalTesting()
        app.adminPanelBS.changeOldOrderData(orderId)
        app.adminPanelBS.runCommandForAutocloseOldOrder()

        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferNotVisible()
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.checkCancelStatusByCustomerInOldOrder()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(order)
        app.orderPage.checkCancelStatusByFreelInOldOrder()
    }
    
}