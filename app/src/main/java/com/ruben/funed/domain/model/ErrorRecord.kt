package com.ruben.funed.domain.model

/**
 * Created by ruben.quadros on 19/06/21.
 **/
sealed class ErrorRecord {

    object ClientError: ErrorRecord()
    object ServerError: ErrorRecord()
    object NoNetwork: ErrorRecord()
    object GenericError: ErrorRecord()
}
