package com.example.myapplication

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HospitalAdapter(private val hospitals: List<Hospital>) : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hospital, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.tvHospitalNome.text = hospital.nome
        holder.tvEstoqueGeralValor.text = hospital.estoque.situacaoGeral
        holder.tvEstoqueTipoABNegativo.text = hospital.estoque.situacaoTipoABNegativo
        holder.tvEstoqueTipoABPositivo.text = hospital.estoque.situacaoTipoABPositivo
        holder.tvEstoqueTipoANegativo.text = hospital.estoque.situacaoTipoANegativo
        holder.tvEstoqueTipoBNegativo.text = hospital.estoque.situacaoTipoBNegativo
        holder.tvEstoqueTipoBPositivo.text = hospital.estoque.situacaoTipoBPositivo
        holder.tvEstoqueTipoONegativo.text = hospital.estoque.situacaoTipoONegativo
        holder.tvEstoqueTipoOPositivo.text = hospital.estoque.situacaoTipoOPositivo

    }

    override fun getItemCount(): Int = hospitals.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHospitalNome: TextView = view.findViewById(R.id.tvHospitalNome)
        val tvEstoqueGeralValor: TextView = view.findViewById(R.id.tvEstoqueGeralValor)
        val tvEstoqueTipoABNegativo: TextView = view.findViewById(R.id.tvEstoqueTipoABNegativoValor)
        val tvEstoqueTipoABPositivo: TextView = view.findViewById(R.id.tvEstoqueTipoABPositivoValor)
        val tvEstoqueTipoANegativo: TextView = view.findViewById(R.id.tvEstoqueTipoANegativoValor)
        val tvEstoqueTipoBNegativo: TextView = view.findViewById(R.id.tvEstoqueTipoBNegativoValor)
        val tvEstoqueTipoBPositivo: TextView = view.findViewById(R.id.tvEstoqueTipoBPositivoValor)
        val tvEstoqueTipoONegativo: TextView = view.findViewById(R.id.tvEstoqueTipoONegativoValor)
        val tvEstoqueTipoOPositivo: TextView = view.findViewById(R.id.tvEstoqueTipoOPositivoValor)

    }
}