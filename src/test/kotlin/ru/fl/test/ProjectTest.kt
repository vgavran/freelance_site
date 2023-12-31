package ru.fl.test

import com.codeborne.selenide.Selenide.sleep
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import ru.fl.model.UsersData.Users.*
import java.io.IOException
import java.sql.SQLException

class ProjectTest : TestBase() {

    /**    Нужно отключение рекапчи при публикации проекта
     * @Test
     * //Публикация проекта незарегистрированным пользователем
     * public void publishProjectWithRegistration() throws SQLException {
     * String projectName = "project not registration user" + getRandomLetters();
     * String login = getRandomNumberLetter();
     * String email = login + "@fl.com";
     * app.goTo().mainPage();
     * app.mainPage.createProjectClick();
     * app.newProjectPage.publishProjectClick();
     * app.fillForm().project(projectName,"Test project not registration user");
     * app.newRegistrationPage.fillNewFormReg(email, app.getProperty("passNew"));
     * app.newProjectPage.checkNeedConfirmEmail();
     * String projectId = app.newProjectPage.getProjectId();
     * String urlActivate = app.db.getUrlActivateEmail(login);
     * app.goTo().page(urlActivate);
     * app.goTo().projectManage(projectId);
     * app.newProjectPage.checkPublishProject();
     * app.goTo().allWork();
     * app.allWorksPage.checkPublishedOrderInListProjects(projectName, projectId);
     * }
     */
    /**    Нужно отключение рекапчи при публикации проекта
     * @Test
     * //Публикация проекта неавторизованным пользователем
     * public void publishProjectWithAuthorization() throws SQLException {
     * String projectName = "project not authorization user" + getRandomLetters();
     * String login = app.login().getLogin(CustomerIndividualPRO);
     * app.login().signIn(CustomerIndividualPRO);
     * app.headerPage.createProjectClick();
     * app.newProjectPage.publishProjectClick();
     * app.fillForm().project(projectName,"Test project not authorization user");
     * app.loginPage.goToAuthFromPublishProject();
     * app.login().fillFormAuthorization(login, app.getProperty("passOld"));
     * app.newProjectPage.disableCheckboxForAllNew();
     * app.newProjectPage.publishProjectNew();
     * String projectId = app.newProjectPage.getProjectId();
     * app.newProjectPage.checkPublishProject();
     * app.goTo().allWork();
     * app.allWorksPage.checkPublishedOrderInListProjects(projectName, projectId);
     * }
     */

    @Test
    //Успешная публикация проекта существующим юзером с PRO
    fun publishProjectByProUser() {
        val projectName = "project PRO user" + randomLetters()
        app.login().signIn(CustomerIndividualPRO)
        app.create().project(projectName)
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.findProject(projectName)
        app.listAllWorkPage.applyFilter()
        app.listAllWorkPage.checkPublishedOrderInListProjects(projectName, projectId)
    }

    @Test
    //Успешная публикация проекта с указанием дедлайна и бюджета
    fun publishProjectWithDeadlineAndBudget() {
        val projectName = "project with deadline and budget" + randomLetters()
        val budget: String  = (1200..25000).random().toString()
        val deadline: String = (10..22).random().toString()
        app.login().signIn(CustomerIndividualPRO)
        app.mainPage.createProjectClick()
        app.projectPage.createProject()
        app.projectPage.nameProject(projectName)
        app.projectPage.setCategoryProject()
        app.projectPage.goToStepTwo()
        app.projectPage.descriptionProject("Test project with deadline and budget")
        app.projectPage.goToStepThree()
        app.projectPage.setBudget(budget)
        app.projectPage.setDeadline(deadline)
        app.projectPage.goToStepFour()
        app.projectPage.publicationProjectWizard()
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        app.projectPage.checkBudget(budget)
        app.projectManagePage.checkDeadline(deadline)
    }

    @Test
    //Успешная публикация проекта без указания дедлайна и бюджета
    fun publishProjectWithoutDeadlineAndBudget() {
        val projectName = "project without deadline and budget" + randomLetters()
        app.login().signIn(CustomerIndividualPRO)
        app.create().project(projectName)
        app.projectPage.publicationProject()
        app.projectPage.checkNoBudget()
        app.projectPage.checkNoDeadline()
    }

