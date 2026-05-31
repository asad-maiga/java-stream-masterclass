# 🚀 Java Stream API Masterclass & Playground

![Java Version](https://img.shields.io/badge/Java-16%2B-blue?logo=java)
![Stream API](https://img.shields.io/badge/Feature-Stream_API-success)
![Clean Code](https://img.shields.io/badge/Architecture-Clean_Code-orange)

This repository is an interactive learning and practice space that demonstrates the transition from traditional (imperative) data processing methods to the modern and declarative **Java Stream API** world.

The code not only demonstrates how the Stream API works but also incorporates enterprise software development standards such as **Method References**, the **DTO (Data Transfer Object) Pattern**, and the **Rich Domain Model**.

## 📖 What I Learned / What's in This Repository?

The `StreamPlayground.java` file in this project explores data processing workflows by dividing them into 5 main modules:

* **Module 1: Filtering (Predicates) & Clean Code**
    * Usage of `.filter()`.
    * Encapsulating business rules within the Entity and calling them via `Method Reference (::)` instead of using lengthy Lambda expressions (`t -> ...`).
* **Module 2: Transformation (Mapping) & DTO Pattern**
    * Transforming Database Entity objects into secure API responses (`TransferDTO`) using the `.map()` function.
* **Module 3: Calculations and Reductions (Aggregations)**
    * Reducing financial data (e.g., total revenue) into a single result using `.reduce()`.
* **Module 4: Advanced Grouping (Advanced Collectors)**
    * Applying SQL's `GROUP BY` logic in-memory using `Collectors.groupingBy()`.
* **Module 5: Pipeline Control**
    * Using `.skip()` and `.limit()` for Pagination logic.
    * Using `.anyMatch()` for performance-oriented short-circuit evaluations.

## 💡 Traditional vs. Modern Approach

A quick snippet from the project: Filtering successful and high-volume financial transactions.

**❌ Old School (Imperative - *How* to do it):**
```java
List<Transfer> bigSuccessTransfersOld = new ArrayList<>();
for (Transfer t : transfers) {
    if (t.getStatus().equals("SUCCESS")) {
        if (t.getAmount() > 1000) {
            bigSuccessTransfersOld.add(t);
        }
    }
}

## 🧰 Prerequisites

To run or modify this project locally, ensure you have:

1. **Java 16 or above.**
2. **Maven** or any other build automation tool (optional).
3. A suitable **IDE** like IntelliJ IDEA (recommended for best experience).

---

## 🚀 How to Use This Project?

This project can be used by developers or learners in the following ways:

1. **Learning Tool:** Explore Stream API features hands-on by modifying the existing use cases.
2. **Template for Projects:** Use it as a template to implement Stream API concepts in your enterprise applications.
3. **Coding Practice:** Challenge yourself to reimplement the modules from scratch and master the features.

### Running the Code
To run the code from this repository:

1. Clone the repository using:
   ```bash
   git clone <repository_url>
   ```
2. Open the project in your favorite IDE (e.g., IntelliJ).
3. Locate the main application file (e.g., `StreamPlayground.java`) and run it.
4. Review the output in the console to understand how each module works.

---

## 🌟 Features Highlighted in the Codebase

- **Encapsulation of Business Logic:** Business rules are properly encapsulated within domain entities for cleaner and more manageable code.
- **DTO Pattern:** Secure transformation and transmission of data between application and external APIs.
- **SQL-like Data Manipulation:** Simulating database operations like filtering, mapping, grouping, and reductions, directly in-memory.
- **Modern Java Practices:** Extensive use of method references, functional style programming, and immutability.

---

## 📂 Project Structure

Here’s a high-level overview of the project's structure: