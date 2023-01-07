package com.advanced.advanceddragonball.data

import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.domain.Bootcamp
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {
    //SUT: System Under Testing -> What do you want to test?
    private lateinit var sut: RepositoryImpl

    @Test
     fun test_getBootcamps_withEmptyList() = runTest {
        //Given
        val localDataSource: LocalDataSource = mockk()
        val remoteDataSource: RemoteDataSource = mockk()

        coEvery {remoteDataSource.getBootcamps() } returns listOf<Bootcamp>(Bootcamp("01", "Bootcamp 1"), Bootcamp("02", "Bootcamp 2"))

         sut = RepositoryImpl(
             localDataSource,
             remoteDataSource,
             RemoteToPresentationMapper(),
             RemoteToLocalMapper(),
             LocalToPresentationMapper()
         )

        //When
        val actual = sut.getBootcamps()

        //Then
        assert(actual.isNotEmpty())
    }
}