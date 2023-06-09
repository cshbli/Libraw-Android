package com.tssystems.librawtest

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.deepvi.libraw.Libraw
import com.tssystems.librawtest.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val selectedImage =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { result ->
            if (result != null) {
                openImage(result)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.open.setOnClickListener {
            selectedImage.launch(arrayOf("*/*"))
        }
        binding.list.setOnClickListener {
            val b = AlertDialog.Builder(this@MainActivity)
            b.setMessage(Libraw.cameraList)
            b.setCancelable(true)
            b.show()
        }
    }

    private fun openImage(uri: Uri) {
        binding.progressBar.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            val input: InputStream?
            try {
                input = contentResolver.openInputStream(uri)
                val os = openFileOutput("temp", MODE_PRIVATE)
                while (true) {
                    val temp = ByteArray(1024 * 512)
                    val l = input!!.read(temp)
                    if (l <= 0) break
                    os.write(temp, 0, l)
                }
                input.close()
                os.close()
            } catch (e: Exception) {
                e.printStackTrace()
                binding.status.text = String.format("Exception while decoding %s : %s", uri.path, e)
            }
            val path = getFileStreamPath("temp").absolutePath
            val bitmap = Libraw.decodeAsBitmap(path, true)
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.INVISIBLE
                binding.image.setImageBitmap(bitmap)
                if (bitmap == null) binding.status.text =
                    String.format("Libraw failed to decode file %s", uri.path)
                else binding.status.text =
                    String.format("Libraw succeeded to decode file %s", uri.path)
            }
        }
    }
}