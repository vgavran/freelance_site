package ru.fl.pages

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.refresh
import com.codeborne.selenide.WebDriverRunner
import org.openqa.selenium.By
import org.testng.Assert.assertEquals
import java.time.Duration
import java.util.*


class ProjectPage: SelenideObjects(){

    fun createProject() {
        el(By.xpath("//*[contains(text(),'Опубликовать заказ')]"))
            .shouldBe(visible).click()
    }

    fun nameProject(projectName: String?) {
        el(By.xpath("//h3[contains(text(),'Что нужно сделать?')]")).shouldBe()
        el(By.id("ui-input-project-title-input")).shouldBe(visible).value =
            projectName
    }

    fun setCategoryProject() {
        el(By.xpath("//p[contains(text(),'Разработка сайтов')]")).shouldBe(visible).click()
        el(By.xpath("//*[@for = 'ui-radio-prof_group-84']")).shouldBe(visible).click()
        el(By.xpath("//div[@class='d-inline check-green']//*[local-name()='svg']")).shouldBe()
    }

    fun setCategoryProject(category: String?, subcategory: String?) {
        el(By.xpath(String.format("//p[contains(text(),'%s')]", category))).shouldBe(visible).click()
        el(By.xpath(String.format("//*[contains(text(),'%s')]", subcategory))).shouldBe(visible).click()
        el(By.xpath("//div[@class='d-inline check-green']//*[local-name()='svg']")).shouldBe()
    }

    fun goToStepTwo() {
        el(By.id("qa-project-wizard-step-1-button-next")).scrollTo().shouldBe(visible).click()
    }

    fun descriptionProject(projectDescription: String?) {
        elx("//h3[contains(text(),'Подробное описание')]").shouldBe()
        elx("//*[@data-id = 'qa-ui-textarea-input-message']").value = projectDescription
    }

    fun descriptionProject() {
        elx("//h3[contains(text(),'Подробное описание')]").shouldBe()
        elx("//*[@data-id = 'qa-ui-textarea-input-message']").value = "Какое-то описание заказа"
    }

    fun goToStepThree() {
        el(By.id("qa-project-wizard-step-2-button-next")).scrollTo().shouldBe(visible).click()
    }

    fun setBudget(budget: String?) {
        elx("//h3[contains(text(),'Бюджет и срок')]").shouldBe(visible)
        elx("//input[@id='ui-input-project-order-budget-input']").`val`(budget)
    }

    fun setNoBudget() {
        elx("//h3[contains(text(),'Бюджет и срок')]").shouldBe(visible)
        elx("//*[@for = 'ui-checkbox-budge-check']").shouldBe(visible).click()
    }


    fun setDeadline(deadline: String?) {
        el(By.id("date-picker")).shouldBe(visible).click()
        val calendar = Calendar.getInstance()
        val monthNames = arrayOf(
            "январь",
            "февраль",
            "март",
            "апрель",
            "май",
            "июнь",
            "июль",
            "август",
            "сентябрь",
            "октябрь",
            "ноябрь",
            "декабрь"
        )
        val month = monthNames[calendar.get(Calendar.MONTH).plus(1)]
        val monthInPicker = elx("//*[@class = 'vc-title']").ownText.replace(Regex("[^/а-яёА-ЯЁ]"), "")
        if (month != monthInPicker) {
            elxs("//*[@id='finish-date-block']//*[@class = 'vc-svg-icon']").last().shouldBe(visible).click()
        }
        elx("//*[@id='finish-date-block']//*[text() = '$deadline']").shouldBe(visible).click()
    }

    fun setNoDeadline() {
        elx("//*[@for = 'ui-checkbox-project-order-date-check']").shouldBe(visible).click()
    }

    fun goToStepFour() {
        elx("//*[@data-id = 'qa-project-next-3']").shouldBe(visible).click()
    }

    fun publicationProjectWizard() {
        el(By.id("qa-project-wizard-step-4-button-next")).shouldBe(visible).click()
    }

    fun publicationProject() {
        el(By.id("vas-button")).shouldBe(visible).click()
    }

    fun checkBlockPublicationWithoutPRO() {
        elx("//*[contains(text(), 'Чтобы снять ограничения')]").shouldBe(visible)
    }

    fun checkButtonBuyPRO() {
        elx("//*[contains(text(), 'Купить аккаунт PRO')]").shouldBe(visible)
    }

    fun payPublicationProject() {
        elx("//button[contains(text(), 'Опубликовать за 199')]").shouldBe(visible).click()
    }

    fun disableCheckboxForAll() {
        elx("//*[@for = 'checkbox-input-vas-forall']").shouldBe(visible).click()
    }

    fun goToOrder() {
        elxs("//*[contains(text(), 'Перейти в заказ')]").last().shouldBe(visible).click()
    }

    fun getProjectId() : String {
        var id = el(By.id("take-care-close")).getAttribute("data-id")
        return id.toString()
    }

    fun checkNeedConfirmEmail() {
        elx("//*[@for = 'closed']").shouldHave(text("Заказ не опубликован"))
        elx("//*[contains(text()[2], 'Для отправки заказа на проверку нужно подтвердить вашу эл. почту')]").shouldBe(visible)
    }

    fun checkSentModeration() {
        elx("//*[@for = 'closed']/span").shouldHave(text("Заказ на проверке"))
        el(By.id("fl-wait-time-title")).shouldHave(text("Максимальное время ожидания модерации — 59 минут с момента отправки заказа на проверку"))
    }

    fun checkPublishedProject() {
        refresh()
        elx("//*[@for = 'closed']").shouldHave(text("Заказ опубликован"))
    }

    fun checkClosedProject(){
        refresh()
        elx("//*[@for = 'closed']").shouldHave(text("Заказ не опубликован"))
    }

    fun checkBudget(expectedBudget: String?) {
        val actualBudget: String? = elx("//*[@name = 'cost']").value
        assertEquals(actualBudget, expectedBudget)
    }

    fun checkMinLimitBudget() {
        elx("//*[@data-id='qa-valid-error-project-order-budget-input']").shouldBe(visible)
            .shouldHave(text("Минимальный бюджет 1200"))
    }

    fun checkNoBudget() {
        val actualBudget: String? = elx("//*[@name = 'cost']").value
        assertEquals(actualBudget, "0")
    }

    fun checkNoDeadline() {
        val actualDate: String? = elx("//*[@id = 'date-picker']").text
        assertEquals(actualDate, "Дедлайн")
    }

    fun publicationSameProject() {
        elx("//*[text() = 'Опубликовать аналогичный заказ']").shouldBe(visible).click()
    }

    fun moveToVacancy() {
        elx("//a[contains(text(),'Сделать вакансией')]").shouldBe(visible).click()
        WebDriverRunner.driver().switchTo().alert().accept()
    }

    fun checkMovedToProject() {
        elx("//a[contains(text(),'Отклики по заказу')]").shouldBe(visible)
    }

    fun clickOnProjectStatusToggle() {
        refresh()
        elx("//*[@for = 'closed']").shouldBe(visible).click()
    }

    fun addAttachedFile() {
        elx("//*[@data-id = 'file']").sendKeys("./passport.jpeg")
        elx("//*[@data-id = 'qa-file-uploader-open-upload-file']").shouldBe(visible, Duration.ofSeconds(10))
    }

    fun checkFileIsAttached() {
        elx("//*[contains(@class, 'b-icon_attach_jpeg')]").shouldBe(visible)
    }

    fun goToOffersTab() {
        el(By.id("offers-count")).shouldBe(visible).click()
    }
}