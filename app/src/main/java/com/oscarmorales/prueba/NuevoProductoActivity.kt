package com.oscarmorales.prueba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        var idProducto: Int? = null

        if (intent.hasExtra("producto")) {
            val producto = intent.extras?.getSerializable("producto") as Producto

            nombreText.setText(producto.nombre)
            precioText.setText(producto.precio.toString())
            descripcionText.setText(producto.descripcion)
            idProducto = producto.idProducto
            Toast.makeText(this,"Entro",Toast.LENGTH_SHORT).show()
        }

        val database = AppDatabase.getDatabase(this)


        btnGuardar.setOnClickListener{


            val nombre = nombreText.text.toString()
            val precio = precioText.text.toString().toDouble()
            val descripcion = descripcionText.text.toString()

            val producto = Producto(nombre,precio,descripcion,R.drawable.ic_launcher_background)

            if (idProducto != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    producto.idProducto = idProducto

                    database.productos().update(producto)

                    this@NuevoProductoActivity.finish()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch{

                    database.productos().insertAll(producto)

                    this@NuevoProductoActivity.finish()
                }
            }
        }
    }
}