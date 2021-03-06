package com.example.billing.activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.billing.fragments.*
import com.example.sport.ui.view.TopAppBar
import com.example.billing.utils.RememberState
import com.example.billing.utils.datas.Screening

const val EXTRA_FRAGMENT = "dataType"
const val STATE_BAR = "StatusBar"
const val RE_INIT = "reInit"

class TemplateActivity : BaseActivity() {

    val dataType: RememberState<String> = RememberState("null")

    var model:ViewModel? = null

    override fun onBackPressed() {
        when(dataType.getState().value) {
            "筛选" -> {
                if((model!! as ScreenFragmentModel).keyboardVisible.value) {
                    (model!! as ScreenFragmentModel).keyboardVisible set false
                }else {
                    super.onBackPressed()
                }
            }
            "添加明细" -> {
                if ((model!! as AddDetailFragmentModel).modifierState.getState().value) {
                    finish()
                    isRefreshing.value = true
                    isRefreshing.value = false
                } else {
                    (model!! as AddDetailFragmentModel).erroVisible set true
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    @Composable
    override fun StatusBar() {
        TopAppBar(
            onLeft = {
                IconButton(
                    onClick = {
                        Billing.saveData()
                        finish()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp, 32.dp),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        ) {
            Text(
                text = dataType.getState().value
            )
        }
    }

    override fun initBundle(bundle: Bundle) {
        super.initBundle(bundle)
        dataType set bundle.getString(EXTRA_FRAGMENT).toString()
        StatusBar set bundle.getBoolean(STATE_BAR, true)
        reInit set bundle.getBoolean(RE_INIT,false)
    }

    @Composable
    override fun Content() {
        when (dataType.getState().value) {
            "设置" -> {
                SettingFragment(templateActivity = this)
            }
            "底部栏样式" -> {
                SettingStyle()
            }
            "关于" -> {
                AboutFragment(templateActivity = this)
            }
            "筛选" -> {
                ScreenFragment(templateActivity = this)
                model = viewModel() as ScreenFragmentModel
            }
            "添加明细" -> {
                AddDetailFragment(templateActivity = this)
                model = viewModel() as AddDetailFragmentModel
            }
            "类别设置" -> {
                CreateDetailTypeFragment(templateActivity = this)
            }
            "渠道设置" -> {
                CreateMovDirectionFragment(templateActivity = this,false)
            }
            "对象设置" -> {
                CreateMovDirectionFragment(templateActivity = this,true)
            }
            else -> {
                ErrorFragment()
            }
        }
    }
}