package com.example.myanimeapp.ui.my_animes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myanimeapp.databinding.FragmentMyAnimesBinding

class MyAnimesFragment : Fragment() {

    private lateinit var binding: FragmentMyAnimesBinding
    private lateinit var model: MyAnimesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(this)[MyAnimesViewModel::class.java]
        binding = FragmentMyAnimesBinding.inflate(inflater, container, false)

        with(binding){
            val adapterAnimes = MyAnimesAdapter(arrayListOf(),
            onItemClicked = {})

            model.updateMyAnimes()

            myAnimesRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MyAnimesFragment.requireContext())
                adapter = adapterAnimes
                setHasFixedSize(false)
            }

            model.errorLiveData.observe(viewLifecycleOwner){
                    error->
                Toast.makeText(requireContext(),error.errorMessageInt!!, Toast.LENGTH_SHORT).show()
            }

            model.changeAdapterLiveData.observe(viewLifecycleOwner){
                    animes ->
                adapterAnimes.appendItems(animes)
            }
        }

        return binding.root
    }
}