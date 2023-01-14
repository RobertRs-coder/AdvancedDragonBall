package com.advanced.advanceddragonball.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.data.RepositoryImpl
import com.advanced.advanceddragonball.data.local.LocalDataSource
import com.advanced.advanceddragonball.data.mappers.LocalToPresentationMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToLocalMapper
import com.advanced.advanceddragonball.data.mappers.RemoteToPresentationMapper
import com.advanced.advanceddragonball.data.remote.RemoteDataSource
import com.advanced.advanceddragonball.utils.generateHeroes
import com.advanced.advanceddragonball.utils.getOrAwaitValue
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HeroListViewModelTest {

    //Regla para LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    //SUT
    private lateinit var sut: HeroListViewModel
    //Dependencies
    private lateinit var repository: Repository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)

        repository = mockk()

        sut = HeroListViewModel(
            repository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN getHeroes EXPECTS Success returns list of heroes`() = runTest {

        //Given
        sut = HeroListViewModel(repository)
        coEvery { repository.getHeroes() } returns HeroListState.Success(generateHeroes())

        //When
        val actual = sut.getHeroes()
        val actualLiveData = sut.state.getOrAwaitValue()

        //Then
        Truth.assertThat(actualLiveData).isEqualTo(HeroListState.Success(generateHeroes()))

    }
}