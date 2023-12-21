package com.dicoding.nav_capstone.ui.resep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.data.repository.ResultState
import com.dicoding.nav_capstone.databinding.ActivityDetailBinding
import com.dicoding.nav_capstone.databinding.ActivityResepBinding
import com.dicoding.nav_capstone.ui.ViewModelFactory
import com.dicoding.nav_capstone.ui.detail.DetailActivity
import com.dicoding.nav_capstone.ui.detail.DetailActivity.Companion.EXTRA_ID
import com.dicoding.nav_capstone.ui.detail.DetailViewModel
import com.dicoding.nav_capstone.ui.detail.ResepAdapter

class ResepActivity : AppCompatActivity() {
    private val viewModel by viewModels<ResepViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityResepBinding

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val margin = resources.getDimensionPixelSize(R.dimen.bottom_height)
        binding.caraResep.setPadding(0, 0, 0, margin)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val idResep = intent.getStringExtra(EXTRA_ID)
        val bundle = Bundle()
        bundle.putString(EXTRA_ID, idResep)

        if (idResep != null){
            viewModel.getResep(idResep).observe(this) { detailResep ->
                when(detailResep){
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val rempah = detailResep.data.dataResep
                        binding.apply {
                            judulResep.text = "${detailResep.data.dataResep.namaResep}"
                            relatedKeyword.text = "${detailResep.data.dataResep.relatedKeyword}"

                            val ingredientsList = detailResep.data.dataResep.ingredients
                            bahanResep.text = ingredientsList.joinToString("\n - ")

                            val stepsList = detailResep.data.dataResep.steps
                            caraResep.text = stepsList.joinToString("\n - ")

                            Glide.with(this@ResepActivity)
                                .load(rempah.image)
                                .placeholder(R.drawable.load_gambar)
                                .error(R.drawable.load_gambar)
                                .into(binding.ivResep)
                        }
                    }
                    is ResultState.Error -> {
                        Log.d("ResepActivity", "Error fetching resep rempah: ${detailResep.error}")

                    }
                    else -> {}
                }
            }
        }
    }
}