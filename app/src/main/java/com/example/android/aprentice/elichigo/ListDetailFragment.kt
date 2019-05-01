package com.example.android.aprentice.elichigo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListDetailFragment : Fragment() {

    var list: TaskList? = null
    private lateinit var listItemsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_detail, container, false)

        view?.let {
            listItemsRecyclerView = it.findViewById(R.id.list_items_recyclerview)
            listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter( list ?: TaskList(name = ""))
            listItemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        }

        return view
    }

    fun addTask(item: String) {
        val adapter = listItemsRecyclerView.adapter as ListItemsRecyclerViewAdapter
        adapter.insertTask(item)
    }

    companion object {
        @JvmStatic
        fun newInstance(list: TaskList): ListDetailFragment {
            val fragment = ListDetailFragment()
            val args = Bundle()
            args.putParcelable(MainActivity.INTENT_LIST_KEY, list)
            fragment.arguments = args

            return fragment
        }
    }
}
