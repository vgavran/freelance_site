package ru.fl.pages

class OrdersAllPage: SelenideObjects(){
    fun getNameExistOrder(): String {
        return elxs("//*[@id = 'project-card-name']").first().text
    }
}