package ru.fl.pages

import com.codeborne.selenide.Condition.*
import org.openqa.selenium.By


class HeaderPage: SelenideObjects(){

    fun goToAllWork() {
        elx("//*[@data-id = 'qa-head-work']").shouldBe(visible).click()
    }

    fun goToFreelancersCatalogOld() {
        elx("//*[contains(@class, 'navbar-light')]//*[@title = 'Раздел фрилансеров']")
            .shouldBe(visible).click()
    }

    fun goToAllVacancies() {
        elx("//*[@data-id = 'qa-navbar-left-dropdown-1']").shouldBe(visible).click()
        el(By.linkText("Вакансии")).shouldBe(visible).click()
    }

    fun goToAllCompetition() {
        elx("//*[@data-id = 'qa-navbar-left-dropdown-1']").shouldBe(visible).click()
        el(By.linkText("Конкурсы")).shouldBe(visible).click()
    }

    fun goToChatPage() {
        elx("//*[contains(@class, 'navbar-light')]//*[contains(text(), 'Чаты')]").shouldBe(visible).click()
    }

    fun goToOrdersCustomer() {
        elx("//*[@data-id = 'qa-head-my-orders']").shouldBe(visible).click()
        elx("//*[@title='Список моих заказов']").shouldBe(visible).click()
    }

    fun goToOrdersFreelancer() {
        elx("//*[@data-id = 'qa-head-my-orders']").shouldBe(visible).click()
    }

    fun createProjectClick() {
        elx("//*[@data-id = 'qa-head-right-block']//*[contains(text(), 'Разместить заказ')]")
            .shouldBe(enabled)
            .shouldBe(visible).click()
    }

    fun openSettings() {
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[@title = 'Настройки аккаунта']").shouldBe(visible).click()
    }

    fun goToPRO() {
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[@data-id = 'qa-head-pro']").shouldBe(visible).click()
    }

    fun goToPRONewReg() {
        elx("//*[@data-id = 'qa-head-buy-pro']").shouldBe(visible).click()
    }

    fun goToAccount() {
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[contains(text(), 'Счет')]").shouldBe(visible).click()
    }

    fun goToAdminsBS() {
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[contains(text(), 'Админка заказов')]").shouldBe(visible).click()
    }

    fun signOut() {
        elx("//*[@class = 'nav-item dropdown']").shouldBe(visible)
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[contains(text(), 'Выйти из аккаунта')]").shouldBe(visible).click()
    }

    fun goTolinkedAccount() {
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[contains(text(), 'Войти как')]").shouldBe(visible).click()
    }

    fun checkIsAccountCustomer() {
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[@data-id = 'qa-head-dropdown-5']//*[text() = 'Заказчик']").shouldBe(visible)
    }

    fun checkIsAccountFreelancer() {
        el(By.id("navbarRightDropdown")).shouldBe(visible).click()
        elx("//*[contains(@class, 'text-gray-dark')]").shouldHave(text("Фрилансер"))
    }

    fun goToRegistration() {
        elx("//*[@data-id = 'qa-head-registration']").shouldBe(visible).click()
    }

    fun goToAuthorization() {
        elx("//*[@data-id = 'qa-head-sign-in']").shouldBe(visible).click()
    }

    fun buttonNewCatalogCustomer() {
        elx("//*[@data-id = 'qa-head-checked-freelancers']").shouldBe(visible).click()
    }

    fun buttonNewCatalogFreelancer() {
        elx("//*[@data-id = 'qa-head-new-freelancers']").shouldBe(visible).click()
    }

    fun notificationBellClick() {
        el(By.id("fl-notify-btn")).shouldBe(visible).click()
    }

    fun checkNotificationBell(name: String) {
        elx("//*[@id = 'fl-notify-feed']//*[contains(text(), '$name')]").shouldBe(visible)
    }
}