package ru.fl.appmanager

import com.codeborne.selenide.Configuration.baseUrl
import java.sql.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class DataBaseInterface{
    var connection: Connection = DriverManager.getConnection(
        "jdbc:postgresql://192.168.%%.%%:%%%%/alpha", "autotest", "%%%%%%%%"
    )
    var stmt = connection.createStatement()

    @Throws(SQLException::class)
    fun getUrlActivateEmail(login: String): String {
        val rs: ResultSet = stmt.executeQuery(
                "SELECT code FROM activate_code JOIN users ON users.uid = activate_code.user_id WHERE users.login = '$login'".trimIndent()
        )
        rs.next()
        val codeAccept: String = rs.getString("code")
        return "${baseUrl}/registration/activate.php?code=$codeAccept"
    }

    @Throws(SQLException::class)
    fun updateOrdersSummary(loginCustomer: String, loginFreelancer: String, sum: Int) {
        val uidCustomer: ResultSet =
            stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$loginCustomer'")
        uidCustomer.next()
        val idCustomer: String = uidCustomer.getString("uid")
        val uidFreelancer: ResultSet =
            stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$loginFreelancer'")
        uidFreelancer.next()
        val idFreelancer: String = uidFreelancer.getString("uid")
        stmt.executeUpdate("update tservices_orders_summary set value = $sum where employer_id = $idCustomer AND freelancer_id = $idFreelancer")
    }

    @Throws(SQLException::class)
    fun resetChoisePersonalAccount(login: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update sbr_reqv set user_made_choice = false where user_id = $id")
    }

    @Throws(SQLException::class)
    fun setChoisePersonalAccount(login: String?) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update sbr_reqv set user_made_choice = true where user_id = $id")
    }

    @Throws(SQLException::class)
    fun getPersonalAccountType(login: String): String {
        //0 - аноним, 1 - физик, 2 - юрик
        val rs: ResultSet = stmt.executeQuery(
            "SELECT form_type FROM sbr_reqv JOIN users ON users.uid = sbr_reqv.user_id WHERE users.login = '$login'".trimIndent()
            )
        rs.next()
        return rs.getString("form_type")
    }

    @Throws(SQLException::class)
    fun setPersonalAccountTypeAnonym(login: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update sbr_reqv set form_type = 0 where user_id = $id")
    }

    @Throws(SQLException::class)
    fun changeStatusFinanceApproved(login: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update sbr_reqv set validate_status = 2 where user_id = $id")
    }

    @Throws(SQLException::class)
    fun setPersonalAccountTypeIndividual(login: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update sbr_reqv set form_type = 1 where user_id = $id")
    }

    @Throws(SQLException::class)
    fun setPersonalAccountTypeEntity(login: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update sbr_reqv set form_type = 2 where user_id = $id")
    }

    @Throws(SQLException::class)
    fun resetStatusFinance(login: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update sbr_reqv set validate_status = null where user_id = $id")
    }

    @Throws(SQLException::class)
    fun setSumPersonalAccountLegalEntity(login: String?, sum: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update account set sum = $sum where uid = $id")
    }

    @Throws(SQLException::class)
    fun setSumPersonalAccountIndividual(login: String?, sum: String) {
        val uid: ResultSet = stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        stmt.executeUpdate("update account set safe_deal_sum = $sum where uid = $id")
    }

    @Throws(SQLException::class)
    fun getSumPersonalAccountIndividual(login: String?): Float {
        val uid: ResultSet =
            stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        val rs: ResultSet =
            stmt.executeQuery("SELECT safe_deal_sum FROM account WHERE uid = $id")
        rs.next()
        return rs.getString("safe_deal_sum").toFloat()
    }

    @Throws(SQLException::class)
    fun getSumPersonalAccountLegalEntity(login: String?): Float {
        val uid: ResultSet =
            stmt.executeQuery("SELECT uid FROM users WHERE users.login = '$login'")
        uid.next()
        val id: String = uid.getString("uid")
        val rs: ResultSet = stmt.executeQuery("SELECT sum FROM account WHERE uid = $id")
        rs.next()
        return rs.getString("sum").toFloat()
    }

    @Throws(SQLException::class)
    fun editDateCompetition(name: String) {
        val endDate = Timestamp.valueOf(LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS))
        val winDate = Timestamp.valueOf(LocalDateTime.now().minusDays(2).truncatedTo(ChronoUnit.SECONDS))
        stmt.executeUpdate("update projects set end_date = '$endDate' where name = '$name'")
        stmt.executeUpdate("update projects set win_date = '$winDate' where name = '$name'")
    }
}