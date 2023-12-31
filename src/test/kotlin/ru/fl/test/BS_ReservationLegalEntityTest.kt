package ru.fl.test

import com.codeborne.selenide.WebDriverRunner.url
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.model.UsersData.Users.*
import java.io.IOException
import java.sql.SQLException

class BS_ReservationLegalEntityTest : TestBase() {
    @Test
    //Проверка резервирования по БС, зак юрик, на счете достаточно денег для оплаты полной суммы сделки.
    @Throws(IOException::class, SQLException::class)
    fun paymentDealByLSLegalEntity() {
        val projectName = "project " + randomLetters() + " safe deal pay personal account"
        val tokenCustomer: String = app.api().getToken(CustomerlLegalEntityPRO)
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(CustomerlLegalEntityPRO)
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
    //Проверка резервирования по БС, зак юрик,  на счете достаточно денег для частичной оплаты суммы сделки.
    @Throws(IOException::class, SQLException::class)
    fun paymentDealByLSAndAccountLegalEntity() {
        val loginCustomer: String = app.login().getLogin(CustomerEntityLS).toString()
        app.db.setChoisePersonalAccount(loginCustomer)
        app.db.setSumPersonalAccountLegalEntity(loginCustomer, "12000.000000")
        val projectName = "project " + randomLetters() + " safe deal pay personal account + bank account"
        val tokenCustomer: String = app.api().getToken(CustomerEntityLS)
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName, "14400")
        app.api().offerProject(tokenFreelancer, projectId, "14400")
        app.login().signIn(CustomerEntityLS)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.topUpLS.byBankAccount("2400")
        app.goTo().personalAccount()
        val numberLS: String = app.personalAccountPage.getLSNumber()
        app.login().signOut()
        app.login().signIn(Arbitr)
        app.goTo().adminLSPage()
        app.adminPanelBS.approveLS(numberLS, "2400")
        app.login().signOut()
        app.login().signIn(CustomerEntityLS)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.checkSuccessPayment()
    }

    @Test
    //Проверка резервирования по БС, зак юрик, на счете нет денег для оплаты сделки.
    @Throws(IOException::class, SQLException::class)
    fun paymentDealByAccountLegalEntity() {
        val loginCustomer: String = app.login().getLogin(CustomerEntityLS).toString()
        app.db.setChoisePersonalAccount(loginCustomer)
        app.db.setSumPersonalAccountLegalEntity(loginCustomer, "0.000000")
        val projectName = "project " + randomLetters() + " safe deal pay + bank account"
        val budget = "5620"
        val tokenCustomer: String = app.api().getToken(CustomerEntityLS)
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName, budget)
        app.api().offerProject(tokenFreelancer, projectId, budget)
        app.login().signIn(CustomerEntityLS)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.topUpLS.byBankAccount(budget)
        app.goTo().personalAccount()
        val numberLS: String = app.personalAccountPage.getLSNumber()
        app.login().signOut()
        app.login().signIn(Arbitr)
        app.goTo().adminLSPage()
        app.adminPanelBS.approveLS(numberLS, budget)
        app.login().signOut()
        app.login().signIn(CustomerEntityLS)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.checkSuccessPayment()
    }

    @Test
    //Проверка необходимости заполнения фин данных для резервирования по БС, зак юрик, на счете нет денег для оплаты сделки
    @Throws(SQLException::class, IOException::class)
    fun paymentDealByCardAnonym() {
        val loginCustomer: String = app.login().getLogin(CustomerAnonymLS).toString()
        val tokenFreelancer: String = app.api().getToken(FreelIndividualPRO)
        val projectName = "project " + randomLetters() + " safe deal pay card"
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.db.setPersonalAccountTypeAnonym(loginCustomer)
        app.login().signIn(CustomerAnonymLS)
        app.goTo().mainPage()
        app.create().project(projectName)
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.api().offerProject(tokenFreelancer, projectId)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.checkNeedFillFinance()
        app.projectExecutorsPage.goToFillFinance()
        app.financeNewPage.checkVisibleFinancePage()
    }

    @Test
    @Throws(InterruptedException::class)
    fun safeDealForLegalEntity() {
        app.login().signIn(CustomerlLegalEntityPRO)
        val projectName = "Selenide " + randomLetters() + " БС ЮЛ"
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
        app.login().signIn(CustomerlLegalEntityPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        app.projectOffersPage.waitLoadOrder()
        val orderExecutor: String = url()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(project)
        app.projectPage.goToOrder()
        app.orderPage.orderConfirm()
        val order: String = url()
        app.login().signOut()
        app.login().signIn(CustomerlLegalEntityPRO)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.reservePay()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(order)
        app.orderPage.notifyCompletedWork()
        app.login().signOut()
        app.login().signIn(CustomerlLegalEntityPRO)
        app.goTo().page(orderExecutor)
        app.projectExecutorsPage.closeOrder("Ожидается запрос на получение гонорара")
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().page(order)
        app.orderPage.receiveMoneyToUMFreel()
        app.orderPage.checkPaymentStatus()
    }
}