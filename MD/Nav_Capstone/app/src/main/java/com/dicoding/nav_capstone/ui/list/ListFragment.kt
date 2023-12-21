package com.dicoding.nav_capstone.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.data.repository.ResultState
import com.dicoding.nav_capstone.databinding.FragmentListBinding
import com.dicoding.nav_capstone.ui.detail.DetailActivity
import com.dicoding.nav_capstone.ui.ViewModelFactory
import com.google.android.material.search.SearchView

class ListFragment : Fragment(), ListRempahAdapter.OnItemClickListener{

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

        //searchBar
        with(binding) {
            searchView1.setupWithSearchBar(searchBar1)
            searchView1
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView1.hide()
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val searchResult =
                            searchView1.text.toString() // Mengambil nilai query dari SearchView
                        if (!searchResult.isNullOrEmpty()) {
                            searchBar1.setText(searchResult)//untuk menampilkan pencarian di search bar
                            viewModel.findUser(searchResult) // Memanggil API dengan query yang sesuai
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Masukkan kata kunci pencarian",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@setOnEditorActionListener true
                    }
                    false
                }
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

        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvListRempah.addItemDecoration(itemDecoration)
    }

    private fun observeRempahList() {
        viewModel.getAllRempah().observe(viewLifecycleOwner, Observer { listStory ->
            when (listStory) {
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    // Tambahkan logika untuk menangani kondisi loading
                }
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val story = listStory.data.listRempah
                    Log.d("ListFragment", "Data rempah: $story")
                    adapter.submitList(story)
                }
                is ResultState.Error -> {
                    // Tambahkan logika untuk menangani kondisi error
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