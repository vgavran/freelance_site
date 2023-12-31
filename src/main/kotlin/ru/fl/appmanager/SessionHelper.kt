package ru.fl.appmanager

import com.codeborne.selenide.Selenide
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.apache.http.HttpEntity
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import ru.fl.model.UsersData
import java.io.IOException


class SessionHelper(val app: ApplicationManager) {

    private val httpClient: CloseableHttpClient = HttpClients.custom().build()

    @Throws(IOException::class)
    fun getToken(user: UsersData.Users): String {
        val username = app.login().getLogin(user)
        val pass = app.login().getPass(user)
        val post = HttpPost(app.getProperty("api.hostname") + "/login")
        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>()
        val clientId = app.getProperty("api.client_id")
        params.add(BasicNameValuePair("username", username))
        params.add(BasicNameValuePair("password", pass))
        params.add(BasicNameValuePair("client_id", clientId))
        params.add(BasicNameValuePair("secure_session", "on"))
        params.add(BasicNameValuePair("return", "index.php"))
        post.entity = UrlEncodedFormEntity(params)
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val parsed: JsonElement = JsonParser.parseString(body).asJsonObject["auth"]
        val token: JsonElement = parsed.asJsonObject.get("token")
        return Gson().fromJson<String>(token, String::class.java).replace("\"", "")
    }

