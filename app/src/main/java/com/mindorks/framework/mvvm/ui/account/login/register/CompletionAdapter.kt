package com.mindorks.framework.mvvm.ui.account.login.register

import android.content.Context
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.mindorks.framework.mvvm.R

class CompletionAdapter(
    context: Context,
    private val resource: Int,
    private val list: MutableList<String>
) : ArrayAdapter<String?>(
    context, resource, list as List<String?>
) {
    fun removeItems() {
        list.clear()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val text = list[position]
        val inflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(resource, null)
        val textView = view.findViewById<TextView>(R.id.completiontext)
        textView.text = text
        return view
    }
}