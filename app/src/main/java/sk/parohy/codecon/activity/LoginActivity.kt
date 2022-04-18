package sk.parohy.codecon.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.preference.PreferenceManager
import sk.parohy.codecon.R
import sk.parohy.codecon.api.NetworkResult
import sk.parohy.codecon.api.isLoading
import sk.parohy.codecon.api.isSuccessful
import sk.parohy.codecon.viewmodel.LoginViewModel

fun Context.startLoginActivity() {
    val intent = Intent(this, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model by viewModels<LoginViewModel>()
        setContent {
            val state by model.state.collectAsState(initial = null)

            LaunchedEffect(state) {
                if (state.isSuccessful)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }

            LoginScreen(state = state, onSubmit = { u, p ->
                model.login(u, p, PreferenceManager.getDefaultSharedPreferences(this))
            })
        }
    }
}

@Composable
private fun LoginScreen(state: NetworkResult<String>?, onSubmit: (String, String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(R.string.login_headline),
            style = MaterialTheme.typography.h1,
            fontSize = 32.sp
        )

        Text(
            text = stringResource(R.string.login_loading),
            fontSize = 21.sp,
            modifier = Modifier.alpha(if (state.isLoading) 1f else 0f)
        )

        LoginForm(
            error = (state as? NetworkResult.Failure)?.ex?.message,
            onSubmit = onSubmit
        )
    }
}

@Composable
private fun LoginForm(error: String?, onSubmit: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var passsword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = {
                Text(text = stringResource(R.string.login_username))
            },
            isError = error != null
        )
        error?.let { Text(
            modifier = Modifier.align(Alignment.Start),
            text = it,
            color = Color.Red
        ) }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = passsword,
            onValueChange = { passsword = it },
            label = {
                Text(text = stringResource(R.string.login_password))
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = error != null
        )
        error?.let { Text(
            modifier = Modifier.align(Alignment.Start),
            text = it,
            color = Color.Red
        ) }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            onClick = { onSubmit(username, passsword) },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.login_submit)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginForm() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LoginForm(null, { a, b -> })
        LoginForm("Time out", { a, b -> })
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(state = null, onSubmit = { u, p -> })
}