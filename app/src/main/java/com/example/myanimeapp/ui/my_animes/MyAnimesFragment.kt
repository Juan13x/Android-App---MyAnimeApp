package com.example.myanimeapp.ui.my_animes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.FragmentMyAnimesBinding
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.ui.shared.SharedViewModel
import com.google.android.material.snackbar.Snackbar

class MyAnimesFragment : Fragment() {

    private lateinit var binding: FragmentMyAnimesBinding
    private lateinit var model: MyAnimesViewModel
    private lateinit var sharedModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(this)[MyAnimesViewModel::class.java]
        binding = FragmentMyAnimesBinding.inflate(inflater, container, false)
        sharedModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        with(binding){
            val adapterAnimes = MyAnimesAdapter(
                sharedModel.animeListMyAnimes,
                findNavController()
            )

            myAnimesRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MyAnimesFragment.requireContext())
                adapter = adapterAnimes
                setHasFixedSize(false)
            }

            model.errorLiveData.observe(viewLifecycleOwner) { errorData ->
                if(errorData != null) {
                    myAnimesProgressBar.visibility = View.GONE
                    when (errorData.errorEvent) {
                        e_Error.GenericInt -> {
                            Snackbar.make(myAnimesCoordinatorLayout, errorData.errorMessageInt!!, Toast.LENGTH_SHORT)
                                .setBackgroundTint(resources.getColor(R.color.Red))
                                .setTextColor(resources.getColor(R.color.White))
                                .show()
                        }
                        else -> {
                            Snackbar.make(myAnimesCoordinatorLayout, errorData.errorMessageStr!!, Toast.LENGTH_SHORT)
                                .setBackgroundTint(resources.getColor(R.color.Red))
                                .setTextColor(resources.getColor(R.color.White))
                                .show()
                        }
                    }
                }
            }

            model.changeAdapterLiveData.observe(viewLifecycleOwner){
                    animes ->
                if(animes != null) {
                    adapterAnimes.appendItems(ArrayList(animes))
                    myAnimesProgressBar.visibility = View.GONE
                }
            }

            model.reloadLiveData.observe(viewLifecycleOwner){
                if(it != null){
                    adapterAnimes.appendItems(ArrayList(sharedModel.animeListMyAnimes))
                    myAnimesProgressBar.visibility = View.GONE
                }
            }
        }
        model.isNecessaryToReload(sharedModel.animeListMyAnimes)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        model.resetLiveData()
    }
}