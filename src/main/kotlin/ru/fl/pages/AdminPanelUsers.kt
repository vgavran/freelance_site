package ru.fl.pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By

class AdminPanelUsers: SelenideObjects(){
    fun searchUser(userName: String?) {
        el(By.id("search_name")).sendKeys(userName)
        elx("//button[@type='submit']").shouldBe(Condition.visible).click()
    }

    fun activate() {
        el(By.id("chk_all")).click()
        elx("//button[contains(@text, 'Активировать')]").shouldBe(Condition.visible).click()
    }
}