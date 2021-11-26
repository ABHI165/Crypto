package com.abhi165.assignment.presentation.component

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abhi165.assignment.data.response.Transaction
import com.abhi165.assignment.data.response.X
import com.abhi165.assignment.ui.theme.CollapsedBg
import com.abhi165.assignment.ui.theme.ExpandedBg
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


const val EXPAND_ANIMATION_DURATION = 300
const val COLLAPSE_ANIMATION_DURATION = 300
const val FADE_IN_ANIMATION_DURATION = 350
const val FADE_OUT_ANIMATION_DURATION = 300


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun PostItem(
    postdata: Transaction,
    onPostClicked: () -> Unit,
    isExpanded: Boolean
) {
    val transitionState = remember {
        MutableTransitionState( isExpanded).apply {
            targetState = !isExpanded
        }
    }
    val transition = updateTransition(transitionState, label = "tran")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "Tran_bg") {
        if ( isExpanded) ExpandedBg else CollapsedBg
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "Tran_padding") {
        if ( isExpanded) 26.dp else 24.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "Tran_elevation") {
        if ( isExpanded) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "Tran_corner") {
        if ( isExpanded) 5.dp else 16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "Tran_rotate") {
        if ( isExpanded) 0f else 180f
    }

    Card(
        backgroundColor = cardBgColor,
        contentColor = Color.Black,
        onClick = onPostClicked,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onPostClicked
                )
                PostTitle(title = postdata.x.hash,isExpanded)
            }


            PostBody(visible =  isExpanded, body = postdata.x)
        }
    }
}






@Composable
fun PostTitle(title: String,isExpanded: Boolean) {
    if(isExpanded)
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Start,
            color = Color.White,
            style = MaterialTheme.typography.h6

        )
    else
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
}


@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = com.abhi165.assignment.R.drawable.arrow),
                contentDescription = "",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}


@ExperimentalAnimationApi
@Composable
fun PostBody(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    body: X
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION_DURATION))
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.heightIn(10.dp))

            body.inputs.forEach {
                Text(
                    text = "Amount:- $${it.prevOut.value}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1
                )
                val formatter: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                formatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata") 

                val time =  formatter.format(body.time.toLong())
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Time:- $time",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Address:- ${it.prevOut.addr}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(25.dp))
            }

        }

    }
}