package com.dicoding.nav_capstone.ui.home

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
import com.dicoding.nav_capstone.databinding.FragmentHomeBinding
import com.dicoding.nav_capstone.ui.ViewModelFactory
import com.dicoding.nav_capstone.ui.artikel.ArtikelActivity
import com.dicoding.nav_capstone.ui.detail.DetailActivity

class HomeFragment : Fragment(), RekomendasiAdapter.OnItemClickListener,
    KamusAdapter.OnItemClickListener,
    ArtikelAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var rekomendasiAdapter: RekomendasiAdapter
    private lateinit var kamusAdapter: KamusAdapter
    private lateinit var artikelAdapter: ArtikelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d("HomeFragment", "RecyclerView initialized: ${binding.rvKamusRempah}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
        observeRempahList()

    }

    private fun setupRecyclerView() {
        rekomendasiAdapter = RekomendasiAdapter(this)
        binding.rvRekomendasi.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRekomendasi.adapter = rekomendasiAdapter

        kamusAdapter = KamusAdapter(this)
        binding.rvKamusRempah.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKamusRempah.adapter = kamusAdapter

        artikelAdapter = ArtikelAdapter(this)
        binding.rvArtikel.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArtikel.adapter = artikelAdapter

        val margin = resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
        binding.rvArtikel.setPadding(0, 0, 0, margin)
    }

    private fun observeRempahList() {
        viewModel.getHomeData().observe(viewLifecycleOwner, Observer { listStory ->
            when (listStory) {
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val randomInfo = listStory.data.randomInfo
                    binding.randomInfo.text = "${randomInfo.isiInfo}"

                    val story = listStory.data.recommendedRempah
                    Log.d("ListFragment", "Data rempah: $story")
                    rekomendasiAdapter.submitList(story)

                    val story1 = listStory.data.dictionaryRempah
                    Log.d("ListFragment", "Data rempah: $story")
                    kamusAdapter.submitList(story1)

                    val story2 = listStory.data.articleList
                    Log.d("ListFragment", "Data rempah: $story")
                    artikelAdapter.submitList(story2)

                }

                is ResultState.Error -> {
                    Log.d("ListFragment", "Error fetching rempah list: ${listStory.error}")
                }
            }
        })
    }

    override fun onStoryClicked(id: String, type: String) {
        when (type) {
            "artikel" -> {
                val intent = Intent(requireContext(), ArtikelActivity::class.java)
                intent.putExtra("extra_id", id)
                startActivity(intent)
            }

            "rempah" -> {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("extra_id", id)
                startActivity(intent)
            }

            else -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}