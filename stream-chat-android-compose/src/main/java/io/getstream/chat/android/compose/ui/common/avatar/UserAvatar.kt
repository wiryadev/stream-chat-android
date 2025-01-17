package io.getstream.chat.android.compose.ui.common.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.initials
import io.getstream.chat.android.compose.ui.theme.ChatTheme

/**
 * Represents the [User] avatar that's shown on the Messages screen or in headers of DMs.
 *
 * Based on the state within the [User], we either show an image or their initials.
 *
 * @param user The user whose avatars we want to show.
 * @param modifier Modifier for styling.
 * @param shape The shape of the avatar.
 * @param contentDescription The content description of the avatar.
 * @param showOnlineIndicator If we show online indicator or not.
 * @param onlineIndicatorAlignment The alignment of online indicator.
 * @param onlineIndicator Custom composable that allows to replace the default online indicator.
 * @param onClick The handler when the user clicks on the avatar.
 */
@Composable
public fun UserAvatar(
    user: User,
    modifier: Modifier = Modifier,
    shape: Shape = ChatTheme.shapes.avatar,
    contentDescription: String? = null,
    showOnlineIndicator: Boolean = true,
    onlineIndicatorAlignment: OnlineIndicatorAlignment = OnlineIndicatorAlignment.TopEnd,
    onlineIndicator: @Composable BoxScope.() -> Unit = {
        OnlineIndicator(modifier = Modifier.align(onlineIndicatorAlignment.alignment))
    },
    onClick: (() -> Unit)? = null,
) {
    val avatarContent: (@Composable (modifier: Modifier) -> Unit) = @Composable { modifier ->
        if (user.image.isNotBlank()) {
            val authorImage = rememberImagePainter(data = user.image)
            Avatar(
                modifier = modifier,
                shape = shape,
                painter = authorImage,
                contentDescription = contentDescription,
                onClick = onClick
            )
        } else {
            InitialsAvatar(
                modifier = modifier,
                initials = user.initials,
                shape = shape
            )
        }
    }

    if (showOnlineIndicator && user.online) {
        Box(modifier = modifier) {
            avatarContent(Modifier.fillMaxSize())

            onlineIndicator()
        }
    } else {
        avatarContent(modifier = modifier)
    }
}

/**
 * Component that represents an online indicator to be used with [UserAvatar].
 *
 * @param modifier Modifier for styling.
 */
@Composable
public fun OnlineIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(12.dp)
            .background(ChatTheme.colors.appBackground, CircleShape)
            .padding(2.dp)
            .background(ChatTheme.colors.infoAccent, CircleShape)
    )
}

/**
 * Represents the position of [OnlineIndicator] in [UserAvatar].
 */
public enum class OnlineIndicatorAlignment(public val alignment: Alignment) {
    TopEnd(Alignment.TopEnd),
    BottomEnd(Alignment.BottomEnd),
    TopStart(Alignment.TopStart),
    BottomStart(Alignment.BottomStart)
}
