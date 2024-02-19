# Green-Stops
Green Stop App  A Kotlin-based app using Jetpack Compose that keeps track of Stops visited in a trip including their distance. The app keeps track of the distance traveled and distance left to travel with an option to view km or miles.
Main Activity
This class serves as the entry point for the app and is responsible for setting up the user interface using Jetpack Compose.
onCreate: This function initializes the activity when it is first created. It sets the content to a `Surface` with a specific color and displays the `AffirmationsApp` composable function.

# Composable Functions:

AffirmationsApp: 
This function represents the main user interface of the app. It includes various UI elements and state variables for managing the app's behavior.
It uses Scaffold to create a bottom bar to contain LinearProgressIndicator, buttons, switches, and variable text. Tracks index, limit, switch state, progress, and distances.
Rows and columns are used to make the bar aesthetically pleasing.
Loads affirmations from a data source and populates two lists: `listy` for 10 stops as used in the Lazy list and `norm_list` for 7 stops used in the normal list.
A lazy list or AffirmationList is called to present a list.

AffirmationCard:  
This function renders a card displaying details of each stop, including the station name and distance till the stop.
Uses text to display station and distance
Compares the current index to the stop index to mark completed stops using a different color.

LazyList:
Creates a lazy list (scrollable list) using `LazyColumn` to display each card made by the AffirmationCard function. (for 10 stops)

AffirmationList:
Creates a list (not scrollable ) using ‘Column` to display each card made by the AffirmationCard function. (for 7 stops)

Switcher:
Renders a switch UI element for toggling between different distance units (miles or kilometers).
Takes a boolean value representing the current state of the switch and a callback function for handling state changes.

convert:
Converts a distance from kilometers to miles and returns the result as a formatted string to keep miles at 2 decimal places.

