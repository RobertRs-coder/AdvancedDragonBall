package com.advanced.advanceddragonball.data

import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.LocationRemoteToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.fakes.FakeLocalDataSource
import com.advanced.advanceddragonball.fakes.FakePrefsDataStore
import com.advanced.advanceddragonball.ui.list.HeroListState
import com.advanced.advanceddragonball.utils.generateHeroes
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplWithMocksTest {

    //UUT o SUT
    private lateinit var repositoryImpl: RepositoryImpl
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource

    private val prefsDataStore = FakePrefsDataStore()

    @Before
    fun setUp(){
        localDataSource = mockk()
        remoteDataSource = mockk()

        repositoryImpl = RepositoryImpl(
            localDataSource,
            remoteDataSource,
            prefsDataStore,
            RemoteToPresentationMapper(),
            RemoteToLocalMapper(),
            LocalToPresentationMapper(),
            LocationRemoteToPresentationMapper()
        )
    }

//    @Test
//    fun `WHEN getHeroes THEN SUCCESS return list from local and remote called`() = runTest {
//        // GIVEN
////        coEvery { localDataSource.getHeroes()} returns emptyList()
//        coEvery { localDataSource.insertHeroes(any()) } returns Unit
//        coEvery { remoteDataSource.getHeroes("TOKEN") } returns HeroListState.Success(generateHeroes())
//
//        // WHEN
//        val actual = repositoryImpl.getHeroes()
//
//        // THEN
//        coVerify { remoteDataSource.getHeroes("TOKEN") }
//        coVerify(exactly = 2) { localDataSource.getHeroes() }
//    }
//
//    @Test
//    fun `WHEN getHeroes THEN SUCCESS return list from local and remote called with FAKE`() = runTest {
//        // GIVEN
//        val localDataSource = FakeLocalDataSource()
//        repositoryImpl = RepositoryImpl(
//            localDataSource,
//            remoteDataSource,
//            prefsDataStore,
//            RemoteToPresentationMapper(),
//            RemoteToLocalMapper(),
//            LocalToPresentationMapper()
//        )
//
//        coEvery { remoteDataSource.getHeroes("TOKEN") } returns HeroListState.Success(generateHeroes())
//
//        // WHEN
//        val actual = repositoryImpl.getHeroes()
//
//        // THEN
//        Truth.assertThat(actual).isNotEmpty()
//        Truth.assertThat(actual.getOrNull()?.first().name).isEqualTo("Name 0")
//        coVerify { remoteDataSource.getHeroes() }
//        Truth.assertThat(actual).containsExactlyElementsIn(generateHeros())
//    }
}