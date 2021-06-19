package com.ruben.funed.domain.model

/**
 * Created by ruben.quadros on 19/06/21.
 **/
data class Record<out R>(
    val status: StatusRecord,
    val data: R?,
    val error: ErrorRecord?
)
