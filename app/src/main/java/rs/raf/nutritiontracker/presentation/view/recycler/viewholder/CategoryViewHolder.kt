package rs.raf.nutritiontracker.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding
import java.util.function.Consumer

class CategoryViewHolder(
    private val itemBinding: LayoutItemCategoryBinding,
    var onItemMoreClicked: (Int) -> Unit,
    var listener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        // ovde stavljam listener samo na konktretnu sliku u view-u koja se binduje
        itemBinding.moreAboutCatIB.setOnClickListener{
            onItemMoreClicked.invoke(bindingAdapterPosition)
        }

        // ovde stavljam listener na celu View komponentu
        itemView.setOnClickListener{
           listener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(category: Category) {
        Picasso
            .get()
            .load(category.strCategoryThumb)
            .into(itemBinding.categoryIV)
        itemBinding.categoryNameTV.text = category.strCategory
    }
}