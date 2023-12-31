package ru.fl.appmanager

import com.codeborne.selenide.Configuration.baseUrl
import com.codeborne.selenide.Selenide.open

class NavigationHelper()  {

    fun page(url: String?) {
        open(url)
    }

    fun mainPage() {
        open("$baseUrl")
    }

    fun newCatalog() {
        open("$baseUrl/catalog")
    }

    fun catalog() {
        open("$baseUrl/freelancers")
    }

    fun adminPanelUserSearch() {
        open("$baseUrl/siteadmin/user_search")
    }

    fun adminPanelPotok() {
        open("$baseUrl/siteadmin/user_content/?site=choose")
    }

    fun registrationFreelancer() {
        open("$baseUrl/account/registration/?disable_captcha=k9l5UK!YZib4QTW&providerType=1")
    }

    fun registrationCustomer() {
        open("$baseUrl/account/registration/?disable_captcha=k9l5UK!YZib4QTW&providerType=2")
    }

    fun auth() {
        open("$baseUrl/account/login/?disable_captcha=k9l5UK!YZib4QTW")
    }

    fun allOrders() {
        open("$baseUrl/projects/all/list/#/")
    }

    fun allWork() {
        open("$baseUrl/projects/#/")
    }

    fun personalAccount() {
        open("$baseUrl/bill/history/?period=3")
    }

    fun projectRecommendation(projectId: String?) {
        open("$baseUrl/projects/$projectId/manage/recommendations/")
    }

    fun project(projectId: String?) {
        open("$baseUrl/projects/$projectId")
    }

    fun projectManage(projectId: String?) {
        open("$baseUrl/projects/$projectId/manage/#/")
    }

    fun projectOffers(projectId: String?) {
        open("$baseUrl/projects/$projectId/manage/offers/#/")
    }

    fun projectExecutors(projectId: String?) {
        open("$baseUrl/projects/$projectId/manage/executors/#/")
    }

    fun order(orderId: String?) {
        open("$baseUrl/tu/order/$orderId")
    }

    fun financePage(login: String?) {
        open("$baseUrl/users/$login/setup/finance/")
    }

    fun profile(login: String?) {
        open("$baseUrl/users/$login/#/")
    }

    fun catalogSignUpPage() {
        open("$baseUrl/catalog/signup/")
    }

    fun scorePage() {
        open("$baseUrl/bill/history/?period=3")
    }

    fun adminLSPage() {
        open("$baseUrl/siteadmin/bill_personal_invoices/")
    }

    fun adminOldLSPage() {
        open("$baseUrl/siteadmin/billinvoices/")
    }

    fun searchProject() {
        open("$baseUrl/search/?type=projects")
    }

    fun searchFreelancer() {
        open("$baseUrl/search/?type=users&action=search_advanced")
    }

    fun createVacancy(){
        open("$baseUrl/vacancy/create/#/")
    }

    fun vacancyManage(vacancyId: String?) {
        open("$baseUrl/vacancy/$vacancyId/manage/")
    }

    fun vacancyEdit(vacancyId: String?) {
        open("$baseUrl/vacancy/$vacancyId/manage/edit")
    }

    fun vacancyPayment(vacancyId: String?) {
        open("$baseUrl/vacancy/$vacancyId/manage/payment")
    }

    fun vacancy(vacancyId: String?) {
        open("$baseUrl/projects/$vacancyId/")
    }

    fun chatOffersPage() {
        open("$baseUrl/messages/#/")
    }

    fun chatDealsPage() {
        open("$baseUrl/messages/#/orders")
    }

    fun chatFLTeamPage() {
        open("$baseUrl/messages/#/systems")
    }

    fun chatPage(){
        open("$baseUrl/messages/#")
    }

    fun sumsabInternalTesting() {
        open("$baseUrl/internal_testing/catalog-sumsub/")
    }

    fun catalogSignupIdentification() {
        open("$baseUrl/catalog/signup/identification/")
    }

    fun autoCancelInternalTesting() {
        open("$baseUrl/internal_testing/tservices-cancel/")
    }
}