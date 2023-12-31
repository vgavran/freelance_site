package ru.fl.test

import com.codeborne.selenide.Selenide.sleep
import org.testng.Assert
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import ru.fl.model.UsersData.Users.*
import java.io.IOException


class BS_ComissionTest : TestBase() {
    //Проверка комиссии если не было сделок
    @Test
    @Throws(IOException::class)
    fun checkComissionWithoutPayments() {
        app.login().signIn(CustomerlLegalEntityPRO)
        app.create().project(
            "Комиссия без сделок " + randomLetters()
        )
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.login().signOut()
        val email = randomNumberLetter() + "@fl.com"
        val tokenFreel = app.api().registration(email, "freelancer")
        val buyPro = app.api().getLinkBuyPRO(tokenFreel)
        app.goTo().page(buyPro)
        app.pay().yooMoneyCard()
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().project(projectId)
        val paid: String = app.offerPage.getInfoPaidAmount()
        val text: String = app.offerPage.getInfoHaveDeal()
        val discount: Int = app.offerPage.getInfoDiscount()
        Assert.assertEquals(text, "Вы еще не работали вместе")
        Assert.assertEquals(paid, "Всего заказчик выплатил вам 0 .")
        Assert.assertEquals(discount, 20)
        app.offerPage.checkSumComission(10)
    }

    //Проверка комиссии, если были сделки, выплачено 18к
    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun checkComissionWithPayments_18k() {
        val emailFreelancer = randomNumberLetter() + "@fl.com"
        val nameProject = "Заказ " + randomLetters()
        val tokenFreelancer = app.api().registration(emailFreelancer, "freelancer")
        val buyPRO = app.api().getLinkBuyPRO(tokenFreelancer)
        app.goTo().page(buyPRO)
        app.pay().yooMoneyCard()
        app.create().dealBsCardApi(CustomerIndividualPRO, emailFreelancer, "10800", nameProject)
        sleep(50000)
        app.create().dealBsCardApi(CustomerIndividualPRO, emailFreelancer, "10800", nameProject)
        app.login().signIn(CustomerIndividualPRO)
        app.create().project(nameProject)
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.login().signOut()
        app.login().signIn(emailFreelancer, app.getProperty("passNew"))
        app.goTo().project(projectId)
        val paid: String = app.offerPage.getInfoPaidAmount()
        val text: String = app.offerPage.getInfoHaveDeal()
        val discount: Int = app.offerPage.getInfoDiscount()
        Assert.assertEquals(text, "Вы уже работали вместе")
        Assert.assertEquals(paid, "Всего заказчик выплатил вам 18000 .")
        Assert.assertEquals(discount, 20)
        app.offerPage.checkSumComission(10)
    }

    @Test
    //Проверка комисии, если есть персональная комиссия 5%
    @Throws(IOException::class, InterruptedException::class)
    fun checkComissionWithIndividualDiscount() {
        val emailFreelancer = randomNumberLetter() + "@fl.com"
        val pass = app.getProperty("passNew")
        val nameProject = "Заказ " + randomLetters()
        val tokenFreelancer = app.api().registration(emailFreelancer, "freelancer")
        val buyPRO = app.api().getLinkBuyPRO(tokenFreelancer)
        app.goTo().page(buyPRO)
        app.pay().yooMoneyCard()
        app.create().dealBsCardApi(CustomerWithIndividualDiscount, emailFreelancer, "10395", nameProject)
        app.login().signIn(CustomerWithIndividualDiscount)
        app.create().project(
            "Комиссия с двумя сделками " + randomLetters()
        )
        app.projectPage.publicationProject()
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.login().signOut()
        app.login().signIn(emailFreelancer, pass)
        app.goTo().project(projectId)
        val paid: String = app.offerPage.getInfoPaidAmount()
        val text: String = app.offerPage.getInfoHaveDeal()
        val discount: Int = app.offerPage.getInfoDiscount()
        Assert.assertEquals(text, "Вы уже работали вместе")
        Assert.assertEquals(paid, "Всего заказчик выплатил вам 9900 .")
        Assert.assertEquals(discount, 5)
        app.offerPage.checkSumComission(10)
    }

    @Test
    //Проверка комиссии, выплачено 50к, комиссия 12%
    @Throws(IOException::class, InterruptedException::class)
    fun checkComissionWithPayments_50k() {
        val loginFreelancer = randomNumberLetter()
        val emailFreelancer = "$loginFreelancer@fl.com"
        val loginCustomer = app.login().getLogin(CustomerIndividualPRO).toString()
        val nameProject = "Заказ " + randomLetters()
        val tokenFreelancer = app.api().registration(emailFreelancer, "freelancer")
        val buyPRO = app.api().getLinkBuyPRO(tokenFreelancer)
        app.goTo().page(buyPRO)
        app.pay().yooMoneyCard()
        app.create().dealBsCardApi(CustomerIndividualPRO, emailFreelancer, "5000", nameProject)
        app.login().signIn(CustomerIndividualPRO)
        app.create().project(
            "Комиссия " + randomLetters()
        )
        app.projectPage.publicationProject()
        app.db.updateOrdersSummary(loginCustomer, loginFreelancer, 50000)
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.login().signOut()
        app.login().signIn(emailFreelancer, app.getProperty("passNew"))
        app.goTo().project(projectId)
        val paid: String = app.offerPage.getInfoPaidAmount()
        val worked: String = app.offerPage.getInfoHaveDeal()
        val discount: Int = app.offerPage.getInfoDiscount()
        Assert.assertEquals(worked, "Вы уже работали вместе")
        Assert.assertEquals(paid, "Всего заказчик выплатил вам 50000 .")
        Assert.assertEquals(discount, 12)
        app.offerPage.checkSumComission(10)
    }

    @Test
    //Проверка комиссии, выплачено 350к, комиссия 7%
    @Throws(IOException::class, InterruptedException::class)
    fun checkComissionWithPayments_350k() {
        val loginFreelancer = randomNumberLetter()
        val emailFreelancer = "$loginFreelancer@fl.com"
        val loginCustomer = app.login().getLogin(CustomerIndividualPRO).toString()
        val nameProject = "Заказ " + randomLetters()
        val tokenFreelancer = app.api().registration(emailFreelancer, "freelancer")
        val buyPRO = app.api().getLinkBuyPRO(tokenFreelancer)
        app.goTo().page(buyPRO)
        app.pay().yooMoneyCard()
        app.create().dealBsCardApi(CustomerIndividualPRO, emailFreelancer, "5000", nameProject)
        app.login().signIn(CustomerIndividualPRO)
        app.create().project(
            "Комиссия " + randomLetters()
        )
        app.projectPage.publicationProject()
        app.db.updateOrdersSummary(loginCustomer, loginFreelancer, 350000)
        val projectId: String = app.projectPage.getProjectId()
        app.api().forcePublicationProject(projectId)
        app.login().signOut()
        app.login().signIn(emailFreelancer, app.getProperty("passNew"))
        app.goTo().project(projectId)
        val paid: String = app.offerPage.getInfoPaidAmount()
        val worked: String = app.offerPage.getInfoHaveDeal()
        val discount: Int = app.offerPage.getInfoDiscountMin()
        Assert.assertEquals(worked, "Вы уже работали вместе")
        Assert.assertEquals(paid, "Всего заказчик выплатил вам 350000 .")
        Assert.assertEquals(discount, 7)
        app.offerPage.checkSumComission(10)
    }
}