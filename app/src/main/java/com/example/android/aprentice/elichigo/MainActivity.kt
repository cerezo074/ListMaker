package com.example.android.aprentice.elichigo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    lateinit var listsRecyclerView: RecyclerView
    val listDataManager: ListDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val lists = ListDataManager(this)
        listsRecyclerView = findViewById<RecyclerView>(R.id.lists_recycler_view)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists.readLists(), this)

        fab.setOnClickListener {
           showCreateListDialog()
        }
    }

    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle)  { dialog, _ ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)

            val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)

            dialog.dismiss()
            showListDetail(list)
        }

        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        startActivity(listDetailIntent)
    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }

    companion object {
        const val INTENT_LIST_KEY = "list"
    }

}
