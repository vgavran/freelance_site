package ru.fl.pages

import com.codeborne.selenide.Condition
import org.openqa.selenium.By

class ProjectRecommendationPage : SelenideObjects() {
    fun sendInvitation(nameFreel: String?) {
        elx("//*[@class = 'cards']//*[contains(text(), '$nameFreel')]").scrollIntoView(false).click()
        elx("//*[@class = 'ui-modal-content']//button[contains(text(), 'Пригласить откликнуться')]").shouldBe(Condition.visible).click()
    }

    fun checkInvitationSuccessfullySent() {
        elx("//*[@class = 'ui-modal-content']//*[contains(@class, 'text-success')]").shouldBe(
            Condition.visible)
    }

    fun closeModalUserProfile() {
        el(By.id("button-close")).shouldBe(Condition.visible).click()
    }
}