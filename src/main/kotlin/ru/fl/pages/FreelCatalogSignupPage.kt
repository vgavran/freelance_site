package ru.fl.pages

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.sleep
import org.openqa.selenium.By
import ru.fl.appmanager.RandomUtils.Companion.getRandomText
import java.time.Duration


class FreelCatalogSignupPage: SelenideObjects(){

    fun aboutFreel(textLength: Int): FreelCatalogSignupPage {
        val text: String = getRandomText(textLength)
        el(By.id("about_me")).value = text
        return this
    }

    fun hourlyRate(hourlyRate : String?): FreelCatalogSignupPage {
        elx("//input[@name='cost_hour']").value = hourlyRate
        return this
    }

    fun selectCountry(country: String?): FreelCatalogSignupPage {
        sleep(1500)
        el(By.id("filterCountry")).selectOption(country)
        return this
    }

    fun selectCity(city: String?): FreelCatalogSignupPage {
        sleep(1500)
        el(By.id("filterCity")).selectOption(city)
        return this
    }

    fun setChekboxes() {
        elx("//label[@for='checkbox-18-plus']").scrollTo().shouldBe(visible).click()
        elx("//label[@for='checkbox-document-approved']").scrollTo().shouldBe(visible).click()
    }

    fun saveBtnClick() {
        el(By.id("button-catalog-profile-save")).scrollTo().shouldBe(visible).click()
    }

    fun checkSuccessSaveForm() {
        elx("//h1").shouldHave(text("Ваш профиль не опубликован"))
        elx("//button[contains(text(), 'Подтвердить личность')]").shouldBe(visible)
    }

    fun closeSumSub() {
        elx("//button[@aria-label = 'Close']").shouldBe(visible).click()
    }

    fun checkSumSumModal() {
        el(By.id("modal-sumsub")).shouldBe(visible, Duration.ofSeconds(10))
    }

    fun checkStatusSuccessConfirmProfil() {
        elx("//h1").shouldHave(text("Ваш профиль опубликован"))
    }

    fun checkEditedCityInProfil(city: String) {
        elx("//div[contains(text(), '$city')]").shouldBe(visible)
    }

    fun setStatusSumsab(status: String) {
        el(By.id("identity_verify_status")).selectOptionByValue(status)
        elx("//button[contains(text(),'Установить статус проверки анкеты')]").shouldBe(visible).click()
    }

    fun checkStatusOnModerationProfil() {
        elx("//h1").shouldHave(text("Ваш профиль будет опубликован после проверки"))
        elx("//div[contains(text(), 'Обычно на это требуется 40–60 минут')]").shouldBe(visible)
        elx("//a[contains(text(), 'Перейти в раздел')]").shouldBe(visible)
    }

    fun checkStatusCanselBySumsub() {
        elx("//h1").shouldHave(text("Ваш профиль не опубликован"))
        elx("//button[contains(text(), 'Пройти повторную проверку')]").shouldBe(visible)
        elx("//a[contains(text(), 'Перейти в раздел')]").shouldBe(visible)
    }

    fun checkStatusCanselFinallyBySumsub() {
        elx("//h1").shouldHave(text("Ваш профиль не опубликован"))
        elx("//div[contains(text(), 'Не удалось подтвердить личность')]").shouldBe(visible)
        elx("//a[contains(text(), 'Перейти в раздел')]").shouldBe(visible)
    }

    fun checkStatusCanselByModeratorWithComment(modComment: String) {
        elx("//h1").shouldHave(text("Ваш профиль не опубликован"))
        elx("//div[contains(text(), 'Комментарий службы поддержки')]").shouldBe(visible)
        elx("//div[@class='text-3 mt-16']").shouldHave(text(modComment))
        elx("//a[contains(text(), 'Редактировать анкету')]").shouldBe(visible)
        elx("//a[contains(text(), 'Перейти в раздел')]").shouldBe(visible)
    }

}