package com.advanced.advanceddragonball.data

import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.local.model.HeroLocal
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.data.remote.response.HeroRemote
import com.advanced.advanceddragonball.domain.Bootcamp
import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.fakes.FakeLocalDataSource
import com.advanced.advanceddragonball.utils.generateBootcamps
import com.advanced.advanceddragonball.utils.generateHeroes
import com.advanced.advanceddragonball.utils.generateHeroesLocal
import com.advanced.advanceddragonball.utils.generateHeroesRemote
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
class RepositoryImplTest {
//    //SUT: System Under Testing -> What do you want to test?
//    //UUT o SUT
//    private lateinit var sut: RepositoryImpl
//    private lateinit var localDataSource: LocalDataSource
//    private lateinit var remoteDataSource: RemoteDataSource
//
//    @Before
//    fun setUp(){
//        localDataSource = mockk()
//        remoteDataSource = mockk()
//
//        sut = RepositoryImpl(
//            localDataSource,
//            remoteDataSource,
//            RemoteToPresentationMapper(),
//            RemoteToLocalMapper(),
//            LocalToPresentationMapper()
//        )
//    }
//
//    @Test
//     fun test_getBootcamps_withEmptyList() = runTest {
//        //Given
//        coEvery {remoteDataSource.getBootcamps() } returns listOf<Bootcamp>()
//
//        //When
//        val actual = sut.getBootcamps()
//
//        //Then
//        assert(actual.isEmpty())
//
//        // Version Truth
//        Truth.assertThat(actual).isEmpty()
//    }
//
//    @Test
//    fun test_getBootcamps_withNotEmptyList() = runTest {
//        //Given
//        coEvery {remoteDataSource.getBootcamps() } returns generateBootcamps()
//
//        //When
//        val actual = sut.getBootcamps()
//
//        //Then
//        // Version JUnit
//        assert(actual.isNotEmpty())
//        assertEquals(actual[0].name, "Name 0")
//
//        // Version Truth
//        Truth.assertThat(actual).isNotEmpty()
//        Truth.assertThat(actual.first().name).isEqualTo("Name 0")
//        Truth.assertThat(actual).containsExactlyElementsIn(generateBootcamps())
//    }
//
//    @Test
//    fun test_getHeroes_withLocalEmptyList() = runTest {
//        //Given
//        coEvery { localDataSource.getHeroes() } returns generateHeroesLocal()
//
//        //When
//        val actual = sut.getHeroesWithCache()
//
//        //Then
//        // Version JUnit
//        assert(actual.isNotEmpty())
//        assertEquals(actual[0].name, "Name 0")
//
//        // Version Truth
//        Truth.assertThat(actual).isNotEmpty()
//        Truth.assertThat(actual.first().name).isEqualTo("Name 0")
//        Truth.assertThat(actual).containsExactlyElementsIn(generateHeroes())
//    }
//
//    @Test
//    fun test_getHeroes_withLocalList() = runTest {
//        //Given
//
//        coEvery {localDataSource.getHeroes() } returns listOf<HeroLocal>(
//            HeroLocal("01", "Hero 1", "", "", false))
//
//
//        //When
//        val actual = sut.getHeroesWithCache()
//
//        //Then
//        assert(actual.isNotEmpty())
//        assertEquals(actual[0].name, "Hero 1")
//    }
//
//    @Test
//    fun test_getHeroes_withFakeLocalDataSource() = runTest {
//        //Given
//
//        val localDataSource = FakeLocalDataSource()
//        sut = RepositoryImpl(
//            localDataSource,
//            remoteDataSource,
//            RemoteToPresentationMapper(),
//            RemoteToLocalMapper(),
//            LocalToPresentationMapper()
//        )
//
//        coEvery { remoteDataSource.getHeroes() } returns generateHeroesRemote()
//        //When
//        val actual = sut.getHeroesWithCache()
//
//        //Then
//        assert(actual.isNotEmpty())
//        assertEquals(actual[0].name, "Name 0")
//
//        // THEN
//        Truth.assertThat(actual).isNotEmpty()
//        Truth.assertThat(actual.first().name).isEqualTo("Name 0")
//        coVerify { remoteDataSource.getHeroes() }
//        Truth.assertThat(actual).containsExactlyElementsIn(generateHeroes())
//    }
}