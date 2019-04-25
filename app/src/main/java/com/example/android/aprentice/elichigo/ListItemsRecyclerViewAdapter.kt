package com.example.android.aprentice.elichigo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListItemsRecyclerViewAdapter(private var list: TaskList): RecyclerView.Adapter<ListItemViewHolder>() {

    fun insertTask(task: String) {
        list.task.add(task)
        notifyItemInserted(list.task.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_view_holder, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.task.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.taskTextView.text = list.task[position]
    }

}
