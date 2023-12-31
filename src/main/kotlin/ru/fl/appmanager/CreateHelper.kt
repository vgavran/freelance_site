package ru.fl.appmanager

import com.codeborne.selenide.Selenide
import ru.fl.model.UsersData
import java.io.IOException

class CreateHelper(val app: ApplicationManager){
    fun vacancy(name: String,
                vacancyType: String = "standard",
                salaryFrom: String = "5000",
                salaryTo: String = "10000") {
        app.headerPage.goToAllVacancies()
        app.vacancyPage.clickPublishVacancy()
        app.vacancyPage.nameVacancy(name)
        app.vacancyPage.chooseSpecialization()
        app.vacancyPage.chooseSkill()
        app.vacancyPage.descriptionVacancy(name)
        app.vacancyPage.goToWorkConditionsStep()
        app.vacancyPage.setSalary(salaryFrom, salaryTo)
        app.vacancyPage.chooseCountryAndCity()
        app.vacancyPage.setPhone()
        app.vacancyPage.goToVacancyPublicationStep()
        app.vacancyPage.goToVacancyTypeStep()
        app.vacancyPage.chooseVacancyType(vacancyType)
    }

    fun competition(name: String, description: String, endDate: Int, winDate: Int) {
        app.headerPage.goToAllCompetition()
        app.competitionPage.createCompetition()
        app.competitionPage.nameCompetition(name)
        app.competitionPage.descriptionCompetition(description)
        app.competitionPage.chooseSpecializationCompetition()
        app.competitionPage.calendarEndDateClick()
        app.competitionPage.setDate(endDate)
        app.competitionPage.calendarWinDateClick()
        app.competitionPage.setDate(winDate)
        app.competitionPage.publishCompetition()
    }

    fun directOrderSafeDeal(name: String,
                            description: String = "Какое-то описание прямого заказа",
                            budget: String = "1200",
                            term: String = "5") {
        app.orderFormPage.orderName(name)
        app.orderFormPage.orderDescription(description)
        app.orderFormPage.orderTerm(term)
        app.orderFormPage.orderPrice(budget)
        app.orderFormPage.buttonOfferOrders()
    }

    fun directOrderNotSafeDeal(name: String,
                    description: String = "Какое-то описание прямого заказа",
                    budget: String = "1200",
                    term: String = "5") {
        app.orderFormPage.orderName(name)
        app.orderFormPage.orderDescription(description)
        app.orderFormPage.orderTerm(term)
        app.orderFormPage.orderPrice(budget)
        app.orderFormPage.selectNotSafeDeal()
        app.orderFormPage.acceptRisks()
        app.orderFormPage.buttonOfferOrders()
    }

    @Throws(IOException::class, InterruptedException::class)
    fun dealBsCardApi(customer: UsersData.Users, emailFreelancer: String, amount: String, projectName: String) {
        val tokenCustomer: String = app.api().getToken(customer)
        val tokenFreelancer: String = app.api().getToken(emailFreelancer, app.getProperty("passNew"))
        val projectId: String = app.api().createProject(tokenCustomer, projectName, amount)
        val offerId: String = app.api().offerProject(tokenFreelancer, projectId, amount)
        app.api().selectExecutor(tokenCustomer, projectId, offerId)
        val orderId: String = app.api().createOrderPaymentByCard(tokenCustomer, offerId)
        app.api().addPhone(tokenFreelancer)
        app.api().confirmOrder(tokenFreelancer, orderId)
        app.paymentPage.testPaymentOrder(orderId)
        app.api().completeOrder(tokenCustomer, orderId)
        app.login().signIn(emailFreelancer, app.getProperty("passNew"))
        app.goTo().order(orderId)
        app.orderPage.receiveMoneyToCardFreel()
        Selenide.sleep(2000)
        Selenide.refresh()
        app.orderPage.checkPaymentStatus()
        app.login().signOut()
    }

    //Создание проекта без указания бюджета, с открытой датой
    fun project(name: String, description: String = "Проект без указания бюджета, с открытой датой") {
        app.mainPage.createProjectClick()
        app.projectPage.createProject()
        app.projectPage.nameProject(name)
        app.projectPage.setCategoryProject()
        app.projectPage.goToStepTwo()
        app.projectPage.descriptionProject(description)
        app.projectPage.goToStepThree()
        app.projectPage.setNoBudget()
        app.projectPage.setNoDeadline()
        app.projectPage.goToStepFour()
        app.projectPage.publicationProjectWizard()
        app.projectPage.disableCheckboxForAll()
    }

    //Создание проекта с прикреплением файла
    fun projectWithAttach(name: String) {
        app.mainPage.createProjectClick()
        app.projectPage.createProject()
        app.projectPage.nameProject(name)
        app.projectPage.setCategoryProject()
        app.projectPage.goToStepTwo()
        app.projectPage.descriptionProject()
        app.projectPage.addAttachedFile()
        app.projectPage.goToStepThree()
        app.projectPage.setNoBudget()
        app.projectPage.setNoDeadline()
        app.projectPage.goToStepFour()
        app.projectPage.publicationProjectWizard()
        app.projectPage.disableCheckboxForAll()
    }

    fun offerProject(description: String = "Standart offer text",
                     term: String = "5",
                     budget: String = "1300") {
        app.offerPage.offerDescription(description)
        app.offerPage.termOffer(term)
        app.offerPage.totalCost(budget)
        app.offerPage.pressSubmitAnswerButton()
    }

    fun offerVacancy(description: String = "Standart offer vacancy text") {
        app.offerPage.descriptionOfferVacancy(description)
        app.offerPage.submitOfferVacancy()
    }

    fun offerCompetition(description: String = "I want to help you in Konkurs text") {
        app.offerPage.descriptionOfferCompetition(description)
        app.offerPage.submitOfferCompetition()
    }

    fun offerProjectWithPreliminaryCost(description: String = "Standart offer text",
                     term: String = "5",
                     budget: String = "1300") {
        app.offerPage.offerDescription(description)
        app.offerPage.termOffer(term)
        app.offerPage.totalCost(budget)
        app.offerPage.preliminaryCost()
        app.offerPage.pressSubmitAnswerButton()
    }

    fun offerProjectWithExamplesOfWork(description: String = "Standart offer text",
                                        term: String = "5",
                                        budget: String = "1300") {
        app.offerPage.offerDescription(description)
        app.offerPage.termOffer(term)
        app.offerPage.totalCost(budget)
        app.offerPage.addWorkExamples()
        app.offerPage.selectExamplesOfWork()
        app.offerPage.pressSubmitAnswerButton()
    }

    fun offerProjectWithSampleWorkUpload(description: String = "Standart offer text",
                                       term: String = "5",
                                       budget: String = "1300") {
        app.offerPage.offerDescription(description)
        app.offerPage.termOffer(term)
        app.offerPage.totalCost(budget)
        app.offerPage.addWorkExamples()
        app.offerPage.uploadSampleOfWork()
        app.offerPage.pressUpload()
        app.offerPage.pressSubmitAnswerButton()
    }
}