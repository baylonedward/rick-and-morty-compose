package com.ebaylon.rickandmortycompose.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

/**
 * Created by: ebaylon.
 * Created on: 6/10/24.
 */

object Transitions {
  fun slideInFromRight(): EnterTransition {
    return slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
      // Offsets the content by 1/3 of its width to the right, and slide towards left
      // Overwrites the default animation with tween for this slide animation.
      fullWidth / 3
    } + fadeIn(
      // Overwrites the default animation with tween
      animationSpec = tween(durationMillis = 200)
    )
  }

  fun slideInFromLeft(): EnterTransition {
    return slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth ->
      // Offsets the content by 1/3 of its width to the right, and slide towards left
      // Overwrites the default animation with tween for this slide animation.
      -fullWidth / 3
    } + fadeIn(
      // Overwrites the default animation with tween
      animationSpec = tween(durationMillis = 300)
    )
  }

  fun slideOutTowardsRight(): ExitTransition {
    return slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessMedium)) { fullWidth ->
      // Overwrites the ending position of the slide-out to 200 (pixels) to the right
      200
    } + fadeOut(
      animationSpec = tween(durationMillis = 200)
    )
  }

  fun fadeOut(): ExitTransition {
    return fadeOut(
      animationSpec = tween(durationMillis = 200)
    )
  }
}