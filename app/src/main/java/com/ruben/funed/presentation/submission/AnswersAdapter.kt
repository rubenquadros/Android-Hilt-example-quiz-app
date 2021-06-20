package com.ruben.funed.presentation.submission

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruben.funed.R
import com.ruben.funed.cache.DBConstants
import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.databinding.AnswersCellBinding
import com.ruben.funed.presentation.base.BaseRecyclerViewAdapter
import com.ruben.funed.utility.ApplicationConstants
import dagger.hilt.android.scopes.ActivityScoped
import katex.hourglass.`in`.mathlib.MathView

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@ActivityScoped
class AnswersAdapter: BaseRecyclerViewAdapter<AnswersAdapter.ViewHolder, List<TestEntity>, Any>() {

    private lateinit var items: List<TestEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AnswersCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.qnoTv.text = holder.itemView.context.getString(
            R.string.test_question_number,
            items[position].qno.toString()
        )
        holder.marksTv.text = holder.itemView.context.getString(
            R.string.test_question_marks,
            items[position].marks.toString()
        )
        holder.questionTv.setDisplayText(items[position].question)
        if (items[position].status == DBConstants.DEFAULT_STATUS) {
            holder.answerIv.visibility = View.GONE
            holder.answerTv.visibility = View.VISIBLE
            holder.answerTv.setDisplayText(items[position].status)
        } else {
            if (items[position].type == ApplicationConstants.MC) {
                holder.answerIv.visibility = View.GONE
                holder.answerTv.visibility = View.VISIBLE
                holder.answerTv.setDisplayText(items[position].answer)
            } else {
                if (items[position].answer.isEmpty() &&
                    (items[position].answerImage.isEmpty() || items[position].answerImage == ApplicationConstants.NULL_STRING)) {
                    holder.answerIv.visibility = View.GONE
                    holder.answerTv.visibility = View.VISIBLE
                    holder.answerTv.setDisplayText(items[position].status)
                } else if (items[position].answer.isEmpty()) {
                    holder.answerTv.visibility = View.GONE
                    holder.answerIv.visibility = View.VISIBLE
                    Glide.with(holder.itemView)
                        .load(Uri.parse(items[position].answerImage))
                        .into(holder.answerIv)
                } else if(items[position].answerImage.isEmpty() || items[position].answerImage == ApplicationConstants.NULL_STRING) {
                    holder.answerIv.visibility = View.GONE
                    holder.answerTv.visibility = View.VISIBLE
                    holder.answerTv.setDisplayText(items[position].answer)
                } else {
                    holder.answerTv.visibility = View.VISIBLE
                    holder.answerIv.visibility = View.VISIBLE
                    holder.answerTv.setDisplayText(items[position].answer)
                    Glide.with(holder.itemView)
                        .load(Uri.parse(items[position].answerImage))
                        .into(holder.answerIv)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun setItems(items: List<TestEntity>) {
        this.items = items
    }

    override fun setListener(listener: Any) {
        //do nothing
    }

    class ViewHolder(binding: AnswersCellBinding): RecyclerView.ViewHolder(binding.root) {
        val qnoTv: AppCompatTextView = binding.qnoTv
        val marksTv: AppCompatTextView = binding.marksTv
        val questionTv: MathView = binding.questionTv
        val answerTv: MathView = binding.answerTv
        val answerIv: AppCompatImageView = binding.answerIv
    }
}