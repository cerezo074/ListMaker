package com.example.android.aprentice.elichigo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val taskTextView: TextView = itemView.findViewById(R.id.textview_task)

}