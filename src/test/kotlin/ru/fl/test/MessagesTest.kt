package ru.fl.test

import com.codeborne.selenide.Selenide.closeWindow
import com.codeborne.selenide.Selenide.switchTo
import com.codeborne.selenide.WebDriverRunner.url
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.model.UsersData.Users.*

class MessagesTest : TestBase() {

    @Test
    fun sendMessagesSafeDealByOffer() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "selenide project " + randomLetters() + " messages by offer"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(freelancer)
        app.goTo().projectOffers(projectId)
        app.offerPage.startDiscussion()
        var textMessage = "Тестовое сообщение в оффере от фрилансера!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(customer)
        app.headerPage.goToChatPage()
        app.chatPage.checkNotVisibleChat(projectName)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.startDiscuss()
        textMessage = "Тестовое сообщение в оффере от заказчика!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.headerPage.goToChatPage()
        app.chatPage.checkVisibleChat(projectName)
        app.chatPage.enterChat(projectName)
        textMessage = "Еще одно тестовое сообщение в оффере от заказчика!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.headerPage.goToChatPage()
        app.chatPage.checkVisibleChat(projectName)
        app.chatPage.enterChat(projectName)
        textMessage = "Еще одно тестовое сообщение в оффере от фрилансера!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun sendMessagesVacancy() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val vacancyName = "vacancy project " + randomLetters() + " messages by offer"
        val tokenCustomer: String = app.api().getToken(customer)
        val vacancyId: String = app.api().createVacancy(tokenCustomer, vacancyName)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().vacancyManage(vacancyId)
        app.vacancyPage.checkVacancyPublished()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.findProject(vacancyName)
        app.listAllWorkPage.applyFilter()
        app.vacancyPage.goToVacancy(vacancyName)
        val offerPhrase = "Отклик на вакансию" + randomLetters()
        app.create().offerVacancy(offerPhrase)
        app.offerPage.startDiscussion()
        var textMessage = "Тестовое сообщение в оффере от фрилансера!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(customer)
        app.headerPage.goToChatPage()
        app.chatPage.checkNotVisibleChat(vacancyName)
        app.goTo().vacancyManage(vacancyId)
        app.vacancyPage.startDiscuss()
        textMessage = "Тестовое сообщение в оффере от заказчика!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.headerPage.goToChatPage()
        app.chatPage.checkVisibleChat(vacancyName)
        app.chatPage.enterChat(vacancyName)
        textMessage = "Еще одно тестовое сообщение в оффере от заказчика!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.headerPage.goToChatPage()
        app.chatPage.checkVisibleChat(vacancyName)
        app.chatPage.enterChat(vacancyName)
        textMessage = "Еще одно тестовое сообщение в оффере от фрилансера!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun sendMessagesSafeDealByOrderFromProject() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " messages by order from project"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.startDiscuss()
        var textMessage = "Тестовое сообщение в заказе от заказчика!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDealNumber()
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.startDiscuss()
        textMessage = "Тестовое сообщение в заказе от фрилансера!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDealNumber()
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun sendMessagesDirectSafeDeal() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " messages by direct safe deal"
        val loginFreel = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreel)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.create().directOrderSafeDeal(projectName)
        val order: String = url()
        app.orderPage.startDiscuss()
        var textMessage = "Тестовое сообщение в прямом заказе от заказчика!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDealNumber()
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().page(order)
        app.orderPage.startDiscuss()
        textMessage = "Тестовое сообщение в прямом заказе от фрилансера!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDealNumber()
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.checkVisibleButtonInChat("Беру заказ в работу")
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun sendMessagesDirectNotSafeDeal() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " messages by direct not safe deal"
        val loginFreel = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreel)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.create().directOrderNotSafeDeal(projectName)
        val order: String = url()
        app.orderPage.startDiscuss()
        var textMessage = "Тестовое сообщение в прямом заказе от заказчика!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDealNumber()
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().page(order)
        app.orderPage.startDiscuss()
        textMessage = "Тестовое сообщение в прямом заказе от фрилансера!"
        app.chatPage.sendMessage(textMessage)
        app.chatPage.checkMessageSent(textMessage)
        app.chatPage.checkVisibleDealNumber()
        app.chatPage.checkVisibleDurationAndDescription()
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun filterSafeDealByOrder() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " messages by safe deal from order"
        val tokenFreelancer = app.api().getToken(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId = app.api().createOrderPaymentByCard(tokenCustomer, offerId)
        app.pay().dealByTestPayment(orderId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.login().signIn(customer)
        app.goTo().chatDealsPage()
        app.chatPage.setFilterDealFromOrder()
        app.chatPage.checkVisibleChat(projectName)
        app.login().signIn(freelancer)
        app.goTo().chatDealsPage()
        app.chatPage.setFilterDealFromOrder()
        app.chatPage.checkVisibleChat(projectName)
    }

    @Test
    fun filterSafeDirectDeal() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " messages by direct safe deal"
        val loginFreel = app.login().getLogin(freelancer)
        app.login().signIn(customer)
        app.goTo().profile(loginFreel)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.create().directOrderSafeDeal(projectName)
        app.goTo().chatDealsPage()
        app.chatPage.setFilterDirectOrder()
        app.chatPage.checkVisibleChat(projectName)
        app.login().signIn(freelancer)
        app.goTo().chatDealsPage()
        app.chatPage.setFilterDirectOrder()
        app.chatPage.checkVisibleChat(projectName)
    }

    @Test
    fun filterNotSafeDeal() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " messages by direct not safe deal"
        val loginFreel = app.login().getLogin(freelancer)
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val textMessage = "Какой-то текст сообщения"
        app.login().signIn(customer)
        app.goTo().profile(loginFreel)
        app.profilePage.offerOrder()
        app.profilePage.selectNewOrderCreate()
        app.create().directOrderNotSafeDeal(projectName)
        val orderId = app.orderPage.getOrderId()
        app.api().sendMessagesToDeal(tokenCustomer, orderId, textMessage)
        app.api().sendMessagesToDeal(tokenFreelancer, orderId, textMessage)
        app.goTo().chatDealsPage()
        app.chatPage.setFilterNotSafeDeal()
        app.chatPage.checkVisibleChat(projectName)
        app.login().signIn(freelancer)
        app.goTo().chatDealsPage()
        app.chatPage.setFilterNotSafeDeal()
        app.chatPage.checkVisibleChat(projectName)
    }

    @Test
    fun filterOrdersInOffer() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " messages by offer"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        val countMessagesCustomer = (1..5).random()
        for (i in 1..countMessagesCustomer) {
            val textMessage = "$i message from customer"
            app.api().sendMessagesToOffer(tokenCustomer, projectId, offerId, textMessage)
        }
        app.login().signIn(freelancer)
        app.goTo().chatOffersPage()
        app.chatPage.setFilterOrder()
        app.chatPage.checkUnreadMessages(countMessagesCustomer, projectName, offerId)
        app.login().signOut()
        val countMessagesFreelancer = (1..5).random()
        for (i in 1..countMessagesFreelancer) {
            val textMessage = "$i message from freelancer"
            app.api().sendMessagesToOffer(tokenFreelancer, projectId, offerId, textMessage)
        }
        app.login().signIn(customer)
        app.goTo().chatOffersPage()
        app.chatPage.setFilterOrder()
        //Плюс одно сообщение - сам оффер от фрила.
        app.chatPage.checkUnreadMessages(countMessagesFreelancer +1, projectName, offerId)
    }

    @Test
    fun filterVacancyInOffer() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val vacancyName = "vacancy " + randomLetters() + " messages by offer"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val vacancyId: String = app.api().createVacancy(tokenCustomer, vacancyName)
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, vacancyId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        val offerId = app.api().offerProject(tokenFreelancer, vacancyId)
        val countMessagesCustomer = (1..5).random()
        for (i in 1..countMessagesCustomer) {
            val textMessage = "$i message from customer"
            app.api().sendMessagesToOffer(tokenCustomer, vacancyId, offerId, textMessage)
        }
        app.login().signIn(freelancer)
        app.goTo().chatOffersPage()
        app.chatPage.setFilterVacancy()
        app.chatPage.checkUnreadMessages(countMessagesCustomer, vacancyName, offerId)
        app.login().signOut()
        val countMessagesFreelancer = (1..5).random()
        for (i in 1..countMessagesFreelancer) {
            val textMessage = "$i message from freelancer"
            app.api().sendMessagesToOffer(tokenFreelancer, vacancyId, offerId, textMessage)
        }
        app.login().signIn(customer)
        app.goTo().chatOffersPage()
        app.chatPage.setFilterVacancy()
        //Плюс одно сообщение - сам оффер от фрила.
        app.chatPage.checkUnreadMessages(countMessagesFreelancer +1, vacancyName, offerId)
    }

    @Test
    fun checkButtonChangeCost() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " button change cost"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(freelancer)
        app.goTo().projectOffers(projectId)
        app.orderPage.startDiscuss()
        app.chatPage.checkVisibleButtonInChat("Изменить стоимость и срок")
        app.chatPage.checkNotVisibleButton("Выбрать исполнителем")
        app.chatPage.checkNotVisibleButton("Беру заказ в работу")
        app.chatPage.goToChangeCost()
        closeWindow()
        switchTo().window(0)
        app.create().offerProject("Отредактировано =)", "1", "2345")
        app.chatPage.goToChangeCost()
        closeWindow()
        switchTo().window(0)
        app.create().offerProject("Отредактировано повторно =)", "2", "3456")
        app.chatPage.checkVisibleButtonInChat("Изменить стоимость и срок")
        app.chatPage.checkNotVisibleButton("Выбрать исполнителем")
        app.chatPage.checkNotVisibleButton("Беру заказ в работу")
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun checkButtonSelectExecutor() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " button select executor"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.startDiscuss()
        app.chatPage.checkVisibleButtonInChat("Выбрать исполнителем")
        app.chatPage.checkNotVisibleButton("Изменить стоимость и срок")
        app.chatPage.checkNotVisibleButton("Беру заказ в работу")
        app.chatPage.goToSelectExecutor()
        closeWindow()
        switchTo().window(0)
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        app.projectExecutorsPage.startDiscuss()
        app.chatPage.checkNotVisibleButton("Выбрать исполнителем")
        app.chatPage.checkNotVisibleButton("Изменить стоимость и срок")
        app.chatPage.checkNotVisibleButton("Беру заказ в работу")
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun checkButtonTakeToWork() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " button take to work"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(customer)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingExecutor()
        app.projectOffersPage.setDeadline()
        app.projectOffersPage.acceptTask()
        app.projectOffersPage.suggestOrderClick()
        val orderId: String = app.projectOffersPage.getOrderId()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.startDiscuss()
        app.chatPage.checkVisibleButtonInChat("Беру заказ в работу")
        app.chatPage.checkNotVisibleButton("Изменить стоимость и срок")
        app.chatPage.checkNotVisibleButton("Выбрать исполнителем")
        app.chatPage.takeOrderToWork()
        closeWindow()
        switchTo().window(0)
        app.chatPage.inputCode()
        app.chatPage.checkSuccessfulTakeToWork()
        app.chatPage.checkNotVisibleButton("Беру заказ в работу")
        app.chatPage.checkNotVisibleButton("Изменить стоимость и срок")
        app.chatPage.checkNotVisibleButton("Выбрать исполнителем")
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun checkServiceMessagesChat() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project " + randomLetters() + " button take to work"
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(freelancer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectExecutor(tokenCustomer,projectId, offerId)
        val orderId = app.api().createOrderPaymentByCard(tokenCustomer, offerId)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.login().signIn(customer)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.reservePay()
        app.projectExecutorsPage.startDiscuss()
        app.chatPage.checkServiseMessage("Ведите переписку здесь")
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.startDiscuss()
        app.chatPage.checkServiseMessage("Ведите переписку здесь")
        app.chatPage.closeChat()
        app.orderPage.moveOrderToArbitrage("Фрилансер обращается в арбитраж")
        app.login().signOut()
        app.login().signIn(Arbitr)
        app.goTo().order(orderId)
        app.orderPage.sendMessageByArbitr("Сообщение от арбитра со ссылкой http://www.fl2.test")
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().projectExecutors(projectId)
        app.projectExecutorsPage.startDiscuss()
        app.chatPage.checkMessageFromArbitr("Сообщение от арбитра")
        app.chatPage.closeChat()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().order(orderId)
        app.orderPage.startDiscuss()
        app.chatPage.checkMessageFromArbitr("Сообщение от арбитра")
        app.chatPage.closeChat()
        app.login().signOut()
    }

    @Test
    fun personalMessageFromAdminToFreelancer(){
        val freelancer = FreelIndividualPRO
        app.login().signIn(Moderator)
        val moderatorUID = app.login().getUID(Moderator)
        val username = app.login().getLogin(freelancer)
        val messageAdminText = "Message from administrator fl.com " + randomLetters()
        val messageFreelText = "Message from freelancer " + randomLetters()
        app.goTo().profile(username)
        app.chatPage.sendPersonalMessageToFreel(messageAdminText)
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().chatOffersPage()
        app.goTo().chatFLTeamPage()
        app.chatPage.findChatWithUser(moderatorUID)
        app.chatPage.checkMessageSent(messageAdminText)
        app.chatPage.sendMessage(messageFreelText)
        app.chatPage.checkMessageSent(messageFreelText)
    }

    @Test
    fun personalMessageFromAdminToCustomer(){
        val customer = CustomerIndividualPRO
        app.login().signIn(Moderator)
        val moderatorUID = app.login().getUID(Moderator)
        val username = app.login().getLogin(customer)
        val messageAdminText = "Message from administrator fl.com" + randomLetters()
        val messageCustomerText = "Message from customer " + randomLetters()
        app.goTo().profile(username)
        app.chatPage.sendPersonalMessageToCustomer(messageAdminText)
        app.login().signOut()
        app.login().signIn(customer)
        app.goTo().chatOffersPage()
        app.goTo().chatFLTeamPage()
        app.chatPage.findChatWithUser(moderatorUID)
        app.chatPage.checkMessageSent(messageAdminText)
        app.chatPage.sendMessage(messageCustomerText)
        app.chatPage.checkMessageSent(messageCustomerText)
    }
}