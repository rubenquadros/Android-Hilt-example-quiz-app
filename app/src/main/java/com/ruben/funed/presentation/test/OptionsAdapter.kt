package com.ruben.funed.presentation.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruben.funed.databinding.OptionsCellBinding
import com.ruben.funed.presentation.base.BaseRecyclerViewAdapter
import dagger.hilt.android.scopes.FragmentScoped
import katex.hourglass.`in`.mathlib.MathView

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@FragmentScoped
class OptionsAdapter : BaseRecyclerViewAdapter<OptionsAdapter.ViewHolder, List<String>, OptionsAdapter.AnswerListener>() {

  private lateinit var items: List<String>
  private lateinit var listener: AnswerListener

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = OptionsCellBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.optionsTv.setDisplayText(items[position])
    holder.optionsTv.setOnClickListener {

    }
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun setItems(items: List<String>) {
    this.items = items
  }

  override fun setListener(listener: AnswerListener) {
    this.listener = listener
  }

  class ViewHolder(binding: OptionsCellBinding) : RecyclerView.ViewHolder(binding.root) {
    val optionsTv: MathView = binding.optionsTv
  }

  interface AnswerListener {
    fun onAnswerSelected()
  }
}