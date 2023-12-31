package com.dicoding.nav_capstone.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.nav_capstone.data.local.model.SessionModel
import com.dicoding.nav_capstone.data.local.preferences.SessionPreferences
import com.dicoding.nav_capstone.data.local.preferences.datastore
import com.dicoding.nav_capstone.databinding.FragmentProfileBinding
import com.dicoding.nav_capstone.ui.ViewModelFactory
import com.dicoding.nav_capstone.ui.setting.SettingsActivity
import com.dicoding.nav_capstone.ui.welcome.WelcomeActivity
import kotlinx.coroutines.flow.Flow

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonKeluar = binding.buttonKeluar
        buttonKeluar.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDarkmode.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.textSubscription.setOnClickListener {
            val intent = Intent(requireContext(), SubscriptionActivity::class.java)
            startActivity(intent)
        }

        val sessionPreferences = SessionPreferences.getInstance(requireContext().datastore)
        val sessionFlow: Flow<SessionModel> = sessionPreferences.getSession()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sessionFlow.collect { sessionModel ->
                if (sessionModel.isLogin) {
                    binding.textEmailProfile.text = "${sessionModel.email}"
                }

            }
        }

    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setMessage("Apakah kamu yakin ingin keluar?")
            setPositiveButton("Yakin") { _, _ ->
                viewModel.logOut()
                val intent = Intent(requireContext(), WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            setNegativeButton("Batal") { _, _ ->
                // Do Nothing
            }
            create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
