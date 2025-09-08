import com.pi.ProjectInclusion.database.LocalDataSource
import com.pi.ProjectInclusion.domain.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDataSource : LocalDataSource {
    // implement minimal methods if needed, or leave empty if not used
    override fun saveValue(key: String, value: String) {

    }

    override fun getValue(key: String, defaultValue: String): String {
        return ""
    }

    override fun clearValue(key: String) {
        TODO("Not yet implemented")
    }

    override fun clearAll() {
        TODO("Not yet implemented")
    }
}

class FakeConnectivityObserver : ConnectivityObserver {
    override fun observe(): Flow<ConnectivityObserver.Status> =
        flowOf(ConnectivityObserver.Status.Available)

    override fun getCurrentStatus(): ConnectivityObserver.Status =
        ConnectivityObserver.Status.Available
}