package com.sample.weather.ui.report

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.weather.R
import com.sample.weather.ui.user.Preferences


class ReportItemFragment : Fragment() {

    private var reportViewModel: ReportItemViewModel? = null
    private  var preferences : Preferences? = null
    private var adapter : ReportItemRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        reportViewModel = ViewModelProviders.of(this).get(ReportItemViewModel::class.java)
        preferences = Preferences.getPreferences(requireContext())


        val tvUserName = view.findViewById<AppCompatTextView>(R.id.tvUserName)
        val list = view.findViewById<RecyclerView>(R.id.list)

        tvUserName.setText(preferences?.userName)

        adapter = ReportItemRecyclerViewAdapter()
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter

        reportViewModel?.getWeatherReportArr()?.observe(viewLifecycleOwner, Observer {
            adapter?.addItems(it)
        })


        reportViewModel!!.asyncTask.execute(preferences?.latitiude.toString(), preferences?.longitude.toString(), preferences?.temperature.toString());

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportItemFragment()
    }
}