package com.appintuitions.rvkotlin.view.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appintuitions.rvkotlin.R
import com.appintuitions.rvkotlin.adapters.MoviesAdapter
import com.appintuitions.rvkotlin.databinding.FragmentDashboardBinding
import com.appintuitions.rvkotlin.viewmodel.models.MovieSort
import com.appintuitions.rvkotlin.util.Util
import com.appintuitions.rvkotlin.viewmodel.MainViewModel
import com.appintuitions.rvkotlin.viewmodel.models.Movie
import kotlinx.android.synthetic.main.lt_movies.*
import kotlinx.android.synthetic.main.lt_movies.view.*

class DashboardFragment : Fragment() {

    private lateinit var homeViewModel: MainViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sortByList = ArrayList<String>()

        sortByList.add("Title")
        sortByList.add("Popularity")
        sortByList.add("Rating")

        root.spSortBy.adapter = ArrayAdapter(
            requireContext(),
            R.layout.lt_spinner, sortByList
        )

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

                Util.sortList(sortBy, list)

                adapter.notifyDataSetChanged()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        homeViewModel.isLoading.observe(viewLifecycleOwner,{
            if(it){
                progressBar.visibility = View.VISIBLE
                movieRoot.visibility = View.GONE
            }else{
                progressBar.visibility = View.GONE
                movieRoot.visibility = View.VISIBLE
            }
        })

        homeViewModel.loadLatestMovies()

        adapter = MoviesAdapter(requireContext(), list)

        root.rvList.layoutManager = LinearLayoutManager(requireContext())

        root.rvList.adapter = adapter

        homeViewModel.latestMovies.observe(viewLifecycleOwner, {
            Log.e("latest", "changed " + homeViewModel.latestMovies.value?.size)

            if(it.size>0) {
                list.clear()
                list.addAll(it)

                Util.sortList(sortBy, list)

                adapter.notifyDataSetChanged()
                rvList.visibility = View.VISIBLE
                no_tv.visibility = View.GONE
            }else{
                rvList.visibility = View.GONE
                no_tv.visibility = View.VISIBLE
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}