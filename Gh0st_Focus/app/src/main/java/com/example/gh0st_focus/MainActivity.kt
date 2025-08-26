package com.example.gh0st_focus

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gh0st_focus.TimerViewModel.Companion.format
import com.example.gh0st_focus.ui.Gh0stFocusTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val vm: TimerViewModel by viewModels()

    private val requestNotifPerm = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* handled gracefully */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= 33) {
            requestNotifPerm.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            // Force dark + AMOLED black; set amoled=false if you prefer slightly lifted dark
            Gh0stFocusTheme(useDarkTheme = true, amoled = true) {
                val sysUi = rememberSystemUiController()
                val barsColor = MaterialTheme.colorScheme.background
                val darkIcons = false // keep icons light on dark bars

                SideEffect {
                    sysUi.setStatusBarColor(color = barsColor, darkIcons = darkIcons)
                    sysUi.setNavigationBarColor(color = barsColor, darkIcons = darkIcons)
                }

                val state by vm.state.collectAsState()
                DisposableEffect(state.isRunning) {
                    if (state.isRunning) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    } else {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    }
                    onDispose { window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) }
                }

                AppScaffold(vm)
            }
        }
    }
}

@Composable
private fun AppScaffold(vm: TimerViewModel) {
    val state by vm.state.collectAsState()
    val ctx = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Gh0st Focus",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground
            )

            // Time readout
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(28.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = format(state.remainingMillis),
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Presets + auto-repeat
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val presets = listOf(15, 25, 45)
                presets.forEach { min ->
                    AssistChip(
                        onClick = { vm.setMinutes(min) },
                        label = { Text("${min}m") },
                        leadingIcon = {},
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Spacer(Modifier.weight(1f))

                FilterChip(
                    selected = state.autoRepeat,
                    onClick = { vm.toggleAutoRepeat() },
                    label = { Text("Auto-repeat") },
                    leadingIcon = {
                        if (state.autoRepeat) Icon(Icons.Filled.Check, contentDescription = null)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            // Custom minutes
            var custom by remember { mutableStateOf(TimerViewModel.toMinutes(state.totalMillis).toString()) }
            LaunchedEffect(state.totalMillis) { custom = TimerViewModel.toMinutes(state.totalMillis).toString() }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = custom,
                    onValueChange = { s -> custom = s.filter { it.isDigit() }.take(3) },
                    label = { Text("Minutes") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
                Button(
                    onClick = {
                        val m = custom.toIntOrNull()
                        if (m != null && m > 0) vm.setMinutes(m)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text("Set") }
            }

            // Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val btnColors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

                if (!state.isRunning) {
                    ElevatedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { vm.start() },
                        shape = RoundedCornerShape(16.dp),
                        colors = btnColors
                    ) { Text("Start") }
                } else {
                    ElevatedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { vm.pause() },
                        shape = RoundedCornerShape(16.dp),
                        colors = btnColors
                    ) { Text("Pause") }
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { vm.reset() },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    // ✅ Fix: use a real BorderStroke instead of trying to null a Brush
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) { Text("Reset") }
            }

            // Footer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Session length",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "${TimerViewModel.toMinutes(state.totalMillis)} minutes",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Completed",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "${state.completedSessions}",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            // Big center button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val isRunning = state.isRunning
                Button(
                    onClick = { if (isRunning) vm.pause() else vm.start() },
                    shape = CircleShape,
                    modifier = Modifier
                        .size(88.dp)
                        .shadow(8.dp, CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.22f)
                        else
                            MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(if (isRunning) "Pause" else "Start", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
