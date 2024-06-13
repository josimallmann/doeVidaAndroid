package com.example.myapplication
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainScreenFragment : Fragment() {

    private lateinit var pedirDoacao: Button
    private lateinit var btnVoltar: Button
    private lateinit var filtrarButton: Button
    private lateinit var themeSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_screen, container, false)

        // Inicializar os botões e o switch
        pedirDoacao = view.findViewById(R.id.pedirDoacao)
        btnVoltar = view.findViewById(R.id.btnVoltar)
        filtrarButton = view.findViewById(R.id.filtrarButton)

        themeSwitch = view.findViewById(R.id.themeSwitch)

        // Configurar os listeners de clique
        pedirDoacao.setOnClickListener {
            navigateToPost()
        }
        btnVoltar.setOnClickListener {
            navigateToLogin()
        }
        filtrarButton.setOnClickListener {
            navigateToHospitais()
        }

        // Recuperar o estado do tema das preferências
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return view
        val isNightMode = sharedPref.getBoolean("NIGHT_MODE", false)
        themeSwitch.isChecked = isNightMode
        setNightMode(isNightMode)

        // Configurar o listener do switch
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            setNightMode(isChecked)
            with(sharedPref.edit()) {
                putBoolean("NIGHT_MODE", isChecked)
                apply()
            }
        }

        return view
    }

    private fun setNightMode(isNightMode: Boolean) {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    private fun navigateToPost() {
        // Navegar para o PostFragment usando NavController
        findNavController().navigate(R.id.action_mainScreenFragment_to_postFragment)
    }

    private fun navigateToLogin() {
        // Navegar para o LoginFragment usando NavController
        findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)
    }
    private fun navigateToHospitais() {
        findNavController().navigate(R.id.hospitalFragment)
    }

}