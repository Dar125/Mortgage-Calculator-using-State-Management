# Mortgage Calculator Using State Management

## Description

This Android application is a Mortgage Calculator created in Kotlin.

The application allows the user to:
- Enter a loan amount
- Select a loan term (10, 15, or 30 years)
- Select an interest rate
- Calculate the monthly payment
- Calculate the total payment

## Lab 5 Features

This version includes:

1. State management using MortgageProvider to share data between MainActivity and ModifyActivity.
2. SharedPreferences to save:
   - Loan amount
   - Loan term
   - Interest rate

When the application is closed and reopened, the saved values are loaded automatically.

## Files

### Kotlin Files
- MainActivity.kt
- ModifyActivity.kt
- Mortgage.kt
- MortgageProvider.kt

### Layout Files
- activity_main.xml
- activity_modify.xml

## Technologies Used

- Kotlin
- Android Studio
- SharedPreferences
- State Management

## Author

Student Name
