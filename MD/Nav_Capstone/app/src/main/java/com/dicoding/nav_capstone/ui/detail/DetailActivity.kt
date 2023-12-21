package com.dicoding.nav_capstone.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.nav_capstone.R
import com.dicoding.nav_capstone.data.local.database.FavoriteRempah
import com.dicoding.nav_capstone.data.repository.ResultState
import com.dicoding.nav_capstone.databinding.ActivityDetailBinding
import com.dicoding.nav_capstone.ui.resep.ResepActivity
import com.dicoding.nav_capstone.ui.ViewModelFactory
import com.dicoding.nav_capstone.ui.favorite.FavViewModelFactory
import com.dicoding.nav_capstone.ui.favorite.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity(), ResepAdapter.OnItemClickListener {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        FavViewModelFactory.getInstance(this)
    }

    private var favoriteUser = FavoriteRempah(0)
    private lateinit var binding: ActivityDetailBinding
    private lateinit var resepAdapter: ResepAdapter

    companion object {
        const val EXTRA_ID = "extra_id"
        const val KEY_NAME = "key_name"
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            // Navigate back when the backButton is clicked
            onBackPressed()
        }

        val idRempah = intent.getStringExtra(EXTRA_ID)
        val bundle = Bundle()
        bundle.putString(EXTRA_ID, idRempah)

        resepAdapter = ResepAdapter(this)
        binding.rvResep.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvResep.adapter = resepAdapter


        if (idRempah != null){
            viewModel.getDetailRempah(idRempah).observe(this) { detailRempah ->
                when(detailRempah){
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val rempah = detailRempah.data.dataRempah
                        binding.apply {
                            titleTextView.text = "${detailRempah.data.dataRempah.namaRempah}"
                            latinTextView.text = "${detailRempah.data.dataRempah.namaLatin}"
                            descTv.text = "${detailRempah.data.dataRempah.deskripsi}"
                            daerahTv.text = "${detailRempah.data.dataRempah.daerahPenghasil}"
                            kandunganTv.text = "${detailRempah.data.dataRempah.kandungan}"
                            manfaatTv.text = "${detailRempah.data.dataRempah.manfaat}"
                            penyimpananTv.text = "${detailRempah.data.dataRempah.caraPenyimpanan}"
                            masakanTv.text = "${detailRempah.data.dataRempah.masakan}"
                            pengobatanTv.text = "${detailRempah.data.dataRempah.pengobatanTradisional}"
                            minumanTv.text = "${detailRempah.data.dataRempah.minuman}"
//                            rvResep.adapter = "${detailRempah.data.dataRempah.}"
                            Glide.with(this@DetailActivity)
                                .load(rempah.image)
                                .into(binding.fotoRempah)

                            val resepRempah = detailRempah.data.dataRempah.resepTerkait
                            resepAdapter.submitList(resepRempah)

                        }
                        rempah.idRempah.let {
                            favoriteUser.idRempah = it
                            favoriteUser.nama = rempah.namaRempah
                            favoriteUser.image = rempah.image
                            favoriteUser.latin = rempah.namaLatin
                            favoriteViewModel.getFavoriteById(idRempah).observe(this) { it ->
                                val isFavorite = it != null
                                setFavoriteButtonState(isFavorite)
                                binding.favButton.setOnClickListener {
                                    if (isFavorite) {
                                        favoriteViewModel.removeFavorite(favoriteUser)
                                        showSnackbarMessage("Rempah berhasil dihapus dari favorit")
                                    } else {
                                        favoriteViewModel.addToFavorite(favoriteUser)
                                        showSnackbarMessage("Rempah berhasil ditambahkan ke favorit")
                                    }
                                }
                            }
                        }
                        binding.shareButton.setOnClickListener{
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            val shareMessage = "Tahukah kamu?\n${rempah.daerahPenghasil} adalah daerah penghasil ${rempah.namaRempah} terbesar di Indonesia.\n\nJelajahi informasi rempah lainnya di RempaHustle app!"
                            intent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                            val chooser = Intent.createChooser(intent, "Bagikan temuan rempahmu!")
                            startActivity(chooser)
                        }
                    }
                    else -> {}
                }
            }
        }

        viewModel.userName.observe(this) { userName ->
            favoriteUser.let {
                it.idRempah = userName.dataRempah.idRempah
                it.nama = userName.dataRempah.namaRempah
                it.image = userName.dataRempah.image
                it.latin = userName.dataRempah.namaLatin
            }
        }

    }

    override fun onStoryClicked(id: String) {
        val intent = Intent(this, ResepActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }

    private fun setFavoriteButtonState(isFavorite: Boolean) {
        binding.favButton.setImageResource(if (isFavorite) R.drawable.ic_fav_red else R.drawable.ic_fav_grey)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvResep, message, Snackbar.LENGTH_SHORT).show()
    }
}