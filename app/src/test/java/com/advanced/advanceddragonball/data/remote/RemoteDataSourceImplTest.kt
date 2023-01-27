package com.advanced.advanceddragonball.data.remote

import android.annotation.SuppressLint
import com.advanced.advanceddragonball.base.BaseNetworkTest
import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.fakes.FakeRemoteDataSource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@SuppressLint("CheckResult")
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceImplTest: BaseNetworkTest() {
    // SUT o UUT
    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @Test
    fun `WHEN getHeroes EXPECT success and returns hero list`() = runTest {
        // GIVEN
        remoteDataSourceImpl = RemoteDataSourceImpl(api)

        // WHEN
        val actual = remoteDataSourceImpl.getHeroes("TOKEN")

        // THEN
        Truth.assertThat(actual).isNotNull()
        Truth.assertThat(actual.getOrNull()).hasSize(15)
        Truth.assertThat(actual.getOrNull()?.first()?.name).isEqualTo("Maestro Roshi")
    }

    @Test
    fun `WHEN getHeroDetail EXPECT success and returns hero`() = runTest {
        // GIVEN
        remoteDataSourceImpl = RemoteDataSourceImpl(api)

        // WHEN
        val actual = remoteDataSourceImpl.getHeroDetail("Hero", "TOKEN")

        // THEN
        Truth.assertThat(actual).isNotNull()
        Truth.assertThat(actual.getOrNull()?.name).isEqualTo("Goku")
    }


    @Test
    fun `WHEN getHeroDetail EXPECT success and returns hero detail`() = runTest {
        // GIVEN
        remoteDataSourceImpl = RemoteDataSourceImpl(api)

        // WHEN
        val actual = remoteDataSourceImpl.getHeroDetail("Hero", "TOKEN")

        // THEN
        Truth.assertThat(actual).isNotNull()
        Truth.assertThat(actual.getOrNull()?.name).isEqualTo("Goku")
    }


    @Test
    fun `WHEN getLocations with any string EXPECT success and returns hero location`() = runTest {
        // GIVEN
        remoteDataSourceImpl = RemoteDataSourceImpl(api)
        // WHEN
        val actual = remoteDataSourceImpl.getHeroLocations("Goku Locations", "TOKEN")

        // THEN
        assertTrue(actual.isSuccess)
        val heroLocations = actual.getOrNull()?.first()
        heroLocations?.apply {
            Truth.assertThat(heroLocations.latitude).isEqualTo("35.71867899343361")
        }
    }
}