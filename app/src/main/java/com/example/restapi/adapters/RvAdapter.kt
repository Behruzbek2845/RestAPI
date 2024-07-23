package com.example.restapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.databinding.ItemRvBinding
import com.example.restapi.utils.MyNotes


class RvAdapter(val rvAction: RvAction,val list: ArrayList<MyNotes>): RecyclerView.Adapter<RvAdapter.VH>() {
    inner class VH(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        fun OnBind(myNotes: MyNotes){
            itemRvBinding.tvSarlavha.text = myNotes.sarlavha
            itemRvBinding.tvBatafsil.text = myNotes.batafsil
            itemRvBinding.tvBajarildi.text = myNotes.bajarildi.toString()
            itemRvBinding.tvSana.text = myNotes.sana
            itemRvBinding.tvZaruriylik.text = myNotes.zarurlik
            itemRvBinding.tvOxxirgiMuddat.text = myNotes.oxirgi_muddat
            itemRvBinding.btnMore.setOnClickListener {
                rvAction.onClick(myNotes, adapterPosition, itemRvBinding.btnMore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.OnBind(list[position])
    }

    interface RvAction{
        fun onClick(myNotes: MyNotes, position: Int, imageView: ImageView)
    }

}