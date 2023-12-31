package ru.fl.pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By

class AdminPanelPotok: SelenideObjects(){

    fun clickTakePotokNewCatalog(): AdminPanelPotok {
        elx("//*[@id = 'contents_26']").shouldBe(Condition.visible).click()
        return this
    }

    fun checkAll(): AdminPanelPotok {
        elx("//input[contains(@id, 'check_')]").shouldBe(Condition.visible).click()
        return this
    }

    fun clickAcceptButton(): AdminPanelPotok {
        elx("//a[not(contains(@id, 'a_mass_block')) and contains(@id, 'a_mass_')]").shouldBe(Condition.visible).click()
        return this
    }

    fun leavePotok() {
        el(By.className("b-icon_dop_close")).shouldBe(Condition.visible).click()
    }
}