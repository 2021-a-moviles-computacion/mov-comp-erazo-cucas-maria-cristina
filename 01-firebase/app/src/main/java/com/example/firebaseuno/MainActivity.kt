package com.example.firebaseuno

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Array.set

class MainActivity : AppCompatActivity() {
    val CODIGO_INICIO_SESION=102
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonLogin= findViewById<Button>(R.id.btn_login)
        botonLogin.setOnClickListener{
            llamarLoginUsuario()
        }
    }
    fun llamarLoginUsuario(){
        val providers= arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.hmtl",
                    "https://example.com/privacy.html"
                )
                .build(),
            CODIGO_INICIO_SESION
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODIGO_INICIO_SESION->{
                if(resultCode==Activity.RESULT_OK){
                    val usuario =IdpResponse.fromResultIntent(data)
                    if(usuario?.isNewUser==true){
                        Log.i("firebase-login","Nuevo usuario")
                    }else{
                        Log.i("firebase-login","antiguo usuario")
                    }
                }else
                {
                    Log.i("firebase-login","el usuario cancelo")
                }
            }
        }
        fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){
            val usuarioLogeado = FirebaseAuth
                .getInstance()
                .getCurrentUser()
            if(usuario.email != null){
                //crear roles por ejemplo usuario, administrador
                val db = Firebase.firestore
                val rolesUsuario = arrayListOf("usuario")
                val nuevoUsuario = hashMapOf<String, Any>(
                    "roles" to rolesUsuario,
                    "uid" to usuarioLogeado!!.uid
                )
                val identificadorUsuario = usuario.email
                db.collection("usuario")
                    .document(identificadorUsuario.toString())
                    .set(nuevoUsuario)

                    //.add(nuevoUsuario)
                    .addOnSuccessListener {
                        Log.i("firebase-firestore","SE CREO")
                    }
                    .addOnFailureListener{
                        Log.i("firebase-firestore","fallo")
                    }

            }else{
                Log.i("firebase-login","error")
            }
        }
    }
}