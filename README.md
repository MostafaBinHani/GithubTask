This project is a native mobile application built to search for GitHub users and display a list of search results. The app was developed as part of an internship program, applying knowledge in UI design, architecture, API integration, and Git management.

Features
1. Search GitHub Users: Search for GitHub users by their username using the GitHub REST API.
2. Display Search Results: View a list of search results with user details including user ID, profile picture, and additional fields.
3. Details Screen: Click on a user from the search results to navigate to a details screen displaying more information about the selected user.
4. Pagination: Automatically load more results as you scroll to the end of the list.
Technologies Used
- Android: Kotlin, Jetpack Compose
- Architecture: MVVM
- APIs: GitHub REST API for searching users and fetching user details
- Libraries: Open to any third-party libraries that enhance the functionality or user experience.
API Endpoints
- Search Users: https://api.github.com/search/users?q={user}&page=1
- User Details: https://api.github.com/users/{user_id}

Clone the repository and build the project using Android Studio for Android.
git clone https://github.com/yourusername/github-user-search-app.git
