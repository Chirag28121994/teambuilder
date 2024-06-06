TeamBuilder is a web application designed to streamline the process of managing sports teams, scheduling matches, and tracking player participation. It provides a comprehensive platform for coaches, managers, and players to collaborate and stay organized.

Features
Team Management: Create and manage multiple sports teams with details such as team name, logo, roster, and contact information.

Match Scheduling: Schedule matches for teams, including date, time, location, and opponent details.

Player Participation Tracking: Track player availability and attendance for matches, record match statistics, and designate key players such as the "Man of the Match."

Customizable Match Descriptions: Add custom descriptions or notes to matches for additional context or instructions.

Notification System: Receive notifications for upcoming matches, player availability changes, and other important updates.

Technologies Used
Backend: Java with Spring Boot framework for RESTful API development.

Database: MongoDB for storing match data. Postgresql for storing team and player data.

Frontend: TODO

Testing: TestNG for unit and integration testing.

Build Automation: Maven for dependency management and project build.

Continuous Integration: TODO

Getting Started
To get started with the TeamBuilder application, follow these steps:

Clone the Repository: Clone the TeamBuilder repository to your local machine.

Configure Database: Set up MongoDB & Postgresql and configure the application to connect to your instance. Update the application.properties file with your local connections details.

Build and Run the Application: Use Maven to build and run the application.

bash
Copy code
mvn spring-boot:run
Access the Application: Once the application is running, access it through your web browser at http://localhost:8080.

Usage
Team Management: Create new teams, add players to teams, and update team information as needed.

Match Scheduling: Schedule matches for teams, update match details, and view upcoming matches.

Player Participation Tracking: Track player availability for matches, mark players as present or absent, and record match statistics.

Notifications: Receive notifications for upcoming matches, player availability changes, and other important updates via email or in-app notifications.

Contributing
Contributions to the TeamBuilder application are welcome! If you would like to contribute, please follow these steps:

Fork the repository.
Create a new branch for your feature or bug fix.
Make your changes and commit them to your branch.
Push your changes to your fork.
Submit a pull request to the main repository.
