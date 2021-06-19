package com.ruben.funed.presentation.base

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ruben.quadros on 19/06/21.
 **/
abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder, I : Any, L : Any> :
    RecyclerView.Adapter<VH>() {

      abstract fun setItems(items: I)
      abstract fun setListener(listener: L)
}