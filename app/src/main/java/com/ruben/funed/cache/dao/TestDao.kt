package com.ruben.funed.cache.dao

import androidx.room.*
import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.cache.entity.UpdateAnswer
import com.ruben.funed.cache.entity.UpdateAnswerImage
import com.ruben.funed.cache.entity.UpdateCompleteAnswer

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@Dao
abstract class TestDao {

    @Transaction
    open suspend fun deleteAndInsert(testData: List<TestEntity>) {
        delete()
        insert(testData)
    }

    @Query("DELETE FROM test_data")
    abstract suspend fun delete()

    @Insert
    abstract suspend fun insert(data: List<TestEntity>)

    @Update(entity = TestEntity::class)
    abstract suspend fun updateAnswerText(updateAnswer: UpdateAnswer)

    @Update(entity = TestEntity::class)
    abstract suspend fun updateAnswerImage(updateAnswerImage: UpdateAnswerImage)

    @Update(entity = TestEntity::class)
    abstract suspend fun updateCompleteAnswer(updateCompleteAnswer: UpdateCompleteAnswer)

    @Query("SELECT * FROM test_data")
    abstract suspend fun getTestData(): List<TestEntity>
}