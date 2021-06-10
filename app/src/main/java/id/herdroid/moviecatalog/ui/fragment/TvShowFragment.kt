package id.herdroid.moviecatalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import id.herdroid.moviecatalog.R
import id.herdroid.moviecatalog.adapter.TvShowAdapter
import id.herdroid.moviecatalog.viewmodel.TvShowViewModel
import id.herdroid.moviecatalog.viewmodel.ViewModelFactory
import id.herdroid.moviecatalog.vo.Status
import kotlinx.android.synthetic.main.fragment_tvshow.*

class TvShowFragment :Fragment(){

    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val academyAdapter = TvShowAdapter()
        viewModel.loadTvShow(page).observe(viewLifecycleOwner) { tvShow ->
            when (tvShow.status) {
                Status.LOADING -> {
                    pb_tv.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    pb_tv.visibility = View.GONE
                    academyAdapter.submitList(tvShow.data)
                    academyAdapter.notifyDataSetChanged()
                }
                else -> {
                    Status.ERROR
                    pb_tv.visibility = View.GONE
                    Toast.makeText(context,getString(R.string.error_msg), Toast.LENGTH_SHORT).show()
                }
            }
        }

        with(rv_tvShow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = academyAdapter
        }
    }






}

