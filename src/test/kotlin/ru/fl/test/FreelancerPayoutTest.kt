package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils
import ru.fl.model.UsersData.Users.*

class FreelancerPayoutTest : TestBase() {

    @Test
    //Выплаты фрилансеру на банковский счет в сделке из заказа, юрик-юрик
    fun paymentsCustomerAndFreelancerLegalEntity(){
        val customer = CustomerlLegalEntityPRO
        val freelancer = FreelLegalEntityPRO
        val projectName = "project " + RandomUtils.randomLetters() + " payments customer and freelancer legal entity"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val projectId = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByInvoice(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToBankAccount()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на юмани в сделке из заказа, зак юрик-фрил физик
    fun paymentsCustomerLegalEntityAndFreelancerIndividual(){
        val customer = CustomerlLegalEntityPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + RandomUtils.randomLetters() + " payments customer legal entity, freelancer individual"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val projectId = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByInvoice(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToUMFreel()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Необходимо заполнить финансы фрилу в сделке из заказа, зак юрик-фрил аноним
    fun paymentsCustomerLegalEntityAndFreelancerAnonym(){
        val customer = CustomerlLegalEntityPRO
        val freelancer = FreelAnonymLS
        val projectName = "project " + RandomUtils.randomLetters() + " payments customer legal entity, freelancer anonym"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val projectId = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByInvoice(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.checkNeedFillFinance()
    }

    @Test
    //Выплаты фрилансеру на карту в сделке из заказа, зак физик - фрил юрик
    fun paymentsCustomerIndividualFreelancerLegalEntityCard(){
        val customer = CustomerIndividualPRO
        val freelancer = FreelLegalEntityPRO
        val projectName = "project " + RandomUtils.randomLetters() + " payments to card customer individual, freelancer legal entity"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val projectId = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByCard(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToCardFreel()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на банковский счет в сделке из заказа, зак физик - фрил юрик
    fun paymentsCustomerIndividualFreelancerLegalEntityBankAccount(){
        val customer = CustomerIndividualPRO
        val freelancer = FreelLegalEntityPRO
        val projectName = "project " + RandomUtils.randomLetters() + " payments to bank account customer individual, freelancer legal entity"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val projectId = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.create().offerProject()
        val offerId = app.offerPage.getOfferId()
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByCard(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToBankAccount()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на карту в сделке из заказа, зак физик - фрил физик
    fun paymentsCustomerAndFreelancerIndividual(){
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + RandomUtils.randomLetters() + " payments to card customer and freelancer individual"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val projectId = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByCard(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToCardFreel()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на карту в сделке из заказа, зак физик - фрил аноним
    fun paymentsCustomerIndividualFreelancerAnonym(){
        val customer = CustomerIndividualPRO
        val loginFreel = RandomUtils.randomNumberLetter()
        val pass = app.getProperty("passNew")
        val email = "$loginFreel@fl.com"
        app.api().registration(email, "freelancer")
        val urlActivate: String = app.db.getUrlActivateEmail(loginFreel)
        app.goTo().page(urlActivate)
        val projectName = "project " + RandomUtils.randomLetters() + " payments customer individual, freelancer anonym"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(loginFreel, pass)
        val buyPRO = app.api().getLinkBuyPRO(tokenFreelancer)
        app.goTo().page(buyPRO)
        app.pay().yooMoneyCard()
        app.api().addPhone(tokenFreelancer)
        val projectId = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByCard(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(loginFreel, pass)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToCardFreel()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на банковский счет в прямой сделке, юрик-юрик
    fun paymentsDirectDealCustomerAndFreelancerLegalEntity(){
        val customer = CustomerlLegalEntityPRO
        val freelancer = FreelLegalEntityPRO
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val loginFreelancer = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        val projectName = "project " + RandomUtils.randomLetters() + " payments direct deal customer and freelancer legal entity"
        app.create().directOrderSafeDeal(projectName)
        val orderId = app.orderPage.getOrderId()
        app.login().signOut()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToBankAccount()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на юмани в прямой сделке, зак юрик-фрил физик
    fun paymentsDirectDealCustomerLegalEntityFreelancerIndividual(){
        val customer = CustomerlLegalEntityPRO
        val freelancer = FreelIndividualPRO
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val loginFreelancer = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        val projectName = "project " + RandomUtils.randomLetters() + " payments direct deal customer legal entity, freelancer individual"
        app.create().directOrderSafeDeal(projectName)
        val orderId = app.orderPage.getOrderId()
        app.login().signOut()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToUMFreel()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Необходимо заполнить финансы фрилу в прямой сделке, зак юрик-фрил аноним
    fun paymentsDirectDealCustomerLegalEntityAndFreelancerAnonym(){
        val customer = CustomerlLegalEntityPRO
        val freelancer = FreelAnonymLS
        val loginFreel = app.login().getLogin(freelancer)
        val projectName = "project " + RandomUtils.randomLetters() + " payments direct deal customer legal entity, freelancer anonym"
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreel)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.create().directOrderSafeDeal(projectName)
        val orderId = app.orderPage.getOrderId()
        app.login().signOut()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.checkNeedFillFinance()
    }

    @Test
    //Выплаты фрилансеру на банковский счет в прямой сделке, зак физик - фрил юрик
    fun paymentsDirectDealCustomerIndividualFreelancerLegalEntity(){
        val customer = CustomerIndividualPRO
        val freelancer = FreelLegalEntityPRO
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val loginFreelancer = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        val projectName = "project " + RandomUtils.randomLetters() + " payments direct deal to bank account customer individual, freelancer legal entity"
        app.create().directOrderSafeDeal(projectName)
        val orderId = app.orderPage.getOrderId()
        app.login().signOut()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToBankAccount()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на карту в прямой сделке, зак физик - фрил физик
    fun paymentsDirectDealCustomerAndFreelancerIndividual(){
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val loginFreelancer = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        val projectName = "project " + RandomUtils.randomLetters() + " payments direct deal to card customer and freelancer individual"
        app.create().directOrderSafeDeal(projectName)
        val orderId = app.orderPage.getOrderId()
        app.login().signOut()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToCardFreel()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }

    @Test
    //Выплаты фрилансеру на карту в прямой сделке, зак физик - фрил аноним
    fun paymentsDirectDealCustomerIndividualFreelancerAnonym(){
        val customer = CustomerIndividualPRO
        val freelancer = FreelAnonymLS
        val tokenCustomer = app.api().getToken(customer)
        val tokenFreelancer = app.api().getToken(freelancer)
        val loginFreelancer = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreelancer)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        val projectName = "project " + RandomUtils.randomLetters() + " payments direct deal to card customer individual, freelancer anonym"
        app.create().directOrderSafeDeal(projectName)
        val orderId = app.orderPage.getOrderId()
        app.login().signOut()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToCardFreel()
        app.orderPage.checkSuccessfulPaymentToFreel()
    }
}