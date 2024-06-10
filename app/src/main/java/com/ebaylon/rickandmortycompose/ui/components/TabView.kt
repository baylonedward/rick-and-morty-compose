package com.ebaylon.rickandmortycompose.ui.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by: ebaylon.
 * Created on: 6/10/24.
 */

/**
 * Displays a tab view with navigation functionality.
 *
 * @param T The type of the route associated with tab items.
 * @param tabBarItems A list of [TabBarItem] objects representing the tabs.
 * @param onTabSelected A callback invoked when a tab is selected, providing the selected [TabBarItem].
 */
@Composable
fun <T> TabView(
  tabBarItems: List<TabBarItem<T>>,
  onTabSelected: (TabBarItem<T>) -> Unit
) {
  // State to track the currently selected tab index
  var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

  // NavigationBar composable to display the tabs
  NavigationBar {
    // Iterate over each tab item and create a NavigationBarItem
    tabBarItems.forEachIndexed { index, tabBarItem ->
      NavigationBarItem(
        selected = selectedTabIndex == index, // Highlight the selected tab
        onClick = {
          selectedTabIndex = index // Update the selected tab index
          onTabSelected(tabBarItem) // Invoke the callback with the selected item
        },
        icon = {
          // Display the appropriate icon based on selection state
          TabBarIconView(
            isSelected = selectedTabIndex == index,
            selectedIcon = tabBarItem.selectedIcon,
            unselectedIcon = tabBarItem.unselectedIcon,
            title = tabBarItem.title,
            badgeAmount = tabBarItem.badgeAmount
          )
        },
        label = { Text(tabBarItem.title) } // Display the tab title
      )
    }
  }
}


/**
 * Displays an icon with an optional badge for a tab bar item.
 *
 * @param isSelected Whether the tab item is currently selected.
 * @param selectedIcon The icon to display when the tab item is selected.
 * @param unselectedIcon The icon to display when the tab item is unselected.
 * @param title The content description for the icon (for accessibility).
 * @param badgeAmount An optional number to display as a badge on the icon.
 */
@Composable
fun TabBarIconView(
  isSelected: Boolean,
  selectedIcon: ImageVector,
  unselectedIcon: ImageVector,
  title: String,
  badgeAmount: Int? = null
) {
  // Use BadgedBox to display the icon with an optional badge
  BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
    // Display the appropriate icon based on selection state
    Icon(
      imageVector = if (isSelected) selectedIcon else unselectedIcon,
      contentDescription = title
    )
  }
}

/**
 * Displays a badge with a count if the provided count is not null.
 * Params:
 * count - The number to display in the badge. If null, no badge is shown.
 */
@Composable
fun TabBarBadgeView(count: Int? = null) {
  if (count != null) {
    Badge {
      Text(count.toString())
    }
  }
}

/**
 * Represents a single item in a tab bar.
 * Params:
 * T - The type of the route associated with this tab item.
 * title - The title of the tab item.
 * selectedIcon - The icon to display when the tab is selected.
 * unselectedIcon - The icon to display when the tab is unselected.
 * route - The route associated with this tab item.
 * badgeAmount - An optional number to display as a badge on the tab item.
 *
 */
data class TabBarItem<T>(
  val title: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val route: T,
  val badgeAmount: Int? = null,
)