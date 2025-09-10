package dashboard

import authentication.FakeAuthenticationRepository
import FakeConnectivityObserver
import FakeLocalDataSource
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CategoryListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SubCategoryListResponse
import com.pi.ProjectInclusion.data.model.profileModel.ProfileNameChangeRequest
import com.pi.ProjectInclusion.domain.useCases.AuthenticationUsesCases
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
import kotlin.test.assertTrue

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
        assertEquals("Track request successful", state.success.message) // adapt to your fake response
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
        val state = dashboardViewModel.getCertificate.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("FAQ request successful", state.success.message) // adapt to your fake response
    }

    @Test
    fun faq_category_emits_error() = runTest {
        // Arrange
        fakeRepo.shouldSucceed = false

        // Act
        dashboardViewModel.getAllCategory()

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = dashboardViewModel.getCertificate.first { !it.isLoading }
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
        val state = dashboardViewModel.getCertificate.first { !it.isLoading }
        assertNotNull(state.success)
        assertEquals("LMS request successful", state.success.message) // adapt to your fake response
    }

    @Test
    fun aq_sub_category__emits_error() = runTest {
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
        assertEquals("Sub-Category Not found!", state.error) // adapt to your fake response
    }

}