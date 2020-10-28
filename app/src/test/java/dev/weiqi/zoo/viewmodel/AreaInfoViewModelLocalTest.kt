package dev.weiqi.zoo.viewmodel

import dev.weiqi.zoo.bean.AreaInfoBean
import dev.weiqi.zoo.testtool.getOrAwaitValue
import dev.weiqi.zoo.testtool.mockkRelaxed
import dev.weiqi.zoo.testtool.shouldBe
import dev.weiqi.zoo.model.State.Companion.isEmpty
import dev.weiqi.zoo.model.State.Companion.isSuccessfully
import dev.weiqi.zoo.repository.RemoteRepository
import dev.weiqi.zoo.testtool.rule.InstantTaskExecutorRule
import dev.weiqi.zoo.testtool.rule.MainCoroutineScopeRule
import dev.weiqi.zoo.ui.areainfo.AreaInfoViewModel
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

class AreaInfoViewModelLocalTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    private val subject: AreaInfoViewModel = spyk(AreaInfoViewModel())

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
    fun `Request area info bean list successfully`() {
        val excepted = mockkRelaxed<List<AreaInfoBean>>()

        every { mockRepository.getAreaInfoList() } returns flowOf(excepted)

        subject.requestAreaInfoBeanList()

        subject.areaInfoBeanList.getOrAwaitValue().isSuccessfully(excepted) shouldBe true
    }

    @Test
    fun `Request area info bean list failed`() {
        every { mockRepository.getAreaInfoList() } returns flow { throw mockkRelaxed() }

        subject.requestAreaInfoBeanList()

        subject.areaInfoBeanList.getOrAwaitValue().isEmpty() shouldBe true
    }
}