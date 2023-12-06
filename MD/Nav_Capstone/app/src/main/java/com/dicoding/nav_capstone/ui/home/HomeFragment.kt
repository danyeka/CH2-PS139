package com.dicoding.nav_capstone.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.databinding.ActivityLoginBinding
import com.dicoding.nav_capstone.databinding.FragmentHomeBinding
import com.dicoding.nav_capstone.ui.ArtikelActivity
import com.dicoding.nav_capstone.ui.MainActivity
import com.dicoding.nav_capstone.ui.list.ListFragment
import com.dicoding.nav_capstone.ui.welcome.RegisterActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

//        _binding?.tvLengkapKamus?.setOnClickListener {
//            val listFragment = ListFragment()
//            requireFragmentManager().beginTransaction()
//                .add(R.id.container, listFragment)
//                .addToBackStack(null)
//                .commit()
//        }

//        binding.tvLengkapKamus.setOnClickListener {
//            startActivity(Intent(this, ListFragment::class.java))
//        }

//        binding.tvLengkapArtikel.setOnClickListener {
//            startActivity(Intent(this.requireContext(), ArtikelActivity::class.java))
//        }

//        binding.tvLengkapKamus.setOnClickListener {
//            startActivity(Intent(this, ListFragment::class.java))
//        }

//        binding.tvLengkapKamus.setOnClickListener {
//            // Buat intent untuk memulai fragment baru
//            val intent = Intent(this, ListFragment::class.java)
//
//            // Mulai fragment baru
//            startActivity(intent)
//        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}