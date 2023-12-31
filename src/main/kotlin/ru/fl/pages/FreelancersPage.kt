package ru.fl.pages

import com.codeborne.selenide.Condition

class FreelancersPage: SelenideObjects(){

    fun search(username: String?): FreelancersPage {
        elx("//*[@id = 'search-request']").shouldBe(Condition.visible).value = username
        elx("//button[@type='submit']").shouldBe(Condition.visible).click()
        return this
    }

    fun clickOnSuggestOfferBtn() {
        elx("//*[text() = 'Предложить заказ']").shouldBe(Condition.visible).click()
    }

    fun goToProfile(username: String?) {
        elx("//span[contains(text(), '$username')]").shouldBe(Condition.visible).click()
    }

    fun createProjectClick() {
        elx("//*[contains(text(), 'Разместить заказ')]").shouldBe(Condition.visible).click()
    }
}