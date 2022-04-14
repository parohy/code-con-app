package sk.parohy.codecon.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class VBActivity<VB: ViewBinding>(inflate: (LayoutInflater) -> VB): AppCompatActivity() {
    private val binding: VB by lazy { inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.onBind()
    }

    abstract fun VB.onBind()
}
