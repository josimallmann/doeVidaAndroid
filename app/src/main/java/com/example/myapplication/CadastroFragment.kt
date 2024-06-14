package com.example.myapplication

import MaskTextWatcher
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class CadastroFragment : Fragment() {

    private lateinit var dbCadastro: DataBaseHelper
    private lateinit var entrarButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cadastro, container, false)

        dbCadastro = DataBaseHelper(requireContext().applicationContext)
        entrarButton = view.findViewById(R.id.entrarButton)

        entrarButton.setOnClickListener {
            navigateToLogin()
        }

        val buttonCadastrar: Button = view.findViewById(R.id.cadastrar)
        buttonCadastrar.setOnClickListener {
            try {
                if (saveUserData(view)) {
                    navigateToMainScreen()
                }
            } catch (e: Exception) {
                Log.e("CadastroFragment", "Erro ao salvar dados: ${e.message}")
                Toast.makeText(requireContext(), "Erro ao cadastrar", Toast.LENGTH_SHORT).show()
            }
        }

        val editTextCpf: EditText = view.findViewById(R.id.editTextCpf)
        val editTextPhone: EditText = view.findViewById(R.id.editTextCelular)
        val editTextDataNascimento: EditText = view.findViewById(R.id.editTextDataNascimento)

        editTextCpf.addTextChangedListener(MaskTextWatcher("###.###.###-##"))
        editTextPhone.addTextChangedListener(MaskTextWatcher("(##) #####-####"))
        editTextDataNascimento.addTextChangedListener(MaskTextWatcher("##/##/####"))

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encontra o Spinner no layout do fragmento
        val spinnerGender: Spinner = view.findViewById(R.id.spinnerGender)

        // Cria um ArrayAdapter usando o array de strings e um layout personalizado do spinner
        val genderAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_options,
            R.layout.spinner_item
        )

        // Especifica o layout a ser usado quando a lista de opções aparecer
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplica o adaptador ao spinner
        spinnerGender.adapter = genderAdapter
    }
    private fun saveUserData(view: View): Boolean {
        val nome: EditText = view.findViewById(R.id.editTextNome)
        val email: EditText = view.findViewById(R.id.editTextEmail)
        val celular: EditText = view.findViewById(R.id.editTextCelular)
        val cpf: EditText = view.findViewById(R.id.editTextCpf)
        val sexo: Spinner = view.findViewById(R.id.spinnerGender)
        val dataNascimento: EditText = view.findViewById(R.id.editTextDataNascimento)
        val tipoSanguineo: EditText = view.findViewById(R.id.editTextTipoSanquineo)
        val cidade: EditText = view.findViewById(R.id.editTextCidade)
        val senha: EditText = view.findViewById(R.id.editTextTSenha)
        val confirmeSenha: EditText = view.findViewById(R.id.editTextTConfirmeSuaSenha)

        if (senha.text.toString().isEmpty() || confirmeSenha.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Senha é obrigatória", Toast.LENGTH_SHORT).show()
            return false
        }

        if (senha.text.toString() != confirmeSenha.text.toString()) {
            Toast.makeText(requireContext(), "Senhas não coincidem", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidCPF(cpf.text.toString())) {
            Toast.makeText(requireContext(), "CPF inválido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidPhone(celular.text.toString())) {
            Toast.makeText(requireContext(), "Telefone inválido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidEmail(email.text.toString())) {
            Toast.makeText(requireContext(), "Email inválido", Toast.LENGTH_SHORT).show()
            return false
        }

        val sexoText = sexo.selectedItem.toString().toLowerCase()
        if (sexoText != "masculino" && sexoText != "feminino") {
            Toast.makeText(requireContext(), "Sexo inválido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidBloodType(tipoSanguineo.text.toString())) {
            Toast.makeText(requireContext(), "Tipo sanguíneo inválido A+, A-, B+, B-, AB+, AB-, O+, O-", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidDateOfBirth(dataNascimento.text.toString())) {
            Toast.makeText(requireContext(), "Data de nascimento inválida (use o formato DD/MM/AAAA)", Toast.LENGTH_SHORT).show()
            return false
        }
        val db = dbCadastro.writableDatabase
        val values = ContentValues().apply {
            put(DataBaseHelper.COLUMN_NAME_NOME, nome.text.toString().trim())
            put(DataBaseHelper.COLUMN_EMAIL, email.text.toString().trim())
            put(DataBaseHelper.COLUMN_NAME_NOME, celular.text.toString().trim())
            put(DataBaseHelper.COLUMN_CPF, cpf.text.toString().trim())
            put(DataBaseHelper.COLUMN_SEXO, sexo.selectedItem.toString().toLowerCase().trim())
            put(DataBaseHelper.COLUMN_DATA_NASCIMENTO, dataNascimento.text.toString().trim())
            put(DataBaseHelper.COLUMN_TIPO_SANGUINEO, tipoSanguineo.text.toString().trim())
            put(DataBaseHelper.COLUMN_CIDADE, cidade.text.toString().trim())
            put(DataBaseHelper.COLUMN_PASSWORD, senha.text.toString().trim())
        }

        val newRowId = db?.insert(DataBaseHelper.TABLE_NAME, null, values)

        if (newRowId != -1L) {
            Toast.makeText(requireContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
            navigateToMainScreen()
        } else {
            Toast.makeText(requireContext(), "Erro ao cadastrar", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun isValidCPF(cpf: String): Boolean {
        val cpfClean = cpf.replace(".", "").replace("-", "")

        if (cpfClean.length != 11 || cpfClean.toLongOrNull() == null) {
            return false
        }

        // Verifica se todos os dígitos são iguais
        if ((0..9).map { it.toString().repeat(11) }.contains(cpfClean)) {
            return false
        }

        // Calcula o primeiro dígito verificador
        val dv1 = (0 until 9).sumOf { cpfClean[it].toString().toInt() * (10 - it) } % 11
        val firstDigit = if (dv1 < 2) 0 else 11 - dv1

        // Calcula o segundo dígito verificador
        val dv2 = (0 until 10).sumOf { cpfClean[it].toString().toInt() * (11 - it) } % 11
        val secondDigit = if (dv2 < 2) 0 else 11 - dv2

        // Retorna true se os dígitos verificadores são iguais aos do CPF fornecido
        return cpfClean[9].toString().toInt() == firstDigit && cpfClean[10].toString().toInt() == secondDigit
    }

    private fun isValidPhone(phone: String): Boolean {
        val phoneRegex = """^\(\d{2}\) \d{4,5}-\d{4}$"""
        return phone.matches(phoneRegex.toRegex())
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"""
        return email.matches(emailRegex.toRegex())
    }
    private fun isValidBloodType(bloodType: String): Boolean {
        val validBloodTypes = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        return validBloodTypes.contains(bloodType.toUpperCase())
    }
    private fun isValidDateOfBirth(dateOfBirth: String): Boolean {
        val regex = """^(?:(?:31(\/)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/)(?:0?[1-9]|1[0-2])\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$"""
        return dateOfBirth.matches(regex.toRegex())
    }
    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
    }
    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.action_cadastroFragment_to_mainScreenFragment)
    }
}