package com.advanced.advanceddragonball.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.ui.list.HeroListState
import com.advanced.advanceddragonball.ui.list.HeroListViewModel
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

class LoginViewModelTest  {
    //Regla para LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    //SUT
    private lateinit var sut: LoginViewModel
    //Dependencies
    private lateinit var repository: Repository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)

        repository = mockk()

        sut = LoginViewModel(
            repository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN login EXPECTS success return login token`() = runTest {

        //Given
        sut = LoginViewModel(repository)
        coEvery {  repository.login("rrojo.va@gmail.com","123456") } returns LoginState.Success("TOKEN")

        //When
        sut.login("rrojo.va@gmail.com", "123456")
        val actualLiveData = sut.state.getOrAwaitValue()

        //Then
        Truth.assertThat((actualLiveData as LoginState.Success).token).isEqualTo("TOKEN")

    }
}