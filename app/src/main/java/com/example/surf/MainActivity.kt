package com.example.surf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surf.ui.theme.SurfTheme
import com.example.surf.ui.theme.avatarSize
import com.example.surf.ui.theme.background
import com.example.surf.ui.theme.buttonBorder
import com.example.surf.ui.theme.cardHeight
import com.example.surf.ui.theme.cornerRadius
import com.example.surf.ui.theme.divider
import com.example.surf.ui.theme.emojiSize
import com.example.surf.ui.theme.greetingNameSize
import com.example.surf.ui.theme.iconSize
import com.example.surf.ui.theme.iconTint
import com.example.surf.ui.theme.loadingSubtitleSize
import com.example.surf.ui.theme.loadingText
import com.example.surf.ui.theme.loadingTitleSize
import com.example.surf.ui.theme.paddingLarge
import com.example.surf.ui.theme.paddingMedium
import com.example.surf.ui.theme.paddingSmall
import com.example.surf.ui.theme.primary
import com.example.surf.ui.theme.tabTextSize
import com.example.surf.ui.theme.textPrimary
import com.example.surf.ui.theme.textSecondary
import com.example.surf.ui.theme.titleSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SurfTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val tabs = listOf("Проекты Surf", "Сотрудники", "О приложении")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val items = List(10) { "Карточка ${it + 1}" }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HeaderTitle(title = "Surf")
            Divider(color = divider, thickness = 1.dp)
            GreetingSection(userName = "Вячеслав", userIcon = R.drawable.ic_launcher_foreground)
            TabsSection(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = background)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(paddingMedium),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items.size) { index ->
                        SurfCard(title = items[index])
                    }
                }
                //LoadingScreen()
            }
        }
    }
}

@Composable
fun HeaderTitle(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = primary,
            fontWeight = FontWeight.Bold,
            fontSize = titleSize,
            modifier = modifier.padding(paddingMedium)
        )
    }
}

@Composable
fun GreetingSection(userName: String, userIcon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = paddingLarge, top = paddingLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Привет,", color = textSecondary, fontSize = 16.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = paddingSmall)
            ) {
                Text(
                    text = userName,
                    fontWeight = FontWeight.Bold,
                    color = textPrimary,
                    fontSize = greetingNameSize,
                    modifier = Modifier.offset(y = (-4).dp)
                )
                Spacer(modifier = Modifier.width(paddingSmall))
                Text(
                    text = "👋",
                    fontSize = emojiSize,
                    modifier = Modifier.offset(y = (-4).dp)
                )
            }
        }
        Icon(
            painter = painterResource(userIcon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .size(avatarSize)
                .padding(end = paddingMedium)
        )
    }
}

@Composable
fun TabsSection(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        edgePadding = paddingMedium,
        divider = {}
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedTabIndex
            Tab(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) primary else divider,
                        fontSize = tabTextSize
                    )
                }
            )
        }
    }
}

@Composable
fun SurfCard(title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(cardHeight),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = title)
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(iconSize)
        )

        Text(
            text = "Что-то пошло не так!",
            fontWeight = FontWeight.Bold,
            fontSize = loadingTitleSize,
            color = Color.Black,
            modifier = Modifier.padding(top = paddingLarge)
        )

        Text(
            text = "Пожалуйста, повторите попытку",
            fontWeight = FontWeight.Bold,
            color = loadingText,
            fontSize = loadingSubtitleSize,
            modifier = Modifier.padding(top = paddingSmall)
        )

        Spacer(modifier = Modifier.height(paddingLarge))

        OutlinedButton(
            onClick = { },
            shape = RoundedCornerShape(cornerRadius),
            border = BorderStroke(1.dp, buttonBorder),
            colors = outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = buttonBorder
            ),
            contentPadding = PaddingValues(horizontal = paddingLarge, vertical = paddingSmall)
        ) {
            Text(
                text = "Обновить",
                fontSize = 16.sp,
                color = buttonBorder
            )
        }
    }
}