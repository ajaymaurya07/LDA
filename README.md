# House Tax Project Documentation

## Overview
This module is a part of the LDA (Lucknow Development Authority) application, specifically designed to handle **House Tax (Property Tax)** services for citizens. It allows users to search for their property, assess taxes, and make online payments.

## Key Features

### 1. Property Management
*   **Property Search:** Users can search for property details using their unique Property ID (PID).
*   **Property Selection:** If a mobile number is linked to multiple properties, users can select the specific property they want to manage.

### 2. Tax Assessment & Billing
*   **Detailed Billing:** View breakdown of House Tax, Water Tax, and Sewer Tax.
*   **Tax Assessment:** Automated calculation of current taxes based on property attributes.
*   **ARV History:** Access records of Annual Rental Value (ARV) over the years.

### 3. Payments & Transactions
*   **Online Payment Integration:** Integrated with **PayU Checkout Pro** for secure payments via UPI, Credit/Debit cards, and Net Banking.
*   **Transaction History:** A complete list of past payments with the ability to view detailed receipts.
*   **Payment Status:** Real-time feedback on payment success, failure, or pending status.

## Technical Details

### Project Structure (House Tax)
*   `com.example.lda.houseTax`: Main package containing activities like `PropertySearchActivity`, `PropertyDetailsActivity`, and `PaymentActivity`.
*   `com.example.lda.houseTax.viewmodel`: Contains `PropertyDetailsViewmodel` for managing tax data.
*   `com.example.lda.houseTax.data`: Adapters and data models for sliders, transactions, and property lists.
*   `com.example.lda.houseTax.utils`: Utility classes like `PreferenceManager` for session handling.

### Tech Stack
*   **Language:** Kotlin
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Payment Gateway:** PayU Checkout Pro SDK
*   **Local Database:** Room (used for caching bill details)
*   **Networking:** Retrofit with GSON converter
*   **UI Components:** DataBinding, ViewPager2, Material Design Components

## Core Workflow
1.  **Login/Search:** User enters PID or searches via mobile number.
2.  **View Details:** App fetches property and bill details from the server.
3.  **Initiate Payment:** User reviews tax amounts and clicks 'Pay Now'.
4.  **PayU Checkout:** User completes payment on the PayU platform.
5.  **Status Update:** App receives payment response and updates the UI (Success/Failure).
