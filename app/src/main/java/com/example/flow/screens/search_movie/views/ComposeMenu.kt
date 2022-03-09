package com.example.flow.screens.search_movie.views


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.flow.R
import com.example.flow.data.features.movie.models.MovieType

@Composable
fun DropdownMenuSearchType(items: Array<String>, onMenuItemClick: (MovieType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .border(
                0.5.dp,
                MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = { expanded = true })
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (label, iconView, dropDown) = createRefs()
            Text(
                text = items[selectedIndex],
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .constrainAs(label) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
                    .padding(16.dp, 0.dp)
            )
            val displayIcon: Painter = painterResource(id = R.drawable.ic_drop_down)
            Icon(
                painter = displayIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .constrainAs(dropDown) {
                        start.linkTo(label.end)
                        top.linkTo(label.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                        val result = when (items[index]) {
                            "Movie" -> MovieType.MOVIE
                            "Series" -> MovieType.SERIES
                            "Episode" -> MovieType.EPISODE
                            else -> MovieType.NOT_SELECTED
                        }
                        onMenuItemClick(result)
                    }) {
                        Text(text = s)
                    }
                }
            }
        }
    }
}

@Composable
fun ComposeMenu(
    menuItems: List<String>,
    menuExpandedState: Boolean,
    selectedIndex: Int,
    updateMenuExpandStatus: () -> Unit,
    onDismissMenuView: () -> Unit,
    onMenuItemClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 10.dp)
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),

        ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (label, iconView) = createRefs()
            Text(
                text = menuItems[selectedIndex],
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(label) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )

            val displayIcon: Painter = painterResource(id = R.drawable.ic_drop_down)
            Icon(
                painter = displayIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                expanded = menuExpandedState,
                onDismissRequest = { onDismissMenuView() },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ) {
                menuItems.forEachIndexed { index, title ->
                    DropdownMenuItem(
                        onClick = {
                            onMenuItemClick(index)
                        }) {
                        Text(text = title)
                    }
                }
            }
        }
    }
}

