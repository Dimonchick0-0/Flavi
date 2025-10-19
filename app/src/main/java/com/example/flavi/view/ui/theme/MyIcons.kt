package com.example.flavi.view.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object MyIcons {

    val KeyboardArrowLeft: ImageVector
        get() {
            if (_Keyboard_arrow_left != null) return _Keyboard_arrow_left!!

            _Keyboard_arrow_left = ImageVector.Builder(
                name = "Keyboard_arrow_left",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(560f, 720f)
                    lineTo(320f, 480f)
                    lineToRelative(240f, -240f)
                    lineToRelative(56f, 56f)
                    lineToRelative(-184f, 184f)
                    lineToRelative(184f, 184f)
                    close()
                }
            }.build()

            return _Keyboard_arrow_left!!
        }

    private var _Keyboard_arrow_left: ImageVector? = null



    val NeutralEmoji: ImageVector
        get() {
            if (_EmojiNeutral != null) return _EmojiNeutral!!

            _EmojiNeutral = ImageVector.Builder(
                name = "EmojiNeutral",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(8f, 15f)
                    arcTo(7f, 7f, 0f, true, true, 8f, 1f)
                    arcToRelative(7f, 7f, 0f, false, true, 0f, 14f)
                    moveToRelative(0f, 1f)
                    arcTo(8f, 8f, 0f, true, false, 8f, 0f)
                    arcToRelative(8f, 8f, 0f, false, false, 0f, 16f)
                }
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(4f, 10.5f)
                    arcToRelative(0.5f, 0.5f, 0f, false, false, 0.5f, 0.5f)
                    horizontalLineToRelative(7f)
                    arcToRelative(0.5f, 0.5f, 0f, false, false, 0f, -1f)
                    horizontalLineToRelative(-7f)
                    arcToRelative(0.5f, 0.5f, 0f, false, false, -0.5f, 0.5f)
                    moveToRelative(3f, -4f)
                    curveTo(7f, 5.672f, 6.552f, 5f, 6f, 5f)
                    reflectiveCurveToRelative(-1f, 0.672f, -1f, 1.5f)
                    reflectiveCurveTo(5.448f, 8f, 6f, 8f)
                    reflectiveCurveToRelative(1f, -0.672f, 1f, -1.5f)
                    moveToRelative(4f, 0f)
                    curveToRelative(0f, -0.828f, -0.448f, -1.5f, -1f, -1.5f)
                    reflectiveCurveToRelative(-1f, 0.672f, -1f, 1.5f)
                    reflectiveCurveTo(9.448f, 8f, 10f, 8f)
                    reflectiveCurveToRelative(1f, -0.672f, 1f, -1.5f)
                }
            }.build()

            return _EmojiNeutral!!
        }

    private var _EmojiNeutral: ImageVector? = null



    val NegativeEmoji: ImageVector
        get() {
            if (_EmojiFrown != null) return _EmojiFrown!!

            _EmojiFrown = ImageVector.Builder(
                name = "EmojiFrown",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(8f, 15f)
                    arcTo(7f, 7f, 0f, true, true, 8f, 1f)
                    arcToRelative(7f, 7f, 0f, false, true, 0f, 14f)
                    moveToRelative(0f, 1f)
                    arcTo(8f, 8f, 0f, true, false, 8f, 0f)
                    arcToRelative(8f, 8f, 0f, false, false, 0f, 16f)
                }
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(4.285f, 12.433f)
                    arcToRelative(0.5f, 0.5f, 0f, false, false, 0.683f, -0.183f)
                    arcTo(3.5f, 3.5f, 0f, false, true, 8f, 10.5f)
                    curveToRelative(1.295f, 0f, 2.426f, 0.703f, 3.032f, 1.75f)
                    arcToRelative(0.5f, 0.5f, 0f, false, false, 0.866f, -0.5f)
                    arcTo(4.5f, 4.5f, 0f, false, false, 8f, 9.5f)
                    arcToRelative(4.5f, 4.5f, 0f, false, false, -3.898f, 2.25f)
                    arcToRelative(0.5f, 0.5f, 0f, false, false, 0.183f, 0.683f)
                    moveTo(7f, 6.5f)
                    curveTo(7f, 7.328f, 6.552f, 8f, 6f, 8f)
                    reflectiveCurveToRelative(-1f, -0.672f, -1f, -1.5f)
                    reflectiveCurveTo(5.448f, 5f, 6f, 5f)
                    reflectiveCurveToRelative(1f, 0.672f, 1f, 1.5f)
                    moveToRelative(4f, 0f)
                    curveToRelative(0f, 0.828f, -0.448f, 1.5f, -1f, 1.5f)
                    reflectiveCurveToRelative(-1f, -0.672f, -1f, -1.5f)
                    reflectiveCurveTo(9.448f, 5f, 10f, 5f)
                    reflectiveCurveToRelative(1f, 0.672f, 1f, 1.5f)
                }
            }.build()

            return _EmojiFrown!!
        }

    private var _EmojiFrown: ImageVector? = null



    val PositiveEmoji: ImageVector
        get() {
            if (_EmojiHeartEyes != null) return _EmojiHeartEyes!!

            _EmojiHeartEyes = ImageVector.Builder(
                name = "EmojiHeartEyes",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f
            ).apply {
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(8f, 15f)
                    arcTo(7f, 7f, 0f, true, true, 8f, 1f)
                    arcToRelative(7f, 7f, 0f, false, true, 0f, 14f)
                    moveToRelative(0f, 1f)
                    arcTo(8f, 8f, 0f, true, false, 8f, 0f)
                    arcToRelative(8f, 8f, 0f, false, false, 0f, 16f)
                }
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(11.315f, 10.014f)
                    arcToRelative(0.5f, 0.5f, 0f, false, true, 0.548f, 0.736f)
                    arcTo(4.5f, 4.5f, 0f, false, true, 7.965f, 13f)
                    arcToRelative(4.5f, 4.5f, 0f, false, true, -3.898f, -2.25f)
                    arcToRelative(0.5f, 0.5f, 0f, false, true, 0.548f, -0.736f)
                    horizontalLineToRelative(0.005f)
                    lineToRelative(0.017f, 0.005f)
                    lineToRelative(0.067f, 0.015f)
                    lineToRelative(0.252f, 0.055f)
                    curveToRelative(0.215f, 0.046f, 0.515f, 0.108f, 0.857f, 0.169f)
                    curveToRelative(0.693f, 0.124f, 1.522f, 0.242f, 2.152f, 0.242f)
                    reflectiveCurveToRelative(1.46f, -0.118f, 2.152f, -0.242f)
                    arcToRelative(27f, 27f, 0f, false, false, 1.109f, -0.224f)
                    lineToRelative(0.067f, -0.015f)
                    lineToRelative(0.017f, -0.004f)
                    lineToRelative(0.005f, -0.002f)
                    close()
                    moveTo(4.756f, 4.566f)
                    curveToRelative(0.763f, -1.424f, 4.02f, -0.12f, 0.952f, 3.434f)
                    curveToRelative(-4.496f, -1.596f, -2.35f, -4.298f, -0.952f, -3.434f)
                    moveToRelative(6.488f, 0f)
                    curveToRelative(1.398f, -0.864f, 3.544f, 1.838f, -0.952f, 3.434f)
                    curveToRelative(-3.067f, -3.554f, 0.19f, -4.858f, 0.952f, -3.434f)
                }
            }.build()

            return _EmojiHeartEyes!!
        }

    private var _EmojiHeartEyes: ImageVector? = null



    val Heart_minus: ImageVector
        get() {
            if (_Heart_minus != null) return _Heart_minus!!

            _Heart_minus = ImageVector.Builder(
                name = "Heart_minus",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(440f, 840f)
                    lineTo(313f, 726f)
                    quadToRelative(-72f, -65f, -123.5f, -116f)
                    reflectiveQuadToRelative(-85f, -96f)
                    reflectiveQuadToRelative(-49f, -87f)
                    reflectiveQuadTo(40f, 339f)
                    quadToRelative(0f, -94f, 63f, -156.5f)
                    reflectiveQuadTo(260f, 120f)
                    quadToRelative(52f, 0f, 99f, 22f)
                    reflectiveQuadToRelative(81f, 62f)
                    quadToRelative(34f, -40f, 81f, -62f)
                    reflectiveQuadToRelative(99f, -22f)
                    quadToRelative(84f, 0f, 153f, 59f)
                    reflectiveQuadToRelative(69f, 160f)
                    quadToRelative(0f, 14f, -2f, 29.5f)
                    reflectiveQuadToRelative(-6f, 31.5f)
                    horizontalLineToRelative(-85f)
                    quadToRelative(5f, -18f, 8f, -34f)
                    reflectiveQuadToRelative(3f, -30f)
                    quadToRelative(0f, -75f, -50f, -105.5f)
                    reflectiveQuadTo(620f, 200f)
                    quadToRelative(-51f, 0f, -88f, 27.5f)
                    reflectiveQuadTo(463f, 300f)
                    horizontalLineToRelative(-46f)
                    quadToRelative(-31f, -45f, -70.5f, -72.5f)
                    reflectiveQuadTo(260f, 200f)
                    quadToRelative(-57f, 0f, -98.5f, 39.5f)
                    reflectiveQuadTo(120f, 339f)
                    quadToRelative(0f, 33f, 14f, 67f)
                    reflectiveQuadToRelative(50f, 78.5f)
                    reflectiveQuadToRelative(98f, 104f)
                    reflectiveQuadTo(440f, 732f)
                    quadToRelative(26f, -23f, 61f, -53f)
                    reflectiveQuadToRelative(56f, -50f)
                    lineToRelative(9f, 9f)
                    lineToRelative(19.5f, 19.5f)
                    lineTo(605f, 677f)
                    lineToRelative(9f, 9f)
                    quadToRelative(-22f, 20f, -56f, 49.5f)
                    reflectiveQuadTo(498f, 788f)
                    close()
                    moveToRelative(160f, -280f)
                    verticalLineToRelative(-80f)
                    horizontalLineToRelative(320f)
                    verticalLineToRelative(80f)
                    close()
                }
            }.build()

            return _Heart_minus!!
        }

    private var _Heart_minus: ImageVector? = null



    val Heart_plus: ImageVector
        get() {
            if (_Heart_plus != null) return _Heart_plus!!

            _Heart_plus = ImageVector.Builder(
                name = "Heart_plus",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(440f, 840f)
                    lineTo(313f, 726f)
                    quadToRelative(-72f, -65f, -123.5f, -116f)
                    reflectiveQuadToRelative(-85f, -96f)
                    reflectiveQuadToRelative(-49f, -87f)
                    reflectiveQuadTo(40f, 339f)
                    quadToRelative(0f, -94f, 63f, -156.5f)
                    reflectiveQuadTo(260f, 120f)
                    quadToRelative(52f, 0f, 99f, 22f)
                    reflectiveQuadToRelative(81f, 62f)
                    quadToRelative(34f, -40f, 81f, -62f)
                    reflectiveQuadToRelative(99f, -22f)
                    quadToRelative(81f, 0f, 136f, 45.5f)
                    reflectiveQuadTo(831f, 280f)
                    horizontalLineToRelative(-85f)
                    quadToRelative(-18f, -40f, -53f, -60f)
                    reflectiveQuadToRelative(-73f, -20f)
                    quadToRelative(-51f, 0f, -88f, 27.5f)
                    reflectiveQuadTo(463f, 300f)
                    horizontalLineToRelative(-46f)
                    quadToRelative(-31f, -45f, -70.5f, -72.5f)
                    reflectiveQuadTo(260f, 200f)
                    quadToRelative(-57f, 0f, -98.5f, 39.5f)
                    reflectiveQuadTo(120f, 339f)
                    quadToRelative(0f, 33f, 14f, 67f)
                    reflectiveQuadToRelative(50f, 78.5f)
                    reflectiveQuadToRelative(98f, 104f)
                    reflectiveQuadTo(440f, 732f)
                    quadToRelative(26f, -23f, 61f, -53f)
                    reflectiveQuadToRelative(56f, -50f)
                    lineToRelative(9f, 9f)
                    lineToRelative(19.5f, 19.5f)
                    lineTo(605f, 677f)
                    lineToRelative(9f, 9f)
                    quadToRelative(-22f, 20f, -56f, 49.5f)
                    reflectiveQuadTo(498f, 788f)
                    close()
                    moveToRelative(280f, -160f)
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

            return _Heart_plus!!
        }

    private var _Heart_plus: ImageVector? = null



    val EllipsisVertical: ImageVector
        get() {
            if (_EllipsisVertical != null) return _EllipsisVertical!!

            _EllipsisVertical = ImageVector.Builder(
                name = "EllipsisVertical",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round
                ) {
                    moveTo(13f, 12f)
                    arcTo(1f, 1f, 0f, false, true, 12f, 13f)
                    arcTo(1f, 1f, 0f, false, true, 11f, 12f)
                    arcTo(1f, 1f, 0f, false, true, 13f, 12f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round
                ) {
                    moveTo(13f, 5f)
                    arcTo(1f, 1f, 0f, false, true, 12f, 6f)
                    arcTo(1f, 1f, 0f, false, true, 11f, 5f)
                    arcTo(1f, 1f, 0f, false, true, 13f, 5f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round
                ) {
                    moveTo(13f, 19f)
                    arcTo(1f, 1f, 0f, false, true, 12f, 20f)
                    arcTo(1f, 1f, 0f, false, true, 11f, 19f)
                    arcTo(1f, 1f, 0f, false, true, 13f, 19f)
                    close()
                }
            }.build()

            return _EllipsisVertical!!
        }

    private var _EllipsisVertical: ImageVector? = null

    val Cleaning_services: ImageVector
        get() {
            if (_Cleaning_services != null) return _Cleaning_services!!

            _Cleaning_services = ImageVector.Builder(
                name = "Cleaning_services",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(120f, 920f)
                    verticalLineToRelative(-280f)
                    quadToRelative(0f, -83f, 58.5f, -141.5f)
                    reflectiveQuadTo(320f, 440f)
                    horizontalLineToRelative(40f)
                    verticalLineToRelative(-320f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(440f, 40f)
                    horizontalLineToRelative(80f)
                    quadToRelative(33f, 0f, 56.5f, 23.5f)
                    reflectiveQuadTo(600f, 120f)
                    verticalLineToRelative(320f)
                    horizontalLineToRelative(40f)
                    quadToRelative(83f, 0f, 141.5f, 58.5f)
                    reflectiveQuadTo(840f, 640f)
                    verticalLineToRelative(280f)
                    close()
                    moveToRelative(80f, -80f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(-120f)
                    quadToRelative(0f, -17f, 11.5f, -28.5f)
                    reflectiveQuadTo(320f, 680f)
                    reflectiveQuadToRelative(28.5f, 11.5f)
                    reflectiveQuadTo(360f, 720f)
                    verticalLineToRelative(120f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(-120f)
                    quadToRelative(0f, -17f, 11.5f, -28.5f)
                    reflectiveQuadTo(480f, 680f)
                    reflectiveQuadToRelative(28.5f, 11.5f)
                    reflectiveQuadTo(520f, 720f)
                    verticalLineToRelative(120f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(-120f)
                    quadToRelative(0f, -17f, 11.5f, -28.5f)
                    reflectiveQuadTo(640f, 680f)
                    reflectiveQuadToRelative(28.5f, 11.5f)
                    reflectiveQuadTo(680f, 720f)
                    verticalLineToRelative(120f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(-200f)
                    quadToRelative(0f, -50f, -35f, -85f)
                    reflectiveQuadToRelative(-85f, -35f)
                    horizontalLineTo(320f)
                    quadToRelative(-50f, 0f, -85f, 35f)
                    reflectiveQuadToRelative(-35f, 85f)
                    close()
                    moveToRelative(320f, -400f)
                    verticalLineToRelative(-320f)
                    horizontalLineToRelative(-80f)
                    verticalLineToRelative(320f)
                    close()
                    moveToRelative(0f, 0f)
                    horizontalLineToRelative(-80f)
                    close()
                }
            }.build()

            return _Cleaning_services!!
        }

    private var _Cleaning_services: ImageVector? = null

    val Refresh: ImageVector
        get() {
            if (_Refresh != null) return _Refresh!!

            _Refresh = ImageVector.Builder(
                name = "Refresh",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(480f, 800f)
                    quadToRelative(-134f, 0f, -227f, -93f)
                    reflectiveQuadToRelative(-93f, -227f)
                    reflectiveQuadToRelative(93f, -227f)
                    reflectiveQuadToRelative(227f, -93f)
                    quadToRelative(69f, 0f, 132f, 28.5f)
                    reflectiveQuadTo(720f, 270f)
                    verticalLineToRelative(-110f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(280f)
                    horizontalLineTo(520f)
                    verticalLineToRelative(-80f)
                    horizontalLineToRelative(168f)
                    quadToRelative(-32f, -56f, -87.5f, -88f)
                    reflectiveQuadTo(480f, 240f)
                    quadToRelative(-100f, 0f, -170f, 70f)
                    reflectiveQuadToRelative(-70f, 170f)
                    reflectiveQuadToRelative(70f, 170f)
                    reflectiveQuadToRelative(170f, 70f)
                    quadToRelative(77f, 0f, 139f, -44f)
                    reflectiveQuadToRelative(87f, -116f)
                    horizontalLineToRelative(84f)
                    quadToRelative(-28f, 106f, -114f, 173f)
                    reflectiveQuadToRelative(-196f, 67f)
                }
            }.build()

            return _Refresh!!
        }

    private var _Refresh: ImageVector? = null

    val AccountCircle: ImageVector
        get() {
            if (_Account_circle != null) return _Account_circle!!

            _Account_circle = ImageVector.Builder(
                name = "Account_circle",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(234f, 684f)
                    quadToRelative(51f, -39f, 114f, -61.5f)
                    reflectiveQuadTo(480f, 600f)
                    reflectiveQuadToRelative(132f, 22.5f)
                    reflectiveQuadTo(726f, 684f)
                    quadToRelative(35f, -41f, 54.5f, -93f)
                    reflectiveQuadTo(800f, 480f)
                    quadToRelative(0f, -133f, -93.5f, -226.5f)
                    reflectiveQuadTo(480f, 160f)
                    reflectiveQuadToRelative(-226.5f, 93.5f)
                    reflectiveQuadTo(160f, 480f)
                    quadToRelative(0f, 59f, 19.5f, 111f)
                    reflectiveQuadToRelative(54.5f, 93f)
                    moveToRelative(246f, -164f)
                    quadToRelative(-59f, 0f, -99.5f, -40.5f)
                    reflectiveQuadTo(340f, 380f)
                    reflectiveQuadToRelative(40.5f, -99.5f)
                    reflectiveQuadTo(480f, 240f)
                    reflectiveQuadToRelative(99.5f, 40.5f)
                    reflectiveQuadTo(620f, 380f)
                    reflectiveQuadToRelative(-40.5f, 99.5f)
                    reflectiveQuadTo(480f, 520f)
                    moveToRelative(0f, 360f)
                    quadToRelative(-83f, 0f, -156f, -31.5f)
                    reflectiveQuadTo(197f, 763f)
                    reflectiveQuadToRelative(-85.5f, -127f)
                    reflectiveQuadTo(80f, 480f)
                    reflectiveQuadToRelative(31.5f, -156f)
                    reflectiveQuadTo(197f, 197f)
                    reflectiveQuadToRelative(127f, -85.5f)
                    reflectiveQuadTo(480f, 80f)
                    reflectiveQuadToRelative(156f, 31.5f)
                    reflectiveQuadTo(763f, 197f)
                    reflectiveQuadToRelative(85.5f, 127f)
                    reflectiveQuadTo(880f, 480f)
                    reflectiveQuadToRelative(-31.5f, 156f)
                    reflectiveQuadTo(763f, 763f)
                    reflectiveQuadToRelative(-127f, 85.5f)
                    reflectiveQuadTo(480f, 880f)
                    moveToRelative(0f, -80f)
                    quadToRelative(53f, 0f, 100f, -15.5f)
                    reflectiveQuadToRelative(86f, -44.5f)
                    quadToRelative(-39f, -29f, -86f, -44.5f)
                    reflectiveQuadTo(480f, 680f)
                    reflectiveQuadToRelative(-100f, 15.5f)
                    reflectiveQuadToRelative(-86f, 44.5f)
                    quadToRelative(39f, 29f, 86f, 44.5f)
                    reflectiveQuadTo(480f, 800f)
                    moveToRelative(0f, -360f)
                    quadToRelative(26f, 0f, 43f, -17f)
                    reflectiveQuadToRelative(17f, -43f)
                    reflectiveQuadToRelative(-17f, -43f)
                    reflectiveQuadToRelative(-43f, -17f)
                    reflectiveQuadToRelative(-43f, 17f)
                    reflectiveQuadToRelative(-17f, 43f)
                    reflectiveQuadToRelative(17f, 43f)
                    reflectiveQuadToRelative(43f, 17f)
                    moveToRelative(0f, 300f)
                }
            }.build()

            return _Account_circle!!
        }

    private var _Account_circle: ImageVector? = null

    val Search: ImageVector
        get() {
            if (_Search != null) return _Search!!

            _Search = ImageVector.Builder(
                name = "Search",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(784f, 840f)
                    lineTo(532f, 588f)
                    quadToRelative(-30f, 24f, -69f, 38f)
                    reflectiveQuadToRelative(-83f, 14f)
                    quadToRelative(-109f, 0f, -184.5f, -75.5f)
                    reflectiveQuadTo(120f, 380f)
                    reflectiveQuadToRelative(75.5f, -184.5f)
                    reflectiveQuadTo(380f, 120f)
                    reflectiveQuadToRelative(184.5f, 75.5f)
                    reflectiveQuadTo(640f, 380f)
                    quadToRelative(0f, 44f, -14f, 83f)
                    reflectiveQuadToRelative(-38f, 69f)
                    lineToRelative(252f, 252f)
                    close()
                    moveTo(380f, 560f)
                    quadToRelative(75f, 0f, 127.5f, -52.5f)
                    reflectiveQuadTo(560f, 380f)
                    reflectiveQuadToRelative(-52.5f, -127.5f)
                    reflectiveQuadTo(380f, 200f)
                    reflectiveQuadToRelative(-127.5f, 52.5f)
                    reflectiveQuadTo(200f, 380f)
                    reflectiveQuadToRelative(52.5f, 127.5f)
                    reflectiveQuadTo(380f, 560f)
                }
            }.build()

            return _Search!!
        }

    private var _Search: ImageVector? = null

    val ArrowForward: ImageVector
        get() {
            if (_Arrow_forward != null) return _Arrow_forward!!

            _Arrow_forward = ImageVector.Builder(
                name = "Arrow_forward",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(647f, 520f)
                    horizontalLineTo(160f)
                    verticalLineToRelative(-80f)
                    horizontalLineToRelative(487f)
                    lineTo(423f, 216f)
                    lineToRelative(57f, -56f)
                    lineToRelative(320f, 320f)
                    lineToRelative(-320f, 320f)
                    lineToRelative(-57f, -56f)
                    close()
                }
            }.build()

            return _Arrow_forward!!
        }

    private var _Arrow_forward: ImageVector? = null

    val Lock: ImageVector
        get() {
            if (_Lock != null) return _Lock!!

            _Lock = ImageVector.Builder(
                name = "Lock",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(240f, 880f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(160f, 800f)
                    verticalLineToRelative(-400f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(240f, 320f)
                    horizontalLineToRelative(40f)
                    verticalLineToRelative(-80f)
                    quadToRelative(0f, -83f, 58.5f, -141.5f)
                    reflectiveQuadTo(480f, 40f)
                    reflectiveQuadToRelative(141.5f, 58.5f)
                    reflectiveQuadTo(680f, 240f)
                    verticalLineToRelative(80f)
                    horizontalLineToRelative(40f)
                    quadToRelative(33f, 0f, 56.5f, 23.5f)
                    reflectiveQuadTo(800f, 400f)
                    verticalLineToRelative(400f)
                    quadToRelative(0f, 33f, -23.5f, 56.5f)
                    reflectiveQuadTo(720f, 880f)
                    close()
                    moveToRelative(0f, -80f)
                    horizontalLineToRelative(480f)
                    verticalLineToRelative(-400f)
                    horizontalLineTo(240f)
                    close()
                    moveToRelative(240f, -120f)
                    quadToRelative(33f, 0f, 56.5f, -23.5f)
                    reflectiveQuadTo(560f, 600f)
                    reflectiveQuadToRelative(-23.5f, -56.5f)
                    reflectiveQuadTo(480f, 520f)
                    reflectiveQuadToRelative(-56.5f, 23.5f)
                    reflectiveQuadTo(400f, 600f)
                    reflectiveQuadToRelative(23.5f, 56.5f)
                    reflectiveQuadTo(480f, 680f)
                    moveTo(360f, 320f)
                    horizontalLineToRelative(240f)
                    verticalLineToRelative(-80f)
                    quadToRelative(0f, -50f, -35f, -85f)
                    reflectiveQuadToRelative(-85f, -35f)
                    reflectiveQuadToRelative(-85f, 35f)
                    reflectiveQuadToRelative(-35f, 85f)
                    close()
                    moveTo(240f, 800f)
                    verticalLineToRelative(-400f)
                    close()
                }
            }.build()

            return _Lock!!
        }

    private var _Lock: ImageVector? = null

    val LockOpen: ImageVector
        get() {
            if (_Lock_open != null) return _Lock_open!!

            _Lock_open = ImageVector.Builder(
                name = "Lock_open",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(240f, 320f)
                    horizontalLineToRelative(360f)
                    verticalLineToRelative(-80f)
                    quadToRelative(0f, -50f, -35f, -85f)
                    reflectiveQuadToRelative(-85f, -35f)
                    reflectiveQuadToRelative(-85f, 35f)
                    reflectiveQuadToRelative(-35f, 85f)
                    horizontalLineToRelative(-80f)
                    quadToRelative(0f, -83f, 58.5f, -141.5f)
                    reflectiveQuadTo(480f, 40f)
                    reflectiveQuadToRelative(141.5f, 58.5f)
                    reflectiveQuadTo(680f, 240f)
                    verticalLineToRelative(80f)
                    horizontalLineToRelative(40f)
                    quadToRelative(33f, 0f, 56.5f, 23.5f)
                    reflectiveQuadTo(800f, 400f)
                    verticalLineToRelative(400f)
                    quadToRelative(0f, 33f, -23.5f, 56.5f)
                    reflectiveQuadTo(720f, 880f)
                    horizontalLineTo(240f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(160f, 800f)
                    verticalLineToRelative(-400f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(240f, 320f)
                    moveToRelative(0f, 480f)
                    horizontalLineToRelative(480f)
                    verticalLineToRelative(-400f)
                    horizontalLineTo(240f)
                    close()
                    moveToRelative(240f, -120f)
                    quadToRelative(33f, 0f, 56.5f, -23.5f)
                    reflectiveQuadTo(560f, 600f)
                    reflectiveQuadToRelative(-23.5f, -56.5f)
                    reflectiveQuadTo(480f, 520f)
                    reflectiveQuadToRelative(-56.5f, 23.5f)
                    reflectiveQuadTo(400f, 600f)
                    reflectiveQuadToRelative(23.5f, 56.5f)
                    reflectiveQuadTo(480f, 680f)
                    moveTo(240f, 800f)
                    verticalLineToRelative(-400f)
                    close()
                }
            }.build()

            return _Lock_open!!
        }

    private var _Lock_open: ImageVector? = null

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

    val NotLikeMovies: ImageVector
        get() {
            if (_Not_Like_Movies != null) return _Not_Like_Movies!!

            _Not_Like_Movies = ImageVector.Builder(
                name = "Box_add",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFFFFFFFF))
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

            return _Not_Like_Movies!!
        }

    private var _Not_Like_Movies: ImageVector? = null

}