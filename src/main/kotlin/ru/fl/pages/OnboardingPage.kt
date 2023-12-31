package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.attribute
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import org.openqa.selenium.By
import java.time.Duration

class OnboardingPage: SelenideObjects(){

    private val fakerConfig = fakerConfig {
        locale =  "ru"
    }
    private val faker =  Faker (fakerConfig)

    fun cancelRegistration() {
        elx("//*[contains(text(), 'Ваш уровень экспертизы')]").shouldBe(Condition.visible)
        el(By.className("navbar-logo")).shouldBe(Condition.visible).click()
        elx("//*[contains(text(), 'Зарегистрируюсь потом')]").shouldBe(Condition.visible).click()
    }

    fun freelLvl() {
        elx("//*[contains(text(), 'Средний')]").shouldBe(Condition.visible).click()
        elx("//*[contains(text(), 'Продолжить')]").shouldBe(Condition.visible).click()
    }

    fun name() {
        val name: String = faker.name.firstName()
        elx("//*[@class = 'circle-loader']").shouldBe(Condition.disappear, Duration.ofSeconds(10))
        el(By.id("firstName")).value = name
    }

    fun surname() {
        val surname: String = faker.name.lastName()
        elx("//*[@class = 'circle-loader']").shouldBe(Condition.disappear, Duration.ofSeconds(10))
        el(By.id("surname")).value = surname
    }

    fun aboutFreel() {
        val description = faker.lorem.supplemental()
        elx("//*[@class = 'circle-loader']").shouldBe(Condition.disappear, Duration.ofSeconds(10))
        el(By.id("aboutMe")).value = description
    }

    fun country() {
        el(By.id("vs1__combobox")).shouldBe(Condition.visible).click()
        el(By.id("vs1__option-1")).shouldBe(Condition.visible).click()
    }

    fun city() {
        el(By.id("vs2__combobox")).shouldBe(Condition.visible).click()
        el(By.id("vs2__option-1")).shouldBe(Condition.visible).click()
    }

    fun goToStepThree() {
        elx("//*[contains(text(), 'Продолжить')]").shouldBe(Condition.visible).click()
    }

    fun category() {
        elx("//*[@for = 'post-2']").shouldBe(Condition.visible).click()
    }

    fun subcategory() {
        elx("//*[@for = 'ui-radio-prof_group-9']").shouldBe(Condition.visible).click()
    }

    fun skills() {
        elx("//*[@data-id = 'qa-skills-search']/.//input").value = "Java"
        elx("//*[@placeholder = 'Название навыка']")
            .shouldHave(attribute("aria-activedescendant"), Duration.ofSeconds(10)).pressEnter()
    }

    fun submit() {
        el(By.cssSelector(".wizard-btn")).shouldBe(Condition.visible).click()
    }
}