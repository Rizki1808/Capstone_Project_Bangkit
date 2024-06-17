package com.example.capstoneproject.ui.feature.item.minumobat

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.data.database.Drugs
import com.example.capstoneproject.data.helper.DrugsDiffCallBack
import com.example.capstoneproject.databinding.ItemObatBinding

class MinumObatAdapter: RecyclerView.Adapter<MinumObatAdapter.MinumObatViewHolder>() {

    private val listDrugs = ArrayList<Drugs>()

    fun setListDrugs(listDrugs: List<Drugs>){
        val diffCallback = DrugsDiffCallBack(this.listDrugs, listDrugs)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listDrugs.clear()
        this.listDrugs.addAll(listDrugs)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinumObatViewHolder {
        val binding = ItemObatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MinumObatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDrugs.size
    }

    override fun onBindViewHolder(holder: MinumObatViewHolder, position: Int) {
        holder.bind(listDrugs[position])
    }

    inner class MinumObatViewHolder(private val binding: ItemObatBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(drugs: Drugs){
            with(binding){
                tvName.text = drugs.name
                tvDose.text = drugs.dose
                tvTime.text = drugs.timeToTake
                tvInstruction.text = drugs.instructions
                cardViewDrugs.setOnClickListener {
                    val intent = Intent(it.context, TambahObatActivity::class.java)
                    intent.putExtra(TambahObatActivity.EXTRA_DRUGS, drugs)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}