package ru.fl.pages

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.SelenideElement
import org.openqa.selenium.By

open class SelenideObjects {

    fun el(selector: By) : SelenideElement {
        return Selenide.`$`(selector);
    }

    fun els(selector: By) : ElementsCollection {
        return Selenide.`$$`(selector);
    }

    fun elx(selector: String) : SelenideElement {
        return Selenide.`$x`(selector);
    }

    fun elxs(selector: String) : ElementsCollection {
        return Selenide.`$$x`(selector);
    }
}