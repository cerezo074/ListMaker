package com.example.android.aprentice.elichigo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.widget.EditText
import android.widget.FrameLayout

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListSelectionFragment.OnListItemFragmentInteractionListener {

    private var listSelectionFragment: ListSelectionFragment = ListSelectionFragment.newInstance()
    private var fragmentContainer: FrameLayout? = null
    private var largeScreen = false
    private var listFragment: ListDetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        listSelectionFragment = supportFragmentManager.findFragmentById(R.id.list_selection_fragment) as ListSelectionFragment
        fragmentContainer = findViewById(R.id.fragment_container)
        largeScreen = fragmentContainer != null

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
            listSelectionFragment.addList(list)

            dialog.dismiss()
            showListDetail(list)
        }

        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        if (!largeScreen) {
            val listDetailIntent = Intent(this, ListDetailActivity::class.java)
            listDetailIntent.putExtra(INTENT_LIST_KEY, list)
            startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
        } else {
            removeDetailFragment()

            title = list.name
            val listFragment = ListDetailFragment.newInstance(list)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, listFragment, getString(R.string.list_fragment_tag))
                .addToBackStack(null)
                .commit()

            this.listFragment = listFragment

            fab.setOnClickListener { view ->
                showCreateTaskDialog()
            }
        }
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                val task = taskEditText.text.toString()
                listFragment?.addTask(task)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                listSelectionFragment.saveList(data.getParcelableExtra<TaskList>(INTENT_LIST_KEY))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        title = resources.getString(R.string.app_name)
        listFragment?.list.let {
            val list = it ?: return
            listSelectionFragment.listDataManager.saveList(list)
        }

        removeDetailFragment()

        fab.setOnClickListener { view ->
            showCreateListDialog()
        }
    }

    private fun removeDetailFragment() {
        val listFragment = listFragment ?: return
        supportFragmentManager
            .beginTransaction()
            .remove(listFragment)
            .commit()

        this.listFragment = null
    }

    override fun onListItemClicked(list: TaskList) {
        showListDetail(list)
    }

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 20
    }

}
