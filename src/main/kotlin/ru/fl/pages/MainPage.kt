package ru.fl.pages

import com.codeborne.selenide.Condition

class MainPage: SelenideObjects(){
    fun linkNewCatalog() {
        elx("//*[text() = 'Проверенные фрилансеры']").shouldBe(Condition.visible).click()
    }

    fun createProjectClick() {
        elx("//*[@data-id = 'qa-main-page-button-1']").shouldBe(Condition.visible).click()
    }
}