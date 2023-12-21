package com.dicoding.nav_capstone.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.data.repository.ResultState
import com.dicoding.nav_capstone.databinding.FragmentListBinding
import com.dicoding.nav_capstone.ui.ViewModelFactory
import com.dicoding.nav_capstone.ui.detail.DetailActivity

class ListFragment : Fragment(), ListRempahAdapter.OnItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ListViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var adapter: ListRempahAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        Log.d("ListFragment", "RecyclerView initialized: ${binding.rvListRempah}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar1.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
        observeRempahList()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvListRempah.layoutManager = layoutManager

        adapter = ListRempahAdapter(this)

        val margin = resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
        binding.rvListRempah.setPadding(0, 0, 0, margin)

        binding.rvListRempah.adapter = adapter
    }

    private fun observeRempahList() {
        viewModel.getAllRempah().observe(viewLifecycleOwner, Observer { listStory ->
            when (listStory) {
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val story = listStory.data.listRempah
                    Log.d("ListFragment", "Data rempah: $story")
                    adapter.submitList(story)
                }

                is ResultState.Error -> {
                    Log.d("ListFragment", "Error fetching rempah list: ${listStory.error}")
                }
            }
        })
    }

    override fun onStoryClicked(id: String) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}