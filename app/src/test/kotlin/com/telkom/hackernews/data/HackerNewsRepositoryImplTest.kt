package com.telkom.hackernews.data

import com.telkom.hackernews.data.*
import com.telkom.hackernews.data.HackerNewsPreferenceKeys.PREFERENCE_KEY_MY_FAVORITE_STORY
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HackerNewsRepositoryImplTest {

    @Mock
    lateinit var service: HackerNewsService

    @Mock
    lateinit var storage: HackerNewsPreference

    private lateinit var repository: HackerNewsRepository

    @Before
    fun `before each test`() {
        repository = HackerNewsRepositoryImpl(service = service, storage = storage)
    }

    @Test
    fun `given HackerNewsService when call getTopStoriesId then return id of top stories`() {
        val items = listOf(33964285L, 33963383L)

        `when`(service.getTopStoriesId()).thenReturn(Single.just(items))

        repository.getTopStoriesId()
            .test()
            .assertNoErrors()
            .assertValue { it == items }

        verify(service).getTopStoriesId()
    }

    @Test
    fun `given HackerNewsService when call getDetailItem with specify story id then return one of item on top stories`() {
        val storyId = 33964285L
        val response = HackerNewsDetailItemResponse(
            by = "kungfudoi",
            descendants = 44,
            id = 33964285L,
            kids = listOf(33965712L, 33965226L),
            score = 124,
            time = 1670901260,
            title = "UC Berkeley launches SkyPilot to help navigate soaring cloud costs",
            type = "story",
            url = "https://www.datanami.com/2022/12/12/uc-berkeley-launches-skypilot-to-help-navigate-soaring-cloud-costs/"
        )

        `when`(service.getDetailItem(storyId)).thenReturn(Single.just(response))

        repository.getDetailItem(storyId)
            .test()
            .assertNoErrors()
            .assertValue { it == response }

        verify(service).getDetailItem(storyId)
    }

    @Test
    fun `given HackerNewsPreference when call get storage with specify key then return preference value of key`() {
        val value = "UC Berkeley launches SkyPilot to help navigate soaring cloud costs"

        `when`(storage.get(PREFERENCE_KEY_MY_FAVORITE_STORY, "")).thenReturn(value)

        assertEquals(value, repository.getMyFavorite())

        verify(storage).get(PREFERENCE_KEY_MY_FAVORITE_STORY, "")
    }

    @Test
    fun `given HackerNewsPreference when call set storage with specify value then set preference value`() {
        val value = "UC Berkeley launches SkyPilot to help navigate soaring cloud costs"

        repository.setMyFavorite(value)

        verify(storage).set(PREFERENCE_KEY_MY_FAVORITE_STORY, value)
    }

    @After
    fun `after each test`() {
        verifyNoMoreInteractions(service, storage)
    }
}