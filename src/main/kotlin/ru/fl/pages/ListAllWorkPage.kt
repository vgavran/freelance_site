package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.visible
import org.openqa.selenium.By

class ListAllWorkPage: SelenideObjects(){

    fun checkPublishedOrderInListProjects(projectName: String?, projectId: String?) {
        elx("//*[@id = 'prj_name_$projectId']").shouldHave(Condition.text(projectName))
    }

    fun createProjectClick() {
        elx("//*[contains(text(), 'Разместить заказ')]").shouldBe(visible).click()
    }

    fun switchFilter() {
        el(By.id("tpl-filter-switch")).shouldBe(visible).click()
    }

    fun selectSpecialization(specialization: String) {
        elx("//*[@data-id = 'qa-combo-input-span']").shouldBe(visible).click()
        elxs("//*[@data-id = 'qa-combo-input']//*[text() = '$specialization']").last().shouldBe(visible).click()
        elx("//*[@data-id = 'qa-combo-input']//*[contains(text(), 'Все специальности')]").shouldBe(visible).click()
        elx("//*[@data-id = 'qa-combo-button-gray']").shouldBe(visible).click()
    }

    fun applyFilter() {
        elx("//*[@data-id = 'qa-combo-set-filter']").shouldBe(visible).click()
    }

    fun checkFiltration(specialization: String) {
        elxs("//*[contains(@id, 'prj_name')]").first().shouldBe(visible).click()
        elx("//*[contains(@id, 'project_info')]//*[contains(text(), '$specialization')]").shouldBe(visible)
    }

    fun findProject(projectName: String) {
        el(By.id("pf_keywords")).shouldBe(visible).value = projectName
    }
}