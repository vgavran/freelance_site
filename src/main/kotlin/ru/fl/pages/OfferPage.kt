package ru.fl.pages

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.sleep
import com.codeborne.selenide.Selenide.switchTo
import com.codeborne.selenide.SelenideElement
import org.openqa.selenium.By
import org.testng.Assert.assertEquals
import java.time.Duration

class OfferPage: SelenideObjects(){

    fun startDiscussion() {
        elx("//a[contains(text(), \"Обсудить\")] ").shouldBe(visible).click()
    }

    fun comission(): Int {
        var text = elx("//*[@data-role = 'final-fee-amount']").text.toInt()
        return text
    }

    fun getInfoHaveDeal(): String {
        var text = el(By.cssSelector(".ml-md-58 > div:nth-child(1) > div:nth-child(1)")).text.trim { it <= ' ' }
        return text
    }

    fun getInfoPaidAmount(): String {
        var text = el(By.cssSelector(".ml-md-58 > div:nth-child(1) > div:nth-child(2)")).text.trim { it <= ' ' }
        return text
    }

    fun getInfoDiscount(): Int {
            val text: String = elx("//*[contains(text(), 'Текущая комиссия сервиса')]/strong").shouldBe(visible).text.replace("%", "")
            return text.toInt()
        }

    fun getInfoDiscountMin(): Int {
            val text: String = elx("//*[contains(text(), 'Вы достигли минимальной комиссии')]/strong").shouldBe(visible).text.replace("%", "")
            return text.toInt()
        }

    fun freelancerFee(cost: Int) {
        el(By.id("final-amount")).value = cost.toString()
    }

    fun getTotalCost(): Int {
        val el: SelenideElement = el(By.id("el-cost_from"))
        el.shouldBe(visible).click()
        return el.value!!.toInt()
    }

    fun checkSumComission(countRandomSum: Int) {
        for (i in 0 until countRandomSum) {
            val fee: Int = (1200..500000).random()
            freelancerFee(fee)
            sleep(2000)
            val totalCost = getTotalCost()
            val comission = comission()
            assertEquals(fee + comission, totalCost)
        }
    }

    fun offerDescription(descr: String?) {
        el(By.id("el-descr")).shouldBe(visible).value = descr
    }

    fun offerDescriptionNotAuth(descr: String?) {
        el(By.id("el-ps_text")).shouldBe(visible).value = descr
    }

    fun termOffer(term: String?) {
        el(By.id("el-time_from")).value = term
    }

    fun totalCost(cost: String?) {
        el(By.id("el-cost_from")).value = cost
    }

    fun pressAnswerButton(projectId : String) {
        elx("//*[@id = 'project_info_$projectId']//*[contains(text(), 'Ответить на проект')]")
            .shouldBe(visible).click()
    }

    fun pressSubmitAnswerButton() {
        el(By.id("el-submit")).scrollIntoView(false).shouldBe(visible).click()
    }

    fun pressSubmitAnswerButtonNotAuth() {
        elx("//*[@id = 'anonoffer']//*[contains(text(), 'Добавить')]").shouldBe(visible).click()
    }

    fun descriptionOfferVacancy(commentText: String?) {
        elx("//*[contains(@data-id, 'qa-ui-textarea')]")
            .scrollIntoView(false)
            .shouldBe(visible, Duration.ofSeconds(10))
            .value = commentText
    }

    fun descriptionOfferCompetition(comment: String?) {
        el(By.id("comment-box"))
            .scrollIntoView(false)
            .shouldBe(visible, Duration.ofSeconds(10))
            .value = comment
    }

    fun submitOfferVacancy() {
        elx("//button[contains(text(), 'Откликнуться')]").shouldBe(visible).click()
    }

    fun submitOfferCompetition() {
        el(By.id("offer_submit")).shouldBe(visible).click()
    }

    fun saveOfferChanges() {
        elx("//button[contains(text(), 'Сохранить изменения')]").shouldBe(visible).click()
    }

    fun closeModalAcceptPhone() {
        elx("//button[@aria-label = 'Закрыть']").shouldBe(visible).click()
    }

    fun checkNeedBuyPRO() {
        el(By.id("project_answer_popup")).shouldBe(visible)
    }

    fun checkSaveOffer() {
        elx("//*[@class = 'modal-body']//*[contains(text(), 'Ваш отклик сохранен, но скрыт от заказчика')]").shouldBe(visible)
    }

    fun clickBuyPRO() {
        elx("//*[@class = 'modal-body']//*[contains(text(), 'Купить аккаунт PRO')]").shouldBe(visible).click()
    }

    fun closeModalOfferSaving() {
        elx("//*[@class = 'modal-body']//*[contains(text(), 'Куплю потом')]").shouldBe(visible).click()
    }

    fun preliminaryCost() {
        el(By.id("el-is_final_cost-0")).shouldBe(visible).click()
    }

    fun checkOfferRejectByCustomer() {
        elx("//*[@id = 'my-offer']//*[contains(text(), 'Вы получили отказ')]").shouldBe(visible)
    }

    fun cancelOffer() {
        elx("//*[@data-id = 'qa-project-cancel-order']").shouldBe(visible).click()
        Selenide.switchTo().alert().accept();
    }

    fun checkCancelOfferByFreelancer() {
        elx("//*[@id = 'my-offer']//*[contains(text(), 'Вы отказались от заказа')]").shouldBe(visible)
    }

    fun addWorkExamples() {
        elx("//*[@id='work_link']//*[contains(text(),'Добавить примеры работ')]").scrollTo().shouldBe(visible).click()
    }

    fun selectExamplesOfWork() {
        elx("//input[@id='ps_portfolio_work_1589451']").shouldBe(visible).click()
    }

    fun uploadSampleOfWork() {
        switchTo().innerFrame("fupload")
        elx("//*[@name = 'ps_attach']").sendKeys("./passport.jpeg")
    }

    fun pressUpload() {
        el(By.id("ps_pict_add")).shouldBe(visible).click()
        switchTo().defaultContent()
        elx("//*[@id = 'td_pic_1' and @class = 'pic']").shouldBe(visible)
    }

    fun checkFileIsLoaded(){
        elx("//*[@id = 'my-offer']//*[contains(@title, 'фото')]").shouldBe(visible)
    }

    fun getOfferId(): String? {
        return elx("//*[contains(@id, 'project-offer-block')]").getAttribute("id").toString().replace("project-offer-block-", "")
    }
}