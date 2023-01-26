package com.advanced.advanceddragonball.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.LocationRemoteToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.fakes.FakePrefsDataStore
import com.advanced.advanceddragonball.fakes.FakeRemoteDataSource
import com.advanced.advanceddragonball.ui.detail.HeroDetailState
import com.google.common.truth.Truth
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplWithFakesTest {

    //UUT o SUT
    private lateinit var repositoryImpl: RepositoryImpl
    private lateinit var localDataSource: LocalDataSource


    private val prefsDataStore = FakePrefsDataStore()
    private val fakeRemoteDataSource = FakeRemoteDataSource()

    @Before
    fun setUp(){
        localDataSource = mockk()


        repositoryImpl = RepositoryImpl(
            localDataSource,
            fakeRemoteDataSource,
            prefsDataStore,
            RemoteToPresentationMapper(),
            RemoteToLocalMapper(),
            LocalToPresentationMapper(),
            LocationRemoteToPresentationMapper()
        )
    }

    //HeroDetail
    @Test
    fun `WHEN getHeroDetail EXPECT success and returns hero detail`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("SUCCESS")

        // THEN
        assert(actual is HeroDetailState.Success)
        Truth.assertThat((actual as HeroDetailState.Success).hero.name).isEqualTo("Maestro Roshi")
    }

    @Test
    fun `WHEN getHeroDetail EXPECT failure with generic error`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("NULL")

        // THEN
        assert(actual is HeroDetailState.Failure)
        assert((actual as HeroDetailState.Failure).error == "Null pointer error")
    }

    @Test
    fun `WHEN getHeroDetail EXPECT failure with generic error in success call`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("SUCCESS_BUT_NULL")

        // THEN
        assert(actual is HeroDetailState.Failure)
        assert((actual as HeroDetailState.Failure).error == null)
    }

    @Test
    fun `WHEN getHeroDetail EXPECT failure with network error`() = runTest {
        // GIVEN

        // WHEN
        val actual = repositoryImpl.getHeroDetail("NETWORK_ERROR")

        // THEN
        assert(actual is HeroDetailState.NetworkError)
        assert((actual as HeroDetailState.NetworkError).code == 204)
    }

}