    @Test
    //Успешная публикация проекта с вложением
    fun publishProjectWithFile() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelIndividualPRO
        val projectName = "project with attach" + randomLetters()
        app.login().signIn(customer)
        app.create().projectWithAttach(projectName)
        app.projectPage.publicationProject()
        val projectId = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.projectManagePage.checkFileIsAttached()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.goTo().project(projectId)
        app.projectPage.checkFileIsAttached()
    }

    @Test
    fun deleteAttachedFile() {
        val customer = CustomerIndividualPRO
        val projectName = "project delete attach" + randomLetters()
        app.login().signIn(customer)
        app.create().projectWithAttach(projectName)
        app.projectPage.publicationProject()
        val projectId = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.projectManagePage.checkFileIsAttached()
        app.projectManagePage.deleteAttachedFile()
        app.projectManagePage.checkFileIsDeleted()
    }

    @Test
    //Редактирование проекта
    @Throws(InterruptedException::class)
    fun testEditProject() {
        val token = app.api().getToken(CustomerIndividualPRO)
        val projectName = "project for edit " + randomLetters()
        val projectId = app.api().createProject(token, projectName)
        app.api().forcePublicationProject(projectId)
        val editedProjectName = "$projectName отредактированный проект"
        val editedDescription = "Проект отредактирован"
        val editedCategory = "Тексты"
        val editedSubcategory = "Сценарии"
        val deadline: String = (14..22).random().toString()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectManage(projectId)
        app.edit().project(editedProjectName, editedDescription, "2100", deadline, editedCategory, editedSubcategory)
        app.projectManagePage.checkEditedProject(editedProjectName, editedDescription)
        app.projectManagePage.checkEditedSpecialization(editedCategory, editedSubcategory)
        app.projectManagePage.checkDeadline(deadline)
        app.projectManagePage.checkFileIsAttached()
    }

    @Test
    //Проверка невозможности публикации заказа без подтверждения email
    @Throws(IOException::class, SQLException::class)
    fun publishProjectWithUnconfirmedEmail() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val pass: String = app.getProperty("passNew")
        val projectName = "project with unconfirmed email" + randomLetters()
        app.api().registration(email, "customer")
        app.login().signIn(login, pass)
        app.goTo().allWork()
        app.listAllWorkPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Первый заказ")
        app.projectPage.checkNeedConfirmEmail()
        val projectId: String = app.projectPage.getProjectId()
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.api().forcePublicationProject(projectId)
        app.goTo().projectManage(projectId)
        app.projectPage.checkPublishedProject()
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.findProject(projectName)
        app.listAllWorkPage.applyFilter()
        app.listAllWorkPage.checkPublishedOrderInListProjects(projectName, projectId)
    }

    @Test
    //Проверка публикации аналогичного заказа
    @Throws(IOException::class, SQLException::class)
    fun publishSameProject() {
        val projectName = "project for repeat " + randomLetters()
        val sameProjectName = "same project for repeat " + randomLetters()
        val login: String = app.login().getLogin(CustomerIndividualLS).toString()
        val token: String = app.api().getToken(login, app.getProperty("passNew"))
        val projectId: String = app.api().createProject(token, projectName, "1200")
        app.api().forcePublicationProject(projectId)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().project(projectId)
        app.projectPage.publicationSameProject()
        app.fillForm().project(sameProjectName, "Test same project for repeat")
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        val newProjectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(newProjectId)
        app.projectPage.checkPublishedProject()
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.findProject(sameProjectName)
        app.listAllWorkPage.applyFilter()
        app.listAllWorkPage.checkPublishedOrderInListProjects(sameProjectName, newProjectId)
    }

    @Test
    //Успешная публикация проекта, перенос его в вакансию и ее оплата
    fun publishProjectByProUserAndMoveToVacancy() {
        val projectName = "Project moved to vacancy " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName, "1200")
        app.login().signIn(Arbitr)
        app.goTo().project(projectId)
        app.projectPage.moveToVacancy()
        app.vacancyPage.checkMovedVacancy()
        app.login().signOut()
        val payment = app.api().getLinkPaymentVacancy(tokenCustomer, projectId)
        app.goTo().page(payment)
        app.pay().yooMoneyCard()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().vacancyEdit(projectId)
        app.vacancyPage.checkVacancyPublished()
    }

    @Test (enabled = false) //FIXME после выбора типа оплаты кидает в проект, а не на оплату
    //Проверка необходимости покупки PRO или оплаты размещения второго заказа в одной категории, оплата размещения, публикация
    @Throws(IOException::class, SQLException::class)
    fun testCreatingProjectWithoutProInSameCategory() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val pass: String = app.getProperty("passNew")
        val projectName = randomLetters()
        val publishedProjectName = randomLetters() + " второй заказ"
        app.api().registration(email, "customer")
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.login().signIn(login, pass)
        app.goTo().profile(login)
        app.profilePage.createProject()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Первый заказ без ПРО")
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        app.headerPage.goToAllWork()
        app.listAllWorkPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(publishedProjectName, "Второй заказ без ПРО")
        app.projectPage.checkBlockPublicationWithoutPRO()
        app.projectPage.checkButtonBuyPRO()
        app.projectPage.payPublicationProject()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.pay().yooMoneyCard()
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.projectPage.checkPublishedProject()
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.findProject(publishedProjectName)
        app.listAllWorkPage.applyFilter()
        app.listAllWorkPage.checkPublishedOrderInListProjects(publishedProjectName, projectId)
    }

    @Test (enabled = false) //FIXME после выбора типа оплаты кидает в проект, а не на оплату
    //Проверка необходимости покупки PRO или оплаты размещения пятого заказа в разных категориях, оплата размещения, публикация
    @Throws(IOException::class, SQLException::class)
    fun testCreatingProjectWithoutProInDifferentCategories() {
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val pass: String = app.getProperty("passNew")
        val projectName = randomLetters()
        val publishedProjectName = randomLetters() + " пятый заказ"
        app.api().registration(email, "customer")
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.login().signIn(login, pass)
        app.goTo().profile(login)
        app.profilePage.createProject()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Менеджмент", "Арт-директор")
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        app.headerPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Разработка сайтов", "Проектирование")
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        app.headerPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Дизайн и Арт", "Дизайн сайтов")
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        app.headerPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(projectName, "Программирование", "Проектирование")
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        app.headerPage.createProjectClick()
        app.projectPage.createProject()
        app.fillForm().project(publishedProjectName, "Маркетинг и продажи", "Креатив")
        //Лимит на количество публикаций в минуту - только ждать =(
        sleep(40000)
        app.projectPage.publicationProjectWizard()
        app.projectPage.checkBlockPublicationWithoutPRO()
        app.projectPage.checkButtonBuyPRO()
        app.projectPage.payPublicationProject()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.pay().yooMoneyCard()
        app.projectPage.disableCheckboxForAll()
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.projectPage.checkPublishedProject()
        app.goTo().allWork()
        app.listAllWorkPage.switchFilter()
        app.listAllWorkPage.findProject(publishedProjectName)
        app.listAllWorkPage.applyFilter()
        app.listAllWorkPage.checkPublishedOrderInListProjects(publishedProjectName, projectId)
    }

    @Test //Проверка минимальной суммы для создания заказа
    @Throws(InterruptedException::class)
    fun testMinLimitBudget() {
        app.login().signIn(CustomerlLegalEntityPRO)
        val projectName = "selenide the best project " + randomLetters()
        app.goTo().catalog()
        app.freelancersPage.createProjectClick()
        app.projectPage.createProject()
        app.projectPage.nameProject(projectName)
        app.projectPage.setCategoryProject()
        app.projectPage.goToStepTwo()
        app.projectPage.descriptionProject("Какое-то описание проекта")
        app.projectPage.goToStepThree()
        app.projectPage.setBudget("1198")
        app.projectPage.goToStepFour()
        app.projectPage.checkMinLimitBudget()
    }

    @Test
    //Повторная публикация проекта, ранее снятого с публикации
    fun republishProjectByProUser() {
        val projectName = "Project republished " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectManage(projectId)
        app.projectPage.checkSentModeration()
        app.api().forcePublicationProject(projectId)
        app.projectPage.checkPublishedProject()
        app.projectPage.clickOnProjectStatusToggle()
        app.projectPage.checkClosedProject()
        app.projectPage.clickOnProjectStatusToggle()
        app.projectPage.checkPublishedProject()
    }

    @Test
    fun addCandidate() {
        val projectName = "Project add candidate " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val tokenFreelancer = app.api().getToken(FreelIndividualPRO)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingOrRemoveCandidate()
        app.projectOffersPage.checkFreelanserIsCandidate()
        app.projectOffersPage.choosingOrRemoveCandidate()
        app.projectOffersPage.checkFreelanserIsNotCandidate()
    }

    @Test
    fun removeCandidate() {
        val projectName = "Project remove candidate " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val tokenFreelancer = app.api().getToken(FreelIndividualPRO)
        val offerId = app.api().offerProject(tokenFreelancer, projectId)
        app.api().selectCandidate(tokenCustomer, projectId, offerId)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.choosingOrRemoveCandidate()
        app.projectOffersPage.checkFreelanserIsNotCandidate()
    }

    @Test
    fun rejectOfferByCustomer() {
        val projectName = "Project add candidate " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val tokenFreelancer = app.api().getToken(FreelIndividualPRO)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.removeOffer()
        app.projectOffersPage.checkOfferNotVisible()
        app.login().signOut()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().project(projectId)
        app.offerPage.checkOfferRejectByCustomer()
    }

    @Test
    fun cancelOfferByFreelancer() {
        val projectName = "Project add candidate " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val tokenFreelancer = app.api().getToken(FreelIndividualPRO)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(FreelIndividualPRO)
        app.goTo().project(projectId)
        app.offerPage.cancelOffer()
        app.offerPage.checkCancelOfferByFreelancer()
        app.login().signOut()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.checkOfferNotVisible()
    }

    //Проверка перемещения оффера в спам и не отображения офферов от этого фрила для заказчика
    @Test
    fun moveOfferToSpam() {
        var projectName = "Project add candidate " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        var projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val tokenFreelancer = app.api().getToken(FreelIndividualPRO)
        app.api().offerProject(tokenFreelancer, projectId)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.moveOfferToSpam()
        app.projectOffersPage.checkOfferNotVisible()
        /** Баг? Заказчику отображаются офферы от фрила со спамом **/
//        projectName = "Project add candidate " + randomLetters()
//        projectId = app.api().createProject(tokenCustomer, projectName)
//        app.api().forcePublicationProject(projectId)
//        app.api().offerProject(tokenFreelancer, projectId)
//        app.goTo().projectOffers(projectId)
//        app.projectOffersPage.checkOfferNotVisible()
    }

    @Test
    fun filterOnOffersTab() {
        val projectName = "Project republished " + randomLetters()
        val tokenCustomer: String = app.api().getToken(CustomerIndividualPRO)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        val tokenFreelFirst = app.api().getToken(FreelLegalEntityPRO)
        val nameFreelFirst = app.login().getName(FreelLegalEntityPRO)
        val tokenFreelSecond = app.api().getToken(FreelInCatalog)
        val freelId = app.login().getUID(FreelInCatalog)
        val nameFreelSecond = app.login().getName(FreelInCatalog)
        val tokenFreelLast = app.api().getToken(FreelIndividualPRO)
        val nameFreelLast = app.login().getName(FreelIndividualPRO)
        app.api().sendInvitationToProject(tokenCustomer, projectId, freelId)
        val offerId = app.api().offerProject(tokenFreelFirst, projectId)
        app.api().offerProject(tokenFreelSecond, projectId)
        app.api().offerProject(tokenFreelLast, projectId)
        app.api().selectCandidate(tokenCustomer, projectId, offerId)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().projectOffers(projectId)
        app.projectOffersPage.selectFilterNewOffers()
        app.projectOffersPage.checkSort(nameFreelLast)
        app.projectOffersPage.selectFilterInvitees()
        app.projectOffersPage.checkSort(nameFreelSecond)
        app.projectOffersPage.selectFilterCandidates()
        app.projectOffersPage.checkSort(nameFreelFirst)
    }

    @Test
    fun InviteFreelToProject() {
        val customer = CustomerIndividualPRO
        val freelancer = FreelInCatalog
        val nameFreel = app.login().getName(freelancer)
        val projectName = "Project invitation " + randomLetters()
        val tokenCustomer: String = app.api().getToken(customer)
        val projectId: String = app.api().createProject(tokenCustomer, projectName)
        app.api().forcePublicationProject(projectId)
        app.login().signIn(customer)
        app.goTo().projectRecommendation(projectId)
        app.projectRecommendationPage.sendInvitation(nameFreel)
        app.projectRecommendationPage.checkInvitationSuccessfullySent()
        app.projectRecommendationPage.closeModalUserProfile()
        app.login().signOut()
        app.login().signIn(freelancer)
        app.headerPage.notificationBellClick()
        app.headerPage.checkNotificationBell(projectName)
    }
}