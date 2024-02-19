package com.example.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.data.Datasource
import com.example.affirmations.model.Affirmation
import com.example.affirmations.ui.theme.AffirmationsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF6CAE75)
                ) {
                    AffirmationsApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun AffirmationsApp() {
    var indexer by remember { mutableStateOf(-1) }
    var limit by remember { mutableStateOf(10) }
    var switchState by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0.0f) }
    var left by remember { mutableStateOf(54.0) }
    var covered by remember { mutableStateOf(0.0) }

    // list of all stations
    val listy = mutableListOf<Affirmation>()
    for (i in Datasource().loadAffirmations()){
        listy.add(i)
    }

    if (limit == 7) left = 37.0

    // list of 7 that fit
    val norm_list = mutableListOf<Affirmation>()
    val affirmations = Datasource().loadAffirmations()

    for (i in affirmations.take(7)) {
        norm_list.add(i)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF15471B),
                contentColor = Color(0xFFC1CC99),
                modifier = Modifier.height(150.dp)
            ) {
                Column {
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFC1CC99)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        //horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Distance Covered: \n" + if (switchState) {
                                convert(covered).toBigDecimal().toPlainString() + " mi"
                            } else {
                                covered.toBigDecimal().toPlainString() + " Km"
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        Text(
                            text = "Distance Left: \n" + if (switchState) {
                                convert(left).toBigDecimal().toPlainString() + " mi"
                            } else {
                                left.toBigDecimal().toPlainString() + " Km"
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Switcher(roundUp = switchState, onRoundUpChanged = {switchState = it})
                        Button(onClick = {
                            if (indexer < (limit-1)){
                                indexer++
                                covered += listy[indexer].distant
                                left -= listy[indexer].distant
                                progress += if (limit == 10) 0.1f
                                else 0.142857f
                            }
                            },colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC1CC99),
                            contentColor = Color(0xFF51583F) )
                            ) {
                                Text("Next Stop")
                            }

                        Button(onClick = {
                            indexer = -1
                            left = 54.0
                            covered = 0.0
                            progress = 0.0f

                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFC1CC99),
                                contentColor = Color(0xFF51583F) )
                            )
                                {
                                Text("Reset")
                            }
                        }
                    }
                }
            },
        )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            // for normal list
//            limit = 7
//            AffirmationList(affirmationList = norm_list, dist = switchState, indexx = indexer,)

            // for lazy list
            LazyList(affirmationList = listy, dist = switchState, indexx = indexer )
        }

    }
}

@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    modifier: Modifier = Modifier,
    dist: Boolean,
    indexx: Int,
    ) {
    val cardColor = if (indexx >= affirmation.state) {
        Color(0xFFC7dfbb)
    } else {
        Color(0xFFE1CA96)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = Color(0xFF51583F)
        )
    ) {
        Column {
            Text(
                text = affirmation.station,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(start = 12.dp)
                    .padding(end = 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = if (dist) {
                    "Distance till stop: " + convert(affirmation.distant).toBigDecimal().toPlainString() + " mi"
                } else {
                    "Distance till stop: " + affirmation.distant.toBigDecimal().toPlainString() + " Km"
                },
                modifier = Modifier.padding(bottom = 12.dp)
                    .padding(start = 12.dp)
                    .padding(end = 12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// for lazy list
@Composable
fun LazyList(affirmationList: List<Affirmation>,
                    modifier: Modifier = Modifier,
                    dist: Boolean,
                    indexx: Int,
                    )
{
    LazyColumn(modifier = modifier) {
        itemsIndexed(affirmationList) { index, affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                dist = dist,
                indexx = indexx
            )
        }
    }
}

// for normal list without lazy component
@Composable
fun AffirmationList(
    affirmationList: List<Affirmation>,
    modifier: Modifier = Modifier,
    dist: Boolean,
    indexx: Int,
) {
    Column(modifier = modifier) {
        affirmationList.forEachIndexed { index, affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                dist = dist,
                indexx = indexx
            )
        }
    }
}

@Composable
fun Switcher(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier){
    Row {
        //Text(text = "Change \nto miles")
        Switch(
            modifier = modifier
                .padding(8.dp)
                .padding(end = 8.dp),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFFC1CC99),
                checkedTrackColor = Color(0xFF357E3F),
                uncheckedThumbColor = Color(0xFF357E3F),
                uncheckedTrackColor = Color(0xFFC1CC99),
            )
        )
    }
}

fun convert(num: Double): String {
    val ans = num * 0.62
    return String.format("%.2f", ans)
}
