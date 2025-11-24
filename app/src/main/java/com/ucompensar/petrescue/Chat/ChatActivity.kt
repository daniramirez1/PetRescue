package com.ucompensar.petrescue.Chat

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ucompensar.petrescue.Constantes
import com.ucompensar.petrescue.R
import com.ucompensar.petrescue.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private var uid = ""
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var  progressDialog: ProgressDialog
    private var miUid = ""

    private var chatRuta = ""
    private var imagenUri: Uri?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializaciones
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        val uidRecibido = intent.getStringExtra("uid")

        // CORRECCIÓN 1: Asegura que el UID sea válido para la ruta de Firebase
        if (uidRecibido != null) {
            uid = limpiarRutaFirebase(uidRecibido)
        } else {
            // Manejar si el UID no se pasó (ejemplo: cerrar actividad o mostrar error)
            finish()
            return
        }

        miUid = firebaseAuth.uid!!
        chatRuta = Constantes.rutaChat(uid, miUid)

        cargarInfo()
    }

    /**
     * Limpia la cadena de caracteres prohibidos por Firebase Realtime Database
     * (reemplaza el punto '.' con coma ',').
     */
    private fun limpiarRutaFirebase(input: String): String {
        return input.replace('.', ',')
    }

    private fun cargarInfo(){
        var ref = FirebaseDatabase.getInstance().getReference("Usuarios")

        // Usamos el 'uid' limpiado para acceder al nodo del usuario
        ref.child(uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    // CORRECCIÓN 2: Manejo seguro de valores nulos
                    val nombres = snapshot.child("nombres").value as? String ?: "Usuario Desconocido"
                    val imagenUrl = snapshot.child("imagen").value as? String

                    binding.txtNombreUsuario.text = nombres

                    try {
                        if (!imagenUrl.isNullOrEmpty()) {
                            Glide.with(this@ChatActivity)
                                .load(imagenUrl)
                                .placeholder(R.drawable.perfil_usuario)
                                .into(binding.toolbarIv)
                        } else {
                            binding.toolbarIv.setImageResource(R.drawable.perfil_usuario)
                        }
                    }catch (e: Exception){
                        // Log.e(TAG, "Error cargando imagen: ${e.message}")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Implementa manejo de errores aquí (ej. Log o Toast)
                }
            })
    }

}