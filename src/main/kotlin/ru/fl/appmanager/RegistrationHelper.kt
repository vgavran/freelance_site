package ru.fl.appmanager

class RegistrationHelper(val app: ApplicationManager){

    fun onbording() {
        app.onboardingPage.freelLvl()
        app.onboardingPage.name()
        app.onboardingPage.surname()
        app.onboardingPage.aboutFreel()
        app.onboardingPage.country()
        app.onboardingPage.city()
        app.onboardingPage.goToStepThree()
        app.onboardingPage.category()
        app.onboardingPage.subcategory()
        app.onboardingPage.skills()
        app.onboardingPage.submit()
    }

    fun customer(email: String, passNew: String) {
        app.headerPage.goToRegistration()
        app.registrationPage.selectCustomer()
        app.goTo().registrationCustomer()
        app.registrationPage.email(email)
        app.registrationPage.pass(passNew)
        app.registrationPage.submit()
    }

    fun freel(email: String, passNew: String) {
        app.headerPage.goToRegistration()
        app.registrationPage.selectFreelancer()
        app.goTo().registrationFreelancer()
        app.registrationPage.email(email)
        app.registrationPage.pass(passNew)
        app.registrationPage.submit()
    }

    fun freelOldModal(email: String, passNew: String) {
        app.registrationPage.emailOldModal(email)
        app.registrationPage.passOldModal(passNew)
        app.registrationPage.submitOldModal()
    }
}