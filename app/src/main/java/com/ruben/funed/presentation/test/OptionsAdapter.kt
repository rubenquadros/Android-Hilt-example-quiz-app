package com.ruben.funed.presentation.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ruben.funed.R
import com.ruben.funed.databinding.OptionsCellBinding
import com.ruben.funed.domain.model.OptionsRecord
import com.ruben.funed.presentation.base.BaseRecyclerViewAdapter
import dagger.hilt.android.scopes.FragmentScoped
import katex.hourglass.`in`.mathlib.MathView

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@FragmentScoped
class OptionsAdapter : BaseRecyclerViewAdapter<OptionsAdapter.ViewHolder, OptionsRecord, OptionsAdapter.AnswerListener>() {

  private lateinit var items: OptionsRecord
  private lateinit var listener: AnswerListener
  private var answerPosition = -1
  private var answer = ""

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = OptionsCellBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.optionsTv.setDisplayText(items.options[position])
    if (items.isSelected[position]) {
      holder.optionsParent.setCardBackgroundColor(
          ContextCompat.getColor(holder.itemView.context, R.color.orange_200)
      )
      holder.optionsTv.setBackgroundColor(
          ContextCompat.getColor(holder.itemView.context, R.color.orange_200))
    } else {
      holder.optionsParent.setCardBackgroundColor(
          ContextCompat.getColor(holder.itemView.context, R.color.white)
      )
      holder.optionsTv.setBackgroundColor(
          ContextCompat.getColor(holder.itemView.context, R.color.white)
      )
    }
    holder.optionsTv.setOnClickListener {
      if (answerPosition != -1 && position != answerPosition) {
        items.isSelected[answerPosition] = !items.isSelected[answerPosition]
        notifyItemChanged(answerPosition)
        items.isSelected[position] = !items.isSelected[position]
        notifyItemChanged(position)
        listener.onAnswerSelected(items.options[position], position)

      } else if (position != answerPosition) {
        items.isSelected[position] = !items.isSelected[position]
        notifyItemChanged(position)
        listener.onAnswerSelected(items.options[position], position)
      }
      answer = items.options[position]
      answerPosition = position
    }
  }

  override fun getItemCount(): Int {
    return items.options.size
  }

  override fun setItems(items: OptionsRecord) {
    this.items = items
  }

  override fun setListener(listener: AnswerListener) {
    this.listener = listener
  }

  class ViewHolder(binding: OptionsCellBinding) : RecyclerView.ViewHolder(binding.root) {
    val optionsParent: CardView = binding.optionsParent
    val optionsTv: MathView = binding.optionsTv
  }

  interface AnswerListener {
    fun onAnswerSelected(answer: String, position: Int)
  }
}