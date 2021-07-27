package com.example.aplicacionesmoviles2021a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import java.net.URI

import android.net.Uri


class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    val CODIGO_RESPUESTA_INTENT_IMPLICITO = 402

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EBaseDeDatos.TablaUsuario = ESqliteHelperUsuario(this)


        val botonIrCicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonIrCicloVida.setOnClickListener { abrirActividad(A1ciclcoVida::class.java) }
        val botonIrListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )
        botonIrListView.setOnClickListener { abrirActividad(BListView::class.java) }

        val botonIrIntent = findViewById<Button>(
            R.id.btn_ir_intent
        )
        botonIrIntent
            .setOnClickListener { abrirActividadConParametros(CIntentExplicitoParametros::class.java) }

        val botonAbrirIntentImplicito = findViewById<Button>(
            R.id.btn_ir_intent_implicito
        )
        botonAbrirIntentImplicito
            .setOnClickListener {
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                startActivityForResult(intentConRespuesta, CODIGO_RESPUESTA_INTENT_IMPLICITO)
            }

        val botorIrUsuario= findViewById<Button>(R.id.btn_ir_usuario)
        botorIrUsuario.setOnClickListener{abrirActividad(Crud::class.java)}

        val botonAbrirRecyclerView= findViewById<Button>(R.id.btn_ir_recycler_view)
            botonAbrirRecyclerView
                .setOnClickListener{
                    abrirActividadConParametros(GRecyclerView::class.java)
                }

        val botonirHTTP= findViewById<Button>(R.id.btn_ir_http)
        botonirHTTP
            .setOnClickListener{
                abrirActividadConParametros(HTTPActivity::class.java)
            }





    }

    fun abrirActividad(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        // this.startActivity(intent)
        startActivity(intentExplicito)
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
//        intentExplicito.putExtra("nombre", "Adrian")
//        intentExplicito.putExtra("apellido", "Eguez")
//        intentExplicito.putExtra("edad", 32)
//        intentExplicito.putExtra(
//            "entrenador",
//            BEntrenador("Adrian", "Eguez")
//        )
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)

//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            when(it.resultCode){
//                Activity.RESULT_OK-> {
//                    //Ejecutar codigo OK
//                    it.data?.getStringExtra("nombreModofocado")
//                    it.data?.getIntExtra("edadModificada",0)
//                    it.data?.getParcelableExtra<BEntrenador>("entrenadorModificado")
//                }
//            }
//        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODIGO_RESPUESTA_INTENT_EXPLICITO -> {
                if (resultCode == RESULT_OK) {
                    Log.i("intent-explicito", "SI actualizo los datos")
                    if (data != null) {
                        val nombre = data.getStringExtra("nombreModificado")
                        val edad = data.getIntExtra("edadModificado", 0)
                        val entrenador =
                            data.getParcelableExtra<BEntrenador>("entrenadorModificado")
                        Log.i("intent-explicito", "${nombre}")
                        Log.i("intent-explicito", "${edad}")
                        if (entrenador != null) {
                            Log.i("intent-explicito", "${entrenador.nombre}")
                        }
                    }
                }
            }
            CODIGO_RESPUESTA_INTENT_IMPLICITO -> {
                if(resultCode == RESULT_OK){
                    if(data != null){
                        if(data.data != null){
                            val uri: Uri = data.data!!
                            val cursor = contentResolver.query(
                                uri,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            cursor?.moveToFirst()
                            val indiceTelefono = cursor?.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                            val telefono = cursor?.getString(
                                indiceTelefono!!
                            )
                            cursor?.close()
                            Log.i("resultado", "Telefono ${telefono}")
                        }
                    }
                }
            }
        }
    }
}