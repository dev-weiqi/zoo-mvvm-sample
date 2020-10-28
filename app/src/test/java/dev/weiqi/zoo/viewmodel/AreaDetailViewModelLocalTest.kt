package dev.weiqi.zoo.viewmodel

import androidx.lifecycle.Observer
import dev.weiqi.zoo.bean.PlantInfoBean
import dev.weiqi.zoo.testtool.anyString
import dev.weiqi.zoo.testtool.getOrAwaitValue
import dev.weiqi.zoo.testtool.mockkRelaxed
import dev.weiqi.zoo.testtool.shouldBe
import dev.weiqi.zoo.model.State
import dev.weiqi.zoo.model.State.Companion.isEmpty
import dev.weiqi.zoo.model.State.Companion.isError
import dev.weiqi.zoo.model.State.Companion.isSuccessfully
import dev.weiqi.zoo.repository.RemoteRepository
import dev.weiqi.zoo.testtool.rule.InstantTaskExecutorRule
import dev.weiqi.zoo.testtool.rule.MainCoroutineScopeRule
import dev.weiqi.zoo.ui.areadetail.AreaDetailViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class AreaDetailViewModelLocalTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    private val subject: AreaDetailViewModel = AreaDetailViewModel()

    @RelaxedMockK
    private lateinit var mockRepository: RemoteRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        startKoin { modules(module { factory { mockRepository } }) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
        stopKoin()
    }

    @Test
    fun `Request plant info bean list successfully`() {
        val excepted = mockkRelaxed<List<PlantInfoBean>>()
        every { mockRepository.getPlantInfoBeanList(any()) } returns flowOf((excepted))
        val mockedObserver = spyk(Observer<State<List<PlantInfoBean>>> { })
        subject.plantInfoBeanList.observeForever(mockedObserver)

        subject.requestPlantInfoBeanList(anyString())

        subject.plantInfoBeanList.getOrAwaitValue().isSuccessfully(excepted) shouldBe true
    }

    @Test
    fun `Request plant info bean list failed`() {
        every { mockRepository.getPlantInfoBeanList(any()) } returns flow { throw Throwable() }

        subject.requestPlantInfoBeanList(anyString())

        subject.plantInfoBeanList.getOrAwaitValue().isError() shouldBe true
    }
}