package hu.bme.aut.android.diggerdoggo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.diggerdoggo.*
import hu.bme.aut.android.diggerdoggo.data.UpgradeItem
import kotlin.concurrent.thread


class UpgradeAdapter(private val listener: BonesListener, context: Context) :
    RecyclerView.Adapter<UpgradeAdapter.UpgradeViewHolder>() {
    private val upgradeContext = context
    private val items = mutableListOf<UpgradeItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpgradeViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_upgrade_list, parent, false)
        return UpgradeViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UpgradeViewHolder, position: Int) {
        var item = items[position]
        holder.nameTextView.text = item.name
        holder.descriptionTextView.text = item.description
        holder.effectTextView.text = "/tap: +"+item.effect.toString()
        holder.priceTextView.text = "price: "+item.price.toString()
        holder.iconImageView.setImageResource(getResId(item.icon))
       if (!item.isbought){
           holder.buyButton.setOnClickListener {
               if(bones >= item.price){
                   bones -= item.price
                   bonesptap += item.effect
                   listener.onBonesChanged()
                   val changeItem = UpgradeItem(item.id, item.icon, item.name, item.description, item.effect, item.price
                       , true)
                   thread{
                       database.UpgradeItemDao().update(changeItem)
                   }
                   holder.buyButton.setImageResource(R.drawable.bought)
                   Toast.makeText(upgradeContext, "You now get more bones per tap!",Toast.LENGTH_SHORT).show()
                   holder.buyButton.setOnClickListener {
                       Toast.makeText(upgradeContext, "Already bought!",Toast.LENGTH_SHORT).show()
                   }
               }
               else Toast.makeText(upgradeContext, "Not enough bones!",Toast.LENGTH_SHORT).show()
           }
       }
       else {
           holder.buyButton.setOnClickListener {
               Toast.makeText(upgradeContext, "Already bought!",Toast.LENGTH_SHORT).show()
           }
       }

        holder.item = item

        if(item.isbought) {
            holder.buyButton.setImageResource(R.drawable.bought)
        }
        else holder.buyButton.setImageResource(R.drawable.buybutton)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    interface BonesListener {
        fun onBonesChanged()
    }

    inner class UpgradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val iconImageView: ImageView
        val nameTextView: TextView
        val descriptionTextView: TextView
        val effectTextView: TextView
        val priceTextView: TextView
        val buyButton: ImageButton

        var item: UpgradeItem? = null

        init {
            iconImageView = itemView.findViewById(R.id.UpgradeItemIconImageView)
            nameTextView = itemView.findViewById(R.id.UpgradeItemNameTextView)
            descriptionTextView = itemView.findViewById(R.id.UpgradeItemDescriptionTextView)
            effectTextView = itemView.findViewById(R.id.UpgradeItemEffectTextView)
            priceTextView = itemView.findViewById(R.id.UpgradeItemPriceTextView)
            buyButton = itemView.findViewById(R.id.UpgradeItemBuyButton)
        }
    }

    fun addItem(item: UpgradeItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(upgradeItems: List<UpgradeItem>) {
        items.clear()
        items.addAll(upgradeItems)
        notifyDataSetChanged()
    }

    private fun getResId(icon: String): Int {
        return when (icon) {
            "R.drawable.bowl" -> R.drawable.bowl
            "R.drawable.pedigrii" -> R.drawable.pedigrii
            "R.drawable.fantasticks" -> R.drawable.fantasticks
            "R.drawable.basictoy" -> R.drawable.basictoy
            "R.drawable.ball" -> R.drawable.ball
            "R.drawable.squeakychicken" -> R.drawable.squeakychicken
            "R.drawable.angryplushie" -> R.drawable.angryplushie
            "R.drawable.strangemushroom" -> R.drawable.strangemushroom
            else -> R.drawable.bonesmall
        }
    }

}