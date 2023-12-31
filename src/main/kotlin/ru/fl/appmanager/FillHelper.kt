package ru.fl.appmanager

class FillHelper(val app: ApplicationManager){

    fun directOrder(name: String,
                    description: String = "Какое-то описание прямого заказа",
                    term: String = "3",
                    budget: String = "1200") {
        app.orderFormPage.orderName(name)
        app.orderFormPage.orderDescription(description)
        app.orderFormPage.orderTerm(term)
        app.orderFormPage.orderPrice(budget)
    }

    //Заполнение формы создания заказа
    fun project(name: String, description: String = "Стандартное описание заказа") {
        app.projectPage.nameProject(name)
        app.projectPage.setCategoryProject()
        app.projectPage.goToStepTwo()
        app.projectPage.descriptionProject(description)
        app.projectPage.goToStepThree()
        app.projectPage.setNoBudget()
        app.projectPage.setNoDeadline()
        app.projectPage.goToStepFour()
        app.projectPage.publicationProjectWizard()
    }

    //Заполнение формы с указанием категории и подкатегории заказа
    fun project(name: String, category: String, subcategory: String) {
        app.projectPage.nameProject(name)
        app.projectPage.setCategoryProject(category, subcategory)
        app.projectPage.goToStepTwo()
        app.projectPage.descriptionProject()
        app.projectPage.goToStepThree()
        app.projectPage.setNoBudget()
        app.projectPage.setNoDeadline()
        app.projectPage.goToStepFour()
        app.projectPage.publicationProjectWizard()
    }

    //Заполнение формы создания вакансии
    fun vacancy(name: String, salaryFrom: String, salaryTo: String) {
        app.vacancyPage.nameVacancy(name)
        app.vacancyPage.chooseSpecialization()
        app.vacancyPage.chooseSkill()
        app.vacancyPage.descriptionVacancy(name)
        app.vacancyPage.goToWorkConditionsStep()
        app.vacancyPage.setSalary(salaryFrom, salaryTo)
        app.vacancyPage.chooseCountryAndCity()
        app.vacancyPage.setPhone()
        app.vacancyPage.goToVacancyPublicationStep()
        app.vacancyPage.goToVacancyTypeStep()
    }

    fun financeFreelIndividualResident() {
        app.financeOldPage
            .resident()
            .individual()
            .fio()
            .birthday()
            .passportSeries()
            .passportNumber()
            .passportDate()
            .passportBy()
            .passportCode()
            .addressResident()
            .innIndividual()
            .yooMoneyNumber()
            .cardNumber()
            .passportScan()
            .save()
    }

    fun financeFreelIndividualNotResident() {
        app.financeOldPage
            .notResident()
            .individual()
            .fio()
            .birthday()
            .passportSeries()
            .passportNumber()
            .passportDate()
            .passportBy()
            .addressNotResident()
            .yooMoneyNumber()
            .accountNotResident()
            .bankName()
            .bankRfName()
            .corrAccount()
            .bic()
            .bankRfInn()
            .authorizedBankAccount()
            .passportScan()
            .save()
    }

    fun financeCustomerIndividualNotResident() {
        app.financeOldPage
            .notResident()
            .individual()
            .fio()
            .birthday()
            .passportSeriesNotResident()
            .passportNumberNotResident()
            .passportDate()
            .passportByNotResident()
            .addressNotResident()
            .save()
    }

    fun financeCustomerIndividualResident() {
        app.financeOldPage
            .resident()
            .individual()
            .fio()
            .birthday()
            .passportSeries()
            .passportNumber()
            .passportDate()
            .passportBy()
            .passportCode()
            .addressResident()
            .save()
    }

    fun financeFreelancerEntityResident() {
        app.financeOldPage
            .resident()
            .entity()
            .fio()
            .birthday()
            .typeEntity()
            .nameCompany()
            .addressResident()
            .innEntity()
            .kpp()
            .accountEntity()
            .corrAccount()
            .bic()
            .save()
    }

    fun financeEntityNotResident() {
        app.financeOldPage
            .notResident()
            .entity()
            .fio()
            .birthday()
            .nameCompany()
            .addressNotResident()
            .accountNotResident()
            .bankRfName()
            .corrAccount()
            .bic()
            .bankRfInn()
            .authorizedBankAccount()
            .save()
    }

    fun financeCustomerEntityResidentNew() {
        app.financeNewPage
            .resident()
            .fio()
            .birthday()
            .typeEntity()
            .innEntity()
            .continueBtn()
            .nameCompanyResident()
            .addressResident()
            .kpp()
            .account()
            .corrAccountResident()
            .bicResident()
            .save()
    }

    fun financeCustomerEntityResidentInnNew() {
        app.financeNewPage
            .resident()
            .fio()
            .birthday()
            .typeEntity()
            .innAutocomplete()
            .continueBtn()
            .account()
            .corrAccountResident()
            .bicResident()
            .save()
    }

    fun financeCustomerEntityNotResidentNew() {
        app.financeNewPage
            .notResident()
            .fio()
            .birthday()
            .nameCompanyNotResident()
            .addressNotResident()
            .accountNotResident()
            .bankName()
            .corrAccountNotResident()
            .bicNotResident()
            .bankInn()
            .bankCorrName()
            .authorizedBankAccount()
            .save()
    }

    fun freelFormNewCatalog(about : Int, hourlyRate : String, country : String, city : String){
        app.freelCatalogSignupPage.aboutFreel(about)
        app.freelCatalogSignupPage.hourlyRate(hourlyRate)
        app.freelCatalogSignupPage.selectCountry(country)
        app.freelCatalogSignupPage.selectCity(city)
        app.freelCatalogSignupPage.setChekboxes()
        app.freelCatalogSignupPage.saveBtnClick()
    }
}