package ru.fl.pages

import com.codeborne.selenide.Condition.visible
import org.openqa.selenium.By

class FreelCatalogPage: SelenideObjects(){

    fun findFreel(login : String?) {
        el(By.id("search-request")).shouldBe(visible).value = login
        elx("//button[@type = 'submit']").shouldBe(visible).click()
    }

    fun checkFreelFind(login : String?) {
        elx("//*[@class = 'cf-user-in']//*[text() = '$login']").shouldBe(visible)
    }
}