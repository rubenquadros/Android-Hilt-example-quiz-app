package com.ruben.funed.domain.model

/**
 * Created by ruben.quadros on 20/06/21.
 **/
data class OptionsRecord(
  val options: List<String>,
  val isSelected: MutableList<Boolean>
)
