package com.dicoding.nav_capstone.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.FragmentFavoriteBinding
import com.dicoding.nav_capstone.ui.detail.DetailActivity

class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemClickListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteAdapter

    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        FavViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        Log.d("FavFragment", "RecyclerView initialized: ${binding.rvFavRempah}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavList()

        val margin = resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
        binding.rvFavRempah.setPadding(0, 0, 0, margin)

    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter(this)
        binding.rvFavRempah.layoutManager = LinearLayoutManager(requireContext())

        val margin = resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
        binding.rvFavRempah.setPadding(0, 0, 0, margin)

        binding.rvFavRempah.adapter = adapter
    }

    private fun observeFavList() {
        favoriteViewModel.getFavorite().observe(viewLifecycleOwner) { favUser ->
            if (favUser.isEmpty()) {
                binding.rvFavRempah.visibility = View.GONE
                binding.emptyFavMessageLayout.visibility = View.VISIBLE
            } else {
                binding.rvFavRempah.visibility = View.VISIBLE
                binding.emptyFavMessageLayout.visibility = View.GONE
                adapter.submitList(favUser)
            }
        }
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