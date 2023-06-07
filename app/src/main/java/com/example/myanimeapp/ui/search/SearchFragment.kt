package com.example.myanimeapp.ui.search

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.FragmentSearchBinding
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.ui.e_Colors
import com.example.myanimeapp.ui.shared.SharedViewModel
import com.google.android.material.snackbar.Snackbar


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var model: SearchViewModel
    private lateinit var sharedModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[SearchViewModel::class.java]
        binding = FragmentSearchBinding.inflate(layoutInflater)
        sharedModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private fun FragmentSearchBinding.showButtonToLoadAiringAnimes() {
        searchAiringAnimesButton.visibility = View.VISIBLE
        sharedModel.stateButtonAiringAnimes = true
    }

    private fun FragmentSearchBinding.highButtonToLoadAiringAnimes() {
        searchAiringAnimesButton.visibility = View.GONE
        sharedModel.stateButtonAiringAnimes = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(binding) {
            searchAiringAnimesButton.visibility = View.GONE
            searchRecyclerView.visibility = View.GONE

            val adapter =
                SearchAdapter(
                    sharedModel.animeListSearch,
                    mapOf(
                        e_Colors.ADD to
                                resources.getColor(R.color.colorPrimaryVariant),
                        e_Colors.DELETE to
                                resources.getColor(R.color.colorError)
                    ),
                    findNavController()
                )
            searchRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@SearchFragment.requireContext())
                this.adapter = adapter
                setHasFixedSize(false)
            }

            model.errorLiveData.observe(viewLifecycleOwner) { errorData ->
                if(errorData != null){
                    searchRecyclerView.visibility = View.VISIBLE
                    searchProgressBar.visibility = View.GONE
                    when (errorData.errorEvent) {
                        e_Error.GenericInt -> {
                            Snackbar.make(searchCoordinatorLayout, errorData.errorMessageInt!!, Toast.LENGTH_SHORT)
                                .setBackgroundTint(resources.getColor(R.color.Red))
                                .setTextColor(resources.getColor(R.color.White))
                                .show()
                        }
                        else -> {
                            Snackbar.make(searchCoordinatorLayout, errorData.errorMessageStr!!, Toast.LENGTH_SHORT)
                                .setBackgroundTint(resources.getColor(R.color.Red))
                                .setTextColor(resources.getColor(R.color.White))
                                .show()
                        }
                    }
                }
            }
            model.changeWithSearchAdapterLiveData.observe(viewLifecycleOwner){
                    animes ->
                if(animes != null){
                    showButtonToLoadAiringAnimes()
                    searchRecyclerView.visibility = View.VISIBLE
                    searchTypeQueryTextView.text = getString(R.string.search_Result, model.getQuery())
                    adapter.appendItems(animes)
                    searchProgressBar.visibility = View.GONE
                }
            }

            model.changeWithAiringAnimesAdapterLiveData.observe(viewLifecycleOwner){
                    animes ->
                if(animes != null){
                    highButtonToLoadAiringAnimes()
                    sharedModel.airingAnimes.addAll(animes)
                    adapter.appendItems(animes)
                    searchProgressBar.visibility = View.GONE
                    searchRecyclerView.visibility = View.VISIBLE
                }

            }

            model.reloadLiveData.observe(viewLifecycleOwner){
                if(it != null){
                    adapter.appendItems(ArrayList(sharedModel.animeListSearch))
                    if(sharedModel.stateButtonAiringAnimes){
                        searchAiringAnimesButton.visibility = View.VISIBLE
                        searchTypeQueryTextView.text = getString(R.string.search_Result, sharedModel._query)
                    }
                    else{
                        searchAiringAnimesButton.visibility = View.GONE}
                    searchProgressBar.visibility = View.GONE
                    searchRecyclerView.visibility = View.VISIBLE
                }
            }
            val searchText: EditText = searchSearchView.findViewById(searchSearchView.context.resources.getIdentifier("android:id/search_src_text", null, null))
            searchText.filters = arrayOf<InputFilter>(LengthFilter(R.integer.searchView_maxLength))

            searchAiringAnimesButton.setOnClickListener {
                adapter.appendItems(sharedModel.airingAnimes)
                searchRecyclerView.visibility = View.VISIBLE
                searchTypeQueryTextView.setText(R.string.search_defaultTypeQuery_text)
                highButtonToLoadAiringAnimes()
            }
            searchSearchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    if(query.isNotEmpty()){
                        sharedModel._query = query
                        searchTypeQueryTextView.text = getString(R.string.search_Result, query)
                        searchRecyclerView.visibility = View.GONE
                        model.searchAnimes(query)
                        searchProgressBar.visibility = View.VISIBLE
                    }
                    return false
                }
            })
        }
        model.isNecessaryToReload(sharedModel.animeListSearch)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        model.resetLiveData()
    }

}