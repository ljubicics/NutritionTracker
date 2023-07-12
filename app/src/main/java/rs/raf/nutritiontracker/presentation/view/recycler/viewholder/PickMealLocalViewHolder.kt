package rs.raf.nutritiontracker.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.LayoutPickMealBinding
import rs.raf.nutritiontracker.databinding.LayoutSavedMealItemBinding
import java.io.File

class PickMealLocalViewHolder(
    private val itemBinding: LayoutPickMealBinding,
    var onItemAddClicked: (Int) -> Unit,
    var listener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root){
    init {
        itemBinding.pickMealAddIB.setOnClickListener{
            onItemAddClicked.invoke(bindingAdapterPosition)
        }
        itemView.setOnClickListener{
            listener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(savedMeal: SavedMeal) {
        if(savedMeal.strMealThumb?.contains("http") == true) {
            Picasso
                .get()
                .load(savedMeal.strMealThumb)
                .into(itemBinding.pickMealIV)
        } else {
            Picasso
                .get()
                .load(File(savedMeal.strMealThumb))
                .into(itemBinding.pickMealIV)
        }
        itemBinding.pickMealTV.text = savedMeal.strMeal
    }
}