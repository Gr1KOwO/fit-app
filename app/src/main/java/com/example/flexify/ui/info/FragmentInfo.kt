package com.example.flexify.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flexify.R
import com.example.flexify.databinding.FragmentInfoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentInfo: Fragment() {
    private lateinit var binding:FragmentInfoBinding
    private val infoAdapter = InfoAdapter(onClick = ::onItemClick)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf(
            InfoItem(
                img = resources.getDrawable(R.drawable.img),
                titleText = resources.getString(R.string.exercises)
            ),
            InfoItem(
                img = resources.getDrawable(R.drawable.img_food),
                titleText = resources.getString(R.string.CompositionAndcalorieTable)
            )
        )
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.RecyclerView.adapter = infoAdapter
        infoAdapter.submitList(list)
    }


    private fun onItemClick(itemTitle:String)
    {
        if(itemTitle == resources.getString(R.string.CompositionAndcalorieTable)){
            val action = FragmentInfoDirections.actionFragmentInfoToFragmentTypeFood()
            findNavController().navigate(action)
        }
        else{
            val action = FragmentInfoDirections.actionFragmentInfoToExercisesFragment()
            findNavController().navigate(action)
        }
    }
    override fun onResume() {
        super.onResume()
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation?.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation?.visibility = View.GONE
    }

}