    @Throws(IOException::class)
    fun getToken(username: String?, pass: String?): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/login")
        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>()
        val clientId = app.getProperty("api.client_id")
        params.add(BasicNameValuePair("username", username))
        params.add(BasicNameValuePair("password", pass))
        params.add(BasicNameValuePair("client_id", clientId))
        params.add(BasicNameValuePair("secure_session", "on"))
        params.add(BasicNameValuePair("return", "index.php"))
        post.entity = UrlEncodedFormEntity(params)
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val parsed: JsonElement = JsonParser.parseString(body).asJsonObject["auth"]
        val token: JsonElement = parsed.asJsonObject.get("token")
        return Gson().fromJson<String>(token, String::class.java).replace("\"", "")
    }

    @Throws(IOException::class)
    fun registration(email: String?, role: String?): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/register")
        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>()
        val clientId = app.getProperty("api.client_id")
        params.add(BasicNameValuePair("email", email))
        params.add(BasicNameValuePair("password", "Password1!"))
        params.add(BasicNameValuePair("role", role))
        params.add(BasicNameValuePair("client_id", clientId))
        params.add(BasicNameValuePair("secure_session", "on"))
        params.add(BasicNameValuePair("return", "index.php"))
        post.entity = UrlEncodedFormEntity(params)
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val parsed: JsonElement = JsonParser.parseString(body).asJsonObject["auth"]
        val token: JsonElement = parsed.asJsonObject.get("token")
        return Gson().fromJson(token, String::class.java).replace("\"", "")
    }

    @Throws(IOException::class)
    private fun getTextFrom(response: CloseableHttpResponse): String {
        return response.use { response ->
            EntityUtils.toString(response.entity)
        }
    }

    //Создание проекта с указанием бюджета и названия
    @Throws(IOException::class)
    fun createProject(token: String?, name: String?, budget: String? = (1200..5000).random().toString()): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/projects")
        val bodyJson = StringEntity(
            """{
                "name": "$name",
                "description": "Find developer for project",
                "cost": {
                    "amount": "$budget",
                    "currency": "RUB"
                },
                "kind": "project",
                "safe_deal": true,
                "professions": [{
                    "id": 27,
                    "name": "Сайт под ключ",
                    "prof_group": {
                        "id": 2,
                        "name": "Разработка сайтов"
                    }
                }]
            }"""
        )
        post.entity = bodyJson
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val projectId: JsonElement = JsonParser.parseString(body).asJsonObject["id"]
        return Gson().fromJson(projectId, String::class.java)
    }

    //Принудительная публикация проетка без модерации
    @Throws(IOException::class)
    fun forcePublicationProject(projectId: String?) {
        val post = HttpPost(app.getProperty("api.hostname") + "/internal_testing/moderation/project")
        val params: MutableList<NameValuePair> = ArrayList()
        params.add(BasicNameValuePair("project_id", projectId))
        params.add(BasicNameValuePair("state", "0"))
        params.add(BasicNameValuePair("client_id", "65AAADB9F73B4905A30584493CA04500"))
        post.entity = UrlEncodedFormEntity(params)
        val response: CloseableHttpResponse = httpClient.execute(post)
    }

    @Throws(IOException::class)
    fun sendInvitationToProject(token: String?, projectId: String?, freelId: String?) {
        val post = HttpPost(app.getProperty("api.hostname") + "/projects/$projectId/invite?freelancer_id=$freelId")
        post.setHeader("authorization", "Bearer $token")
        val response: CloseableHttpResponse = httpClient.execute(post)
    }

    @Throws(IOException::class)
    fun offerProject(token: String?, projectId: String?, budget: String? = (1200..5000).random().toString()): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/projects/" + projectId + "/offers")
        val bodyJson = StringEntity(
            """
            {
            "description": "Elit non ea qui adipisicing elit irure excepteur amet commodo proident",
            "cost": {
            "amount": "$budget",
            "currency": "RUB"
            },
            "duration": {
            "duration": 4,
            "unit": "day"
            },
            "final": true
            }
            """.trimIndent()
        )
        post.setEntity(bodyJson)
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val offerId: JsonElement = JsonParser.parseString(body).asJsonObject["id"]
        return Gson().fromJson<String>(offerId, String::class.java)
    }

    @Throws(IOException::class)
    fun selectCandidate(token: String?, projectId: String?, offerId: String?) {
        val post = HttpPost(app.getProperty("api.hostname") + "/projects/" + projectId + "/offers/candidate")
        val bodyJson = StringEntity("""{"items": [{"id": $offerId}]}""")
        post.entity = bodyJson
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
    }

    @Throws(IOException::class)
    fun selectExecutor(token: String?, projectId: String?, offerId: String?) {
        val post = HttpPost(app.getProperty("api.hostname") + "/projects/" + projectId + "/offers/choose")
        val bodyJson = StringEntity("{$offerId}")
        post.entity = bodyJson
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
    }
    
    @Throws(IOException::class)
    fun createOrderPaymentByCard(token: String?, offerId: String?): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/orders/by_offer/" + offerId)
        val bodyJson = StringEntity("""{"pay_type":"card"}""")
        post.entity = bodyJson
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val entity: HttpEntity = response.entity
        val body: String = EntityUtils.toString(entity, "UTF-8")
        val orderId: JsonElement = JsonParser.parseString(body).asJsonObject["id"]
        return Gson().fromJson<String>(orderId, String::class.java)
    }

    @Throws(IOException::class)
    fun createOrderPaymentByInvoice(token: String?, offerId: String?): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/orders/by_offer/" + offerId)
        val bodyJson = StringEntity("""{"pay_type":"invoice"}""")
        post.entity = bodyJson
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val entity: HttpEntity = response.entity
        val body: String = EntityUtils.toString(entity, "UTF-8")
        val orderId: JsonElement = JsonParser.parseString(body).asJsonObject["id"]
        return Gson().fromJson<String>(orderId, String::class.java)
    }

    @Throws(IOException::class)
    fun getLinkPaymentProject(token: String?, orderId: String?): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/orders/" + orderId + "/reserve/pay")
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val urlPayment: JsonElement = JsonParser.parseString(body).asJsonObject["url"]
        return Gson().fromJson<String>(urlPayment, String::class.java)
    }

    @Throws(IOException::class)
    fun confirmOrder(token: String?, orderId: String?) {
        val post = HttpPost(app.getProperty("api.hostname") + "/orders/" + orderId + "/accept")
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        Selenide.sleep(500)
    }

    @Throws(IOException::class)
    fun completeOrder(token: String?, orderId: String?) {
        val postSmsCustomer = HttpPost(app.getProperty("api.hostname").toString() + "/sms/send")
        postSmsCustomer.setHeader("authorization", "Bearer $token")
        httpClient.execute(postSmsCustomer)
        val postVerifyCustomer = HttpPost(app.getProperty("api.hostname").toString() + "/sms/verify?code=1111")
        postVerifyCustomer.setHeader("authorization", "Bearer $token")
        val response: CloseableHttpResponse = httpClient.execute(postVerifyCustomer)
        val body = getTextFrom(response)
        val urlPayment: JsonElement = JsonParser.parseString(body).asJsonObject["token"]
        val tokenVerify: String = Gson().fromJson<String>(urlPayment, String::class.java).replace("\"", "")
        val post = HttpPost(app.getProperty("api.hostname") + "/orders/" + orderId + "/complete")
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        post.setHeader("Sms-Token", tokenVerify)
        httpClient.execute(post)
    }

    @Throws(IOException::class)
    fun getLinkBuyPRO(token: String): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/tariffs/163/buy")
        post.setHeader("authorization", "Bearer $token")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val urlPayment: JsonElement = JsonParser.parseString(body).asJsonObject["url"]
        return Gson().fromJson<String>(urlPayment, String::class.java)
    }

    @Throws(IOException::class)
    fun addPhone(token: String?) {
        val postAddPhone = HttpPost(
            app.getProperty("api.hostname") + "/sms/phone?phone=7949" + (1111111..9999999).random()
        )
        postAddPhone.setHeader("authorization", "Bearer $token")
        val response: CloseableHttpResponse = httpClient.execute(postAddPhone)
        val body = getTextFrom(response)
        val urlPayment = JsonParser.parseString(body).asJsonObject["token"]
        val tokenVerify: String? = Gson().fromJson<String>(urlPayment, String::class.java)
        val postSms = HttpPost(app.getProperty("api.hostname") + "/sms/send")
        postSms.setHeader("authorization", "Bearer $token")
        httpClient.execute(postSms)
        val postVerify = HttpPost(app.getProperty("api.hostname") + "/sms/verify?code=1111")
        postVerify.setHeader("authorization", "Bearer $token")
        httpClient.execute(postVerify)
    }

    @Throws(IOException::class)
    fun createVacancy(token: String?, name: String?): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/vacancy")
        val bodyJson = StringEntity(
            """{
                "name":"$name",
                "description":"{\"ops\":[{\"insert\":\"Vacancy added via API\\n\"}]}",
                "duration":"project",
                "cost_from":10000,
                "cost_to":20000,
                "work_schedule":"fulltime",
                "work_type":"remote",
                "salary_type":"salary",
                "professions":[
                    {
                        "prof_group":94,
                        "id":4
                    }
                ],
                "city_id":5,
                "country_id":1,
                "skills":[
                            {
                        "id": 1,
                        "name": "Photoshop",
                        "sort": 10
                    },
                    {
                        "id": 2,
                        "name": "Верcтка",
                        "sort": 20
                    },
                    {
                        "id": 2,
                        "name": "Postgress",
                        "sort": 30
                    }
                ]
            }"""
        )
        post.setEntity(bodyJson)
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val projectId: JsonElement = JsonParser.parseString(body).asJsonObject["id"]
        return Gson().fromJson<String>(projectId, String::class.java)
    }

    fun getLinkPaymentVacancy(token: String?, vacancyId: String?): String {
        val post = HttpPost(app.getProperty("api.hostname") + "/vacancy/$vacancyId/buy")
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/json")
        val response: CloseableHttpResponse = httpClient.execute(post)
        val body = getTextFrom(response)
        val urlPayment: JsonElement = JsonParser.parseString(body).asJsonObject["url"]
        return Gson().fromJson<String>(urlPayment, String::class.java)
    }

    fun sendMessagesToDeal(token: String, orderId: String?, textMessage: String?) {
        val post = HttpPost(app.getProperty("api.hostname") + "/orders/messages")
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/x-www-form-urlencoded")
        val params: MutableList<NameValuePair> = ArrayList()
        params.add(BasicNameValuePair("order_id", orderId))
        params.add(BasicNameValuePair("text", textMessage))
        post.entity = UrlEncodedFormEntity(params)
        val response: CloseableHttpResponse = httpClient.execute(post)
    }

    fun sendMessagesToOffer(token: String, projectId: String?, offerId: String?, textMessage: String?) {
        val post = HttpPost(app.getProperty("api.hostname") + "/projects/$projectId/offers/$offerId/messages")
        post.setHeader("authorization", "Bearer $token")
        post.setHeader("Content-type", "application/x-www-form-urlencoded")
        val params: MutableList<NameValuePair> = ArrayList()
        params.add(BasicNameValuePair("text", textMessage))
        post.entity = UrlEncodedFormEntity(params)
        val response: CloseableHttpResponse = httpClient.execute(post)
    }

    @Throws(IOException::class)
    fun setStatusSumsub(token: String, status: String) {
        val post = HttpPost(app.getProperty("api.hostname") + "/internal_testing/catalog-sumsub/status")
        post.setHeader("authorization", "Bearer $token")
        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>()
        params.add(BasicNameValuePair("identity_verify_status", status))
        post.entity = UrlEncodedFormEntity(params)
        httpClient.execute(post)
    }

    @Throws(IOException::class)
    fun setSumsubModeratorComment(token: String, moderComment: String) {
        val post = HttpPost(app.getProperty("api.hostname") + "/internal_testing/catalog-sumsub/moderator-comment")
        post.setHeader("authorization", "Bearer $token")
        val params: MutableList<NameValuePair> = ArrayList<NameValuePair>()
        params.add(BasicNameValuePair("moderator_comment", moderComment))
        post.entity = UrlEncodedFormEntity(params)
        httpClient.execute(post)
    }

}