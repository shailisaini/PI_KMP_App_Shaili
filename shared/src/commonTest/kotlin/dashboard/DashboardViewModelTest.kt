package dashboard

import FakeConnectivityObserver
import FakeLocalDataSource
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.profileModel.ChangePasswordRequest
import com.pi.ProjectInclusion.data.model.profileModel.ProfileNameChangeRequest
import com.pi.ProjectInclusion.domain.useCases.DashboardUsesCases
import com.pi.ProjectInclusion.ui.viewModel.DashboardViewModel
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var dashboardViewModel: DashboardViewModel

    /**
     * Fake for getAuthViewModel (instead of hitting real API)
     */
    private lateinit var fakeRepo: FakeDashboardRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {

        // Set Main dispatcher for viewModelScope
        Dispatchers.setMain(testDispatcher)
        fakeRepo = FakeDashboardRepository()
        val fakeAuthUseCases = DashboardUsesCases(fakeRepo)
        val fakeLocalData = FakeLocalDataSource()
        val fakeConnectivity = FakeConnectivityObserver()

        dashboardViewModel = DashboardViewModel(
            fakeAuthUseCases,
            fakeLocalData,
            fakeConnectivity
        )
    }

    @Test
    fun profile_change_request_emits_success() = runTest {
        fakeRepo.shouldSucceed = true

        val request = ProfileNameChangeRequest("newName") // adapt to your model

        // Act
        dashboardViewModel.getProfileChangeRequest(
            request,
            "fake_token",
            docPic = "fakeBytes".toByteArray(),
            fileName = "doc.png"
        )

        // advance dispatcher so launched coroutines run
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getChangeRequest.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("Change request successful", state.success.message) // adapt message
    }

    @Test
    fun profile_change_request_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false
        val request = ProfileNameChangeRequest("newName")

        // Act
        dashboardViewModel.getProfileChangeRequest(
            request,
            "fake_token"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getChangeRequest.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Request Not sent!", state.error) // adapt message
    }

    @Test
    fun track_change_request_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.getTrackChangeRequest(
            strToken = "fake_token",
            requestTypeId = "123"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getTrackRequest.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals(
            "Track request successful",
            state.success.message
        ) // adapt to your fake response
    }

    @Test
    fun track_change_request_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.getTrackChangeRequest(
            strToken = "fake_token",
            requestTypeId = "123"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getTrackRequest.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Request Not found!", state.error) // adapt to your fake response
    }

    // Certificate
    @Test
    fun lms_certificate_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.getLMSUserCertificate(
            CertificateRequest(),
            strToken = "fake_token"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getCertificate.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("LMS request successful", state.success.message) // adapt to your fake response
    }

    @Test
    fun lms_certificate_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.getLMSUserCertificate(
            CertificateRequest(),
            strToken = "fake_token"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getCertificate.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Request Not sent!", state.error) // adapt to your fake response
    }

    // Faq Category
    @Test
    fun faq_category_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.getAllCategory()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getCategoryList.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("Faq Category", state.success[0].name)
    }

    @Test
    fun faq_category_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.getAllCategory()

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getCategoryList.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Request Not found!", state.error) // adapt to your fake response
    }

    // Faq Sub Category
    @Test
    fun faq_sub_category_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.getAllSubCategory()

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getSubCategoryList.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("Faq Sub Category", state.success[0].name) // adapt to your fake response
    }

    @Test
    fun aq_sub_category__emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.getAllSubCategory()

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getSubCategoryList.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Sub-Category Not found!", state.error) // adapt to your fake response
    }

    // Faq Sub Category
    @Test
    fun faq_sub_category_by_category_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.getAllSubCategoryByCategoryId(1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getSubCategoryByCategoryIdList.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("All Sub-category by Category", state.success.message) // adapt to your fake response
    }

    @Test
    fun aq_sub_category_by_category_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.getAllSubCategoryByCategoryId(1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getSubCategoryByCategoryIdList.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("No Sub-category by Category", state.error) // adapt to your fake response
    }

    // get All FAQ
    @Test
    fun all_faq_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.getAllFAQs("1","","","","","")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getFAQsList.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("All FAQ", state.success.message) // adapt to your fake response
    }

    @Test
    fun all_faq_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.getAllFAQs("1","","","","","")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getFAQsList.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("All FAQ", state.error) // adapt to your fake response
    }

    @Test
    fun change_password_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.changePassword(ChangePasswordRequest("",""),"Token")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.changePassword.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("Password change Successfully!", state.success.message) // adapt to your fake response
    }

    @Test
    fun change_password_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.changePassword(ChangePasswordRequest("",""),"Token")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.changePassword.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Password Error!", state.error) // adapt to your fake response
    }

    // deactivate
    @Test
    fun deactivate_user_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.deactivateUser("token", "1")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getAccountDelete.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("Deactivate user!", state.success.message) // adapt to your fake response
    }

    @Test
    fun deactivate_user_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.deactivateUser("token", "1")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getAccountDelete.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Error to deactivate user", state.error) // adapt to your fake response
    }

    // check profile completion
    @Test
    fun profile_completion_emits_success() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = true

        // Act
        dashboardViewModel.checkProfileCompletion("1")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.checkProfile.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("Check Profile Completion", state.success.message) // adapt to your fake response
    }

    @Test
    fun profile_completion_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.checkProfileCompletion("1")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.checkProfile.first { !it.isLoading }
        assertNotNull(state.error)
        assertEquals("Error!", state.error) // adapt to your fake response
    }

}