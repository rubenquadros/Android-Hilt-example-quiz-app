package com.ruben.funed.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@Entity
data class UpdateAnswer(
    @ColumnInfo(name = "question_id")
    val id: String,
    @ColumnInfo(name = "answer")
    val answer: String
)
