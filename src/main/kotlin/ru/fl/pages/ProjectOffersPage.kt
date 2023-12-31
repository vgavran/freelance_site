package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.sleep
import org.openqa.selenium.By
import java.time.Duration

class ProjectOffersPage: SelenideObjects(){

    fun setDeadline() {
        el(By.id("date-picker")).shouldBe(visible).click()
        elx("//*[@class = 'vc-arrow is-right']").shouldBe(visible).click()
        val day = (1..28).random()
        val el = elxs("//*[contains(@class, 'vc-day')]//*[text() = '$day']")
        if(day <14) {
            el.first().shouldBe(visible).click()
        }
        else{
            el.last().shouldBe(visible).click()
        }
    }

    fun choosingExecutor() {
        el(By.partialLinkText("Выбрать исполнителем")).shouldBe(visible, Duration.ofSeconds(10)).shouldBe(visible).click()
    }

    fun acceptTask() {
        elx("//button[@type = 'submit']")
            .scrollTo()
            .shouldBe(visible, Duration.ofSeconds(10)).shouldBe(visible).click()
    }

    fun startDiscuss() {
        elx("//div[contains(text(), 'Обсудить')]").shouldBe(visible, Duration.ofSeconds(10)).shouldBe(visible).click()
    }

    fun publishProjectClick() {
        el(By.xpath("//*[contains(@class, 'w-100')][contains(text(),'Опубликовать заказ')]"))
            .shouldBe(visible).click()
    }

    fun suggestOrderClick() {
        el(By.id("offer-choose-executor")).shouldBe(visible, Duration.ofSeconds(10)).shouldBe(visible).click()
    }

    fun waitLoadOrder() {
        el(By.className("card-list")).shouldBe(visible, Duration.ofSeconds(10))
    }

    fun checkOfferWasSuccessfully(text : String) {
        elx("//*[@id = 'offers-card-list']//*[contains(text(), '$text')]").shouldBe(visible)
    }

    fun checkOfferBudgetAndDeadline(offerBudget: String) {
        val text = elx("//*[@id='offers-card-list']//*[contains(@class,'text-lowercase')]").shouldHave(ownText(offerBudget))
    }

    fun increaseViews() {
        elx("//*[contains(text(), 'Увеличить просмотры')]").shouldBe(visible).click()
    }

    fun selectFilterNewOffers() {
        el(By.id("vs1__combobox")).shouldBe(visible).click()
        el(By.id("vs1__option-0")).shouldBe(visible).click()
    }

    fun selectFilterInvitees() {
        el(By.id("vs1__combobox")).shouldBe(visible).click()
        el(By.id("vs1__option-2")).shouldBe(visible).click()
    }

    fun selectFilterCandidates() {
        el(By.id("vs1__combobox")).shouldBe(visible).click()
        el(By.id("vs1__option-1")).shouldBe(visible).click()
    }

    fun checkSort(nameFreel : String?) {
        elx("//*[@data-id = 'qa-offers-card-list-item-0']//*[@data-id = 'qa-offers-author-href']/a").shouldHave(ownText(nameFreel))
    }

    fun choosingOrRemoveCandidate() {
        elx("//*[@data-id = 'qa-offers-to-candidate']").shouldBe(visible).click()
    }

    fun checkFreelanserIsCandidate() {
        sleep(200)
        elx("//*[@data-id = 'qa-offers-candidate-label']").shouldBe(visible)
    }

    fun checkFreelanserIsNotCandidate() {
        sleep(200)
        elx("//*[@data-id = 'qa-offers-candidate-label']").shouldNotBe(visible)
    }

    fun removeOffer() {
        el(By.id("dropdownMenuButton")).shouldBe(visible).click()
        elx("//*[@id = 'offers-card-list']//*[contains(text(), 'Удалить отклик')]").shouldBe(visible).click()
    }

    fun moveOfferToSpam() {
        el(By.id("dropdownMenuButton")).shouldBe(visible).click()
        elx("//*[@id = 'offers-card-list']//*[contains(text(), 'Спам')]").shouldBe(visible).click()
    }

    fun checkOfferNotVisible() {
        elx("//*[@id = 'offers-card-list']//*[contains(text(), 'На этом месте появятся отклики фрилансеров')]").shouldBe(
            visible)
    }

    fun checkOfferWithPreliminaryCoast() {
        elx("//*[@id='my-offer']//*[contains(text(),'Мои условия, предварительные')]").shouldBe(visible)
    }

    fun checkOfferWithExamplesOfWork() {
        elx("//*[@data-id='qa-offers-card-list-item-0']//*[@class = 'fl-commercial__image']").shouldBe(visible)
    }

    fun getOrderId() : String {
        sleep(1000)
        elx("//*[@class = 'circle-loader']").shouldBe(Condition.disappear, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-order-card']").shouldBe(Condition.visible, Duration.ofSeconds(10))
        return elx("//*[@data-id = 'qa-card-bs-info']/span").text.substring(5)
    }

    fun buyHighlightVAS() {
        elx("//*[@data-id = 'qa-category-highlight-block']").shouldBe(visible).click()
    }

    fun upViewsClick() {
        elx("//*[@data-target = '#up-views-modal']").shouldBe(visible).click()
    }

    fun buyPinVAS() {
        elx("//*[@class = 'modal-body']//*[text() = 'Закрепить наверху']").shouldBe(visible).click()
    }

    fun checkSimpleDeal() {
        elx("//*[@id = 'step2']//h1").shouldBe(visible).shouldHave(text("Упрощенная сделка"))
        el(By.id("accounting_documents")).shouldHave(attribute("class", "d-flex align-items-baseline mt-24  opacity-3 "))
    }

    fun checkDealWithDocumentFlow() {
        elx("//*[@id = 'step2']//h1").shouldBe(visible).shouldHave(text("Сделка с документооборотом"))
        el(By.id("accounting_documents")).shouldHave(attribute("class", "d-flex align-items-baseline mt-24 "))
    }

    fun addPhoneCustomer() {
        val phone = "+7949" + (1111111..9999999).random()
        elx("//*[@id = 'modal-sms-phone']//*[@class = 'form-control']").shouldBe(visible).value = phone
        elx("//button[contains(text(), 'Получить СМС с кодом')]").shouldBe(enabled).click()
        el(By.id("input-code")).shouldBe(visible).value = "1111"
    }
}