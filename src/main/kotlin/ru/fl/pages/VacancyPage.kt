package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selectors
import com.codeborne.selenide.Selectors.withText
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import java.time.Duration

class VacancyPage: SelenideObjects(){
    fun clickPublishVacancy() {
        elx("//*[contains(text(), 'Опубликовать вакансию')]").shouldBe(Condition.visible).click()
    }

    fun startDiscuss() {
        elx("//*[contains(text(),'Обсудить')]").shouldBe(Condition.visible, Duration.ofSeconds(10)).click()
    }

    fun nameVacancy(name: String?) {
        el(By.id("ui-input-title")).value = name
    }

    fun chooseSpecialization() {
        elx("//*[@data-id='qa-vacancy-order-more-specialization-1']").shouldBe(Condition.visible).click()
        elx("//*[@for='ui-radio-prof_group-61']").shouldBe(Condition.visible).click()
    }

    fun chooseSkill() {
        elx("//*[@class='vs__search']").value = "пекарь"
        el(By.id("vs1__listbox")).shouldBe(Condition.visible)
        elx("//*[@class='vs__search']").sendKeys(Keys.ENTER)
    }

    fun descriptionVacancy(name: String?) {
        elx("//*[@class='ql-editor ql-blank']").value = name
    }

    fun goToWorkConditionsStep() {
        elx("//*[@data-id='qa-vacancy-next-1']").scrollTo().click()
    }

    fun setSalary(salaryFrom: String?, salaryTo: String?) {
        el(By.id("ui-input-salary-from")).value = salaryFrom
        el(By.id("ui-input-salary-to")).value = salaryTo
    }

    fun setWorkConditions() {
        elx("//*[@for='ui-radio-office']").shouldBe(Condition.visible).click()
        elx("//*[@for='ui-radio-fulltime']").shouldBe(Condition.visible).click()
        elx("//*[@for='ui-radio-salary']").shouldBe(Condition.visible).click()
    }

    fun chooseCountryAndCity() {
        el(By.id("country")).click()
        elx("//*[contains(text(),'Россия')]").scrollTo().shouldBe(Condition.visible).click()
        el(By.id("cities")).scrollIntoView(false).shouldBe(Condition.visible).click()
        elx("//*[contains(text(),'Абаза')]").scrollIntoView(false).shouldBe(Condition.visible).click()
    }

    fun setPhone() {
        elx("//*[@data-id='qa-vacancy-order-phone']").value = "+79441111111"
        el(By.id("ui-input-phone-comment")).value = "комментарий * 123456"
    }

    fun goToVacancyPublicationStep() {
        elx("//*[@data-id='qa-vacancy-next-2']").scrollTo().click()
    }

    fun goToVacancyTypeStep() {
        elx("//*[@data-id='qa-vacancy-next-3']").scrollTo().click()
    }

    fun chooseVacancyType(vacancyType: String) {
        elx("//*[@for='ui-radio-$vacancyType']").shouldBe(Condition.appear).click()
    }

    fun paymentVacancy() {
        elx("//*[contains(text(), 'Оплатить')]").scrollTo().click()
    }

    fun topUpLsClick() {
        elx("//button[contains(text(), 'Пополнить счет на')]").scrollTo().click()
    }

    fun goToVacancy(name: String?) {
        elx(String.format("//*[(contains(text(),'%s'))]", name)).shouldBe(Condition.visible).click()
    }

    fun pushVacancyOfferTab() {
        elx("//*[@data-id='manage-top-bar-tab-responses']").shouldBe(Condition.visible).click()
    }

    fun pushVacancyManageTab() {
        elx("//*[@data-id='manage-top-bar-tab-vacancy-manage-edit']").shouldBe(Condition.visible).click()
    }

    fun removeFromPublication() {
        elx("//*[@for='ui-switch-is-published']").shouldBe(Condition.visible).click()
        elx("//*[@data-id='qa-modal-reason-take-of']").shouldBe(Condition.visible).click()
    }

    fun checkVacancyPublished() {
        pushVacancyManageTab()
        elx("//*[contains(text(), 'Поиск кандидатов')]").shouldBe(Condition.visible)
        elx("//*[@id='ui-switch-is-published']").shouldBe(Condition.checked)
    }

