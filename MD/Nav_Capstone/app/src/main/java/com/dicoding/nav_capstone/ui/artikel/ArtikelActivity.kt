package com.dicoding.nav_capstone.ui.artikel

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.data.repository.ResultState
import com.dicoding.nav_capstone.databinding.ActivityArtikelBinding
import com.dicoding.nav_capstone.ui.ViewModelFactory

class ArtikelActivity : AppCompatActivity() {

    private val viewModel by viewModels<ArtikelViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityArtikelBinding

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val margin = resources.getDimensionPixelSize(R.dimen.bottom_height)
        binding.kontenArtikel.setPadding(0, 0, 0, margin)

        val idArtikel = intent.getStringExtra(EXTRA_ID)
        val bundle = Bundle()
        bundle.putString(EXTRA_ID, idArtikel)

        if (idArtikel != null) {
            viewModel.getArtikel(idArtikel).observe(this) { detailRempah ->
                when (detailRempah) {
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val rempah = detailRempah.data.dataArtikel
                        binding.apply {
                            judulArtikel.text = "${detailRempah.data.dataArtikel.judulArtikel}"
                            sumberArtikel.text = "${detailRempah.data.dataArtikel.sumberArtikel}"
                            kontenArtikel.text = "${detailRempah.data.dataArtikel.isiArtikel}"
                            Glide.with(this@ArtikelActivity)
                                .load(rempah.image)
                                .into(binding.ivArtikel)
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}