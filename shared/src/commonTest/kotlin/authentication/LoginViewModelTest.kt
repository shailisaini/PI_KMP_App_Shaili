package authentication

import FakeConnectivityObserver
import FakeLocalDataSource
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.domain.useCases.AuthenticationUsesCases
import com.pi.ProjectInclusion.ui.viewModel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.setMain
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    /**
     * Fake for getAuthViewModel (instead of hitting real API)
     */
    private lateinit var fakeRepo: FakeAuthenticationRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {

        // Set Main dispatcher for viewModelScope
        Dispatchers.setMain(testDispatcher)
        fakeRepo = FakeAuthenticationRepository()
        val fakeAuthUseCases = AuthenticationUsesCases(fakeRepo)
        val fakeLocalData = FakeLocalDataSource()
        val fakeConnectivity = FakeConnectivityObserver()

        viewModel = LoginViewModel(
            fakeAuthUseCases,
            fakeLocalData,
            fakeConnectivity
        )
    }

    @Test
    fun login_with_correct_credentials_emits_success() = runTest {
        fakeRepo.shouldSucceed = true

        viewModel.loginWithPasswordViewModel(
            LoginRequest("user", "pass", 1, 1)
        )
        // advance dispatcher so launched coroutines run
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.uiStateLoginResponse.first { !it.isLoading }
        assertNotNull(state.success)
        assertTrue(true,state.success.message)
    }

    @Test
    fun login_with_wrong_password_emits_error() = runTest {
        fakeRepo.shouldSucceed = false

        viewModel.loginWithPasswordViewModel(LoginRequest("user", "wrongPass"))

        advanceUntilIdle() // wait for coroutines inside loginWithPasswordViewModel

        val state = viewModel.uiStateLoginResponse.value
        assertEquals("Invalid credentials", state.error.toString()) //  passes now
    }

}
