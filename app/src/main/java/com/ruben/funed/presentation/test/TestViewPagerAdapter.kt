package com.ruben.funed.presentation.test

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ruben.funed.remote.model.Question

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class TestViewPagerAdapter(
    activity: AppCompatActivity,
    private val questions: ArrayList<Question>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun createFragment(position: Int): Fragment {
        return TestFragment.newInstance(
            questions[position],
            position == 0,
            position == questions.size - 1
        )
    }
}