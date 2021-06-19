package com.ruben.funed.repository.fakeimplementation.success

import com.ruben.funed.remote.model.TestResponse
import com.ruben.funed.remote.rest.RestApi
import com.ruben.funed.repository.fakeimplementation.RepoTestConstants

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class FakeSuccessApi: RestApi {
    override suspend fun getTest(): TestResponse =
        TestResponse(
            RepoTestConstants.ASSESSMENT_ID,
            RepoTestConstants.ASSESSMENT_NAME,
            RepoTestConstants.ASSESSMENT_SUBJECT,
            RepoTestConstants.ASSESSMENT_TIME,
            RepoTestConstants.ASSESSMENT_QUESTIONS,
            RepoTestConstants.ASSESSMENT_MARKS
        )

}