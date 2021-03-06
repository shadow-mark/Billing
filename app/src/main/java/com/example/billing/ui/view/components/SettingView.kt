package com.example.billing.ui.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billing.R
import com.example.sport.ui.view.MDialog
import com.example.billing.utils.RememberState
import com.example.billing.utils.datas.MovDirectionState
import com.example.billing.utils.isItemOfList

@Composable
fun <T> SettingItemView(
    title: String,
    modifier: Modifier = Modifier,
    list: List<T>,
    listItemToString: (T) -> String,
    checkedList: SnapshotStateList<T>,
    content: (@Composable () -> Unit)? = null
) {
    val showDialog = RememberState<Boolean>(false)
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    showDialog set true
                }
                .padding(15.dp)
        ) {
            Text(
                text = title,
                Modifier
                    .weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = "前往",
                modifier = Modifier.size(16.dp, 16.dp)
            )
        }
        MDialog(modifier = modifier, visible = showDialog) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .width(200.dp)
                    .clickable {
                        if (!isItemOfList(list, checkedList)) {
                            list.forEach {
                                checkedList.remove(it)
                                checkedList.add(it)
                            }
                        } else {
                            list.forEach {
                                checkedList.remove(it)
                            }
                        }
                    }
            ) {
                Text(
                    text = "全选",
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(3f)
                        .padding(10.dp)
                        .padding(start = 15.dp)
                )
                if (isItemOfList(list,checkedList)) {
                    Box(Modifier.weight(1f)) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = title,
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            list.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .width(200.dp)
                        .clickable {
                            if (checkedList.indexOf(it) != -1) {
                                checkedList.remove(it)
                            } else {
                                checkedList.add(it)
                            }
                        }
                ) {
                    Text(
                        text = listItemToString(it),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(3f)
                            .padding(10.dp)
                            .padding(start = 15.dp)
                    )
                    if (checkedList.indexOf(it) != -1) {
                        Box(Modifier.weight(1f)) {
                            Text(text = (checkedList.indexOf(it) + 1).toString())
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = title,
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        if (content != null) {
            Box(Modifier.padding(10.dp)) {
                content()
            }
        }
    }

}

@Composable
fun SettingItemView(
    title: String,
    selectlist: MutableList<String>,
    default: Int,
    onClickeditem: (index: Int) -> Unit
) {
    var nowItemindex by remember {
        mutableStateOf(default)
    }
    val showDialog = RememberState<Boolean>(false)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                showDialog set true
            }
            .padding(15.dp)
    ) {
        Text(
            text = title,
            Modifier
                .weight(1f)
        )
        Text(
            text = selectlist[nowItemindex], textAlign = TextAlign.End, modifier = Modifier
                .weight(0.5f)
        )
    }

    MDialog(visible = showDialog) {
        for (i in 0 until selectlist.size) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .width(200.dp)
                .clickable {
                    nowItemindex = i
                    onClickeditem(i)
                    showDialog set false
                }) {
                Text(
                    text = selectlist[i], textAlign = TextAlign.Left, modifier = Modifier
                        .weight(3f)
                        .padding(10.dp)
                        .padding(start = 15.dp)
                )
                if (nowItemindex == i) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = title,
                        modifier = Modifier.weight(
                            1f
                        )
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun SettingItemView(
    title: String,
    checketext: MutableList<String>,
    default: Boolean,
    onCheckedChange: (i: Boolean) -> Unit,
    content: (@Composable () -> Unit)? = null
) {
    var checkedState by remember { mutableStateOf(default) }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    checkedState = !checkedState
                    onCheckedChange(checkedState)
                }
                .padding(15.dp)
        ) {
            Text(
                text = title,
                Modifier
                    .weight(1f)
            )
            Text(text = checketext[0], textAlign = TextAlign.Right)
            Switch(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    onCheckedChange(it)
                }
            )
            Text(
                text = checketext[1],
                textAlign = TextAlign.Left,
//                modifier = Modifier.padding(end = 10.dp)
            )
        }
        if (content != null) {
            Box(Modifier.padding(10.dp)) {
                content()
            }
        }
    }
}

@Composable
fun SettingItemView(
    title: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(15.dp)
    ) {
        Text(
            text = title,
            Modifier
                .weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = "",
            modifier = Modifier.size(16.dp, 16.dp)
        )
    }
}

@Composable
fun DictText(
    key: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(10.dp)
    ) {
        Text(
            text = key,
            Modifier
                .weight(1f)
        )
        Text(
            text = value, textAlign = TextAlign.Right, modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), maxLines = 1
        )
    }
}