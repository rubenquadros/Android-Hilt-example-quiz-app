package com.ruben.funed.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruben.funed.cache.DBConstants

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@Entity(tableName = "test_data")
data class TestEntity(
    @PrimaryKey
    @ColumnInfo(name = "question_id")
    val id: String,
    @ColumnInfo(name = "question_number")
    val qno: Long,
    @ColumnInfo(name = "marks")
    val marks: Long,
    @ColumnInfo(name = "question_type")
    val type: String,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "answer")
    val answer: String = DBConstants.DEFAULT_ANSWER,
    @ColumnInfo(name = "answer_image")
    val answerImage: String = DBConstants.DEFAULT_IMAGE,
    @ColumnInfo(name = "status")
    val status: String = DBConstants.DEFAULT_STATUS
)
