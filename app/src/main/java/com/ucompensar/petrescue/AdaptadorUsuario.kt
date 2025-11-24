package com.ucompensar.petrescue

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ucompensar.petrescue.Chat.ChatActivity

class AdaptadorUsuario(
    context: Context,
    listaUsusarios : List<Usuario>): RecyclerView.Adapter<AdaptadorUsuario.ViewHolder?>() {

        private var context: Context
        private val listaususarios: List<Usuario>

        init {
            this.context = context
            this.listaususarios= listaUsusarios
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_usuario,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val usuario : Usuario = listaususarios[position]
        holder.uid.text = usuario.email
        holder.email.text = usuario.email
        holder.nombres.text = usuario.nombres
        Glide.with(context).load(usuario.imagen).placeholder(R.drawable.ic_imagen_perfil).into(holder.imagen)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("uid",holder.uid.text)
            Toast.makeText(context, "Has seleccionado al usuario: ${holder.nombres.text}", Toast.LENGTH_SHORT).show()
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return listaususarios.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var uid : TextView
        var email: TextView
        var nombres : TextView
        var imagen : ImageView

        init {
            uid = itemView.findViewById(R.id.item_uid)
            email = itemView.findViewById(R.id.item_email)
            nombres = itemView.findViewById(R.id.item_nombre)
            imagen = itemView.findViewById(R.id.item_imagen)


        }
    }

}