    fun checkVacancyNotPublished() {
        pushVacancyManageTab()
        elx("//*[contains(@class, 'align-items-start')]").find(withText("Вакансия не опубликована")).shouldBe(Condition.visible)
        elx("//*[@id='ui-switch-is-published']").shouldNotBe(Condition.checked)
    }

    fun checkVacancyOffer(offerPhrase: String?) {
        el(Selectors.byText(offerPhrase)).shouldBe(Condition.visible)
    }

    fun addPinVacancyVAS() {
        elx("//*[@for = 'ui-checkbox-top']").shouldBe(Condition.visible).click()
    }

    fun addHighlightVacancyVAS() {
        elx("//*[@for = 'ui-checkbox-color']").shouldBe(Condition.visible).click()
    }

    fun addLabelUrgentVacancyVAS() {
        elx("//*[@for = 'ui-checkbox-urgent']").shouldBe(Condition.visible).click()
    }

    fun addHideVacancyVAS() {
        elx("//*[@for = 'ui-checkbox-hide']").shouldBe(Condition.visible).click()
    }

    fun checkPinVacancyVAS() {
        el(withText("Закреплена в ленте")).shouldBe(Condition.visible)
    }

    fun checkHighlightVacancyVAS() {
        el(withText("Выделена в ленте цветом")).shouldBe(Condition.visible)
    }

    fun checkLabelUrgentVacancyVAS() {
        el(withText("Выделена меткой «Срочно»")).shouldBe(Condition.visible)
    }

    fun checkHideVacancyVAS() {
        el(withText("Скрыта от поисковых систем")).shouldBe(Condition.visible)
    }

    //Переход к покупке закрепления в уже опубликованной вакансии
    fun pinVacancyVasInPublishedVacancy(){
        el(withText("Закрепить вакансию в ленте на 24 часа")).shouldBe(Condition.visible).click()
    }

    //Переход к покупке выделения цветом в уже опубликованной вакансии
    fun highlightVacancyVasInPublishedVacancy(){
        el(withText("Выделить вакансию в ленте цветом")).shouldBe(Condition.visible).click()
    }

    //Переход к покупке добавления метки "Срочно" в уже опубликованной вакансии
    fun labelUrgentVacancyVasInPublishedVacancy(){
        el(withText("Выделить меткой «Срочно»")).shouldBe(Condition.visible).click()
    }

    //Переход к покупке скрытия от поисковиков в уже опубликованной вакансии
    fun hideVacancyVasInPublishedVacancy(){
        el(withText("Скрыть вакансию от поисковых систем")).shouldBe(Condition.visible).click()
    }

    fun goToPaymentVAS() {
        elx("//button[@class = 'btn btn-primary w-100']").shouldBe(Condition.visible).click()
    }

    fun editOfferVacancy() {
        elx ("//*[@id = 'frl_edit_bar']//*[text() = 'Редактировать предложение']").shouldBe(Condition.visible).click()
    }

    fun saveChangesVacancy() {
        elx ("//button[contains(text(),'Сохранить изменения')]").shouldBe(Condition.visible).click()
    }

    fun editVacancyByAdmin() {
        elx("//a[contains(text(),'Редактировать')]").shouldBe(Condition.visible).click()
    }

    fun checkMovedVacancy() {
        elx("//span[contains(text(),'Вакансия еще не оплачена')]").shouldBe(Condition.exist)
    }

    fun selectProjectTypeAndSave() {
        el(By.id("attachedfiles_selectfile_div")).shouldBe(Condition.visible)
        el(By.id("popup_qedit_prj_fld_kind_1")).shouldBe(Condition.visible).click()
        elx("//*[@id='popup_qedit_prj_frm']//*[contains(text(), 'Сохранить изменения')]").shouldBe(Condition.visible).click()
    }

    fun goToFillFinances() {
        elx("//a[contains(text(), 'Оплатить')]").scrollTo().shouldBe(Condition.visible).click()
    }

    fun getPaymentAmount(): String{
        return elx("//*[text() = 'Итого к оплате']/following::span[2]").text.replace("[ ₽]".toRegex(), "")
    }
}