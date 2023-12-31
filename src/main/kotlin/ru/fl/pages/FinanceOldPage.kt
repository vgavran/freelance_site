package ru.fl.pages

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import org.openqa.selenium.By
import ru.fl.appmanager.RandomUtils
import ru.fl.appmanager.RandomUtils.Companion.getRandomNumbers
import java.time.Duration

class FinanceOldPage: SelenideObjects(){

    private val fakerConfig = fakerConfig {
        locale =  "ru"
    }
    private val faker =  Faker (fakerConfig)

    fun fio(): FinanceOldPage {
        val surname: String = faker.name.femaleFirstName()
        val name: String = faker.name.femaleLastName()
        if (el(By.id("i_1_fio")).isDisplayed) {
            el(By.id("i_1_fio")).value = "$surname $name Евгеньевна"
        } else if (el(By.id("i_2_fio")).isDisplayed) {
            el(By.id("i_2_fio")).value = "$surname $name Захаровна"
        }
        return this
    }

    fun birthday(): FinanceOldPage {
        if (el(By.id("i_1_birthday")).isDisplayed) {
            el(By.id("i_1_birthday")).sendKeys("21.08.1984")
        } else if (el(By.id("i_2_birthday")).isDisplayed) {
            el(By.id("i_2_birthday")).sendKeys("29.04.1975")
        }
        return this
    }

    fun phone(): FinanceOldPage {
        val randomValues = RandomUtils.getRandomNumbers(7)
        val phone = "+7935$randomValues"
        if (el(By.id("i_1_phone")).isDisplayed) {
            el(By.id("i_1_phone")).value = phone
        } else if (el(By.id("i_2_phone")).isDisplayed) {
            el(By.id("i_2_phone")).value = phone
        }
        return this
    }

    fun resident(): FinanceOldPage {
        el(By.id("_rt2")).shouldBe(visible).click()
        return this
    }

    fun notResident(): FinanceOldPage {
        el(By.id("_rt3")).shouldBe(visible).click()
        return this
    }

    fun individual(): FinanceOldPage {
        el(By.id("status-fiz")).shouldBe(visible).click()
        return this
    }

    fun entity(): FinanceOldPage {
        el(By.id("status-ip")).shouldBe(visible).click()
        return this
    }

    fun typeEntity(): FinanceOldPage {
        //2 - ИП, 9 - ООО
        el(By.id("i_2_type")).selectOptionByValue("9")
        return this
    }

    fun nameCompany(): FinanceOldPage {
        val name: String = faker.harryPotter.spells()
        el(By.id("i_2_full_name")).value = name
        return this
    }

    fun passportSeries(): FinanceOldPage {
        el(By.id("i_1_idcard_ser")).value = "4520"
        return this
    }

    fun passportSeriesNotResident(): FinanceOldPage {
        el(By.id("i_1_idcard_ser")).value = "UKR"
        return this
    }

    fun passportNumber(): FinanceOldPage {
        val number = getRandomNumbers(6)
        el(By.id("i_1_idcard")).value = number.toString()
        return this
    }

    fun passportNumberNotResident(): FinanceOldPage {
        val number = getRandomNumbers(6)
        el(By.id("i_1_idcard")).value = number.toString()
        return this
    }

    fun passportDate(): FinanceOldPage {
        el(By.id("i_1_idcard_from")).sendKeys("03.09.2013")
        return this
    }

    fun passportBy(): FinanceOldPage {
        el(By.id("i_1_idcard_by")).value = "Отделением УФМС ЗАО г. Москвы"
        return this
    }

    fun passportByNotResident(): FinanceOldPage {
        el(By.id("i_1_idcard_by")).value = "UKR"
        return this
    }

    fun passportCode(): FinanceOldPage {
        el(By.id("i_1_idcard_cod")).value = "770-071"
        return this
    }

    fun addressResident(): FinanceOldPage {
        val address: String = "119618, Россия, г. Москва, ул. Новая д. 23"
        if (el(By.id("i_1_address_reg")).isDisplayed) {
            el(By.id("i_1_address_reg")).value = address
        }
        if (el(By.id("i_1_address")).isDisplayed) {
            el(By.id("i_1_address")).value = address
        }
        if (el(By.id("i_2_address_jry")).isDisplayed) {
            el(By.id("i_2_address_jry")).value = address
        }
        if (el(By.id("i_2_address")).isDisplayed) {
            el(By.id("i_2_address")).value = address
        }
        return this
    }

    fun addressNotResident(): FinanceOldPage {
        val address = "01001, Литва, г. Вилюнюс, ул. Любая д.6 кв. 23"
        if (el(By.id("i_1_address")).isDisplayed) {
            el(By.id("i_1_address")).value = address
        }
        if (el(By.id("i_1_address_reg")).isDisplayed) {
            el(By.id("i_1_address_reg")).value = address
        }
        if (el(By.id("i_2_address_jry")).isDisplayed) {
            el(By.id("i_2_address_jry")).value = address
        }
        if (el(By.id("i_2_address")).isDisplayed) {
            el(By.id("i_2_address")).value = address
        }
        return this
    }

