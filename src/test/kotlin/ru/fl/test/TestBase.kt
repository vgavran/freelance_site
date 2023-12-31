package ru.fl.test

import com.codeborne.selenide.Selenide
import org.apache.commons.io.FileUtils
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.ITestContext
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeSuite
import ru.fl.appmanager.ApplicationManager
import ru.fl.model.UsersData.Users.CustomerIndividualPRO
import ru.fl.model.UsersData.Users.CustomerlLegalEntityPRO
import ru.talenttech.xqa.okfront.config.OkFrontConfig
import ru.talenttech.xqa.okfront.utlis.SelenideGlobalConfigurator
import java.io.File
import java.io.IOException
import java.sql.SQLException


open class TestBase {

    private fun setCapability() : DesiredCapabilities {
        val chromeOptions = ChromeOptions()
        chromeOptions.setCapability("env", object : ArrayList<Any?>() {
            init {
                add("LANG=ru_RU.UTF-8")
                add("LANGUAGE=ru:RU")
                add("LC_ALL=ru_RU.UTF-8")
            }
        })
        return DesiredCapabilities(chromeOptions)
    }

    var config: OkFrontConfig = OkFrontConfig.Builder()
        .browserCapabilities(setCapability())
        .timeout(4000)
        .remoteAddress("http://130.193.40.21:8085/wd/hub")
        //.remoteAddress("http://localhost:4444/wd/hub")
        //.browserSize("1920x1080")
        .startMaximized(true)
        .holdBrowserOpen(false)
        .logEachStep(false)
        .fastSetValue(false)
        .containerName("")
        //.baseUrl("http://fl2.test")
        .baseUrl(System.getProperty("STAGE_URL"))
        .screenshots(true)
        .pageLoadTimeout(120000)
        .build()

    @BeforeSuite(alwaysRun = true)
    @Throws(SQLException::class, IOException::class)
    fun before(context: ITestContext) {
        SelenideGlobalConfigurator.configure(config)
        app = ApplicationManager()
        app.init()

        //Проверяем, что у заказчиков есть деньги на счете, если нет, то добавляем
        val loginCustomerIndividual: String? = app.login().getLogin(CustomerIndividualPRO)
        val sumAccIndividual: Float = app.db.getSumPersonalAccountIndividual(loginCustomerIndividual)
        if (sumAccIndividual < 20000) {
            app.db.setSumPersonalAccountIndividual(loginCustomerIndividual, "500000.000000")
        }
        val loginCustomerLegalEntity: String? = app.login().getLogin(CustomerlLegalEntityPRO)
        val sumAccLegalEntity: Float = app.db.getSumPersonalAccountLegalEntity(loginCustomerLegalEntity)
        if (sumAccLegalEntity < 20000) {
            app.db.setSumPersonalAccountLegalEntity(loginCustomerLegalEntity, "500000.000000")
        }
        val loginCustomerWithIndividualDiscountComission = "bhjdhgfjhgjfghj"
        val sumAccForBS: Float = app.db.getSumPersonalAccountIndividual(loginCustomerWithIndividualDiscountComission)
        if (sumAccForBS < 50000) {
            app.db.setSumPersonalAccountIndividual(loginCustomerWithIndividualDiscountComission, "500000.000000")
        }

        //Удаляем старые репорты
        FileUtils.deleteDirectory(File("build/downloads"))
        FileUtils.deleteDirectory(File("build/reports"))
    }

    @AfterMethod(alwaysRun = true)
    @Throws(Exception::class)
    fun tearDown() {
        Selenide.closeWebDriver()
    }

    companion object {
        lateinit var app: ApplicationManager
    }
}
