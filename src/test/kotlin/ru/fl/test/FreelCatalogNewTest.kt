package ru.fl.test

import org.testng.Assert
import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils
import ru.fl.appmanager.RandomUtils.Companion.randomLetters
import ru.fl.appmanager.RandomUtils.Companion.randomNumberLetter
import ru.fl.model.UsersData.Users.*
import java.io.IOException
import java.sql.SQLException

class FreelCatalogNewTest : TestBase() {

    @Test
    fun goToCatalogFromMainPage() {
        app.goTo().mainPage()
        app.mainPage.linkNewCatalog()
        app.freelNewCatalogPage.checkTransitionOnCatalog()
    }

    @Test
    fun goToCatalogFromHeaderCustomer() {
        app.login().signIn(CustomerIndividualPRO)
        app.headerPage.buttonNewCatalogCustomer()
        app.freelNewCatalogPage.checkTransitionOnCatalog()
    }

    @Test
    fun goToCatalogFromHeaderFreelancer() {
        app.login().signIn(FreelIndividualPRO)
        app.headerPage.buttonNewCatalogFreelancer()
        app.freelNewCatalogPage.checkTransitionOnCatalog()
    }

    /* TODO Старый бейдж убрали, новый будет в ближ. время, нужно будет вренуть тест после добавления бейджа
    @Test
    fun testLabelsVerifyCatalogProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        app.login().signIn(FreelIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.goToCatalogProfile(nameFreel)
        app.freelNewCatalogPage.checkDisplayedLablesVerify()
    }
     */