    fun innIndividual(): FinanceOldPage {
        val number = "809234449512"
        el(By.id("i_1_inn")).value = number
        return this
    }

    fun innEntity(): FinanceOldPage {
        val number = "1564390250"
        el(By.id("i_2_inn")).value = number
        return this
    }

    fun kpp(): FinanceOldPage {
        val number = "425365" + getRandomNumbers(3)
        el(By.id("i_2_kpp")).value = number
        return this
    }

    fun yooMoneyNumber(): FinanceOldPage {
        el(By.id("i_1_el_yd")).value = "4100123456789"
        return this
    }

    fun cardNumber(): FinanceOldPage {
        val number: String = faker.business.creditCardNumbers().replace("-", "")
        el(By.id("i_1_el_ccard")).value = number
        return this
    }

    fun accountEntity(): FinanceOldPage {
        el(By.id("i_2_bank_rs")).value = "40702810700005105496"
        return this
    }

    fun accountNotResident(): FinanceOldPage {
        val iban: String = faker.bank.ibanDetails("LV")
        if (el(By.id("i_1_bank_rs")).isDisplayed) {
            el(By.id("i_1_bank_rs")).value = iban
        } else if (el(By.id("i_2_bank_rs")).isDisplayed) {
            el(By.id("i_2_bank_rs")).value = iban
        }
        return this
    }

    fun bankName(): FinanceOldPage {
        el(By.id("i_1_bank_name")).value = "Актисиасельтс СЕБ Банк, Таллин"
        return this
    }

    fun bankRfName(): FinanceOldPage {
        if (el(By.id("i_1_bank_rf_name")).isDisplayed) {
            el(By.id("i_1_bank_rf_name")).value = "ОАО «Альфа-Банк», Москва"
        } else if (el(By.id("i_2_bank_rf_name")).isDisplayed) {
            el(By.id("i_2_bank_rf_name")).value = "ОАО «Альфа-Банк», Москва"
        }
        return this
    }

    fun corrAccount(): FinanceOldPage {
        if (el(By.id("i_1_bank_rf_ks")).isDisplayed) {
            el(By.id("i_1_bank_rf_ks")).value = "30111810200000000593"
        } else if (el(By.id("i_2_bank_rf_ks")).isDisplayed) {
            el(By.id("i_2_bank_rf_ks")).value = "30111810200000000593"
        } else if (el(By.id("i_2_bank_ks")).isDisplayed) {
            el(By.id("i_2_bank_ks")).value = "30111810200000000593"
        }
        return this
    }

    fun bic(): FinanceOldPage {
        val bic = "044525593"
        if (el(By.id("i_2_bank_bik")).isDisplayed) {
            el(By.id("i_2_bank_bik")).value = bic
        } else if (el(By.id("i_1_bank_rf_bik")).isDisplayed) {
            el(By.id("i_1_bank_rf_bik")).value = bic
        } else if (el(By.id("i_2_bank_rf_bik")).isDisplayed) {
            el(By.id("i_2_bank_rf_bik")).value = bic
        }
        return this
    }

    fun bankRfInn(): FinanceOldPage {
        if (el(By.id("i_1_bank_rf_inn")).isDisplayed) {
            el(By.id("i_1_bank_rf_inn")).value = "7728168971"
        } else if (el(By.id("i_2_bank_rf_inn")).isDisplayed) {
            el(By.id("i_2_bank_rf_inn")).value = "7728168971"
        }
        return this
    }

    fun authorizedBankAccount(): FinanceOldPage {
        if (el(By.id("i_1_bank_kor_ks")).isDisplayed) {
            el(By.id("i_1_bank_kor_ks")).value = "30101810800000000000"
        } else if (el(By.id("i_2_bank_kor_ks")).isDisplayed) {
            el(By.id("i_2_bank_kor_ks")).value = "30101810800000000000"
        }
        return this
    }

    fun passportScan(): FinanceOldPage {
        el(By.name("attachedfiles_file")).sendKeys("./passport.jpeg")
        elx("//*[text() = 'passport.jpeg']").shouldBe(visible, Duration.ofSeconds(10))
        return this
    }

    fun save() {
        el(By.partialLinkText("Сохранить")).shouldBe(visible).click()
    }

    fun acceptPhone() {
        if (el(By.partialLinkText("Подтвердить")).isDisplayed) {
            el(By.partialLinkText("Подтвердить")).shouldBe(visible).click()
        }
    }

    fun checkSaveFinance() {
        elx("//*[@class = 'b-fon b-fon_width_full b-fon_padbot_10']").shouldBe(visible).shouldHave(text("Изменения внесены"))
    }

    fun acceptFinance() {
        el(By.id("__finance_unblocked")).shouldBe(visible).click()
        elx("//*[contains(text(), 'проверены')]").shouldBe(visible)
    }
}