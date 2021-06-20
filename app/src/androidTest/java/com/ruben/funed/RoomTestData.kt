package com.ruben.funed

import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.cache.entity.UpdateAnswer
import com.ruben.funed.cache.entity.UpdateAnswerImage
import com.ruben.funed.cache.entity.UpdateCompleteAnswer

/**
 * Created by ruben.quadros on 20/06/21.
 **/
object RoomTestData {
    val DEFAULT_TEST_DATA = getDefaultTestRoomData()
    val NEW_TEST_DATA = getNewTestRoomData()
    const val UPDATED_ANSWER_TEXT = "Frozen raw chicken"
    val UPDATE_ANSWER_TEXT = UpdateAnswer(
        "5828310103619c7514afb9f1",
        UPDATED_ANSWER_TEXT
    )
    const val UPDATED_ANSWER_IMAGE = "file:///usr/0/data/myPicture.jpg"
    val UPDATE_ANSWER_IMAGE = UpdateAnswerImage(
        "5828310103619c7514afb9f1",
        UPDATED_ANSWER_IMAGE
    )
    val UPDATE_COMPLETE_ANSWER = UpdateCompleteAnswer(
        "5828310103619c7514afb9f1",
        UPDATED_ANSWER_TEXT,
        UPDATED_ANSWER_IMAGE
    )
    const val DEFAULT_ANSWER = "Not Attempted"
    const val DEFAULT_ANSWER_IMAGE = "NA"

    private fun getDefaultTestRoomData(): List<TestEntity> {
        val testData: ArrayList<TestEntity> = arrayListOf()
        testData.add(
            TestEntity(
                id = "5828310103619c7514afb9f1",
                qno = 1,
                marks = 1,
                type = "MC",
                question = "<p>Which one of these foods is likely to contain the most bacteria?</p>"
            )
        )
        testData.add(
            TestEntity(
                id = "58577c6203619cc9a8de800b",
                qno = 2,
                marks = 2,
                type = "SA",
                question = "What are communicable diseases? State any two examples."
            )
        )
        return testData
    }

    private fun getNewTestRoomData(): List<TestEntity> {
        val testData: ArrayList<TestEntity> = arrayListOf()
        testData.add(
            TestEntity(
                id = "5828310103619c7514afb9f1",
                qno = 1,
                marks = 1,
                type = "MC",
                question = "<p>Which one of these foods is likely to contain the most bacteria?</p>"
            )
        )
        testData.add(
            TestEntity(
                id = "58577c6203619cc9a8de800b",
                qno = 2,
                marks = 2,
                type = "SA",
                question = "What are communicable diseases? State any two examples."
            )
        )
        testData.add(
            TestEntity(
                id = "5ad447869979b3737f1ad71a",
                qno = 3,
                marks = 3,
                type = "SA",
                question = "<p>Prakhar came back from school and ate the rice&nbsp;<span>that was kept outside on the dining table. After&nbsp;</span><span>few hours, he complained of diarrhoea and&nbsp;</span><span>abdominal pain. His mother took him to the&nbsp;</span><span>doctor who explained why this happened and&nbsp;</span><span>asked them to preserve their food properly.&nbsp;</span></p><p>(a) What must happened to Prakhar?</p><p>(b) Give the probable cause for his condition.</p><p>(c) Mention the values shown by the doctor.</p>"
            )
        )
        return testData
    }
}