package ru.fl.appmanager

class AdminHelper(val app: ApplicationManager) {

    fun activateEmail(userName: String) {
        app.adminPanelUsers.searchUser(userName)
        app.adminPanelUsers.activate()
    }
}