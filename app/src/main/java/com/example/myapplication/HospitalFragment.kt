package com.example.myapplication
import android.os.Bundle
import android.util.Log
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

        val hospitals = listOf(
            Hospital("Hospiptal Erasto Gaertner", Estoque("medio", "baixo", "alto", "baixo", "alto", "medio", "medio", "baixo", "baixo")),
        )

        hospitalAdapter = HospitalAdapter(hospitals)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = hospitalAdapter

        fetchHospitals()
    }

    private fun fetchHospitals() {
        Log.d("HospitalFragment", "Buscando hospitais...")
        val apiService = ApiClient.instance.create(ApiService::class.java)
        apiService.getHospitals().enqueue(object : Callback<List<Hospital>> {
            override fun onResponse(call: Call<List<Hospital>>, response: Response<List<Hospital>>) {
                if (response.isSuccessful) {
                    val hospitals = response.body() ?: emptyList()
                    Log.d("HospitalFragment", "Hospitais encontrados: ${hospitals.size}")
                    // Atualizar os dados do adaptador
                    hospitalAdapter.updateData(hospitals)
                } else {
                    Log.e("HospitalFragment", "Resposta sem sucesso: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Hospital>>, t: Throwable) {
                Log.e("HospitalFragment", "Falha ao buscar hospitais", t)
            }
        })
    }


    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.action_hospitalFragment_to_mainScreenFragment)
    }
}