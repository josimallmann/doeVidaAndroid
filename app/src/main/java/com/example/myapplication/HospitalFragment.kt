package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HospitalFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hospitalAdapter: HospitalAdapter
    private lateinit var btnVoltarMainS: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hospital, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        btnVoltarMainS = view.findViewById(R.id.btnVoltarMainS)

        btnVoltarMainS.setOnClickListener {
            navigateToMainScreen()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hospitals = listOf(
            Hospital("Hospiptal Erasto Gaertner", Estoque("medio", "baixo", "alto", "baixo", "alto", "medio", "medio", "baixo", "baixo")),
        )

        hospitalAdapter = HospitalAdapter(hospitals)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = hospitalAdapter
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.action_hospitalFragment_to_mainScreenFragment)
    }
}