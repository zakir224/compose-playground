package com.learn.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {

    private val model: VehicleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VehicleDetailsPage()
        }
    }

    @Composable
    fun VehicleDetailsPage() {

        val observeAsState = model.vehicle.observeAsState(emptyList())
        MaterialTheme {
            Column {
                ActionBar(name = "Vehicle")
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(observeAsState.value) { vehicle ->
                        VehicleInfo(
                            vehicle = vehicle,
                            { model.removeVehicle(vehicle = vehicle) },
                            { model.followVehicle(vehicle = vehicle) }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ActionBar(name: String) {
        TopAppBar(title = { Text(text = name) })
    }

    @Composable
    fun VehicleInfo(vehicle: Vehicle, onRemoveClick: () -> Unit, onFollowClick: () -> Unit) {
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
                    "${vehicle.make} ${vehicle.model} ${vehicle.year}",
                    style = typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = vehicle.listPrice.toString(), style = typography.h6)
                Text("Manhattan, NY", style = typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                ActionRow(vehicle.followed, onRemoveClick, onFollowClick)
            }
        }
    }

    @Composable
    fun ActionRow(followed: Boolean, onRemoveClick: () -> Unit, onFollowClick: () -> Unit) {
        Row {
            Button(onClick = onRemoveClick, Modifier.weight(1f)) { Text("Remove") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onFollowClick, Modifier.weight(1f)) {
                if(followed.not()) Text("Follow")
                else Text(text = "Unfollow")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewGreeting() {
        VehicleDetailsPage()
    }
}
