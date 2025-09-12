package authentication

import FakeConnectivityObserver
import FakeLocalDataSource
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
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
import kotlin.Int
import kotlin.String
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
            fakeAuthUseCases, fakeLocalData, fakeConnectivity
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
        assertTrue(true, state.success.message)
    }

    @Test
    fun login_with_wrong_password_emits_error() = runTest {
        fakeRepo.shouldSucceed = false

        viewModel.loginWithPasswordViewModel(LoginRequest("user", "wrongPass"))

        advanceUntilIdle() // wait for coroutines inside loginWithPasswordViewModel

        val state = viewModel.uiStateLoginResponse.value
        assertEquals("Invalid credentials", state.error.toString()) //  passes now
    }


    // user Profile
    @Test
    fun user_Profile_emits_success() = runTest {
        fakeRepo.shouldSucceed = true

        viewModel.getUserProfileViewModel(
            "token", "name"
        )
        // advance dispatcher so launched coroutines run
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.viewUserProfile.first { !it.isLoading }
        assertNotNull(state.success)
        assertTrue(true, state.success.message)
    }

    @Test
    fun user_profile_emits_error() = runTest {
        fakeRepo.shouldSucceed = false

        viewModel.getUserProfileViewModel("token", "wrongUser")

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewUserProfile.first { it.error.isNotEmpty() }
        assertNotNull(state.error)
        assertEquals("Invalid User", state.error)
    }

    @Test
    fun select_language_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getLanguages()
        testDispatcher.scheduler.advanceUntilIdle()
        val languageState = viewModel._uiState.first { !it.isLoading }
        assertNotNull(languageState.success)
        assertTrue(
            languageState.success.message?.isNotEmpty() == true, languageState.success.message
        )
    }

    @Test
    fun select_language_fail() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getLanguages()
        testDispatcher.scheduler.advanceUntilIdle()
        val languageState = viewModel._uiState.first { it.error.isNotEmpty() }
        assertNotNull(languageState.error)
        assertEquals("data not found", languageState.error)
    }

    @Test
    fun select_user_type_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getUserType()
        testDispatcher.scheduler.advanceUntilIdle()
        val selectUserTypeState = viewModel._uiStateUserType.first { !it.isLoading }
        assertNotNull(selectUserTypeState.success)
        assertTrue(
            selectUserTypeState.success.message?.isNotEmpty() == true,
            selectUserTypeState.success.message
        )
    }

    @Test
    fun select_user_type_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getUserType()
        testDispatcher.scheduler.advanceUntilIdle()
        val selectUserTypeState = viewModel._uiStateUserType.first { it.error.isNotEmpty() }
        assertNotNull(selectUserTypeState.error)
        assertEquals("data not found", selectUserTypeState.error)
    }

    @Test
    fun oTPOnWhatsapp_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getOTPWhatsappViewModel("user")
        testDispatcher.scheduler.advanceUntilIdle()
        val oTPOnWhatsappState = viewModel._uiStateSendOtp.first { !it.isLoading }
        assertNotNull(oTPOnWhatsappState.success)
        assertTrue(
            oTPOnWhatsappState.success.message?.isNotEmpty() == true,
            oTPOnWhatsappState.success.message
        )
    }

    @Test
    fun oTPOnWhatsapp_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getOTPWhatsappViewModel("user")
        testDispatcher.scheduler.advanceUntilIdle()
        val oTPOnWhatsappState = viewModel._uiStateSendOtp.first { it.error.isNotEmpty() }
        assertNotNull(oTPOnWhatsappState.error)
        assertEquals("Invalid User", oTPOnWhatsappState.error)
    }

    @Test
    fun validate_user_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getValidateUser("user", "1")
        testDispatcher.scheduler.advanceUntilIdle()
        val validateUserState = viewModel._uiStateValidateUser.first { !it.isLoading }
        assertNotNull(validateUserState.success)
        assertTrue(
            validateUserState.success.message?.isNotEmpty() == true,
            validateUserState.success.message
        )
    }

    @Test
    fun validate_user_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getValidateUser("user", "1")
        testDispatcher.scheduler.advanceUntilIdle()
        val validateUserState = viewModel._uiStateValidateUser.first { it.error.isNotEmpty() }
        assertNotNull(validateUserState.error)
        assertEquals("Invalid User", validateUserState.error)
    }

    @Test
    fun verifyOtp_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getVerifyOtpViewModel("user", "123456")
        testDispatcher.scheduler.advanceUntilIdle()
        val verifyOtpState = viewModel.verifyLogin.first { !it.isLoading }
        assertNotNull(verifyOtpState.success)
        assertTrue(
            verifyOtpState.success.message?.isNotEmpty() == true, verifyOtpState.success.message
        )
    }

    @Test
    fun verifyOtp_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getVerifyOtpViewModel("user", "123456")
        testDispatcher.scheduler.advanceUntilIdle()
        val verifyOtpState = viewModel.verifyLogin.first { it.error.isNotEmpty() }
        assertNotNull(verifyOtpState.error)
        assertEquals("Invalid OTP", verifyOtpState.error)
    }

    @Test
    fun loginWithOTP_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getLoginWithOtpViewModel(LoginWithOtpRequest("user", "123456"))
        testDispatcher.scheduler.advanceUntilIdle()
        val loginWithOTPState = viewModel.loginWithOtpState.first { !it.isLoading }
        assertNotNull(loginWithOTPState.success)
        assertTrue(
            loginWithOTPState.success.message?.isNotEmpty() == true,
            loginWithOTPState.success.message
        )
    }

    @Test
    fun loginWithOTP_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getLoginWithOtpViewModel(LoginWithOtpRequest("user", "123456"))
        testDispatcher.scheduler.advanceUntilIdle()
        val loginWithOTPState = viewModel.loginWithOtpState.first { it.error.isNotEmpty() }
        assertNotNull(loginWithOTPState.error)
        assertEquals("Invalid OTP", loginWithOTPState.error)
    }

    @Test
    fun forgetPassword_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.forgetPassword(ForgetPasswordRequest("user", 1, "pass"), "token")
        testDispatcher.scheduler.advanceUntilIdle()
        val forgetPasswordState = viewModel.forgetPassword.first { !it.isLoading }
        assertNotNull(forgetPasswordState.success)
        assertTrue(
            forgetPasswordState.success.message?.isNotEmpty() == true,
            forgetPasswordState.success.message
        )
    }

    @Test
    fun forgetPassword_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.forgetPassword(ForgetPasswordRequest("user", 1, "pass"), "token")
        testDispatcher.scheduler.advanceUntilIdle()
        val forgetPasswordState = viewModel.forgetPassword.first { it.error.isNotEmpty() }
        assertNotNull(forgetPasswordState.error)
        assertEquals("Invalid Password", forgetPasswordState.error)
    }

    @Test
    fun createRegisterPassword_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.createRegisterPassword(
            CreatePasswordRequest("user", "pass", "user", 1, 1), "token"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        val createRegPasswordState = viewModel.createRegPassword.first { !it.isLoading }
        assertNotNull(createRegPasswordState.success)
        assertTrue(
            createRegPasswordState.success.message?.isNotEmpty() == true,
            createRegPasswordState.success.message
        )
    }

    @Test
    fun createRegisterPassword_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.createRegisterPassword(
            CreatePasswordRequest("user", "pass", "user", 1, 1), "token"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        val createRegPasswordState = viewModel.createRegPassword.first { it.error.isNotEmpty() }
        assertNotNull(createRegPasswordState.error)
        assertEquals("Invalid User/Password", createRegPasswordState.error)
    }

    @Test
    fun createFirstStepProfile_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.createFirstStepProfileRepo(
            FirstStepProfileRequest(
                "firstname",
                "middlename",
                "lastname",
                "gender",
                "mobile",
                "whatsapp",
                "dob",
                "email"
            ), "token"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        val firstStepProfileState = viewModel.firstStepProfilePassword.first { !it.isLoading }
        assertNotNull(firstStepProfileState.success)
        assertTrue(
            firstStepProfileState.success.message?.isNotEmpty() == true,
            firstStepProfileState.success.message
        )
    }

    @Test
    fun createFirstStepProfile_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.createFirstStepProfileRepo(
            FirstStepProfileRequest(
                "firstname",
                "middlename",
                "lastname",
                "gender",
                "mobile",
                "whatsapp",
                "dob",
                "email"
            ), "token"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        val firstStepProfileState =
            viewModel.firstStepProfilePassword.first { it.error.isNotEmpty() }
        assertNotNull(firstStepProfileState.error)
        assertEquals("Invalid Fields", firstStepProfileState.error)
    }

    @Test
    fun allStateList_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllStateList()
        testDispatcher.scheduler.advanceUntilIdle()
        val allStatesState = viewModel.allStates.first { !it.isLoading }
        assertNotNull(allStatesState.success)
        assertTrue(
            allStatesState.success.isNotEmpty() == true, allStatesState.success.toString()
        )
    }

    @Test
    fun allStateList_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllStateList()
        testDispatcher.scheduler.advanceUntilIdle()
        val allStatesState = viewModel.allStates.first { it.error.isNotEmpty() }
        assertNotNull(allStatesState.error)
        assertEquals("Data not found", allStatesState.error)
    }

    @Test
    fun allDistrictByStateId_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllDistrictByStateId(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val allDistrictState = viewModel.allDistricts.first { !it.isLoading }
        assertNotNull(allDistrictState.success)
        assertTrue(
            allDistrictState.success.isNotEmpty() == true, allDistrictState.success.toString()
        )
    }

    @Test
    fun allDistrictByStateId_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllDistrictByStateId(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val allDistrictState = viewModel.allDistricts.first { it.error.isNotEmpty() }
        assertNotNull(allDistrictState.error)
        assertEquals("Data not found", allDistrictState.error)
    }

    @Test
    fun allBlockByDistrictId_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllBlockByDistrictId(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val allBlocksState = viewModel.allBlocks.first { !it.isLoading }
        assertNotNull(allBlocksState.success)
        assertTrue(
            allBlocksState.success.isNotEmpty() == true, allBlocksState.success.toString()
        )
    }

    @Test
    fun allBlockByDistrictId_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllBlockByDistrictId(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val allBlocksState = viewModel.allBlocks.first { it.error.isNotEmpty() }
        assertNotNull(allBlocksState.error)
        assertEquals("Data not found", allBlocksState.error)
    }

    @Test
    fun allSchoolsByBlockId_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllSchoolsByBlockId(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val allSchoolsState = viewModel.allSchools.first { !it.isLoading }
        assertNotNull(allSchoolsState.success)
        assertTrue(
            allSchoolsState.success.message?.isNotEmpty() == true, allSchoolsState.success.message
        )
    }

    @Test
    fun allSchoolsByBlockId_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllSchoolsByBlockId(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val allSchoolsState = viewModel.allSchools.first { it.error.isNotEmpty() }
        assertNotNull(allSchoolsState.error)
        assertEquals("Data not found", allSchoolsState.error)
    }

    @Test
    fun allDetailsByUdiseId_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllDetailsByUdiseId("12345678901")
        testDispatcher.scheduler.advanceUntilIdle()
        val allUdiseCodeState = viewModel.allUdiseCode.first { !it.isLoading }
        assertNotNull(allUdiseCodeState.success)
        assertTrue(
            allUdiseCodeState.success.message?.isNotEmpty() == true,
            allUdiseCodeState.success.message
        )
    }

    @Test
    fun allDetailsByUdiseId_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllDetailsByUdiseId("12345678901")
        testDispatcher.scheduler.advanceUntilIdle()
        val allUdiseCodeState = viewModel.allUdiseCode.first { it.error.isNotEmpty() }
        assertNotNull(allUdiseCodeState.error)
        assertEquals("Data not found", allUdiseCodeState.error)
    }

    @Test
    fun createProfessionalProfile_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.createProfessionalProfileRepo(
            ProfessionalProfileRequest(
                "udiseCode", 0, 0, 0, 0, 0, 0, 0, 0, "crrNo"
            ), "token"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        val professionalState = viewModel.professionalProfile.first { !it.isLoading }
        assertNotNull(professionalState.success)
        assertTrue(
            professionalState.success.message?.isNotEmpty() == true,
            professionalState.success.message
        )
    }

    @Test
    fun createProfessionalProfile_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.createProfessionalProfileRepo(
            ProfessionalProfileRequest(
                "udiseCode", 0, 0, 0, 0, 0, 0, 0, 0, "crrNo"
            ), "token"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        val professionalState = viewModel.professionalProfile.first { it.error.isNotEmpty() }
        assertNotNull(professionalState.error)
        assertEquals("Invalid Fields", professionalState.error)
    }

    @Test
    fun allProfession_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllProfessionRepo()
        testDispatcher.scheduler.advanceUntilIdle()
        val professionState = viewModel.professionList.first { !it.isLoading }
        assertNotNull(professionState.success)
        assertTrue(
            professionState.success.isNotEmpty() == true, professionState.success.toString()
        )
    }

    @Test
    fun allProfession_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllProfessionRepo()
        testDispatcher.scheduler.advanceUntilIdle()
        val professionState = viewModel.professionList.first { it.error.isNotEmpty() }
        assertNotNull(professionState.error)
        assertEquals("Data not found", professionState.error)
    }

    @Test
    fun allQualification_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllQualificationRepo(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val qualificationState = viewModel.qualificationList.first { !it.isLoading }
        assertNotNull(qualificationState.success)
        assertTrue(
            qualificationState.success.isNotEmpty() == true, qualificationState.success.toString()
        )
    }

    @Test
    fun allQualification_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllQualificationRepo(0)
        testDispatcher.scheduler.advanceUntilIdle()
        val qualificationState = viewModel.qualificationList.first { it.error.isNotEmpty() }
        assertNotNull(qualificationState.error)
        assertEquals("Data not found", qualificationState.error)
    }

    @Test
    fun allSpecialization_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllSpecializationRepo(0, 0)
        testDispatcher.scheduler.advanceUntilIdle()
        val specializationState = viewModel.specializationList.first { !it.isLoading }
        assertNotNull(specializationState.success)
        assertTrue(
            specializationState.success.isNotEmpty() == true, specializationState.success.toString()
        )
    }

    @Test
    fun allSpecialization_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllSpecializationRepo(0, 0)
        testDispatcher.scheduler.advanceUntilIdle()
        val specializationState = viewModel.specializationList.first { it.error.isNotEmpty() }
        assertNotNull(specializationState.error)
        assertEquals("Data not found", specializationState.error)
    }

    @Test
    fun allReason_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getAllReasonRepo()
        testDispatcher.scheduler.advanceUntilIdle()
        val reasonListState = viewModel.reasonList.first { !it.isLoading }
        assertNotNull(reasonListState.success)
        assertTrue(
            reasonListState.success.message?.isNotEmpty() == true,
            reasonListState.success.message.toString()
        )
    }

    @Test
    fun allReason_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getAllReasonRepo()
        testDispatcher.scheduler.advanceUntilIdle()
        val reasonListState = viewModel.reasonList.first { it.error.isNotEmpty() }
        assertNotNull(reasonListState.error)
        assertEquals("Data not found", reasonListState.error)
    }

    @Test
    fun forceUpdateApp_success() = runTest {
        fakeRepo.shouldSucceed = true
        viewModel.getForceUpdateApp(0.0, 0.0)
        testDispatcher.scheduler.advanceUntilIdle()
        val forceUpdateState = viewModel.forceUpdate.first { !it.isLoading }
        assertNotNull(forceUpdateState.success)
        assertTrue(
            forceUpdateState.success.message?.isNotEmpty() == true,
            forceUpdateState.success.message.toString()
        )
    }

    @Test
    fun forceUpdateApp_failed() = runTest {
        fakeRepo.shouldSucceed = false
        viewModel.getForceUpdateApp(0.0, 0.0)
        testDispatcher.scheduler.advanceUntilIdle()
        val forceUpdateState = viewModel.forceUpdate.first { it.error.isNotEmpty() }
        assertNotNull(forceUpdateState.error)
        assertEquals("Data not found", forceUpdateState.error)
    }
}