package ru.fl.test

import org.testng.annotations.Test
import ru.fl.appmanager.RandomUtils

class NewCatalogFormTest : TestBase() {

    @Test
    //Подача заявки в новый каталог и ее подтверждение
    fun saveFormFreelNewCatalogAndApruveStatus() {
        var country = "Россия"
        var city = "Москва"
        var lenghtAboutFreel = (500..1000).random()
        val hourlyRate = (100..10000).random().toString()
        val login = RandomUtils.randomNumberLetter()
        val email = "$login@fl.com"
        var tokenFreelancer = app.api().registration(email, "freelancer")
        val urlBuyPRO: String = app.api().getLinkBuyPRO(tokenFreelancer)
        app.goTo().page(urlBuyPRO)
        app.pay().yooMoneyCard()
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().catalogSignUpPage()
        app.fillForm().freelFormNewCatalog(lenghtAboutFreel, hourlyRate, country, city)
        app.freelCatalogSignupPage.checkSumSumModal()
        app.freelCatalogSignupPage.closeSumSub()
        app.freelCatalogSignupPage.checkSuccessSaveForm()
        val confirmStatus = "6"
        app.api().setStatusSumsub(tokenFreelancer, confirmStatus)
        // Альтернативный вариант изменение статуса через web-форму
        // app.goTo().sumsabInternalTesting()
        // app.freelCatalogSignupPage.setStatusSumsab(confirmStatus)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusSuccessConfirmProfil()
    }

    @Test
    //Подача заявки в новый каталог, проведение ее по доступным статусам и отклонение с комментарием модератора
    //Код 3 - заявка на модерации
    //Код 4 - заявка отклонена Sumsub, с возможностью повторной отправки
    //Код 5 - заявка отклонена Sumsub окончательно
    //Код 7 - заявка отклонена с комментарием модератора
    fun saveFormFreelNewCatalogAndCansel() {
        var country = "Россия"
        var city = "Москва"
        var lenghtAboutFreel = (500..1000).random()
        val hourlyRate = (100..10000).random().toString()
        val login = RandomUtils.randomNumberLetter()
        val email = "$login@fl.com"
        var tokenFreelancer = app.api().registration(email, "freelancer")
        val urlBuyPRO: String = app.api().getLinkBuyPRO(tokenFreelancer)
        app.goTo().page(urlBuyPRO)
        app.pay().yooMoneyCard()
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().catalogSignUpPage()
        app.fillForm().freelFormNewCatalog(lenghtAboutFreel, hourlyRate, country, city)
        app.freelCatalogSignupPage.checkSumSumModal()
        app.freelCatalogSignupPage.closeSumSub()
        app.freelCatalogSignupPage.checkSuccessSaveForm()

        val onModerationStatus = "3"
        app.api().setStatusSumsub(tokenFreelancer, onModerationStatus)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusOnModerationProfil()

        val cancelBySumsubStatus = "4"
        app.api().setStatusSumsub(tokenFreelancer, cancelBySumsubStatus)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusCanselBySumsub()

        val cancelFinallyBySumsubStatus = "5"
        app.api().setStatusSumsub(tokenFreelancer, cancelFinallyBySumsubStatus)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusCanselFinallyBySumsub()

        val cancelByModeratorWithCommentStatus = "7"
        app.api().setStatusSumsub(tokenFreelancer, cancelByModeratorWithCommentStatus)
        val moderatorComment = "Comment from support service"
        app.api().setSumsubModeratorComment(tokenFreelancer, moderatorComment)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusCanselByModeratorWithComment(moderatorComment)
    }

