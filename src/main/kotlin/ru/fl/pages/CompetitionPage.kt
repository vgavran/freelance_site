package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selectors
import org.openqa.selenium.By

class CompetitionPage: SelenideObjects(){

    fun nameCompetition(name: String?) {
        el(By.id("project_name")).shouldBe(Condition.visible).value = name
    }

    fun createCompetition() {
        el(By.xpath("//*[contains(text(),'Опубликовать конкурс')]"))
            .shouldBe(Condition.visible).click()
    }

    fun chooseSpecializationCompetition() {
        el(By.id("project_profession0")).shouldBe(Condition.visible).click()
        elx("//*[contains(@class, 'b-shadow_zindex_4')]//*[contains(text(), 'Административная поддержка')]").shouldBe(
            Condition.visible
        ).click()
        el(By.id("project_profession0_spec")).shouldBe(Condition.visible).click()
        elx("//*[contains(@class, 'b-shadow_zindex_4')]//*[contains(text(), 'Виртуальный ассистент')]").shouldBe(
            Condition.visible
        ).click()
    }

    fun descriptionCompetition(name: String?) {
        elx("//*[@class='b-textarea__textarea b-textarea__textarea_min-height_200']").shouldBe(Condition.visible).value = name
    }

    fun calendarEndDateClick() {
        el(By.id("project_end_date")).shouldBe(Condition.visible).click()
    }

    fun calendarWinDateClick() {
        el(By.id("project_win_date")).shouldBe(Condition.visible).click()
    }

    fun setDate(date: Int) {
        elx("//*[@style = 'z-index:30']//*[@class = 'b-calendar__nextmonth']").shouldBe(Condition.visible).click()
        elx("//*[@style = 'z-index:30']//*[@class = 'b-calendar__day b-calendar__day_future']//*[text() = $date]").shouldBe(Condition.visible).click()
    }

    fun publishCompetition() {
        el(By.id("project_save_btn_sum")).shouldBe(Condition.visible).click()
    }

    fun goToCompetition(){
        el(By.linkText("Перейти к конкурсу")).shouldBe(Condition.visible).click()
    }

    fun checkSuccessfulCreateCompetition(){
        elx("//*[contains (@class, 'b-shadow_zindex_3')]//*[contains(@class, 'b-layout b-layout_pad_20')]")
            .shouldBe(Condition.visible).shouldHave(Condition.text("Конкурс опубликован"))
        elx("//*[@class = 'b-shadow__icon_close']").shouldBe(Condition.visible).click()
    }

    fun closePopUpVas() {
        elx("//*[@id='quick_prj_win_main_ok']//*[contains(text(), 'Закрыть')]").shouldBe(Condition.visible).click()
    }

    fun findCompetition(name: String?) {
        elx(String.format("//*[(contains(text(),'%s'))]", name)).shouldBe(Condition.visible).click()
    }

    fun respondCompetition() {
        el(By.id("contest-add-button")).shouldBe(Condition.visible).click()
    }

    fun addPinCompetitionVAS() {
        el(By.id("project_top_ok")).shouldBe(Condition.visible).click()
    }

    fun addHighlightCompetitionVAS() {
        el(By.id("project_color")).shouldBe(Condition.visible).click()
    }

    fun addLabelUrgentCompetitionVAS() {
        el(By.id("project_urgent")).shouldBe(Condition.visible).click()
    }

    fun addHideCompetitionyVAS() {
        el(By.id("project_hide")).shouldBe(Condition.visible).click()
    }

    fun buyVusCompetition() {
        el(By.id("project_save_btn_text")).shouldBe(Condition.visible).click()
    }

    fun checkCompetitionOffer(offerPhrase: String?) {
        el(Selectors.byText(offerPhrase)).shouldBe(Condition.visible)
    }

    fun checkPinCompetitionVas() {
        el(By.id("project_top_ok")).shouldBe(Condition.visible).shouldBe(Condition.value("1"))
    }

    fun checkHighlightCompetitionVas() {
        el(By.id("hidden_project_color")).shouldBe(Condition.visible).shouldBe(Condition.value("1"))
    }

    fun checkLabelUrgentCompetitionVas() {
        el(By.id("hidden_project_urgent")).shouldBe(Condition.visible).shouldBe(Condition.value("1"))
    }

    fun checkHideCompetitionVas() {
        el(By.id("project_hide")).shouldBe(Condition.visible).shouldBe(Condition.value("1"))
    }

    fun clickWinner() {
        elxs("//*[@alt = 'Кандидат']").first().shouldBe(Condition.visible).click()
    }

    fun determineWinner() {
        elx("//*[text() = 'Определить победителей']").shouldBe(Condition.visible).click()
    }

    fun clickDefinitedWinner() {
        el(By.id("win-1")).selectOptionContainingText("auto_freel_indi")
        elx("//*[@value='Выбрать победителя']").shouldBe(Condition.visible).click()
    }

    fun checkWinner() {
        elx("//*[@class = 'contest-end' and contains(text(), 'Победители объявлены')]").shouldBe(Condition.visible)
    }
}

