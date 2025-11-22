package com.ucompensar.petrescue

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ucompensar.petrescue.databinding.ActivityRegistroEmailBinding
import org.intellij.lang.annotations.Pattern

class RegistroEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrar.setOnClickListener {
            validarInformacion()
        }

    }


    private var nombres = ""
    private var email = ""
    private var password = ""
    private var r_password = ""

    private fun validarInformacion() {
        nombres = binding.etNombre.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        r_password = binding.etRRegistrar.text.toString().trim()


        if (nombres.isEmpty()){
            binding.etNombre.error = "Ingrese nombre"
            binding.etNombre.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Correo Inválido"
            binding.etEmail.requestFocus()
        }else if(email.isEmpty()){
            binding.etEmail.error = "Ingrese un correo"
            binding.etEmail.requestFocus()
        }else if(password.isEmpty()){
            binding.etPassword.error = "Ingrese Contraseña"
            binding.etPassword.requestFocus()
        }else if(r_password.isEmpty()){
            binding.etRRegistrar.error = "Repita la contraseña"
            binding.etRRegistrar.requestFocus()
        }else if(password != r_password){
            binding.etRRegistrar.error = "No coinciden las contraseñas"
            binding.etRRegistrar.requestFocus()
        }else{
            registrarUsuario()
        }


    }

    private fun registrarUsuario(){
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                actualizarInformacion()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Fallo la creacion de la cuenta debido a ${e.message}"
                    , Toast.LENGTH_SHORT
                ).show()

            }
    }

    private fun actualizarInformacion() {
        progressDialog.setMessage("Guardando Información")
        val uidU = firebaseAuth.uid
        val nombresU = nombres
        val emailU = firebaseAuth.currentUser!!.email
        val tiempoR = Constantes.obtenerTiempoD()

        var datosUsuarios = HashMap<String, Any>()

        datosUsuarios["uid"] = "$uidU"
        datosUsuarios["nombres"] = "$nombresU"
        datosUsuarios["email"] = "$emailU"
        datosUsuarios["tiempoR"] = "$tiempoR"
        datosUsuarios["provedor"] = "Email"
        datosUsuarios["estado"] = "Online"
        datosUsuarios["imagen"] = ""


        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidU!!)
            .setValue(datosUsuarios)
            .addOnSuccessListener {
                progressDialog.dismiss()

                startActivity(Intent(applicationContext, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Fallo la creacion de la cuenta debido a ${e.message}"
                    , Toast.LENGTH_SHORT
                ).show()
            }

    }


}


