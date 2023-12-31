package ru.fl.pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import org.openqa.selenium.By
import ru.fl.appmanager.RandomUtils.Companion.getRandomNumbers
import java.util.*

class FinanceNewPage: SelenideObjects(){

    private val fakerConfig = fakerConfig {
        locale =  "ru"
    }
    private val faker =  Faker (fakerConfig)

    fun checkVisibleFinancePage() {
        elx("//h1").shouldHave(Condition.text("Чтобы платить как юридическое лицо — заполните форму"))
    }

    fun fio(): FinanceNewPage {
        val name: String = faker.name.femaleFirstName()
        val surname: String = faker.name.femaleLastName()
        el(By.name("fio")).shouldBe(Condition.visible).value = "$surname $name Олеговна"
        return this
    }

    fun birthday(): FinanceNewPage {
        el(By.id("date-picker")).shouldBe(Condition.enabled).click()
        elx("//*[contains(@class, 'vc-title')]").shouldBe(Condition.visible).click()
        var yearAgo = (1..10).random()
        for(i in 0..yearAgo){
            elx("//*[@class='vc-nav-arrow is-left']").shouldBe(Condition.visible).click()
        }
        val calendar = Calendar.getInstance()
        val monthNames = arrayOf(
            "янв.",
            "февр.",
            "март",
            "апр.",
            "май",
            "июнь",
            "июль",
            "авг.",
            "сент.",
            "окт.",
            "нояб.",
            "дек."
        )
        val month = monthNames[calendar.get(Calendar.MONTH).plus(3)]
        elx("//*[@class='vc-nav-items']//*[contains(text(), '$month')]").shouldBe(Condition.visible).click()
        var day = (11..28).random()
        elx("//*[contains(@class, 'in-month')]//*[text() = '$day']").shouldBe(Condition.visible).click()
        return this
    }

    fun phone(): FinanceNewPage {
        val randomValues = getRandomNumbers(7)
        val phone = "+7949$randomValues"
        el(By.name("phone")).shouldBe(Condition.visible).value = phone
        return this
    }

    fun resident(): FinanceNewPage {
        elx("//*[@for = 'ru-resident']").shouldBe(Condition.visible).click()
        return this
    }

    fun notResident(): FinanceNewPage {
        elx("//*[@for = 'non-ru-resident']").shouldBe(Condition.visible).click()
        return this
    }

    fun typeEntity(): FinanceNewPage {
        //1 - ИП, 9 - ООО
        el(By.id("org_type")).shouldBe(Condition.visible).selectOptionByValue("9")
        return this
    }

    fun nameCompanyNotResident(): FinanceNewPage {
        val name: String = faker.harryPotter.spells()
        val elems: ElementsCollection = Selenide.`$$x`("//*[@name='org_name']")
        elems[1].shouldBe(Condition.visible).value = name
        return this
    }

    fun nameCompanyResident(): FinanceNewPage {
        val name: String = faker.harryPotter.spells()
        val elems: ElementsCollection = Selenide.`$$x`("//*[@name='org_name']")
        elems[0].shouldBe(Condition.visible).value = name
        return this
    }

    fun addressResident(): FinanceNewPage {
        el(By.name("legal_address")).shouldBe(Condition.visible).value = "119618, Россия, г. Москва, ул. Новая д. 23"
        el(By.name("postal_address")).shouldBe(Condition.visible).value = "119618, Россия, г. Москва, ул. Новая д. 23"
        return this
    }

    fun addressNotResident(): FinanceNewPage {
        val address = "01001, Литва, г. Вилюнюс, ул. Любая д.6 кв. 23"
        val elems: ElementsCollection = Selenide.`$$`(By.name("legal_address"))
        elems[1].shouldBe(Condition.visible).value = address
        val elems1: ElementsCollection = Selenide.`$$`(By.name("postal_address"))
        elems1[1].shouldBe(Condition.visible).value = address
        return this
    }

    fun accountNotResident(): FinanceNewPage {
        val iban: String = faker.bank.ibanDetails("LV")
        val elems: ElementsCollection = Selenide.`$$`(By.name("bank_rs"))
        elems[1].shouldBe(Condition.visible).value = iban
        return this
    }

    fun bankName(): FinanceNewPage {
        el(By.name("bank_name")).shouldBe(Condition.visible).value = "ОАО «Альфа-Банк», Москва"
        return this
    }

    fun bankCorrName(): FinanceNewPage {
        el(By.name("bank_kor_name")).shouldBe(Condition.visible).value = "Актисиасельтс СЕБ Банк, Таллин"
        return this
    }

    fun bankInn(): FinanceNewPage {
        el(By.name("bank_inn")).shouldBe(Condition.visible).value = "7728168971"
        return this
    }

    fun innEntity(): FinanceNewPage {
        val number = "1564390250"
        el(By.id("inn")).shouldBe(Condition.visible).value = number
        return this
    }

    fun innAutocomplete(): FinanceNewPage {
        val number = "7701250210"
        el(By.id("inn")).shouldBe(Condition.visible).value = number
        return this
    }

    fun kpp(): FinanceNewPage {
        val randomValues = getRandomNumbers(3)
        val number = "425357$randomValues"
        el(By.name("kpp")).shouldBe(Condition.visible).value = number
        return this
    }

    fun account(): FinanceNewPage {
        el(By.name("bank_rs")).shouldBe(Condition.visible).value = "40702810700005105496"
        return this
    }

    fun corrAccountNotResident(): FinanceNewPage {
        val elems: ElementsCollection = Selenide.`$$`(By.name("bank_ks"))
        elems[1].shouldBe(Condition.visible).value = "30111810200000000593"
        return this
    }

    fun corrAccountResident(): FinanceNewPage {
        val elems: ElementsCollection = Selenide.`$$`(By.name("bank_ks"))
        elems[0].shouldBe(Condition.visible).value = "30111810200000000593"
        return this
    }

    fun bicNotResident(): FinanceNewPage {
        el(By.name("bank_bik")).shouldBe(Condition.visible).value = "044525593"
        return this
    }

    fun bicResident(): FinanceNewPage {
        el(By.name("bik")).shouldBe(Condition.visible).value = "044525593"
        return this
    }

    fun authorizedBankAccount(): FinanceNewPage {
        el(By.name("bank_kor_ks")).shouldBe(Condition.visible).value = "30101810800000000000"
        return this
    }

    fun continueBtn(): FinanceNewPage {
        el(By.cssSelector("#text-btn")).scrollTo().click()
        return this
    }

    fun save() {
        elx("//*[text() = 'Отправить на проверку']").scrollTo().click()
    }

    fun checkFinaceUnderReview() {
        elx("//h1").shouldHave(Condition.text("Проверяем данные юридического лица"))
    }

    fun backFromFinancePage() {
        elx("//*[contains(text(), 'Назад')]").shouldBe(Condition.visible).click()
    }
}