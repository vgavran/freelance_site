package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import org.openqa.selenium.By
import org.testng.Assert
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


class ProjectManagePage: SelenideObjects(){

    fun editNameProject(textName: String?) {
        el(By.name("name")).shouldBe(visible).value = textName
    }

    fun editCategory(category: String, subcategory: String) {
        el(By.id("prof_groups")).selectOptionContainingText(category)
        el(By.id("professions")).selectOptionContainingText(subcategory)
    }

    fun editDescriptionProject(textDescription: String?) {
        el(By.name("descr")).value = textDescription
    }

    fun editBudget(textBudget: String?) {
        el(By.name("cost")).clear()
        el(By.name("cost")).value = textBudget
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
            elxs("//*[@class = 'vc-arrow is-right']").last().shouldBe(visible).click()
        }
        elx("//*[contains(@class, 'in-month')]//*[text() = '$deadline']").shouldBe(visible).click()
    }

    fun saveChanges() {
        elx("//button[contains(text(), 'Сохранить изменения')]").scrollTo().shouldBe(visible).click()
    }

    fun checkEditedProject(editedName: String?, description: String?) {
        el(By.name("name")).shouldHave(Condition.value(editedName))
        el(By.name("descr")).shouldHave(Condition.value(description))
    }

    fun checkEditedSpecialization(editedCategory: String, editedSubcategory: String) {
        elx("//*[@data-id = 'prof_groups']//*[@class = 'filter-option-inner-inner']").shouldHave(
            Condition.text(
                editedCategory
            )
        )
        elx("//*[@data-id = 'professions']//*[@class = 'filter-option-inner-inner']").shouldHave(
            Condition.text(
                editedSubcategory
            )
        )
    }

    fun checkDeadline(day: String) {
        var year = ""
        val date = LocalDate.now().plusMonths(1).month
        year = if (date.toString() == "JANUARY") {
            LocalDate.now().plusYears(1).year.toString()
        } else {
            LocalDate.now().year.toString()
        }
        val month = date.getDisplayName(TextStyle.FULL, Locale("ru"))
        val expectedDate = "$day $month $year"
        val actualDate = Selenide.`$x`("//*[@id = 'date-picker']").text
        Assert.assertEquals(actualDate, expectedDate)
    }

    fun buyForEveryoneVAS() {
        el(By.id("fl-form-sidebar-open-order")).shouldBe(visible).click()
        elx("//*[@id = 'purchase-for-all']//button[contains(text(), 'Оплатить картой')]").shouldBe(visible).click()
    }

    fun addAttachedFile() {
        elx("//*[@id = 'file' and @type = 'file']").sendKeys("./passport.jpeg")
    }

    fun checkFileIsAttached() {
        elx("//*[@class = 'fl-upload-description']").shouldBe(visible)
    }

    fun deleteAttachedFile() {
        elx("//*[@class = 'fl-upload-close']").shouldBe(visible).click()
        elx("//*[@class = 'ui-modal-content']//button[contains(text(), 'Удалить')]").shouldBe(visible).click()
    }

    fun checkFileIsDeleted() {
        elx("//*[@class = 'fl-upload-description']").shouldNotBe(exist)
    }

    fun buyVasForEveryone(){
        elx("//*[@data-id='qa-category-order-block']").shouldBe(visible).click()
    }
}