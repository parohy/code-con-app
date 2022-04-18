package sk.parohy.codecon.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import sk.parohy.codecon.api.NetworkResult
import sk.parohy.codecon.databinding.ActivityLoginBinding
import sk.parohy.codecon.databinding.LayoutLoginBinding
import sk.parohy.codecon.viewmodel.LoginViewModel

fun ViewBinding.startLoginActivity() {
    val intent = Intent(root.context, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    root.context.startActivity(intent)
}

class LoginActivity: VBActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun ActivityLoginBinding.onBind() {
        val model by viewModels<LoginViewModel>()

        loginForm.initForm(model)

        lifecycleScope.launchWhenResumed {
            model.state.collect { state ->
                when (state) {
                    is NetworkResult.Failure -> loginForm.onError(state.ex.message)
                    is NetworkResult.Success -> startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    else                     -> loginForm.onError(null)
                }

                loading.isVisible = state is NetworkResult.Loading
                loginForm.bSignIn.isEnabled = state !is NetworkResult.Loading
            }
        }
    }

    private fun LayoutLoginBinding.onError(msg: String?) {
        username.error = msg
        password.error = msg
    }

    private fun LayoutLoginBinding.initForm(model: LoginViewModel) {
        bSignIn.setOnClickListener {
            model.login(
                username = etUsername.text?.toString() ?: "",
                password = etPassword.text?.toString() ?: "",
                pref     = PreferenceManager.getDefaultSharedPreferences(root.context)
            )
        }
    }
}