    @Test
    //Отклонение заявки модератором, редактирование пользователем и подтверждение повторной заявки
    fun canselFormNewCatalogAndEdit() {
        var country = "Россия"
        var city = "Москва"
        var lenghtAboutFreel = (300..500).random()
        val hourlyRate = (100..10000).random().toString()
        val login = RandomUtils.randomNumberLetter()
        val email = "$login@fl.com"
        var tokenFreelancer = app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().catalogSignUpPage()
        app.fillForm().freelFormNewCatalog(lenghtAboutFreel, hourlyRate, country, city)
        app.freelCatalogSignupPage.checkSumSumModal()
        app.freelCatalogSignupPage.closeSumSub()
        app.freelCatalogSignupPage.checkSuccessSaveForm()

        val cancelByModeratorWithCommentStatus = "7"
        app.api().setStatusSumsub(tokenFreelancer, cancelByModeratorWithCommentStatus)
        val moderatorComment = "Cansel by moderator"
        app.api().setSumsubModeratorComment(tokenFreelancer, moderatorComment)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.checkDivLinkWithAnkor("Профиль не опубликован в новом разделе фрилансеров")
        app.freelNewCatalogPage.goToLinkShow()
        app.freelCatalogSignupPage.checkStatusCanselByModeratorWithComment(moderatorComment)

        app.freelNewCatalogPage.editFormButton()
        var newlenghtAboutFreel = (200..300).random()
        var newCountry = "Россия"
        var newCity = "Адлер"
        val newHourlyRate = (100..10000).random().toString()
        app.fillForm().freelFormNewCatalog(newlenghtAboutFreel, newHourlyRate, newCountry, newCity)
        app.freelCatalogSignupPage.checkStatusOnModerationProfil()

        val confirmStatus = "6"
        app.api().setStatusSumsub(tokenFreelancer, confirmStatus)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusSuccessConfirmProfil()
        app.freelCatalogSignupPage.checkEditedCityInProfil(newCity)
        app.goTo().newCatalog()
        app.freelNewCatalogPage.checkSpanLinkWithAnkor("Показать, как видят ваш профиль заказчики")
        //Не работает опция "Показать как выглядять ваш профиль заказчики" или поиск пользователя сразу после регистрации
        //Есть время на индексацию и появление профиля в выдаче
        //app.freelNewCatalogPage.checkAboutProfilText(newlenghtAboutFreel)
    }

    @Test
    //Редактирование подтвержденного профиля
    fun saveFormFreelNewCatalogAndEdit() {
        var country = "Россия"
        var city = "Москва"
        var lenghtAboutFreel = (500..1000).random()
        val hourlyRate = (100..10000).random().toString()
        val login = RandomUtils.randomNumberLetter()
        val email = "$login@fl.com"
        var tokenFreelancer = app.api().registration(email, "freelancer")
        app.login().signIn(email, app.getProperty("passNew"))
        app.goTo().catalogSignUpPage()
        app.fillForm().freelFormNewCatalog(lenghtAboutFreel, hourlyRate, country, city)
        app.freelCatalogSignupPage.checkSumSumModal()
        app.freelCatalogSignupPage.closeSumSub()
        app.freelCatalogSignupPage.checkSuccessSaveForm()

        val confirmStatus = "6"
        app.api().setStatusSumsub(tokenFreelancer, confirmStatus)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusSuccessConfirmProfil()

        app.goTo().newCatalog()
        app.freelNewCatalogPage.checkSpanLinkWithAnkor("Показать, как видят ваш профиль заказчики")
        app.freelNewCatalogPage.checkALinkWithAnkor("Редактировать")
        app.freelNewCatalogPage.goToLinkEdit()
        var newlenghtAboutFreel = (200..300).random()
        app.edit().editFormNewCatalog(newlenghtAboutFreel)
        app.freelCatalogSignupPage.checkStatusOnModerationProfil()

        app.api().setStatusSumsub(tokenFreelancer, confirmStatus)
        app.goTo().catalogSignupIdentification()
        app.freelCatalogSignupPage.checkStatusSuccessConfirmProfil()

        app.goTo().newCatalog()
        app.freelNewCatalogPage.checkSpanLinkWithAnkor("Показать, как видят ваш профиль заказчики")

    }

}

