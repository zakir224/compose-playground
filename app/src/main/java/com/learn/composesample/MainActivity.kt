package com.learn.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


class MainActivity : ComponentActivity() {

    private val model: VehicleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VehicleListScreen()
        }
    }

    @Composable
    fun VehicleListScreen() {
        val vehicleListScreenState = model.vehicle.observeAsState(emptyList()).value
        MaterialTheme {
            Column {
                ActionBar(name = "Vehicle")
                VehicleList(vehicleListScreenState)
            }
        }
    }

    @Composable
    fun VehicleList(vehicles: List<Vehicle>) {
        var removedCount by remember { mutableStateOf(0) }
        RemovedVehicleCount(count = removedCount)
        LazyColumn(Modifier.fillMaxWidth()) {
            items(vehicles) { vehicleState ->
                VehicleInfo(
                    vehicleState = vehicleState,
                    {
                        removedCount++
                        model.removeVehicle(vehicle = vehicleState)
                    },
                    { model.followVehicle(vehicle = vehicleState) }
                )
            }
        }
    }

    @Composable
    fun RemovedVehicleCount(count: Int) {
        if(count > 0) {
            Text(text = "Removed $count vehicles!",
                Modifier
                    .padding(8.dp)
                    .background(color = Color.White)
                    .fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }

    @Composable
    fun ActionBar(name: String) {
        TopAppBar(title = { Text(text = name) })
    }

    @Composable
    fun VehicleInfo(vehicleState: Vehicle, onRemoveClick: () -> Unit, onFollowClick: () -> Unit) {
        Card(elevation = 4.dp, modifier = Modifier.padding(8.dp)) {
            Column(Modifier.padding(8.dp)) {
                val typography = MaterialTheme.typography
                Image(
                    painter = painterResource(id = R.drawable.hondasample),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "${vehicleState.make} ${vehicleState.model} ${vehicleState.year}",
                    style = typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = "$${vehicleState.listPrice}", style = typography.h6)
                Text("Manhattan, NY", style = typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                ActionRow(vehicleState.followed, onRemoveClick, onFollowClick)
            }
        }
    }

    @Composable
    fun ActionRow(followedState: Boolean, onRemoveClick: () -> Unit, onFollowClick: () -> Unit) {
        Row {
            Button(onClick = onRemoveClick, Modifier.weight(1f)) { Text("Remove") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onFollowClick, Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = if (followedState) Color.Green else MaterialTheme.colors.primary)
            ) {
                if (followedState.not()) Text("Follow")
                else Text(text = "Unfollow")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewActionRow() {
        ActionRow(true, {}, {})
    }

    @Preview
    @Composable
    fun PreviewRemovedVehicleCount() {
        RemovedVehicleCount(2)
    }

    @Preview
    @Composable
    fun PreviewActionBar() {
        ActionBar("Action Bar")
    }

    @Preview
    @Composable
    fun PreviewVehicle() {
        VehicleInfo(Vehicle(make = "Honda", model = "Accord", year = "2001",listPrice = 45000f, id= 1), {}, {})
    }
}
