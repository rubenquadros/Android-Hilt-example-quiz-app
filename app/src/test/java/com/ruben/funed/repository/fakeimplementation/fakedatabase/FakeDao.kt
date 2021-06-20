package com.ruben.funed.repository.fakeimplementation.fakedatabase

import com.ruben.funed.cache.dao.TestDao
import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.cache.entity.UpdateMcqAnswer
import com.ruben.funed.cache.entity.UpdateShortAnswer
import com.ruben.funed.repository.fakeimplementation.RepoTestConstants

/**
 * Created by ruben.quadros on 21/06/21.
 **/
class FakeDao: TestDao() {

    override suspend fun delete() {
        //do nothing
        //fire and forget
        //tested in cache module
    }

    override suspend fun insert(data: List<TestEntity>) {
        //do nothing
        //fire and forget
        //tested in cache module
    }

    override suspend fun updateMcqAnswer(updateAnswer: UpdateMcqAnswer) {
        //do nothing
        //fire and forget
        //tested in cache module
    }

    override suspend fun updateShortAnswer(updateShortAnswer: UpdateShortAnswer) {
        //do nothing
        //fire and forget
        //tested in cache module
    }

    override suspend fun getTestData(): List<TestEntity> {
        return RepoTestConstants.DATABASE_DATA
    }
}