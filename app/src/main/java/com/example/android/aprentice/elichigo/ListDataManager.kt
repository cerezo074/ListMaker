package com.example.android.aprentice.elichigo

import android.content.Context
import android.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    fun saveList(list: TaskList) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.task.toHashSet())
        sharedPreferences.apply()
    }

    fun readLists(): ArrayList<TaskList> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferenceContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferenceContents) {
            val itemsHashSet = taskList.value as HashSet<String>
            val list = TaskList(taskList.key, ArrayList(itemsHashSet))

            taskLists.add(list)
        }

        return taskLists
    }

}