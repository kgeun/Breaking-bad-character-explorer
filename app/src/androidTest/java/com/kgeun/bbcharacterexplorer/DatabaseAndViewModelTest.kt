package com.kgeun.bbcharacterexplorer

import androidx.lifecycle.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kgeun.bbcharacterexplorer.constants.BBConstants
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.network.BBService
import com.kgeun.bbcharacterexplorer.viewmodel.BBMainViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Rule
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.inject.Singleton

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DatabaseAndViewModelTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var mainViewModel: BBMainViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var analyticsAdapter: AnalyticsAdapter

    var charactersList: List<BBCharacter>? = null

    @Before
    fun init() {
        hiltRule.inject()

        mainViewModel = BBMainViewModel(analyticsAdapter.mainDao, analyticsAdapter.bbService)
        mainViewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                analyticsAdapter.mainDao.truncateCharacters()
            }
        }

        val charactersInputStream: InputStream = context.assets.open("character_result.json")
        val charactersRawString =
            BufferedReader(InputStreamReader(charactersInputStream)).use { it.readText() }

        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            BBCharacter::class.java
        )
        val adapter: JsonAdapter<List<BBCharacter>> = analyticsAdapter.moshi.adapter<List<BBCharacter>>(listMyData)
        charactersList = adapter.fromJson(charactersRawString)
    }

    @Test
    fun databaseTest() {
        mainViewModel.viewModelScope.launch {
            val data = BBCharacter(
                0,
                "TEST",
                "test",
                listOf("1"),
                "A",
                "B",
                "C",
                listOf(1,2,3),
                "1",
                "2",
                listOf()
            )
            withContext(Dispatchers.IO) {
                analyticsAdapter.mainDao.insertCharacter(listOf(data))
            }

            // DB, ViewModel Test (livedata)
            analyticsAdapter.mainDao.getCharactersList().observeOnce {
                if (it != null) {
                    assertEquals(data, it[0])
                }
            }
        }
    }

    @Test
    fun testNumberOfSelectedSeasons() {
        assertEquals(0, mainViewModel.numberOfSelectedSeasons(BBConstants.seasonItems))
    }


    @Test
    fun testGetCharacterByCharId() {
        mainViewModel.viewModelScope.launch {
            mainViewModel.getCharacterByCharId(1).observeOnce {
                if (it != null) {
                    assertEquals(charactersList?.get(1)!!, it)
                }
            }
        }
    }

    @Test
    fun testCreatedDynamicQuery1() {
        val query = mainViewModel.createDynamicQueryForSeasonSearch(listOf(1,2,3))

        assertEquals("SELECT * FROM character WHERE appearance LIKE ? AND appearance LIKE ? AND appearance LIKE ?"
            + " ORDER BY char_id ASC;", query.sql)
    }

    @Test
    fun testCreatedDynamicQuery2() {
        val query = mainViewModel.createDynamicQueryForKeywordAndSeasonSearch("value", listOf(1,2,3))

        assertEquals("SELECT * FROM character WHERE appearance LIKE ? AND appearance LIKE ? AND appearance LIKE ?"
                + " AND name LIKE ? ORDER BY char_id ASC;", query.sql)
    }
}

class CustomObserver<T>(private val handler: (T?) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)
    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T?) -> Unit) {
    val observer = CustomObserver(handler = onChangeHandler)
    observe(observer, observer)
}
