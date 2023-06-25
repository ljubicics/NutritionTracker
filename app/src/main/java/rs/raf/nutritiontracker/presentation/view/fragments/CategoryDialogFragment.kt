package rs.raf.nutritiontracker.presentation.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import rs.raf.nutritiontracker.R

class CategoryDialogFragment(
    private val catDesc: String
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("More about category")
            .setMessage(catDesc)
            .setPositiveButton("OK") { _,_ -> }
            .create()

    companion object {
        const val TAG = "MoreAboutCategoryDialog"
    }
}
