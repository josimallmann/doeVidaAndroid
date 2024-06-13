package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class PostFragment : Fragment() {


    private lateinit var btnFechar: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.post, container, false)

        btnFechar = view.findViewById(R.id.btnFechar)

        // Configurando o botão de pedir doação"
        btnFechar.setOnClickListener {
            navigateToTelaPrincipal()
        }

        return view
    }

    private fun navigateToTelaPrincipal() {
        findNavController().navigate(R.id.action_postFragment_to_mainScreenFragment)
    }
}