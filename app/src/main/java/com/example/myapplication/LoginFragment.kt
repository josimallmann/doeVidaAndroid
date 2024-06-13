package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var cadastreTextView: TextView

    private lateinit var dbLogin: DataBaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login, container, false)

        // Inicializando as views
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.loginButton)
        forgotPasswordTextView = view.findViewById(R.id.forgotPasswordTextView)
        cadastreTextView = view.findViewById(R.id.cadastreTextView)
        dbLogin = DataBaseHelper(requireContext().applicationContext)

        // Configurando o botão de login
        loginButton.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            getLogin(email, password)
        }

        // Configurando o TextView "Esqueceu sua senha?"
        forgotPasswordTextView.setOnClickListener {
            // Navegar para ForgotPasswordFragment
            Toast.makeText(context, "Recuperação de senha ainda não implementada.", Toast.LENGTH_SHORT).show()
        }

        // Configurando o TextView "Cadastre-se"
        cadastreTextView.setOnClickListener {
            navigateToCadastro()
        }

        return view
    }

    private fun getLogin(email: String, password: String) {
        when {
            email.isEmpty() -> {
                Toast.makeText(context, "Email é obrigatório", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(context, "Senha é obrigatória", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val userExists = dbLogin.getLogin(email, password)
                if (userExists) {
                    Toast.makeText(context, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                    // Navegar para a tela principal
                    findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)

                } else {
                    // Exibir mensagem de erro
                    Toast.makeText(context, "Login inválido!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToCadastro() {
       findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
    }
}