package com.example.smarthome.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthome.ListRoomRecyclerViewAdapter
import com.example.smarthome.MainActivity
import com.example.smarthome.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var roomAdapter: ListRoomRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        roomAdapter = ListRoomRecyclerViewAdapter(MainActivity.roomList)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val roomRecyclerView: RecyclerView = binding.recyclerViewRoom
        roomRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        roomRecyclerView.adapter = roomAdapter
        println(roomRecyclerView.findViewHolderForAdapterPosition(0))
        roomAdapter.viewDeviceList(MainActivity.roomList[0], root)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}