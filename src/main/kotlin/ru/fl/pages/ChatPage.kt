package ru.fl.pages

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.sleep
import com.codeborne.selenide.SelenideElement
import org.openqa.selenium.By
import org.openqa.selenium.support.Color
import org.testng.Assert
import org.testng.Assert.assertTrue
import java.time.Duration

class ChatPage: SelenideObjects(){

    fun sendMessage(messageText: String) {
        elx("//div[contains(text(), 'Сегодня')]").shouldBe(visible, Duration.ofSeconds(10))
        elx("//*[@data-id = 'qa-ui-textarea-message-chat']").click()
        elx("//*[@data-id = 'qa-ui-textarea-message-chat']").value = messageText
        elx("//*[@data-id = 'qa-button-send']").shouldBe(visible, Duration.ofSeconds(10)).click()
    }

    fun inputCode() {
        el(By.id("input-code")).shouldBe(visible).value = "1111"
    }

    fun closeChat() {
        elx("//*[@class = 'ui-modal-close']").shouldBe(visible).click()
    }

    fun goToChangeCost() {
        elx("//*[@data-id = 'qa-chat-change-term']").shouldBe(visible).click()
    }

    fun checkVisibleButtonInChat(button: String?) {
        el(By.linkText(button)).shouldBe(visible)
    }

    fun goToSelectExecutor() {
        elx("//*[@data-id = 'qa-chat-choose-executor']").shouldBe(visible).click()
    }

    fun takeOrderToWork() {
        elx("//*[@data-id = 'qa-chat-get-order']").shouldBe(visible).click()
    }

    fun checkSuccessfulReserve() {
        elx("//span[contains(text(), 'Заказчик успешно зарезервировал сумму')]")
            .shouldBe(visible, Duration.ofSeconds(10))
    }

    fun checkSuccessfulTakeToWork() {
        elx("//span[contains(text(), 'Исполнитель подтвердил заказ')]").shouldBe(visible, Duration.ofSeconds(10))
    }

    fun checkNotVisibleButton(button: String) {
        elx("//div[contains(text(), '$button')]").shouldNotBe(visible, Duration.ofSeconds(1))
    }

    fun checkVisibleChat(nameChat: String) {
        elx("//*[@class = 'circle-loader']").shouldBe(disappear, Duration.ofSeconds(10))
        sleep(1000)
        elx("//div[contains(text(), '$nameChat' )]").shouldBe(visible)
    }

    fun checkNotVisibleChat(nameChat: String) {
        elx("//*[@class = 'circle-loader']").shouldBe(disappear, Duration.ofSeconds(10))
        elx("//div[contains(text(), '$nameChat' )]").shouldNotBe(visible)
    }

    fun enterChat(name: String) {
        elx("//*[@data-id = 'qa-chat-card-title' and contains(text(), '$name')]").shouldBe(visible).click()
    }

    fun checkVisibleDurationAndDescription() {
        elx("//*[@id='duration-description']").isDisplayed
    }

    fun checkVisibleDealNumber() {
        elx("//*[@id='deal']").isDisplayed
    }

    fun colorBackground(color: String): Boolean {
        return color == "#fff59e"
    }

    fun checkMessageFromArbitr(arbitrMessage: String) {
        elx("//b[contains(text(), 'Исполнитель  обратился в Арбитраж')]").isDisplayed
        elx("//span[contains(text(), \'$arbitrMessage\' )]").isDisplayed
    }

    fun checkServiseMessage(serviceMessage: String) {
        val element: SelenideElement = elx("//div[contains(text(), \'$serviceMessage\' )]").shouldBe(visible)
        val color: String = element.getCssValue("background-color")
        val hex = Color.fromString(color).asHex()
        assertTrue(colorBackground(hex), "\u001B[31mTest failed. Color background is not yellow.\u001B[0m")
    }
    
    fun checkMessageSent(messageText: String){
        elx("//*[contains(@class, 'fl-messages')]//*[text() = '$messageText']").shouldBe(visible)
    }

    fun sendPersonalMessageToFreel(messageText: String) {
        elx("//*[contains(text(), 'оставить сообщение')]").shouldBe(visible).click()
        el(By.id("msg")).value = messageText
        el(By.id("btn")).click()
    }

    fun sendPersonalMessageToCustomer(messageText: String) {
        elx("//*[contains(text(), 'Оставить сообщение')]").shouldBe(visible).click()
        el(By.id("msg")).value = messageText
        el(By.id("btn")).click()
    }

    fun findChatWithUser(chatName: String?) {
        elx("//*[@data-id = 'qa-chat-list-item-$chatName']").shouldBe(visible).click()
    }

    fun setFilterOrder() {
        elx("//*[@for = 'kind_project']").shouldBe(visible).click()
    }

    fun setFilterVacancy() {
        elx("//*[@for = 'kind_vacancy']").shouldBe(visible).click()
    }

    fun setFilterNotSafeDeal() {
        elx("//*[@for = 'deal_unsafe']").shouldBe(visible).click()
    }

    fun setFilterDealFromOrder() {
        elx("//*[@for = 'type_project,deal_safe']").shouldBe(visible).click()
    }

    fun setFilterDirectOrder() {
        elx("//*[@for = 'type_personal,deal_safe']").shouldBe(visible).click()
    }

    fun checkUnreadMessages(countMessagesExpect: Int, name: String, offerId: String) {
        elxs("//*[@data-id = 'qa-chat-card-title']").first().shouldBe(visible).shouldHave(text(name))
        val countMessagesActual = elx("//*[@data-id = 'qa-chat-list-item-$offerId']//*[@class = 'text-white']").shouldBe(
            visible).text.replace("[^0-9]".toRegex(), "").toInt()
        Assert.assertEquals(countMessagesExpect, countMessagesActual)
    }
}