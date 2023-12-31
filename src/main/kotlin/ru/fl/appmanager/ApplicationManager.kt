package ru.fl.appmanager

import ru.fl.model.UsersData
import ru.fl.pages.*
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*

class ApplicationManager() {

    private var properties: Properties = Properties()

    val accountPage: AccountPage = AccountPage()
    val adminPanelBS: AdminPanelBS = AdminPanelBS()
    val adminPanelPotok: AdminPanelPotok = AdminPanelPotok()
    val adminPanelUsers: AdminPanelUsers = AdminPanelUsers()
    val chatPage: ChatPage = ChatPage()
    val db = DataBaseInterface()
    val financeOldPage: FinanceOldPage = FinanceOldPage()
    val financeNewPage: FinanceNewPage = FinanceNewPage()
    val freelCatalogPage: FreelCatalogPage = FreelCatalogPage()
    val freelCatalogSignupPage: FreelCatalogSignupPage = FreelCatalogSignupPage()
    val freelNewCatalogPage: FreelNewCatalogPage = FreelNewCatalogPage()
    val freelancersPage: FreelancersPage = FreelancersPage()
    val headerPage: HeaderPage = HeaderPage()
    val listAllWorkPage: ListAllWorkPage = ListAllWorkPage()
    val loginPage: LoginPage = LoginPage()
    val mainPage: MainPage = MainPage()
    val offerPage: OfferPage = OfferPage()
    val onboardingPage: OnboardingPage = OnboardingPage()
    val orderFormPage: OrderFormPage = OrderFormPage()
    val orderPage: OrderPage = OrderPage()
    val ordersAllPage: OrdersAllPage = OrdersAllPage()
    val paymentPage: PaymentPage = PaymentPage()
    val personalAccountPage: PersonalAccountPage = PersonalAccountPage()
    val proPage: ProPage = ProPage()
    val profilePage: ProfilePage = ProfilePage()
    val projectPage: ProjectPage = ProjectPage()
    val projectExecutorsPage: ProjectExecutorsPage = ProjectExecutorsPage()
    val projectManagePage: ProjectManagePage = ProjectManagePage()
    val projectOffersPage: ProjectOffersPage = ProjectOffersPage()
    val projectRecommendationPage: ProjectRecommendationPage = ProjectRecommendationPage()
    val registrationPage: RegistrationPage = RegistrationPage()
    val searchPage: SearchPage = SearchPage()
    val topUpLS: TopUpLS = TopUpLS()
    val usersData: UsersData = UsersData(this)
    val vacancyPage: VacancyPage = VacancyPage()
    val competitionPage: CompetitionPage = CompetitionPage()

    @Throws(IOException::class)
    fun init() {
        val target = System.getProperty("target", "local")
        properties.load(FileReader(File(String.format("%s.properties", target))))
    }

    fun getProperty(key: String): String {
        return properties.getProperty(key)
    }

    fun registration(): RegistrationHelper {
        return RegistrationHelper(this)
    }

    fun login(): LoginHelper {
        return LoginHelper(this)
    }

    fun create(): CreateHelper {
        return CreateHelper(this)
    }

    fun edit(): EditHelper {
        return EditHelper(this)
    }

    fun goTo(): NavigationHelper {
        return NavigationHelper()
    }

    fun fillForm(): FillHelper {
        return FillHelper(this)
    }

    fun pay(): PurchaseHelper {
        return PurchaseHelper(this)
    }

    fun admin(): AdminHelper {
        return AdminHelper(this)
    }

    fun api(): SessionHelper {
        return SessionHelper(this)
    }

    fun elasticSearch(): ElasticSearchHelper {
        return ElasticSearchHelper()
    }
}