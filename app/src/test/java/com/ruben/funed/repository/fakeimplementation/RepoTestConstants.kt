package com.ruben.funed.repository.fakeimplementation

import com.ruben.funed.remote.model.Question

/**
 * Created by ruben.quadros on 19/06/21.
 **/
object RepoTestConstants {
    const val ASSESSMENT_ID = "5e97fd8512e60c8459f0531c"
    const val ASSESSMENT_NAME = "Unit Test 1"
    const val ASSESSMENT_SUBJECT = "Science"
    const val ASSESSMENT_TIME = 30L
    const val ASSESSMENT_MARKS = 10L
    val ASSESSMENT_QUESTIONS = arrayListOf<Question>(
        Question(
            "5828310103619c7514afb9f1",
            1,
            "<p>Which one of these foods is likely to contain the most bacteria?</p>",
            arrayListOf(
                "<p>Bottled mayonnaise</p>",
                "<p>Cooked chicken</p>",
                "<p>Frozen raw chicken</p>",
                "<p>Tinned cream</p>"
            ),
            "MC",
            1
        ),
        Question(
            "58577c6203619cc9a8de800b",
            2,
            "What are communicable diseases? State any two examples.",
            arrayListOf(),
            "SA",
            2
        ),
        Question(
            "5ad447869979b3737f1ad71a",
            3,
            "<p>Prakhar came back from school and ate the rice&nbsp;<span>that was kept outside on the dining table. After&nbsp;</span><span>few hours, he complained of diarrhoea and&nbsp;</span><span>abdominal pain. His mother took him to the&nbsp;</span><span>doctor who explained why this happened and&nbsp;</span><span>asked them to preserve their food properly.&nbsp;</span></p><p>(a) What must happened to Prakhar?</p><p>(b) Give the probable cause for his condition.</p><p>(c) Mention the values shown by the doctor.</p>",
            arrayListOf(),
            "SA",
            3
        ),
    )
}