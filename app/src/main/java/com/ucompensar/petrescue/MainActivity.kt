package com.ucompensar.petrescue

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ucompensar.petrescue.Fragmentos.FragmentChats
import com.ucompensar.petrescue.Fragmentos.FragmentPerfil
import com.ucompensar.petrescue.Fragmentos.FragmentUsuarios
import com.ucompensar.petrescue.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser==null){
            irOpcionesLogin()
        }

        //Fragmento por defecto
        verFragmentoPerfil()

        binding.bottonNV.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.item_perfil->{
                    //Visualizar el fragmento perfil
                    verFragmentoPerfil()
                    true
                }
                R.id.item_usuario->{
                    //visualizar el fragmento usuarios
                    verFragmentoUsuarios()
                    true
                }
                R.id.item_chats->{
                    //visualizar el fragmento chats
                    verFragmentoChats()
                    true
                }
                else -> {
                    false
                }
            }
        }

        }

    private fun irOpcionesLogin() {
        startActivity(Intent(applicationContext, OpcionesLoginActivity::class.java))
        finishAffinity()
    }

    private fun verFragmentoPerfil(){
        binding.tvTitulo.text = "Perfil"
        val fragment = FragmentPerfil()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment,"Fragment Perfil")
        fragmentTransaction.commit()

    }
    private fun verFragmentoUsuarios(){
        binding.tvTitulo.text = "Usuarios"
        val fragment = FragmentUsuarios()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment,"Fragment Usuarios")
        fragmentTransaction.commit()
    }
    private fun verFragmentoChats(){
        binding.tvTitulo.text = "Chats"
        val fragment = FragmentChats()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment,"Fragment Chats")
        fragmentTransaction.commit()
    }





    }
