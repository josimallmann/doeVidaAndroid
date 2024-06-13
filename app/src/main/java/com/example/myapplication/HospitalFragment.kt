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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchHospitals()
    }

    private fun fetchHospitals() {
        val apiService = ApiClient.instance.create(ApiService::class.java)
        apiService.getHospitals().enqueue(object : Callback<List<Hospital>> {
            override fun onResponse(call: Call<List<Hospital>>, response: Response<List<Hospital>>) {
                if (response.isSuccessful) {
                    val hospitals = response.body() ?: emptyList()
                    hospitalAdapter = HospitalAdapter(hospitals)
                    recyclerView.adapter = hospitalAdapter
                }
            }

            override fun onFailure(call: Call<List<Hospital>>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.action_hospitalFragment_to_mainScreenFragment)
    }
}