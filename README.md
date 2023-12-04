# BrosFlix - Movie Information App
BrosFlix is an Android application developed using Java and Android Studio that allows users to explore and view information about movies. The app leverages The Movie Database (TMDb) API to fetch and display details about various films. This README provides an overview of the project structure, key components, and functionality.

## Project Structure
The project is organized into several components, including XML layout files, Java classes, and resources. Here's a brief overview of the main folders:

### res (Resources): Contains layout files, images, and string values used in the user interface.
### com.example.myapplication (Main Package): Houses the primary Java classes for the application.
Key Components
### 1. MainActivity.java
The MainActivity class is the main activity of the application. It handles the initialization of views, loading data from the TMDb API, and managing user interactions such as searching and changing the layout of the RecyclerView. Additionally, it facilitates changing the movie category.

### 2. Adaptery.java
Adaptery is a custom adapter for the RecyclerView, allowing users to switch between a list and grid view. It is responsible for loading data into views and managing click events.

### 3. MovieDetailActivity.java
The MovieDetailActivity class displays detailed information about a selected movie, including its title, release date, popularity, and synopsis. It utilizes the Glide library to efficiently load images from URLs.

### 4. MovieModelClass.java
MovieModelClass represents the data model for storing information about a movie, including its ID, name, image, release date, popularity, and synopsis.

### 5. SpacesItemDecoration.java
SpacesItemDecoration is a RecyclerView item decoration class that provides spacing between elements in the RecyclerView.

## XML Layouts
### activity_main.xml: Defines the main activity's interface, including a RecyclerView for displaying movies, a SearchView for searching, and a Switch for changing between list and grid views. It also features a Spinner for selecting the movie category.

### movie_detail.xml: Defines the layout for the MovieDetailActivity, displaying specific details of a selected movie, such as title, release date, popularity, and synopsis. It includes an ImageView for showing the movie poster.

### movie_item.xml: Describes the appearance of an item in the RecyclerView's list view, showing the title, release date, popularity, and movie image.

### grid_layout.xml: Similar to movie_item.xml but tailored for a grid layout in the RecyclerView.

### menu_main.xml: Defines the application menu with three options: "Top Rated," "Most Popular," and "Upcoming Releases." These options allow users to change the displayed movie category.

## Getting Started
To run the BrosFlix app, follow these steps:

### Clone the Repository:
git clone [repository_url]
Open in Android Studio:

### Open Android Studio.
Choose "Open an Existing Project" and select the BrosFlix project.
Build and Run:

Build and run the app on an emulator or physical device.
Features and Functionality
Users can search for movies by title.
The app allows users to switch between list and grid views.
Three movie categories are available: "Top Rated," "Most Popular," and "Upcoming Releases."
Data is fetched from The Movie Database (TMDb) API.
Efficient image loading is implemented using the Glide library.
Data is stored locally using SharedPreferences for persistent storage.
Additional Information
The app uses AsyncTask and HttpURLConnection for background network operations.
Extensive comments in the code provide explanations for specific decisions and additional context.
The code is well-structured and follows Java conventions.
The README file is crucial for understanding the project, including its structure, components, and usage instructions.
Demo Video
For a demonstration of the BrosFlix app, please refer to the Demo Video.

## Screenshots
Visit the Screenshots Folder to view images of the app on different devices.

## API Documentation
Explore The Movie Database (TMDb) API documentation here.

## Compatibility
The app has been tested and optimized for various devices, including Pixel XL, Pixel 3a, Pixel 3a XL, Pixel 6, and more. Compatibility improvements have been implemented to ensure correct rendering and data loading.

## Known Issues
Compatibility exceptions may occur on certain devices, and adjustments have been made to address these issues.
Refer to the code implementation and logs for additional details.
## Conclusion
BrosFlix demonstrates the development of an Android application using Java and Android Studio, integrating data from The Movie Database (TMDb) API. The documentation provides a comprehensive overview of the project, highlighting its structure, key components, and functionality.

Feel free to explore the code, run the app, and contribute to its enhancement. If you encounter any issues or have suggestions for improvement, please open an issue or submit a pull request.

# Happy exploring and movie watching with BrosFlix! 🍿🎬
