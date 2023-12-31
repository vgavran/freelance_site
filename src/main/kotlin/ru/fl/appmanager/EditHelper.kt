package ru.fl.appmanager

class EditHelper(val app: ApplicationManager){

    fun project(textName: String,
                textDescription: String,
                textBudget: String,
                deadline: String,
                category: String = "Менеджмент",
                subcategory: String = "Другое") {
        app.projectManagePage.editNameProject(textName)
        app.projectManagePage.editDescriptionProject(textDescription)
        app.projectManagePage.editCategory(category, subcategory)
        app.projectManagePage.editBudget(textBudget)
        app.projectManagePage.setDeadline(deadline)
        app.projectManagePage.addAttachedFile()
        app.projectManagePage.saveChanges()
    }

    fun offerVacancy(description: String) {
        app.offerPage.descriptionOfferVacancy(description)
        app.offerPage.saveOfferChanges()
    }

    fun editFormNewCatalog(about : Int){
        app.freelCatalogSignupPage.aboutFreel(about)
        app.freelCatalogSignupPage.setChekboxes()
        app.freelCatalogSignupPage.saveBtnClick()
    }

}