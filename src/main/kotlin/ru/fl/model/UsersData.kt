package ru.fl.model

import org.openqa.selenium.InvalidArgumentException
import ru.fl.appmanager.ApplicationManager

open class UsersData(val app: ApplicationManager){

    fun userData(user: Users?): HashMap<String, String> {
        val userData = HashMap<String, String>()
        return when (user) {
            Users.FreelEmail -> {
                userData["email"] = "freelemailpro@test.ru"
                userData
            }
            Users.FreelIndividualPRO -> {
                userData["username"] = "auto_freel_indi"
                userData["name"] = "Freel"
                userData["surname"] = "Individual"
                userData["pass"] = app.getProperty("passNew")
                userData["email"] = "auto_freel_individual_pro@fl.com"
                userData["phone"] = "+79493247562"
                userData
            }
            Users.FreelLegalEntityPRO -> {
                userData["username"] = "auto_freel_enti"
                userData["name"] = "Freel"
                userData["surname"] = "LegalEntity"
                userData["pass"] = app.getProperty("passNew")
                userData["email"] = "auto_freel_entity@fl.com"
                userData["phone"] = "+79493594535"
                userData
            }
            Users.CustomerIndividualPRO -> {
                userData["username"] = "auto_zak_indivi"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.CustomerlLegalEntityPRO -> {
                userData["username"] = "auto_zak_entity"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.FreelIndividualNotPRO -> {
                userData["username"] = "aegupova102"
                userData["pass"] = app.getProperty("passOld")
                userData
            }
            Users.CustomerIndividualNotPRO -> {
                userData["username"] = "aegupova6"
                userData["pass"] = app.getProperty("passOld")
                userData
            }
            Users.Arbitr -> {
                userData["username"] = "norisk"
                userData["pass"] = app.getProperty("passOld")
                userData["uid"] = "226893"
                userData
            }
            Users.Moderator -> {
                userData["username"] = "moderator"
                userData["pass"] = app.getProperty("passOld")
                userData["uid"] = "53791"
                userData
            }
            Users.CustomerAdmin -> {
                userData["username"] = "vg_rabot1"
                userData["pass"] = app.getProperty("passOld")
                userData
            }
            Users.FreelNotInCatalog -> {
                userData["username"] = "auto_freel_cat2"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.FreelInCatalog -> {
                userData["username"] = "auto_freel_cat1"
                userData["name"] = "ФрилДляКаталога"
                userData["uid"] = "481208"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.CustomerEntityLS -> {
                userData["username"] = "zak_ip"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.CustomerIndividualLS -> {
                userData["username"] = "zak_fiz"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.CustomerAnonymLS -> {
                userData["username"] = "zak_anonym"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.CustomerWithIndividualDiscount -> {
                userData["username"] = "bhjdhgfjhgjfghj"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.FreelIndividualLS -> {
                userData["username"] = "fril_fiz"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.FreelLegalEntityLS -> {
                userData["username"] = "fril_ur"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            Users.FreelAnonymLS -> {
                userData["username"] = "freel_anonym"
                userData["name"] = "Freel"
                userData["surname"] = "Anonym"
                userData["pass"] = app.getProperty("passNew")
                userData
            }
            else -> throw InvalidArgumentException("Пользователя $user не существует")
        }
    }

    enum class Users {
        FreelEmail,
        FreelIndividualPRO,
        FreelLegalEntityPRO,
        CustomerIndividualPRO,
        CustomerlLegalEntityPRO,
        FreelIndividualNotPRO,
        CustomerIndividualNotPRO,
        Arbitr,
        Moderator,
        CustomerAdmin,
        FreelInCatalog,
        FreelNotInCatalog,
        CustomerEntityLS,
        CustomerIndividualLS,
        CustomerAnonymLS,
        CustomerWithIndividualDiscount,
        FreelIndividualLS,
        FreelLegalEntityLS,
        FreelAnonymLS
    }
}