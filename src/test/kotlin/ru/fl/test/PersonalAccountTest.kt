package ru.fl.test

import org.testng.Assert
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import ru.fl.model.UsersData.Users.*
import java.io.IOException
import java.sql.SQLException

class PersonalAccountTest : TestBase() {
    @Test //Проверка выбора типа пополнения персонального счета по карте новорегом на странице личного счета
    @Throws(IOException::class, SQLException::class)
    fun selectCardTypeOnAccountPage() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().personalAccount()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(login)
        app.personalAccountPage.checkIndividualSelected()
        val personalAccountType: String = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка выбора типа пополнения персонального счета с банковского счета новорегом на странице личного счета
    @Throws(IOException::class, SQLException::class)
    fun selectAccountTypeOnPersonalAccountPage() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().personalAccount()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.financeNewPage.checkVisibleFinancePage()
        app.goTo().financePage(login)
        app.personalAccountPage.checkEntitySelected()
        val personalAccountType: String = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "2")
    }

    @Test //Проверка выбора типа пополнения персонального счета по карте новорегом при публикации проекта, покупая VAS на странице редактирования заказа
    @Throws(IOException::class, SQLException::class)
    fun selectCardTypeWithPublicationProjectBuyVasOnManagePage() {
        val login: String = randomNumberLetter()
        val email = "$login@fl.com"
        val projectName: String = randomLetters()
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.headerPage.goToAllWork()
        app.listAllWorkPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Какое-то описание")
        app.projectManagePage.buyVasForEveryone()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(login)
        app.personalAccountPage.checkIndividualSelected()
        val personalAccountType = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка выбора типа пополнения персонального счета с банковского счета новорегом при публикации проекта, покупая VAS на странице откликов
    @Throws(IOException::class, SQLException::class)
    fun selectAccountTypeWithPublicationProjectBuyVasOnOffersPage() {
        val login: String = randomNumberLetter()
        val email = "$login@fl.com"
        val projectName: String = randomLetters()
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.headerPage.goToAllWork()
        app.listAllWorkPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Какое-то описание")
        app.projectPage.goToOffersTab()
        app.projectOffersPage.buyHighlightVAS()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.financeNewPage.checkVisibleFinancePage()
        app.goTo().financePage(login)
        app.personalAccountPage.checkEntitySelected()
        val personalAccountType = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "2")
    }

    @Test //Проверка выбора типа пополнения персонального счета по карте новорегом при публикации проекта, покупая VAS на странице откликов в попапе
    @Throws(IOException::class, SQLException::class)
    fun selectCardTypeWithPublicationProjectBuyVasOnOfferPage() {
        val login: String = randomNumberLetter()
        val email = "$login@fl.com"
        val projectName: String = randomLetters()
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.headerPage.goToAllWork()
        app.listAllWorkPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Какое-то описание")
        app.projectPage.goToOffersTab()
        app.projectOffersPage.upViewsClick()
        app.projectOffersPage.buyPinVAS()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(login)
        app.personalAccountPage.checkIndividualSelected()
        val personalAccountType = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка выбора типа пополнения персонального счета по карте новорегом в прямом заказе
    @Throws(IOException::class, SQLException::class)
    fun selectCardTypeWithDirectOrder() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val freelProfile = "${config.baseUrl}/users/aegupova2/portfolio/"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().page(freelProfile)
        app.profilePage.offerOrder()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(login)
        app.personalAccountPage.checkIndividualSelected()
        val personalAccountType: String = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка выбора типа пополнения персонального счета с банковского счета новорегом в прямом заказе
    @Throws(IOException::class, SQLException::class)
    fun selectAccountTypeWithDirectOrder() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val freelProfile = "${config.baseUrl}/users/aegupova2/portfolio/"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().page(freelProfile)
        app.profilePage.offerOrder()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.financeNewPage.checkVisibleFinancePage()
        app.goTo().financePage(login)
        app.personalAccountPage.checkEntitySelected()
        val personalAccountType: String = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "2")
    }

    @Test //Проверка выбора типа пополнения персонального счета по карте новорегом в вакансии
    @Throws(IOException::class, SQLException::class)
    fun selectCardTypeWithVacancy() {
        val vacancyName = "select card type payment in vacancy " + randomLetters()
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.create().vacancy(vacancyName)
        app.vacancyPage.paymentVacancy()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(login)
        app.personalAccountPage.checkIndividualSelected()
        val personalAccountType: String = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка выбора типа пополнения персонального счета с банковского счета новорегом в вакансии
    @Throws(IOException::class, SQLException::class)
    fun selectAccountTypeWithVacancy() {
        val vacancyName = "select card type payment in vacancy " + randomLetters()
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val freelProfile = "${config.baseUrl}/users/aegupova2/portfolio/"
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.create().vacancy(vacancyName)
        app.vacancyPage.paymentVacancy()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.financeNewPage.checkVisibleFinancePage()
        app.goTo().financePage(login)
        app.personalAccountPage.checkEntitySelected()
        val personalAccountType: String = app.db.getPersonalAccountType(login)
        Assert.assertEquals(personalAccountType, "2")
    }

    @Test //Проверка выбора типа пополнения персонального счета по карте при создании заказа из проекта (старый пользователь).
    @Throws(IOException::class, SQLException::class)
    fun selectCardTypeWithOrderFromProject() {
        val loginFreelancer = randomNumberLetter()
        val loginCustomer = "aegupova576"
        val emailFreelancer = "$loginFreelancer@fl.com"
        val tokenFreelancer: String = app.api().registration(emailFreelancer, "freelancer")
        val urlBuyPRO: String = app.api().getLinkBuyPRO(tokenFreelancer)
        val pass: String = app.getProperty("passNew")
        val projectId = "299339"
        val projectPage = "${config.baseUrl}/projects/$projectId/"
        app.goTo().page(urlBuyPRO)
        app.pay().yooMoneyCard()
        app.api().addPhone(tokenFreelancer)
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.login().signIn(emailFreelancer, pass)
        app.goTo().page(projectPage)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(loginCustomer, pass)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(loginCustomer)
        app.personalAccountPage.checkIndividualSelected()
        val personalAccountType: String = app.db.getPersonalAccountType(loginCustomer)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка подтверждения типа пополнения персонального счета по карте зак физик (старый пользователь).
    @Throws(IOException::class, SQLException::class)
    fun confirmCardTypeCustomerIndividual() {
        val loginCustomer: String = app.login().getLogin(CustomerIndividualLS).toString()
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.login().signIn(CustomerIndividualLS)
        app.goTo().personalAccount()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(loginCustomer)
        app.personalAccountPage.checkIndividualSaved()
        val personalAccountType: String = app.db.getPersonalAccountType(loginCustomer)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка смены типа пополнения персонального счета по счету зак физик (старый пользователь).
    @Throws(IOException::class, SQLException::class)
    fun changeToAccountTypeCustomerIndividual() {
        val loginCustomer: String = app.login().getLogin(CustomerIndividualLS).toString()
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.login().signIn(CustomerIndividualLS)
        app.goTo().personalAccount()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.financeNewPage.checkVisibleFinancePage()
        app.goTo().financePage(loginCustomer)
        app.personalAccountPage.checkEntitySelected()
        val personalAccountType: String = app.db.getPersonalAccountType(loginCustomer)
        Assert.assertEquals(personalAccountType, "2")
        app.db.setPersonalAccountTypeIndividual(loginCustomer)
        app.db.changeStatusFinanceApproved(loginCustomer)
    }

    @Test //Проверка подтверждкния типа пополнения персонального счета по счету зак юрик (старый пользователь).
    @Throws(IOException::class, SQLException::class)
    fun confirmAccountTypeCustomerEntity() {
        val loginCustomer: String = app.login().getLogin(CustomerEntityLS).toString()
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.login().signIn(CustomerEntityLS)
        app.goTo().personalAccount()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(loginCustomer)
        app.personalAccountPage.checkEntitySaved()
        val personalAccountType: String = app.db.getPersonalAccountType(loginCustomer)
        Assert.assertEquals(personalAccountType, "2")
    }

    @Test //Проверка смены типа пополнения персонального счета по карте зак юрик (старый пользователь).
    @Throws(IOException::class, SQLException::class)
    fun changeCardTypeCustomerEntity() {
        val loginCustomer: String = app.login().getLogin(CustomerEntityLS).toString()
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.login().signIn(CustomerEntityLS)
        app.goTo().personalAccount()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(loginCustomer)
        app.personalAccountPage.checkIndividualSelected()
        val personalAccountType: String = app.db.getPersonalAccountType(loginCustomer)
        Assert.assertEquals(personalAccountType, "1")
        app.db.setPersonalAccountTypeEntity(loginCustomer)
        app.db.changeStatusFinanceApproved(loginCustomer)
    }

    @Test //Проверка выбора типа пополнения персонального счета по карте зак аноним (старый пользователь).
    @Throws(IOException::class, SQLException::class)
    fun selectCardTypeCustomerAnonym() {
        val loginCustomer: String = app.login().getLogin(CustomerAnonymLS).toString()
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.db.setPersonalAccountTypeAnonym(loginCustomer)
        app.login().signIn(CustomerAnonymLS)
        app.goTo().personalAccount()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.goTo().financePage(loginCustomer)
        app.personalAccountPage.checkIndividualSaved()
        val personalAccountType: String = app.db.getPersonalAccountType(loginCustomer)
        Assert.assertEquals(personalAccountType, "1")
    }

    @Test //Проверка выбора типа пополнения персонального счета по счету зак аноним (старый пользователь).
    @Throws(IOException::class, SQLException::class)
    fun selectAccountTypeCustomerAnonym() {
        val loginCustomer: String = app.login().getLogin(CustomerAnonymLS).toString()
        app.db.resetChoisePersonalAccount(loginCustomer)
        app.db.setPersonalAccountTypeAnonym(loginCustomer)
        app.login().signIn(CustomerAnonymLS)
        app.goTo().personalAccount()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.financeNewPage.checkVisibleFinancePage()
        app.goTo().financePage(loginCustomer)
        app.personalAccountPage.checkEntitySelected()
        val personalAccountType: String = app.db.getPersonalAccountType(loginCustomer)
        Assert.assertEquals(personalAccountType, "2")
        app.db.setPersonalAccountTypeIndividual(loginCustomer)
        app.db.changeStatusFinanceApproved(loginCustomer)
    }

    @Test
    /* Проверка пополнения личного счета заказчиком через банковский платеж */
    @Throws(InterruptedException::class)
    fun testTopUpPersonalAccountEntity() {
        app.login().signIn(CustomerlLegalEntityPRO)
        app.goTo().personalAccount()
        val sumAccount: Int = app.personalAccountPage.getSumAccount()
        val billSum = (300..10000).random().toString()
        app.personalAccountPage.closeOldInvoice()
        app.personalAccountPage.topUpAccountClick()
        app.personalAccountPage.topUpAccountWithBillInvoice(billSum)
        val numberLS: String = app.personalAccountPage.getLSNumber()
        app.login().signOut()
        app.login().signIn(Arbitr)
        app.goTo().adminLSPage()
        app.adminPanelBS.approveLS(numberLS, billSum)
        app.login().signOut()
        app.login().signIn(CustomerlLegalEntityPRO)
        app.goTo().personalAccount()
        val sumAccountNew: Int = app.personalAccountPage.getSumAccount()
        Assert.assertEquals(sumAccountNew, sumAccount + billSum.toInt())
    }

    @Test
    /* Проверка пополнения личного счета заказчиком с заполненными финансами по карте */
    @Throws(
        InterruptedException::class
    )
    fun testTopUpPersonalAccountIndividualWithFinance() {
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().personalAccount()
        val sumAccount: Int = app.personalAccountPage.getSumAccount()
        val billSum = (300..10000).random().toString()
        app.personalAccountPage.topUpAccountClick()
        app.personalAccountPage.topUpAccountWithCard(billSum)
        app.pay().kassaCom()
        val sumAccountNew: Int = app.personalAccountPage.getSumAccount()
        Assert.assertEquals(sumAccountNew, sumAccount + billSum.toInt())
    }

    @Test
    /* Проверка пополнения личного счета заказчиком с не заполненными финансами по карте */
    fun testTopUpPersonalAccountIndividualWithoutFinance() {
        val loginCustomer: String = app.login().getLogin(CustomerAnonymLS).toString()
        app.db.resetStatusFinance(loginCustomer)
        app.login().signIn(CustomerAnonymLS)
        app.goTo().personalAccount()
        val sumAccount: Int = app.personalAccountPage.getSumAccount()
        val billSum = (300..10000).random().toString()
        app.personalAccountPage.topUpAccountClick()
        app.personalAccountPage.topUpAccountWithCard(billSum)
        app.pay().kassaCom()
        val sumAccountNew: Int = app.personalAccountPage.getSumAccount()
        Assert.assertEquals(sumAccountNew, sumAccount + billSum.toInt())
    }

    @Test
    /* Проверка пополнения личного счета фрилансером физлицом/анонимом */
    @Throws(
        InterruptedException::class, SQLException::class
    )
    fun testTopUpPersonalAccountFreelancer() {
        app.login().signIn(FreelIndividualLS)
        app.goTo().personalAccount()
        val sumAccount: Int = app.personalAccountPage.getSumFreelancerAccount()
        var billSum = (10..2000).random().toString()
        app.personalAccountPage.topUpAccountWithCardByFreelancer(billSum)
        val sumAccountNew: Int = app.personalAccountPage.getSumFreelancerAccount()
        Assert.assertEquals(sumAccountNew, sumAccount + billSum.toInt())
    }

    @Test
    /* Проверка пополнения личного счета фрилансером юрлицом */
    @Throws(
        InterruptedException::class, SQLException::class
    )
    fun testTopUpPersonalAccountFreelancerLegal() {
        app.login().signIn(FreelLegalEntityLS)
        app.goTo().personalAccount()
        val sumAccount: Int = app.personalAccountPage.getSumFreelancerAccount()
        var billSum = (300..2000).random().toString()
        app.personalAccountPage.topUpAccountWithBillByFreelancer(billSum)
        val numberbillLS: String = app.personalAccountPage.getFreelLSNumber()
        app.login().signOut()
        app.login().signIn(CustomerAdmin)
        app.goTo().adminOldLSPage()
        app.adminPanelBS.approveLS(numberbillLS, billSum)
        app.login().signOut()
        app.login().signIn(FreelLegalEntityLS)
        app.goTo().personalAccount()
        val sumAccountNew: Int = app.personalAccountPage.getSumFreelancerAccount()
        Assert.assertEquals(sumAccountNew, sumAccount + billSum.toInt())
    }

    @Test //Пополнение персонального счета по карте новорегом
    @Throws(IOException::class, SQLException::class)
    fun topUpPersonalAccountNewregByCard() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val phone = "+7949" + (1111111..9999999).random()
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().personalAccount()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        val sumAccount: Int = app.personalAccountPage.getSumAccount()
        val billSum = (300..10000).random().toString()
        app.personalAccountPage.topUpAccountClick()
        app.personalAccountPage.acceptPhone(phone)
        app.personalAccountPage.topUpAccountWithCard(billSum)
        app.pay().kassaCom()
        val sumAccountNew: Int = app.personalAccountPage.getSumAccount()
        Assert.assertEquals(sumAccountNew, sumAccount + billSum.toInt())
    }

    @Test //Пополнение персонального счета с банковского счета новорегом
    @Throws(IOException::class, SQLException::class)
    fun topUpPersonalAccountNewregByBankAccount() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val phone = "+7949" + (1111111..9999999).random()
        app.api().registration(email, "customer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().personalAccount()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.fillForm().financeCustomerEntityResidentNew()
        app.goTo().personalAccount()
        app.personalAccountPage.topUpAccountClick()
        app.personalAccountPage.acceptPhone(phone)
        app.personalAccountPage.closeOldInvoice()
        app.financeNewPage.checkFinaceUnderReview()
    }
}