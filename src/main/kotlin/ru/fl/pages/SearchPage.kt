package ru.fl.pages

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition
import org.openqa.selenium.By

class SearchPage: SelenideObjects(){
    fun executeSearch(order: String?) {
        el(By.id("search-request")).value = order
        el(By.id("search-button")).shouldBe(Condition.visible).click()
    }

    fun fillSearchField(valueSearch: String){
        el(By.id("search-request")).value = valueSearch
        el(By.id("search-button")).shouldBe(Condition.visible).click()
    }

    fun checkSearchSuccessfulFreel(){
        el(By.id("search-lenta")).isDisplayed
         elxs("//*[@class = 'cf-user']").shouldBe(CollectionCondition.sizeGreaterThanOrEqual(1))
    }

    fun checkSearchSuccessfulProject(){
        el(By.id("search-lenta")).isDisplayed
        elxs("//*[@class = 'number-item']").shouldBe(CollectionCondition.sizeGreaterThanOrEqual(1))
    }
}