package com.example.basededatoslgde

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.basededatoslgde.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val BDD_NAME = "administracion"
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnregistrar.setOnClickListener { registrar() }

        binding.btnbuscar.setOnClickListener{ buscar() }

        binding.btneliminar.setOnClickListener { eliminar() }

        binding.btnmodificar.setOnClickListener { modificar() }
    }

    //altas en BDD
    fun registrar(){
        //creamos objeto de la clase que creamos, el cual va a administrar nuestra BDD
        val admin = AdminSQLiteOpenHelper(this, BDD_NAME, null, 1)
        val BaseDD = admin.writableDatabase //indicamos que va a ser sobreescribible

        //casteamos los edit texts
        val codigo = binding.etCodigo
        val descripcion = binding.etNombre
        val precio = binding.etPrecio

        //si los campos no estan vacios: hacemos lo siguiente
        if (codigo.text.isNotEmpty() && descripcion.text.isNotEmpty() && precio.text.isNotEmpty()){
            val registro = ContentValues() //objeto que

            //colocamos los datos obtenidos en el registro
            registro.put("codigo", codigo.text.toString())
            registro.put("descripcion", descripcion.text.toString())
            registro.put("precio", precio.text.toString())

            //insertamos el registro de valores en nuestra BDD
            BaseDD.insert("articulos", null, registro)

            //siempre que terminemos de usar una bdd, hay que cerrarla
            BaseDD.close()

            //limpiamos los campos de los et
            codigo.text.clear()
            descripcion.text.clear()
            precio.text.clear()

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

        } else {
            Snackbar.make(binding.root, "Agrega texto a los campos", Snackbar.LENGTH_INDEFINITE).setAction("OK"){ Snackbar.ANIMATION_MODE_SLIDE }.show()
        }
    }

    //Consultas en BDD
    fun buscar(){
        val admin = AdminSQLiteOpenHelper(this, BDD_NAME, null, 1)
        val BDD = admin.writableDatabase

        val codigo = binding.etCodigo.text.toString()

        if (codigo.isNotEmpty()){
            val fila:Cursor = BDD.rawQuery("select descripcion, precio from articulos where codigo = $codigo", null)
            //traduccion: selecciona la descripcion y el precio de la tabla articulos donde codigo sea igual a $codigo

            //este metodo revisa si la consulta si obtiene valores
            if (fila.moveToFirst()){
                binding.etNombre.setText(fila.getString(0))
                binding.etPrecio.setText(fila.getString(1))
                BDD.close()
            } else {
                Toast.makeText(this, "El articulo no esta registrado", Toast.LENGTH_SHORT).show()
                binding.etNombre.text.clear()
                binding.etPrecio.text.clear()
                BDD.close()
            }
        } else {
            Snackbar.make(binding.root, "Debes escribir el codigo que quieres buscar", Snackbar.LENGTH_INDEFINITE).setAction("OK"){}.show()

            binding.etNombre.text.clear()
            binding.etPrecio.text.clear()
        }
    }

    //bajas en BDD
    fun eliminar(){
        val admin = AdminSQLiteOpenHelper(this, BDD_NAME, null, 1)
        val BDD = admin.writableDatabase

        val codigo = binding.etCodigo.text.toString()

        if (codigo.isNotEmpty()){
            val cantidad = BDD.delete("articulos", "codigo= $codigo", null)
            BDD.close()

            binding.etCodigo.text.clear()
            binding.etPrecio.text.clear()
            binding.etNombre.text.clear()

            if (cantidad == 1){
                Toast.makeText(this, "Articulo eliminado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Debes escribir el codigo que quieres borrar", Toast.LENGTH_LONG).show()
        }
    }

    //modificaciones en BDD
    fun modificar(){
        val admin = AdminSQLiteOpenHelper(this, BDD_NAME, null, 1)
        val BDD = admin.writableDatabase

        val codigo = binding.etCodigo.text.toString()
        val descripcion = binding.etNombre.text.toString()
        val precio = binding.etPrecio.text.toString()

        if (codigo.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()){
            val registro = ContentValues()
            registro.put("codigo", codigo)
            registro.put("descripcion", descripcion)
            registro.put("precio", precio)

            val cantidad = BDD.update("articulos", registro, "codigo= $codigo", null)
            BDD.close()

            if (cantidad == 1){
                Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se pudo modificar", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}