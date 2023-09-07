package com.foxxx.messageboardapp.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.dialogs.adapter.RcvDialogSpinnerAdapter
import com.foxxx.messageboardapp.utils.CityHelper

class DialogSpinner() {
    fun showSpinnerDialog(context: Context, list: ArrayList<String>, itemTextView: TextView) {

        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_dialog, null)

        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setView(rootView)
        dialog.show()

        val sv = rootView.findViewById<SearchView>(R.id.sv_spinner)
        val adapter = RcvDialogSpinnerAdapter()
        adapter.updateAdapter(list)

        adapter.onItemClickListener(
            object : RcvDialogSpinnerAdapter.OnItemClickListener {
                override fun onClick(item: String) {
                    itemTextView.text = item
                    dialog.dismiss()
                }
            }
        )

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.rv_spinner_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        setSearchViewListener(adapter, sv, list)
    }

    private fun setSearchViewListener(
        adapter: RcvDialogSpinnerAdapter,
        searchView: SearchView?,
        list: List<String>
    ) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = CityHelper.filterListData(list, newText)
                adapter.updateAdapter(tempList)
                return true
            }
        })
    }
}