    @Test
    //Отображение в новом каталоге всех фрилансеров для авторзованного фрилансера
    @Throws(IOException::class)
    fun checkAllFreelancersInCatalogByFreel() {
        val countAllFreelancersElastic: Int = app.elasticSearch().getAllFreelInCatalog()
        app.login().signIn(FreelIndividualPRO)
        app.goTo().newCatalog()
        val countAllFreelancersCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countAllFreelancersCatalog, countAllFreelancersElastic)
    }

    @Test
    //Отображение в новом каталоге всех фрилансеров для авторзованного заказчика
    @Throws(IOException::class)
    fun checkAllFreelancersInCatalogByCustomer() {
        val countAllFreelancersElastic: Int = app.elasticSearch().getAllFreelInCatalog()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.getCountFreelancersCatalog()
        val countAllFreelancersCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countAllFreelancersCatalog, countAllFreelancersElastic)
    }

    @Test
    //Отображение в новом каталоге всех фрилансеров для неавторзованного пользователя
    @Throws(IOException::class)
    fun checkAllFreelancersInCatalogByAnonym() {
        val countAllFreelancersElastic: Int = app.elasticSearch().getAllFreelInCatalog()
        app.goTo().newCatalog()
        app.login().acceptCookies()
        app.freelNewCatalogPage.getCountFreelancersCatalog()
        val countAllFreelancersCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countAllFreelancersCatalog, countAllFreelancersElastic)
    }

    @Test
    //Предложение нового заказа из карточки каталога заказчиком с регистрацией
    @Throws(IOException::class, SQLException::class)
    fun directNewOrderFromCatalogCardByNewregCustomer() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val projectName = "selenide test " + randomLetters() + " order from card freel"
        app.api().registration(email, "customer")
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.login().signIn(login, app.getProperty("passNew"))
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.offerOrder()
        app.freelNewCatalogPage.modalOfferNewOrder()
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.create().directOrderSafeDeal(
            projectName,
            "Предложение нового заказа из карточки каталога заказчиком с регистрацией",
            "1200",
            "10"
        )
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test
    //Предложение нового заказа из карточки каталога старым заказчиком
    @Throws(IOException::class)
    fun directNewOrderFromCatalogCard() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val projectName = "selenide test " + randomLetters() + " order from card freel, new catalog"
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.offerOrder()
        app.freelNewCatalogPage.modalOfferNewOrder()
        app.create()
            .directOrderSafeDeal(projectName, "Предложение нового заказа из карточки нового каталога", "1200", "10")
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test
    //Предложение заказа из выезжающего профиля
    fun directOrderFromOutgoingProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val idFreel = app.login().getUID(FreelInCatalog)
        val projectName = "selenide test " + randomLetters() + " order from outgoing profile"
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToCatalogProfile(idFreel)
        app.freelNewCatalogPage.offerOrderInOutgoingProfileFreel()
        app.create()
            .directOrderSafeDeal(projectName, "Предложение заказа из выезжающего профиля фрилансера", "25000", "30")
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test
    //Предложение заказа из нового профиля
    fun directOrderFromNewProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val projectName = "selenide test " + randomLetters() + " order from new profile"
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToNewProfile(nameFreel)
        app.freelNewCatalogPage.offerOrder()
        app.create()
            .directOrderSafeDeal(projectName, "Предложение заказа из нового профиля фрилансера", "25000", "30")
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test
    //Предложение заказа из шапки выезжающего профиля
    fun directOrderFromHeaderOutgoingProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val idFreel = app.login().getUID(FreelInCatalog)
        val projectName = "selenide test " + randomLetters() + " order from header outgoing profile"
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToCatalogProfile(idFreel)
        app.freelNewCatalogPage.offerOrderInHeaderOutgoingProfileFreel()
        app.create().directOrderSafeDeal(
            projectName,
            "Предложение нового заказа из шапки выезжающего профиля фрилансера",
            "25000",
            "30"
        )
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test
    //Предложение заказа из шапки нового профиля
    fun directOrderFromHeaderNewProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val projectName = "selenide test " + randomLetters() + " order from header new profile"
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToNewProfile(nameFreel)
        app.freelNewCatalogPage.offerOrderInHeaderNewProfile()
        app.create()
            .directOrderSafeDeal(projectName, "Предложение заказа из шапки нового профиля фрилансера", "25000", "30")
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test (enabled = false) //FIXME невозможно предложить существующий заказ
    //Предложение существующего заказа из карточки каталога старым заказчиком
    fun directExistsOrderFromCatalogCard() {
        val nameFreel = app.login().getName(FreelInCatalog)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().allOrders()
        val projectName: String = app.ordersAllPage.getNameExistOrder()
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.offerOrder()
        app.freelNewCatalogPage.modalOfferExistsOrder(projectName)
        app.orderFormPage.orderTerm("5")
        app.orderFormPage.orderPrice("2356")
        app.orderFormPage.buttonOfferOrders()
        app.orderPage.checkDirectOrderCreation(projectName)
    }

    @Test (enabled = false) //FIXME невозможно предложить существующий заказ
    //Предложение нового заказа из карточки каталога новым фрилансером
    @Throws(SQLException::class, IOException::class)
    fun directOrderFromCatalogCardByNewregFreel() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val login = randomNumberLetter()
        val email = "$login@fl.com"
        val projectName = "selenide test " + randomLetters() + " direct order by freel, new catalog"
        app.api().registration(email, "freelancer")
        val urlActivate: String = app.db.getUrlActivateEmail(login)
        app.goTo().page(urlActivate)
        app.login().signIn(login, app.getProperty("passNew"))
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.offerOrder()
        app.freelNewCatalogPage.modalOfferExistsOrder(projectName)
        app.personalAccountPage.selectCardType()
        app.personalAccountPage.confirmSelectedType()
        app.create().directOrderSafeDeal(
            projectName,
            "Предложение нового заказа из карточки нового каталога существующим фрилансером",
            "1200",
            "10"
        )
        app.orderPage.checkDirectOrderCreation(projectName)
        app.headerPage.checkIsAccountCustomer()
    }

    @Test
    //Предложение нового заказа из карточки каталога существующим фрилансером
    @Throws(SQLException::class)
    fun directNewOrderFromCatalogCardByFreel() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val projectName = "selenide test " + randomLetters() + " direct order by freel, new catalog"
        app.login().signIn(FreelIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.offerOrder()
        app.freelNewCatalogPage.modalOfferNewOrder()
        app.create().directOrderSafeDeal(projectName)
        app.orderPage.checkDirectOrderCreation(projectName)
        app.headerPage.checkIsAccountCustomer()
    }
    /**    @Test(priority = 16, retryAnalyzer = RunTestAgain.class)
     * //Предложение нового заказа из карточки каталога анонимом с регистрацией
     * //Нет отключения капчи - нужно сделать.
     * public void directNewOrderFromCatalogCardByAnonymReg(){
     * val nameFreel = app.login().getName(FreelInCatalog)
     * String projectName = "selenide test " + RandomUtils.getRandomLetters() + " direct order by freel, new catalog";
     * String login = (RandomUtils.getRandomNumberLetter());
     * String email = (login + "@selenide.net");
     * app.goTo().catalog();
     * app.envPage.acceptCookies();
     * app.newCatalogPage.searchFreelancer(nameFreel);
     * app.newCatalogPage.offerOrder();
     * app.newCatalogPage.modalOfferNewOrder();
     * app.fillForm().directOrder(projectName, "Предложение нового заказа из карточки нового каталога существующим фрилансером", "10", "10999");
     * app.orderFormPage.fillFormReg(email);
     * app.orderFormPage.clickSubmitBtnWithReg();
     * app.ordersPage.checkDirectOrderCreationWithReg();
     * }
     */
    /** БАГ команда заказчиков
     * @Test(priority = 17)
     * //Предложение нового заказа из карточки каталога анонимом с авторизацией
     * public void directNewOrderFromCatalogCardByAnonymAuth(){
     * val nameFreel = app.login().getName(FreelInCatalog)
     * String projectName = "selenide test " + RandomUtils.getRandomLetters() + " direct order by freel, new catalog";
     * app.goTo().catalog();
     * app.envPage.acceptCookies();
     * app.newCatalogPage.searchFreelancer(nameFreel);
     * app.newCatalogPage.offerOrder();
     * app.newCatalogPage.modalOfferNewOrder();
     * app.fillForm().directOrder(projectName, "Предложение нового заказа из карточки нового каталога существующим фрилансером", "10", "3489");
     * app.orderFormPage.goToAuth();
     * app.goTo().newAuthWithCaptchaDisabled();
     * app.loginPage.login("aegupova2");
     * app.loginPage.pass(app.getProperty("passOld"));
     * app.loginPage.signIn();
     * app.ordersPage.checkDirectOrderCreation(projectName);
     * }
     */

    @Test
    //Проверка наличия кнопки добавления в каталог для нового фрилансера
    fun checkButtonAddOnCatalogIndexPage() {
        val login = RandomUtils.randomNumberLetter()
        val email = "$login@fl.com"
        app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().newCatalog()
        app.freelNewCatalogPage.goToSignupPage()
        app.freelNewCatalogPage.checkSignupPage()
    }

    @Test
    //Проверка элементов на вкладке "О себе" выезжающего профиля
    fun aboutFreelInOutgoingProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val idFreel = app.login().getUID(FreelInCatalog)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToCatalogProfile(idFreel)
        app.freelNewCatalogPage.checkCity()
        app.freelNewCatalogPage.checkAboutFreelOutgoingProfile()
        app.freelNewCatalogPage.checkSpecialisation()
        app.freelNewCatalogPage.checkSkills()
        app.freelNewCatalogPage.checkPortfolioInAboutFreel()
        app.freelNewCatalogPage.checkFeedbacksInAboutFreel()
    }

    @Test
    //Проверка элементов на вкладке "Портфолио" выезжающего профиля
    fun portfolioInOutgoingProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToPortfolioOutgoingProfile()
        app.freelNewCatalogPage.checkPortfolio()
    }

    @Test
    //Проверка элементов на вкладке "Отзывы" выезжающего профиля
    fun PortfolioInOutgoingProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val idFreel = app.login().getUID(FreelInCatalog)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToCatalogProfile(idFreel)
        app.freelNewCatalogPage.goToFeedback()
        app.freelNewCatalogPage.checkFeedbacks()
    }

    @Test
    //Проверка элементов на вкладке "О себе" нового профиля
    fun aboutFreelInNewProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToNewProfile(nameFreel)
        app.freelNewCatalogPage.checkCity()
        app.freelNewCatalogPage.checkAboutFreelNewProfile()
        app.freelNewCatalogPage.checkSpecialisation()
        app.freelNewCatalogPage.checkSkills()
        app.freelNewCatalogPage.checkPortfolioInAboutFreel()
        app.freelNewCatalogPage.checkFeedbacksInAboutFreel()
    }

    @Test
    //Проверка элементов на вкладке "Портфолио" нового профиля
    fun portfolioInNewProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToNewProfile(nameFreel)
        app.freelNewCatalogPage.goToPortfolio()
        app.freelNewCatalogPage.checkPortfolio()
    }

    @Test
    //Проверка элементов на вкладке "Отзывы" нового профиля
    fun feedbacksInNewProfile() {
        val nameFreel = app.login().getName(FreelInCatalog)
        val idFreel = app.login().getUID(FreelInCatalog)
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.searchFreelancer(nameFreel)
        app.freelNewCatalogPage.goToCatalogProfile(idFreel)
        app.freelNewCatalogPage.goToFeedback()
        app.freelNewCatalogPage.checkFeedbacks()
    }

    @Test
    //Проверка фильтрации фрилов в каталоге по специализации
    @Throws(IOException::class)
    fun filterBySpecialization() {
        val countFreelElastic: Int = app.elasticSearch().getFreelFilterSpecializationInCatalog()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.chooseCategoryInFilter()
        app.freelNewCatalogPage.chooseSpecializationInFilter()
        val countFreelCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countFreelCatalog, countFreelElastic)
    }

    @Test
    //Проверка фильтрации фрилов в каталоге по городу
    @Throws(IOException::class)
    fun filterByCity() {
        val countFreelElastic: Int = app.elasticSearch().getFreelFilterCityInCatalog()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.chooseCountryRussiaInFilter()
        app.freelNewCatalogPage.chooseCityInFilter("Москва")
        app.freelNewCatalogPage.getCountFreelancersCatalog()
        val countFreelCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countFreelCatalog, countFreelElastic)
    }

    @Test
    //Проверка фильтрации фрилов в каталоге по сроку на сайте
    @Throws(IOException::class)
    fun filterByRegDate() {
        val countFreelElastic: Int = app.elasticSearch().getFreelFilterRegDateInCatalog()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.chooseRegYearsAgoInFilter()
        app.freelNewCatalogPage.getCountFreelancersCatalog()
        val countFreelCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countFreelCatalog, countFreelElastic)
    }

    @Test
    //Проверка фильтрации фрилов в каталоге по навыкам
    @Throws(IOException::class)
    fun filterBySkills() {
        val countFreelElastic: Int = app.elasticSearch().getFreelFilterSkillsInCatalog()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.chooseSkillsInFilter("git")
        val countFreelCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countFreelCatalog, countFreelElastic)
    }

    @Test
    //Проверка фильтрации фрилов в каталоге по наличию работ
    @Throws(IOException::class)
    fun filterByPortfolio() {
        val countFreelElastic: Int = app.elasticSearch().getFreelFilterWithPortfolioInCatalog()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.setFilterWithPortfolio()
        app.freelNewCatalogPage.getCountFreelancersCatalog()
        val countFreelCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countFreelCatalog, countFreelElastic)
    }

    @Test
    //Проверка фильтрации фрилов в каталоге по наличию отзывов
    @Throws(IOException::class)
    fun filterByFeedbacks() {
        val countFreelElastic: Int = app.elasticSearch().getFreelFilterWithFeedbacksInCatalog()
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.setFilterWithFeedbacks()
        val countFreelCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countFreelCatalog, countFreelElastic)
    }

    @Test
    //Проверка сброса фильтра
    @Throws(IOException::class)
    fun filterCancellation() {
        app.login().signIn(CustomerIndividualPRO)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.chooseCountryAustraliaInFilter()
        val freelNotFound: Int = app.freelNewCatalogPage.checkCountFreelacerInFilter()
        Assert.assertEquals(freelNotFound, 0)
        app.freelNewCatalogPage.cancellationFilter()
        app.freelNewCatalogPage.getCountFreelancersCatalog()
        val countFreelInFilter: Int = app.freelNewCatalogPage.checkCountFreelacerInFilter()
        val countFreelCatalog: Int = app.freelNewCatalogPage.getCountFreelancersCatalog()
        Assert.assertEquals(countFreelCatalog, countFreelInFilter)
    }
}