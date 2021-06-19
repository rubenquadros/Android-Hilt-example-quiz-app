package com.ruben.funed.remote.model

/**
 * Created by ruben.quadros on 19/06/21.
 **/
data class TestResponse(
    val assesmentId: String,
    val assessmentName: String,
    val subject: String,
    val duration: Long,
    val question: List<Question>,
    val totalMarks: Long
)