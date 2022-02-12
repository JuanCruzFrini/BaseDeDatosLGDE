package com.example.basededatoslgde

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basededatoslgde.databinding.ActivityRvBusquedaBinding

class RecyclerBusqueda : AppCompatActivity(){

    lateinit var binding: ActivityRvBusquedaBinding

    lateinit var objetos: ArrayList<Objeto>
    var lista : MutableList<Objeto> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRvBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        objetos = arrayListOf(
                Objeto(1, "hola", 11),
                Objeto(5, "ciao", 22),
                Objeto(3, "adios", 33),
                Objeto(70, "chau", 44),
                Objeto(1, "hola", 11),
                Objeto(2, "chau", 22),
                Objeto(3, "adios", 33))

        //ordena el array por codigo
        objetos.sortBy { objeto -> objeto.codigo }

        //ordena el array por orden alfabetico
        //objetos.sortBy { objeto -> objeto.descripcion }

        lista.addAll(objetos)

        binding.rvBusqueda.layoutManager = LinearLayoutManager(this)
        binding.rvBusqueda.adapter = AdapterBusqueda(this, lista as ArrayList<Objeto>)

        binding.btnBuscar.setOnClickListener { buscar(binding.buscador.text.toString()) }
    }

    fun buscar(query: String?){
        val lista : MutableList<Objeto> = ArrayList()

        for (objeto in objetos){
            if (objeto.descripcion.toLowerCase().contains(query.toString().toLowerCase()) || objeto.descripcion.toString().startsWith(query.toString()) || objeto.codigo.toString() == query.toString()){

                val objetos: ArrayList<Objeto> = arrayListOf(objeto)
                lista.addAll(objetos)
            }
        }
        if(lista.size == 0) Toast.makeText(this, "Sin coincidencias", Toast.LENGTH_SHORT).show()

        //recargamos el recycler con la nueva lista
        binding.rvBusqueda.layoutManager = LinearLayoutManager(this)
        binding.rvBusqueda.adapter = AdapterBusqueda(this, lista as ArrayList<Objeto>)

        ocultarTeclado()
    }

    override fun onBackPressed() {
        var i = 1
        binding.rvBusqueda.adapter = AdapterBusqueda(this, lista as ArrayList<Objeto>)
        if (binding.buscador.text.toString() == "") i++ else binding.buscador.text.clear()
        if (i >= 2) finish()
    }

    fun ocultarTeclado(){
        // Check if no view has focus:
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
