package com.example.myapplication
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_NOME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_CELULAR + " TEXT, "
                + COLUMN_CPF + " TEXT, "
                + COLUMN_SEXO + " TEXT, "
                + COLUMN_DATA_NASCIMENTO + " TEXT, "
                + COLUMN_TIPO_SANGUINEO + " TEXT, "
                + COLUMN_CIDADE + " TEXT, "
                + COLUMN_PASSWORD + " TEXT" + ")")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addCadastro(nome: String, email: String, celular: String, cpf: String, sexo: String,
                    dataNascimento: String, tipoSanguineo: String, cidade: String, senha: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_NOME, nome)
            put(COLUMN_EMAIL, email)
            put(COLUMN_CELULAR, celular)
            put(COLUMN_CPF, cpf)
            put(COLUMN_SEXO, sexo)
            put(COLUMN_DATA_NASCIMENTO, dataNascimento)
            put(COLUMN_TIPO_SANGUINEO, tipoSanguineo)
            put(COLUMN_CIDADE, cidade)
            put(COLUMN_PASSWORD, senha)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getLogin(email: String, senha: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?", arrayOf(email, senha))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Cadastro.db"
        const val TABLE_NAME = "cadastro"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME_NOME = "nome"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_CELULAR = "celular"
        const val COLUMN_CPF = "cpf"
        const val COLUMN_SEXO = "sexo"
        const val COLUMN_DATA_NASCIMENTO = "data_nascimento"
        const val COLUMN_TIPO_SANGUINEO = "tipo_sanguineo"
        const val COLUMN_CIDADE = "cidade"
        const val COLUMN_PASSWORD = "senha"
    }
}