# ExTrack Expense Tracker App

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

ExTrack is an Android application that helps users track their expenses. It includes features such as Firebase authentication for login and registration, SMS integration, Firebase Realtime Database for storing user transactions, displaying transactions in a RecyclerView, an articles section that can be viewed using WebView or externally, and the ability to share articles through social media apps. It also includes a basic profile page.

## Features

- Firebase login and registration authentication with email and password
- SMS integration for receiving transaction notifications
- Firebase Realtime Database for storing user transactions
- Displaying transactions in a RecyclerView for easy tracking
- Articles section for reading informative content
- View articles using WebView or externally in the browser
- Share articles through popular social media apps
- Profile page for managing basic details

## Screenshots

Include a video demo for the app's UI and functionality:

https://drive.google.com/file/d/1XBXuv8dI8LRsF0r3bCjjN12evCBao1k_/view?usp=share_link

## Technologies Used

- Android Studio
- Java
- Firebase Authentication
- Firebase Realtime Database
- SMS Integration
- WebView
- Social media sharing APIs (e.g., WhatsApp, Facebook, Instagram)

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Android Studio (version X.X or higher)
- Android SDK
- Java Development Kit (JDK) 8 or higher
- Firebase account with Authentication and Realtime Database enabled
- Twilio or other SMS service account (if using SMS integration)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/extrack-expense-tracker.git
   ```

2. Open the project in Android Studio.

3. Configure Firebase Authentication:

   - Create a new Firebase project and enable Firebase Authentication.
   - Add the `google-services.json` file to the `app/` directory of the project.
   - Modify the Firebase configuration in the `build.gradle` file and `AndroidManifest.xml`.

4. Configure Firebase Realtime Database:

   - Enable Firebase Realtime Database in your Firebase project.

5. Configure SMS Integration (if applicable):

   - Set up a Twilio or other SMS service account.
   - Add the necessary API credentials to the project and update the code accordingly.

6. Build and run the app on an emulator or physical device.

## Usage

Describe how to use your app and provide any necessary instructions or guidelines. For example:

1. On the login screen, enter your email and password to log in or register for a new account.
2. After logging in, you will be directed to the home screen where you can view your transactions.
3. Add new transactions using the provided form or import transactions from your SMS messages.
4. View the transaction history on the Transactions screen.
5. Explore the Articles section to read informative content.
6. Tap on an article to view it in WebView or externally in the browser.
7. Share articles with friends through social media apps.
8. Manage your basic profile details on the Profile page.
