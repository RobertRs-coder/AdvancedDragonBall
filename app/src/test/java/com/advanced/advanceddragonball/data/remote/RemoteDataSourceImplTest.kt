package com.advanced.advanceddragonball.data.remote

import android.annotation.SuppressLint
import com.advanced.advanceddragonball.base.BaseNetworkTest
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
        Truth.assertThat(actual.getOrNull()?.name).isEqualTo("Maestro Roshi")
    }


    @Test
    fun `WHEN getHeroDetail EXPECT success and returns hero detail`() = runTest {
        // GIVEN
        remoteDataSourceImpl = RemoteDataSourceImpl(api)

        // WHEN
        val actual = remoteDataSourceImpl.getHeroDetail("Hero", "TOKEN")

        // THEN
        Truth.assertThat(actual).isNotNull()
        Truth.assertThat(actual.getOrNull()?.name).isEqualTo("Maestro Roshi")
    }
}