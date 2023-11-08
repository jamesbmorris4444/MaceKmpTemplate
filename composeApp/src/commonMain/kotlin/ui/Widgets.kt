package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.model.RGB

@Composable
fun WidgetButton(padding: PaddingValues, buttonText: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(padding).testTag("WidgetButton"),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            fontSize = 16.sp,
            color = RGB("#ffffff").toComposeColor()
        )
    }
}