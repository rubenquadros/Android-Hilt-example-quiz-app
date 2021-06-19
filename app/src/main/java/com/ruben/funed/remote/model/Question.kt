package com.ruben.funed.remote.model

/**
 * Created by ruben.quadros on 19/06/21.
 **/
data class Question(
    val id: String,
    val qno: Long,
    val text: String,
    val mcOptions: List<String>,
    val type: String,
    val marks: Long
)