# FurEverHome Pet Adoption App

Welcome to FurEverHome Pet Adoption App! This application allows users to adopt pets and manage the adoption process.

## Prerequisites

Before you begin, make sure you have the following prerequisites installed on your machine:

- Java 8
- Maven
- SQL Plus
- Oracle SQL Developer

## Getting Started

Follow the steps below to set up FurEverHome Pet Adoption App on your local environment:

1. Clone the repository:

   ```bash
   git clone https://github.com/CHAMPets/fureverhome-app.git
   ```

2. Navigate to the project directory:

   ```bash
   cd fureverhome-app
   ```

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

4. Setup your database:

   - Configure your OracleDB connection settings using SQL Plus or Oracle SQL Developer.

5. Configuration:

   - Open the `application.properties` file located in the project's `src/main/resources` directory.
   - Modify the configuration settings to match your setup, including OracleDB connection, JPA, Thymeleaf, Spring Security, and email configuration.

6. Create an admin account for the system:

   - Open `src/main/java/com/champets/fureverhome/user/service/impl/UserServiceImpl.java` in your preferred Java IDE.
   - Navigate to line 43 and change `Role role = roleRepository.findByName("USER");` to `Role role = roleRepository.findByName("ADMIN");`.
   - Save the file.

7. Run the application:

   ```bash
   mvn spring-boot:run
   ```

8. Register an account, and it will be automatically registered as an admin.
   - IMPORTANT: After testing, revert the changes made in `UserServiceImpl.java` by changing `Role role = roleRepository.findByName("ADMIN");` back to `Role role = roleRepository.findByName("USER");`.

## Usage

Upon running the app, open your browser and go to `localhost:8080`. You'll be directed to the landing page of the application. From there, you can log in or sign up for an account.

## Testing

The tests for the application are located under `src/test/java/com/champets/fureverhome`. The tests are organized per entity and implemented using JUnit and Mockito.

## Contributing

We welcome contributions from the developer community to enhance FurEverHome Pet Adoption App. If you have ideas for new features or improvements, please submit a pull request. We appreciate your contributions!

## License

This project doesn't have a license yet.

## Acknowledgements

We would like to express our gratitude to the following individuals and organizations:

- Champ Cargosystems for their support and contributions.
- Vener Guevarra for his guidance as the Java 8 core bootcamp instructor.

---

Thank you for choosing FurEverHome Pet Adoption App. We hope you have a great experience using the application. If you encounter any issues or have any questions, please reach out to our support team at fureverhome.app@gmail.com.

Happy pet adoption!
