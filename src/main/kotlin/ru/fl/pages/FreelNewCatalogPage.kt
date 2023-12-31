package ru.fl.pages

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide.sleep
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import java.time.Duration


class FreelNewCatalogPage: SelenideObjects(){
    fun checkTransitionOnCatalog() {
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@data-id = 'qa-catalog-freelancers-header']").shouldHave(text("Проверенные фрилансеры"))
    }

    fun searchCardsFreelancers(): ElementsCollection {
        el(By.id("catalogList")).shouldBe(visible)
        return elxs("//*[@id='catalogList']/div")
    }

    fun goToCatalogProfile(id: String?) {
        elx("//*[@data-id = 'qa-catalog-freelancers-card-title-$id']").shouldBe(visible).click()
    }

    fun goToNewProfile(name: String?) {
        elx("//*[contains(@class, 'fl-card-name') and contains(text(), '$name')]").shouldBe(visible).click()
        elx("//*[@class = 'ui-modal-body']//*[contains(text(), '$name')]").shouldBe(visible).click()
    }

    fun getCountFreelancersCatalog(): Int {
        var freelCards = 0
        el(By.id("catalogSpinner")).shouldBe(disappear)
        elx("//*[@data-id = 'qa-catalog-freelancers-not-all']").shouldBe(visible)
        val pages = elxs("//*[contains(@class, 'fl-pagination-item')]").size
        if (pages > 0){
            freelCards = (pages - 1) * 15
            elx("//*[contains(@class, 'fl-pagination-item') and contains(text(), $pages)]").shouldBe(visible).click()
        }
        elxs("//*[contains(@data-id, 'qa-catalog-freelancers-card-avatar')]").first().shouldBe(visible)
        val freelCardsLastPage = elxs("//*[contains(@data-id, 'qa-catalog-freelancers-card-avatar')]").size
        val countAllFreelCards = freelCards + freelCardsLastPage
        return countAllFreelCards
    }

    fun searchFreelancer(name: String?) {
        el(By.id("ui-input-search-text")).shouldBe(visible, Duration.ofSeconds(10)).value = name
        el(By.id("ui-input-search-text")).sendKeys(Keys.ENTER)
    }

    fun goToPortfolioOutgoingProfile() {
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elxs("//*[@class = 'fl-listing__item-link']").first().click()
    }

    fun offerOrder() {
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[contains(text(), 'Предложить заказ')]").shouldBe(visible).click()
    }

    fun offerOrderInOutgoingProfileFreel() {
        elx("//*[@data-id = 'qa-card-chips']").shouldBe(visible)
        elx("//*[@class = 'd-none d-sm-block']/.//*[contains(text(), 'Предложить заказ')]").click()
    }

    fun offerOrderInHeaderOutgoingProfileFreel() {
        elx("//*[@data-id = 'qa-card-chips']").shouldBe(visible)
        elxs("//*[@class = 'fl-feedback-vote-icon']").last().scrollIntoView(false)
        elx("//*[@class = 'fl-card-top-scrolled']//*[contains(text(), 'Предложить заказ')]").shouldBe(
            visible
        ).click()
    }

    fun offerOrderInHeaderNewProfile() {
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elxs("//*[@class = 'fl-feedback-vote-icon']").last().scrollIntoView(false)
        elx("//*[@id = 'listing-col-ava']//*[text() = 'Предложить заказ']").shouldBe(visible).click()
    }

    fun modalOfferNewOrder() {
        elx("//*[@class = 'mt-20 d-none d-sm-block']//a[contains(text(), 'Создать новый заказ')]").shouldBe(visible).click()
    }

    fun modalOfferExistsOrder(projectName: String?) {
        elx("//*[@class = 'vs__selected']").shouldBe(visible).click()
        elx("//input[@aria-labelledby = 'vs1__combobox']").sendKeys(projectName)
        sleep(500)
        elx("//input[@aria-labelledby = 'vs1__combobox']").sendKeys(Keys.ENTER)
        elx("//*[@class = 'mt-20 d-none d-sm-block']/a[contains(text(), 'Предложить')]").shouldBe(visible).click()
    }

    fun checkCity() {
        elx("//*[@class = 'text-8 text-md-6 mb-16']").shouldBe(visible)
    }

    fun checkAboutFreelOutgoingProfile() {
        elx("//div[@class = 'text-7']").shouldBe(visible)
    }

    fun checkAboutFreelNewProfile() {
        elx("//div[@class = 'text-5']").shouldBe(visible)
    }

    fun checkSpecialisation() {
        elx("//*[contains(text(), 'Специализации')]").shouldBe(visible)
    }

    fun checkSkills() {
        elx("//*[@class = 'cards']//*[contains(text(), 'Навыки')]").shouldBe(visible)
    }

    fun checkPortfolioInAboutFreel() {
        elx("//*[contains(@class, 'fl-listing-about')]").shouldBe(visible)
    }

    fun checkFeedbacksInAboutFreel() {
        elx("//*[@class = 'mt-48 pb-36']").shouldBe(visible)
    }

    fun checkPortfolio() {
        elx("//*[contains(text(), 'Лучшие работы')]").shouldBe(visible)
        elx("//*[contains(text(), 'Клиенты')]").shouldBe(visible)
    }

    fun goToFeedback() {
        elxs("//*[contains(text(),'Отзывы')]").last().click()
    }

    fun goToPortfolio() {
        elxs("//span[contains(text(),'Портфолио')]").last().click()
    }

    fun checkFeedbacks() {
        elx("//*[@class = 'mt-36 pb-36']").shouldBe(visible)
    }

    fun chooseCategoryInFilter() {
        //Выбор категории в фильтре нового каталога фрилов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@id = 'vs7__combobox']").shouldBe(visible).click()
        elx("//*[@id = 'vs7__option-2']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun chooseSpecializationInFilter() {
        //Выбор специализации в фильтре нового каталога фрилов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@id = 'vs8__combobox']").shouldBe(visible).click()
        elx("//*[@id = 'vs8__option-2']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun chooseCountryRussiaInFilter() {
        //Выбор страны в фильтре нового каталога фрилов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@id = 'vs9__combobox']").shouldBe(visible).click()
        elx("//*[@id = 'vs9__option-1']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun chooseCountryAustraliaInFilter() {
        //Выбор страны в фильтре нового каталога фрилов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@id = 'vs9__combobox']").shouldBe(visible).click()
        elx("//*[@id = 'vs9__option-4']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun chooseCityInFilter(city: String?) {
        //Выбор города в фильтре нового каталога фрилов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@id = 'vs10__combobox']").shouldBe(visible).click()
        elx("//*[@aria-labelledby = 'vs10__combobox']").sendKeys(city)
        elx("//*[@id = 'vs10__option-0']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun chooseRegYearsAgoInFilter() {
        //Выбор срока нахождения на сайте fl.com в фильтре нового каталога фрилов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@id = 'vs11__combobox']").shouldBe(visible).click()
        elx("//*[@id = 'vs11__option-2']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun chooseSkillsInFilter(skill: String?) {
        //Выбор навыков в фильтре нового каталога фрилов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@aria-labelledby = 'vs12__combobox']").value = skill
        elx("//*[@placeholder = 'Название навыка']")
            .shouldHave(attribute("aria-activedescendant"), Duration.ofSeconds(10)).pressEnter()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun setFilterWithPortfolio() {
        //Включение фильтра по наличию работ
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@for = 'ui-switch-with-portfolio']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun setFilterWithFeedbacks() {
        //Включение фильтра по наличию отзывов
        el(By.id("catalogSpinner")).shouldNotBe(visible)
        elx("//*[@for = 'ui-switch-with-feedbacks']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun checkCountFreelacerInFilter(): Int {
        val freelCounter = elx("//*[contains(@class, 'fl-found-freelance')]").text.replace("\\D+".toRegex(), "")
        return freelCounter.toInt()
    }

    fun cancellationFilter() {
        elx("//*[text() = 'Сбросьте']").shouldBe(visible).click()
        el(By.id("filterTotalCountSpinner")).shouldNotBe(visible)
    }

    fun goToSignupPage() {
        elx("//*[@data-id='qa-catalog-freelancers-body']//a[contains(text(), 'Разместить здесь свой профиль')]").
            shouldBe(visible).click()
    }

    fun checkSignupPage() {
        elx("//h1[contains(text(), 'Заявка на размещение в разделе')]").isDisplayed
        el(By.id("button-catalog-profile-save")).scrollTo().shouldBe(visible)
    }

    fun checkDivLinkWithAnkor(ankor: String) {
        elx("//div[contains(text(), '$ankor')]").shouldBe(visible)
    }

    fun checkSpanLinkWithAnkor(ankor: String) {
        elx("//span[contains(text(), '$ankor')]").shouldBe(visible)
    }

    fun checkALinkWithAnkor(ankor: String) {
        elx("//a[contains(text(), '$ankor')]").shouldBe(visible)
    }

    fun closeNewCatalogBanner() {
        if (el(By.id("app-banner-profile")).isDisplayed) {
            elx("//button[@data-id='qa-ui-button-icon']//*[name()='svg']").shouldBe(visible).click()
            }
    }

    fun goToLinkShow() {
        elx("//a[contains(text(), 'Показать')]").shouldBe(visible).click()
    }

    fun goToLinkEdit() {
        elx("//a[contains(text(), 'Редактировать')]").shouldBe(visible).click()
    }

    fun editFormButton() {
        elx("//*[@id='section-action']//a[contains(text(), 'Редактировать анкету')]").shouldBe(visible).click()
    }

    fun checkAboutProfilText(textAbout :String) {
        elx("//div[@class = 'text-7']").shouldHave(text(textAbout))
    }

}