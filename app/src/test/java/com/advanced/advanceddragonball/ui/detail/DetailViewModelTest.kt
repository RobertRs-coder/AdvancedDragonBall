package com.advanced.advanceddragonball.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.advanced.advanceddragonball.data.Repository
import com.advanced.advanceddragonball.domain.Hero
import com.advanced.advanceddragonball.ui.list.HeroListState
import com.advanced.advanceddragonball.ui.list.HeroListViewModel
import com.advanced.advanceddragonball.utils.generateHero
import com.advanced.advanceddragonball.utils.generateHeroes
import com.advanced.advanceddragonball.utils.generateLocations
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

class DetailViewModelTest {
    //Regla para LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    //SUT
    private lateinit var sut: DetailViewModel
    //Dependencies
    private lateinit var repository: Repository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)

        repository = mockk()

        sut = DetailViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN getDetail EXPECTS success then return HeroDetail`() = runTest {
        // GIVEN
        coEvery { repository.getHeroDetail(any()) } returns HeroDetailState.Success(
            generateHero()
        )

        // WHEN
        sut.getHeroDetail(Hero("ID", "Name", "Photo", "Description", false))
        val actualLiveData = sut.state.getOrAwaitValue()

        // THEN
        Truth.assertThat((actualLiveData as HeroDetailState.Success).hero).isEqualTo(generateHero())
    }

}