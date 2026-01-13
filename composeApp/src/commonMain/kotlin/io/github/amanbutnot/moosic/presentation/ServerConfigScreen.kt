package io.github.amanbutnot.moosic.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.amanbutnot.moosic.business.PingUiState
import io.github.amanbutnot.moosic.business.PingViewModel
import io.github.amanbutnot.moosic.common.appSettings
import io.github.amanbutnot.moosic.presentation.common.MTextField
import io.github.amanbutnot.moosic.presentation.common.MoosicScaffold
import org.jetbrains.compose.ui.tooling.preview.Preview

object ServerConfigScreen : Screen {
    @Composable
    override fun Content() {
        ServerConfigContent()
    }
}

@Preview()
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerConfigContent(
    viewModel: PingViewModel = viewModel { PingViewModel() }
) {
    val navigator = LocalNavigator.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }

    var serverUrl by rememberSaveable { mutableStateOf(appSettings.serverUrl) }
    var username by rememberSaveable { mutableStateOf(appSettings.username) }
    var password by rememberSaveable { mutableStateOf(appSettings.password) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is PingUiState.Success -> {
                appSettings.serverUrl = serverUrl
                appSettings.username = username
                appSettings.password = password
                snackbarHostState.showSnackbar("Connected successfully!")
                navigator?.pop()
            }
            is PingUiState.Error -> {
                snackbarHostState.showSnackbar((uiState as PingUiState.Error).message)
            }
            else -> {}
        }
    }

    MoosicScaffold(
        title = "Server Configuration",
        onBackClick = { navigator?.pop() },
        snackbarHostState = snackbarHostState,
        scrollBehavior = scrollBehavior
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            MTextField(
                value = serverUrl,
                onValueChange = { serverUrl = it },
                placeholder = "https://server-url.in",
                isPassword = false,
                isNumber = false,
                label = "Server Url"
            )

            MTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "Enter your username",
                isPassword = false,
                isNumber = false,
                label = "Username"
            )

            MTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true,
                isNumber = false,
                label = "Password"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.ping(serverUrl, username, password)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                enabled = uiState !is PingUiState.Loading && serverUrl.isNotBlank() && username.isNotBlank() && password.isNotBlank()
            ) {
                if (uiState is PingUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save and Connect")
                }
            }
        }
    }
}
