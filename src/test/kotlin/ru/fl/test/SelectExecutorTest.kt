package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils
import ru.fl.model.UsersData

class SelectExecutorTest : TestBase(){

    /**Выбор исполнителем фрилансера физлица заказчиком физлицом**/
    @Test
    fun selectExecutorCustomerIndividualFreelIndividual(){
        val projectName = "Project select executor customer and freel individual " + RandomUtils.randomLetters()
        val customer = UsersData.Users.CustomerIndividualPRO
        val freelancer = UsersData.Users.FreelIndividualPRO
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkSimpleDeal()
        app.projectOffersPage.suggestOrderClick()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера юрлица заказчиком физлицом**/
    @Test
    fun selectExecutorCustomerIndividualFreelEntity(){
        val projectName = "Project select executor customer individual and freel entity  " + RandomUtils.randomLetters()
        val customer = UsersData.Users.CustomerIndividualPRO
        val freelancer = UsersData.Users.FreelLegalEntityPRO
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkDealWithDocumentFlow()
        app.projectOffersPage.suggestOrderClick()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера анонима заказчиком физлицом**/
    @Test
    fun selectExecutorCustomerIndividualFreelAnonym(){
        val projectName = "Project select executor customer individual and freel entity  " + RandomUtils.randomLetters()
        val customer = UsersData.Users.CustomerIndividualPRO
        val freelancer = UsersData.Users.FreelAnonymLS
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkSimpleDeal()
        app.projectOffersPage.suggestOrderClick()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера физлица заказчиком юрлицом**/
    @Test
    fun selectExecutorCustomerEntityFreelIndividual(){
        val projectName = "Project select executor customer entity and freel individual " + RandomUtils.randomLetters()
        val customer = UsersData.Users.CustomerlLegalEntityPRO
        val freelancer = UsersData.Users.FreelIndividualPRO
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkDealWithDocumentFlow()
        app.projectOffersPage.suggestOrderClick()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера юрлица заказчиком юрлицом**/
    @Test
    fun selectExecutorCustomerAndFreelEntity(){
        val projectName = "Project select executor customer and freel entity " + RandomUtils.randomLetters()
        val customer = UsersData.Users.CustomerlLegalEntityPRO
        val freelancer = UsersData.Users.FreelLegalEntityPRO
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkDealWithDocumentFlow()
        app.projectOffersPage.suggestOrderClick()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера анонима заказчиком юрлицом**/
    @Test
    fun selectExecutorCustomerEntityFreelAnonym() {
        val projectName = "Project select executor customer entity and freel anonym " + RandomUtils.randomLetters()
        val customer = UsersData.Users.CustomerlLegalEntityPRO
        val freelancer = UsersData.Users.FreelAnonymLS
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkDealWithDocumentFlow()
        app.projectOffersPage.suggestOrderClick()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера физлица заказчиком новорегом (тип пополнения ЛС - карта)**/
    @Test
    fun selectExecutorCustomerAnonymTypeLSCardFreelIndividual(){
        val projectName = "Project select executor customer and freel individual " + RandomUtils.randomLetters()
        val loginCustomer = RandomUtils.randomNumberLetter()
        val emailCustomer = "$loginCustomer@fl.com"
        val pass = app.getProperty("passNew")
        val freelancer = UsersData.Users.FreelIndividualPRO
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().registration(emailCustomer, "customer")
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(emailCustomer, pass)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkSimpleDeal()
        app.projectOffersPage.suggestOrderClick()
        app.projectOffersPage.addPhoneCustomer()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера физлица заказчиком новорегом (тип пополнения ЛС - банковский счет)**/
    @Test
    fun selectExecutorCustomerAnonymTypeLSAccountFreelIndividual(){
        val projectName = "Project select executor customer and freel individual " + RandomUtils.randomLetters()
        val loginCustomer = RandomUtils.randomNumberLetter()
        val emailCustomer = "$loginCustomer@fl.com"
        val pass = app.getProperty("passNew")
        val freelancer = UsersData.Users.FreelIndividualPRO
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().registration(emailCustomer, "customer")
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(emailCustomer, pass)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.personalAccountPage.selectAccountType()
        app.personalAccountPage.confirmSelectedType()
        app.fillForm().financeCustomerEntityResidentNew()
        app.login().signOut()
        app.login().signIn(UsersData.Users.Moderator)
        app.goTo().financePage(loginCustomer)
        app.financeOldPage.acceptFinance()
        app.login().signOut()
        app.login().signIn(emailCustomer, pass)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkDealWithDocumentFlow()
        app.projectOffersPage.suggestOrderClick()
        app.projectOffersPage.addPhoneCustomer()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
    }

    /**Выбор исполнителем фрилансера юрлица заказчиком новорегом (тип пополнения ЛС - карта)**/
    @Test
    fun selectExecutorCustomerAnonymFreelEntity(){
        val projectName = "Project select executor customer and freel individual " + RandomUtils.randomLetters()
        val loginCustomer = RandomUtils.randomNumberLetter()
        val emailCustomer = "$loginCustomer@fl.com"
        val pass = app.getProperty("passNew")
        val freelancer = UsersData.Users.FreelLegalEntityPRO
        val freelancerName = app.login().getName(freelancer) + " " + app.login().getSurname(freelancer)
        val tokenCustomer: String = app.api().registration(emailCustomer, "customer")
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        app.login().signOut()
        app.login().signIn(loginCustomer, pass)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.checkDealWithDocumentFlow()
        app.projectOffersPage.suggestOrderClick()
        app.projectOffersPage.addPhoneCustomer()
        app.projectExecutorsPage.checkSuccessSelectionExecutor(freelancerName)
        app.projectExecutorsPage.checkNeedFillFinance()
    }
}