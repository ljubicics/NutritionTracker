package rs.raf.nutritiontracker.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.LayoutSavedMealItemBinding
import java.io.File

class SavedMealViewHolder(
    private val itemBinding: LayoutSavedMealItemBinding,
    var onItemDeleteClicked: (Int) -> Unit,
    var onItemEditClicked: (Int) -> Unit,
    var listener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        // ovde stavljam listener samo na konktretnu sliku u view-u koja se binduje
        itemBinding.savedMealDeleteIB.setOnClickListener{
            onItemDeleteClicked.invoke(bindingAdapterPosition)
        }

        itemBinding.savedMealEditIB.setOnClickListener {
            onItemEditClicked.invoke(bindingAdapterPosition)
        }

        // ovde stavljam listener na celu View komponentu
        itemView.setOnClickListener{
            listener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(savedMeal: SavedMeal) {
        if(savedMeal.strMealThumb?.contains("http") == true) {
            Picasso
                .get()
                .load(savedMeal.strMealThumb)
                .into(itemBinding.savedMealIV)
        } else {
            Picasso
                .get()
                .load(File(savedMeal.strMealThumb))
                .into(itemBinding.savedMealIV)
        }
        itemBinding.savedMealTV.text = savedMeal.strMeal
    }
}