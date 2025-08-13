package com.example.flavi.view.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object MyIcons {
    val Settings: ImageVector
        get() {
            if (_Settings != null) return _Settings!!

            _Settings = ImageVector.Builder(
                name = "Settings",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(6f, 9.5f)
                    curveTo(6.93191f, 9.5f, 7.71496f, 10.1374f, 7.93699f, 11f)
                    horizontalLineTo(13.5f)
                    curveTo(13.7761f, 11f, 14f, 11.2239f, 14f, 11.5f)
                    curveTo(14f, 11.7455f, 13.8231f, 11.9496f, 13.5899f, 11.9919f)
                    lineTo(13.5f, 12f)
                    lineTo(7.93673f, 12.001f)
                    curveTo(7.71435f, 12.8631f, 6.93155f, 13.5f, 6f, 13.5f)
                    curveTo(5.06845f, 13.5f, 4.28565f, 12.8631f, 4.06327f, 12.001f)
                    lineTo(2.5f, 12f)
                    curveTo(2.22386f, 12f, 2f, 11.7761f, 2f, 11.5f)
                    curveTo(2f, 11.2545f, 2.17688f, 11.0504f, 2.41012f, 11.0081f)
                    lineTo(2.5f, 11f)
                    horizontalLineTo(4.06301f)
                    curveTo(4.28504f, 10.1374f, 5.06809f, 9.5f, 6f, 9.5f)
                    close()
                    moveTo(6f, 10.5f)
                    curveTo(5.44772f, 10.5f, 5f, 10.9477f, 5f, 11.5f)
                    curveTo(5f, 12.0523f, 5.44772f, 12.5f, 6f, 12.5f)
                    curveTo(6.55228f, 12.5f, 7f, 12.0523f, 7f, 11.5f)
                    curveTo(7f, 10.9477f, 6.55228f, 10.5f, 6f, 10.5f)
                    close()
                    moveTo(10f, 2.5f)
                    curveTo(10.9319f, 2.5f, 11.715f, 3.13738f, 11.937f, 3.99998f)
                    lineTo(13.5f, 4f)
                    curveTo(13.7761f, 4f, 14f, 4.22386f, 14f, 4.5f)
                    curveTo(14f, 4.74546f, 13.8231f, 4.94961f, 13.5899f, 4.99194f)
                    lineTo(13.5f, 5f)
                    lineTo(11.9367f, 5.00102f)
                    curveTo(11.7144f, 5.86312f, 10.9316f, 6.5f, 10f, 6.5f)
                    curveTo(9.06845f, 6.5f, 8.28565f, 5.86312f, 8.06327f, 5.00102f)
                    lineTo(2.5f, 5f)
                    curveTo(2.22386f, 5f, 2f, 4.77614f, 2f, 4.5f)
                    curveTo(2f, 4.25454f, 2.17688f, 4.05039f, 2.41012f, 4.00806f)
                    lineTo(2.5f, 4f)
                    lineTo(8.06301f, 3.99998f)
                    curveTo(8.28504f, 3.13738f, 9.06809f, 2.5f, 10f, 2.5f)
                    close()
                    moveTo(10f, 3.5f)
                    curveTo(9.44772f, 3.5f, 9f, 3.94772f, 9f, 4.5f)
                    curveTo(9f, 5.05228f, 9.44772f, 5.5f, 10f, 5.5f)
                    curveTo(10.5523f, 5.5f, 11f, 5.05228f, 11f, 4.5f)
                    curveTo(11f, 3.94772f, 10.5523f, 3.5f, 10f, 3.5f)
                    close()
                }
            }.build()

            return _Settings!!
        }

    private var _Settings: ImageVector? = null

    val Star: ImageVector
        get() {
            if (_Star != null) return _Star!!

            _Star = ImageVector.Builder(
                name = "Star",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(2.866f, 14.85f)
                    curveToRelative(-0.078f, 0.444f, 0.36f, 0.791f, 0.746f, 0.593f)
                    lineToRelative(4.39f, -2.256f)
                    lineToRelative(4.389f, 2.256f)
                    curveToRelative(0.386f, 0.198f, 0.824f, -0.149f, 0.746f, -0.592f)
                    lineToRelative(-0.83f, -4.73f)
                    lineToRelative(3.522f, -3.356f)
                    curveToRelative(0.33f, -0.314f, 0.16f, -0.888f, -0.282f, -0.95f)
                    lineToRelative(-4.898f, -0.696f)
                    lineTo(8.465f, 0.792f)
                    arcToRelative(0.513f, 0.513f, 0f, false, false, -0.927f, 0f)
                    lineTo(5.354f, 5.12f)
                    lineToRelative(-4.898f, 0.696f)
                    curveToRelative(-0.441f, 0.062f, -0.612f, 0.636f, -0.283f, 0.95f)
                    lineToRelative(3.523f, 3.356f)
                    lineToRelative(-0.83f, 4.73f)
                    close()
                    moveToRelative(4.905f, -2.767f)
                    lineToRelative(-3.686f, 1.894f)
                    lineToRelative(0.694f, -3.957f)
                    arcToRelative(0.56f, 0.56f, 0f, false, false, -0.163f, -0.505f)
                    lineTo(1.71f, 6.745f)
                    lineToRelative(4.052f, -0.576f)
                    arcToRelative(0.53f, 0.53f, 0f, false, false, 0.393f, -0.288f)
                    lineTo(8f, 2.223f)
                    lineToRelative(1.847f, 3.658f)
                    arcToRelative(0.53f, 0.53f, 0f, false, false, 0.393f, 0.288f)
                    lineToRelative(4.052f, 0.575f)
                    lineToRelative(-2.906f, 2.77f)
                    arcToRelative(0.56f, 0.56f, 0f, false, false, -0.163f, 0.506f)
                    lineToRelative(0.694f, 3.957f)
                    lineToRelative(-3.686f, -1.894f)
                    arcToRelative(0.5f, 0.5f, 0f, false, false, -0.461f, 0f)
                    close()
                }
            }.build()

            return _Star!!
        }

    private var _Star: ImageVector? = null

    val Favorite_Movies: ImageVector
        get() {
            if (_Box_add != null) return _Box_add!!

            _Box_add = ImageVector.Builder(
                name = "Box_add",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(640f, 320f)
                    horizontalLineToRelative(120f)
                    close()
                    moveToRelative(-440f, 0f)
                    horizontalLineToRelative(338f)
                    horizontalLineToRelative(-18f)
                    horizontalLineToRelative(14f)
                    close()
                    moveToRelative(16f, -80f)
                    horizontalLineToRelative(528f)
                    lineToRelative(-34f, -40f)
                    horizontalLineTo(250f)
                    close()
                    moveToRelative(184f, 270f)
                    lineToRelative(80f, -40f)
                    lineToRelative(80f, 40f)
                    verticalLineToRelative(-190f)
                    horizontalLineTo(400f)
                    close()
                    moveToRelative(182f, 330f)
                    horizontalLineTo(200f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(120f, 760f)
                    verticalLineToRelative(-499f)
                    quadToRelative(0f, -14f, 4.5f, -27f)
                    reflectiveQuadToRelative(13.5f, -24f)
                    lineToRelative(50f, -61f)
                    quadToRelative(11f, -14f, 27.5f, -21.5f)
                    reflectiveQuadTo(250f, 120f)
                    horizontalLineToRelative(460f)
                    quadToRelative(18f, 0f, 34.5f, 7.5f)
                    reflectiveQuadTo(772f, 149f)
                    lineToRelative(50f, 61f)
                    quadToRelative(9f, 11f, 13.5f, 24f)
                    reflectiveQuadToRelative(4.5f, 27f)
                    verticalLineToRelative(196f)
                    quadToRelative(-19f, -7f, -39f, -11f)
                    reflectiveQuadToRelative(-41f, -4f)
                    verticalLineToRelative(-122f)
                    horizontalLineTo(640f)
                    verticalLineToRelative(153f)
                    quadToRelative(-35f, 20f, -61f, 49.5f)
                    reflectiveQuadTo(538f, 589f)
                    lineToRelative(-58f, -29f)
                    lineToRelative(-160f, 80f)
                    verticalLineToRelative(-320f)
                    horizontalLineTo(200f)
                    verticalLineToRelative(440f)
                    horizontalLineToRelative(334f)
                    quadToRelative(8f, 23f, 20f, 43f)
                    reflectiveQuadToRelative(28f, 37f)
                    moveToRelative(138f, 0f)
                    verticalLineToRelative(-120f)
                    horizontalLineTo(600f)
                    verticalLineToRelative(-80f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(-120f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(120f)
                    horizontalLineToRelative(120f)
                    verticalLineToRelative(80f)
                    horizontalLineTo(800f)
                    verticalLineToRelative(120f)
                    close()
                }
            }.build()

            return _Box_add!!
        }

    private var _Box_add: ImageVector? = null



}