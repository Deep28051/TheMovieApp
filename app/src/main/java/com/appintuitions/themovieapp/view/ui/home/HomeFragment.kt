package com.appintuitions.rvkotlin.view.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appintuitions.rvkotlin.R
import com.appintuitions.rvkotlin.adapters.MoviesAdapter
import com.appintuitions.rvkotlin.databinding.FragmentHomeBinding
import com.appintuitions.rvkotlin.viewmodel.models.MovieSort
import com.appintuitions.rvkotlin.util.Util.Companion.sortList
import com.appintuitions.rvkotlin.viewmodel.MainViewModel
import com.appintuitions.rvkotlin.viewmodel.models.Movie
import kotlinx.android.synthetic.main.lt_movies.*
import kotlinx.android.synthetic.main.lt_movies.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var list: ArrayList<Movie> = ArrayList()

    private var sortBy: MovieSort = MovieSort.TITLE

    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sortByList = ArrayList<String>()

        sortByList.add("Title")
        sortByList.add("Popularity")
        sortByList.add("Rating")

        root.spSortBy.adapter = ArrayAdapter(
            requireContext(),
            R.layout.lt_spinner, sortByList
        )

        homeViewModel.isLoading.observe(viewLifecycleOwner,{
            if(it){
                progressBar.visibility = VISIBLE
                movieRoot.visibility = GONE
            }else{
                progressBar.visibility = GONE
                movieRoot.visibility = VISIBLE
            }
        })

        root.spSortBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when {
                    sortByList[position] == "Title" -> {
                        sortBy = MovieSort.TITLE
                    }
                    sortByList[position] == "Popularity" -> sortBy = MovieSort.POPULARITY
                    sortByList[position] == "Rating" -> sortBy = MovieSort.RATING
                }

                sortList(sortBy,list)

                adapter.notifyDataSetChanged()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        homeViewModel.loadPopularMovies()

        adapter = MoviesAdapter(requireContext(), list)

        root.rvList.layoutManager = LinearLayoutManager(requireContext())

        root.rvList.adapter = adapter

        homeViewModel.popularMovies.observe(viewLifecycleOwner, {
            Log.e("popular", "changed " + homeViewModel.popularMovies.value?.size)

            if(it.size>0) {
                list.clear()
                list.addAll(it)

                sortList(sortBy, list)

                adapter.notifyDataSetChanged()
                rvList.visibility = VISIBLE
                no_tv.visibility = GONE
            }else{
                rvList.visibility = GONE
                no_tv.visibility = VISIBLE
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}