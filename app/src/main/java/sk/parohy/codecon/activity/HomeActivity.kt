package sk.parohy.codecon.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.preference.PreferenceManager
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import sk.parohy.codecon.R
import sk.parohy.codecon.api.NetworkResult
import sk.parohy.codecon.api.isLoading
import sk.parohy.codecon.api.model.God
import sk.parohy.codecon.theme.CodeConTheme
import sk.parohy.codecon.theme.codecon_light
import sk.parohy.codecon.theme.codecon_night
import sk.parohy.codecon.viewmodel.HomeViewModel

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model by viewModels<HomeViewModel>()

        setContent {
            CodeConTheme {
                HomeScreen(
                    model = model,
                    onLogout = {
                        model.logOut(PreferenceManager.getDefaultSharedPreferences(this))
                        startLoginActivity()
                    }
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(model: HomeViewModel, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(onLogout = onLogout)

        val state by model.state.collectAsState(initial = null)
        
        LaunchedEffect(state) {
            if (state == null) model.refresh()
        }
        
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
            onRefresh = model::refresh
        ) {
            LazyColumn {
                (state as? NetworkResult.Success)?.let { s ->
                    items(s.value) { god ->
                        GodCard(god = god)
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(onLogout: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.home_headline),
            style = MaterialTheme.typography.h2
        )

        TextButton(
            onClick = onLogout
        ) {
            Text(
                text = stringResource(id = R.string.logout),
                color = if (isSystemInDarkTheme()) codecon_night else codecon_light
            )
        }
    }
}

@Composable
private fun GodCard(god: God) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CodBody(text = "${stringResource(id = R.string.item_god_name)} ${god.name}")
            CodBody(text = "${stringResource(id = R.string.item_god_title)} ${god.title}")
            CodBody(
                text = "${stringResource(id = R.string.item_god_desc)} ${god.desc}",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CodBody(
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Preview
@Composable
private fun GodCardLightPreview() {
    CodeConTheme(false) {
        Column(modifier = Modifier.fillMaxWidth()) {
            GodCard(
                god = God(
                    "Tutut",
                    "Funny joker",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
            )
            GodCard(
                god = God(
                    "Tutut",
                    "Funny joker",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
            )
            GodCard(
                god = God(
                    "Tutut",
                    "Funny joker",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
            )
        }
    }
}

@Preview
@Composable
private fun GodCardDarkPreview() {
    CodeConTheme(true) {
        Column(modifier = Modifier.fillMaxWidth()) {
            GodCard(
                god = God(
                    "Tutut",
                    "Funny joker",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
            )
            GodCard(
                god = God(
                    "Tutut",
                    "Funny joker",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
            )
            GodCard(
                god = God(
                    "Tutut",
                    "Funny joker",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                )
            )
        }
    }
}