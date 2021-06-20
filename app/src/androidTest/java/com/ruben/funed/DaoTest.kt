package com.ruben.funed

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ruben.funed.cache.TestDB
import com.ruben.funed.cache.dao.TestDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@RunWith(AndroidJUnit4::class)
class DaoTest {

    private lateinit var database: TestDB
    private lateinit var dao: TestDao

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, TestDB::class.java).build()
        dao = database.testDao()
    }

    @Test
    fun should_be_able_to_successfully_insert_data_in_table() = runBlocking {
        dao.insert(RoomTestData.DEFAULT_TEST_DATA)
        val result = dao.getTestData()
        Assert.assertEquals(RoomTestData.DEFAULT_TEST_DATA.size, result.size)
    }

    @Test
    fun should_be_able_to_successfully_retrieve_data_from_table() = runBlocking {
        dao.insert(RoomTestData.DEFAULT_TEST_DATA)
        val result = dao.getTestData()
        Assert.assertEquals(RoomTestData.DEFAULT_TEST_DATA.size, result.size)
    }

    @Test
    fun should_be_able_to_insert_default_values_in_table() = runBlocking {
        dao.insert(RoomTestData.DEFAULT_TEST_DATA)
        val result = dao.getTestData()
        Assert.assertEquals(RoomTestData.DEFAULT_ANSWER, result[0].answer)
        Assert.assertEquals(RoomTestData.DEFAULT_ANSWER_IMAGE, result[0].answerImage)
        Assert.assertEquals(RoomTestData.DEFAULT_STATUS, result[0].status)
    }

    @Test
    fun should_be_able_to_delete_data_from_table() = runBlocking {
        dao.insert(RoomTestData.DEFAULT_TEST_DATA)
        val result = dao.getTestData()
        Assert.assertEquals(RoomTestData.DEFAULT_TEST_DATA.size, result.size)
        dao.delete()
        val newResult = dao.getTestData()
        Assert.assertEquals(0, newResult.size)
    }

    @Test
    fun should_be_able_to_delete_and_insert_new_data_in_table() = runBlocking {
        dao.insert(RoomTestData.DEFAULT_TEST_DATA)
        val result = dao.getTestData()
        Assert.assertEquals(RoomTestData.DEFAULT_TEST_DATA.size, result.size)
        dao.deleteAndInsert(RoomTestData.NEW_TEST_DATA)
        val newResult = dao.getTestData()
        Assert.assertEquals(RoomTestData.NEW_TEST_DATA.size, newResult.size)
    }

    @Test
    fun should_be_able_to_successfully_update_answer() = runBlocking {
        dao.insert(RoomTestData.DEFAULT_TEST_DATA)
        val result = dao.getTestData()
        Assert.assertEquals(RoomTestData.DEFAULT_ANSWER, result[0].answer)
        dao.updateMcqAnswer(RoomTestData.UPDATE_ANSWER_TEXT)
        val newResult = dao.getTestData()
        Assert.assertEquals(RoomTestData.UPDATED_ANSWER_TEXT, newResult[0].answer)
        Assert.assertEquals(RoomTestData.UPDATED_STATUS, newResult[0].status)
    }

    @Test
    fun should_be_able_to_successfully_update_complete_answer() = runBlocking {
        dao.insert(RoomTestData.DEFAULT_TEST_DATA)
        val result = dao.getTestData()
        Assert.assertEquals(RoomTestData.DEFAULT_ANSWER, result[0].answer)
        Assert.assertEquals(RoomTestData.DEFAULT_ANSWER_IMAGE, result[0].answerImage)
        dao.updateShortAnswer(RoomTestData.UPDATE_COMPLETE_ANSWER)
        val newResult = dao.getTestData()
        Assert.assertEquals(RoomTestData.UPDATED_ANSWER_TEXT, newResult[0].answer)
        Assert.assertEquals(RoomTestData.UPDATED_ANSWER_IMAGE, newResult[0].answerImage)
        Assert.assertEquals(RoomTestData.UPDATED_STATUS, newResult[0].status)
    }
    
    @After
    @Throws(IOException::class)
    fun closeDB() {
        database.close()
    }
}