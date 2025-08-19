package com.example.lda.formfragment.mutation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lda.R
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.ImagePickerHandler


class SupportingDocument : Fragment() {

    lateinit var button01: Button
    lateinit var button02: Button
    lateinit var button03: Button

    lateinit var fileText1: TextView
    lateinit var fileText2: TextView
    lateinit var fileText3: TextView

    lateinit var imagePickerHandler: ImagePickerHandler

    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_supporting_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container1 = view.findViewById<View>(R.id.img_01)
        val container2 = view.findViewById<View>(R.id.img_02)
        val container3 = view.findViewById<View>(R.id.img_03)

        fileText1 = container1.findViewById(R.id.image_text)
        fileText2 = container2.findViewById(R.id.image_text)
        fileText3 = container3.findViewById(R.id.image_text)

        button01 = container1.findViewById(R.id.button)
        button02 = container2.findViewById(R.id.button)
        button03 = container3.findViewById(R.id.button)

        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)

        // Register camera and gallery launchers
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imagePickerHandler.handleCameraResult()
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imagePickerHandler.handleGalleryResult(it)
            }
        }

        // Initialize ImagePickerHandler
        imagePickerHandler = ImagePickerHandler(this) { index ->
            when (index) {
                1 -> fileText1
                2 -> fileText2
                3 -> fileText3
                else -> fileText1
            }
        }

        // Setup image button click listeners
        imagePickerHandler.setUpImageButtonClicks(
            button01 to 1,
            button02 to 2,
            button03 to 3
        )

        // Assign launchers
        imagePickerHandler.setLaunchers(cameraLauncher, galleryLauncher)


        val submitButton = view.findViewById<Button>(R.id.mutation_btn)
        submitButton.setOnClickListener {
            if (fileText1.text.isNullOrEmpty() && fileText2.text.isNullOrEmpty() && fileText3.text.isNullOrEmpty() ){
                AlertDialogHelper.showAlertDialog(
                    requireContext(),
                    "Alert Message",
                    "Plz Select Minimum One Image!",
                    "Ok", { dialog, which ->
                        dialog.dismiss()
                    },
                    "Cancel", { dialog, which ->
                        dialog.dismiss() // Close the dialog
                    }
                )
                return@setOnClickListener
            }
            else if (!checkBox.isChecked){
                AlertDialogHelper.showAlertDialog(
                    requireContext(),
                    "Alert Message",
                    "Plz Select Check Box!",
                    "Ok", { dialog, which ->
                        dialog.dismiss()
                    },
                    "Cancel", { dialog, which ->
                        dialog.dismiss()
                    }
                )
                return@setOnClickListener
            }

            AlertDialogHelper.showAlertDialog(
                requireContext(),
                "Alert Message",
                "Saved Successfully!!",
                "Ok", { dialog, which ->
                    dialog.dismiss()
                    // Go back 3 fragments
                    val fm = requireActivity().supportFragmentManager
                    if (fm.backStackEntryCount >= 3) {
                        repeat(3) { fm.popBackStack() }
                    } else {
                        requireActivity().finish() // If less than 3 in stack, just finish
                    }
                },
                "Cancel", { dialog, which ->
                    dialog.dismiss()
                }
            )

        }
    }
}
