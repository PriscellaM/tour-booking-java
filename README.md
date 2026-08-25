# OOP Assignment - Tour Booking System (Java)
A comprehensive Java object-oriented tour booking application built for OOP Assignment. **Stage D** serves as the complete integration of all assignment stages, providing a full terminal-based interface to manage accounts, customer profiles, tour inventories, polymorphic ticket bookings, sales analytics, and state serialization.

---

## Features
* **Role-Based Authentication:** Multi-user authentication supporting Administrator and Sales Executive roles with custom input validation (`Account.java`).
* **Customer & Insurance Computation:** Stores customer information and calculates tiered insurance premiums based on total party size (`Customer.java`, `Insurance.java`).
* **Tour Inventory Architecture:** Base abstract `Tour` class implemented across `CityTour`, `Attractions`, and `InterstateInternational` classes.
* **Polymorphic Ticket System:** Supports standard (`NormalTic`) and family discount tiers (`FamilyTic1` with 10% discount, `FamilyTic2` with 15% discount).
* **Cancellations & Refunds:** Built-in calculation module for processing ticket cancellations with a standard 75% refund policy.
* **Data Persistence & Serialization:** Serializes and deserializes application state to and from `data.txt` via `Data.java` and `FileInputOutput.java`.
* **Sales Analytics & Reporting:** Real-time terminal reporting tools to inspect available tour seats, total earnings, and filter sales performance by tour or sales executive.
* **Unit Testing Suite:** Automated unit test assertions verifying ticket pricing formulas, status checks, and refund totals (`JunitTest.java`).

---

## Class Overview
| Class File | Description |
| :--- | :--- |
| **`StageD.java`** | Main application entry point driving the complete terminal interface. |
| **`Account.java`** | User account model handling credentials, roles, and validation. |
| **`Customer.java`** | Manages customer contact details and implements the `Insurance` interface. |
| **`Insurance.java`** | Interface defining customer insurance calculation logic. |
| **`Tour.java`** | Base abstract class extending `Comparable<String>`. |
| **`CityTour.java`** | Subclass for city tour packages. |
| **`Attractions.java`** | Subclass for local attraction packages. |
| **`InterstateInternational.java`** | Subclass for interstate and international trip packages. |
| **`Ticket.java`** | Abstract ticket base class providing core refund and pricing properties. |
| **`NormalTic.java`** | Standard ticket tier (0% discount). |
| **`FamilyTic1.java`** | Family ticket tier 1 (10% discount). |
| **`FamilyTic2.java`** | Family ticket tier 2 (15% discount). |
| **`Data.java`** | Serializable container storing system data across execution sessions. |
| **`FileInputOutput.java`** | Utility managing file I/O operations and serialization with `data.txt`. |
| **`JunitTest.java`** | Test suite validating pricing structures, refund operations, and assertions. |

---

### Prerequisites
* Java Development Kit (JDK 8 or higher)
* JUnit 4 or 5 (for running `JunitTest.java`)
