# ⚡ AthleteCoach AI - Backend

AthleteCoach AI is an intelligent coaching platform designed to provide athletes with data-driven training insights and personalized drill schedules. This backend service handles user authentication, athlete profile management, and the core logic for training recommendations.

---

## 🛠️ Tech Stack
* **Language:** Java 17/21
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security & JWT (JSON Web Tokens)
* **Database:** MySQL
* **Deployment:** Railway (Cloud)
* **Build Tool:** Maven

---

## ✨ Key Features
* **Secure Authentication:** Robust login/signup flow using JWT for stateless session management.
* **Athlete Profile Management:** Create and update performance metrics for individual athletes.
* **AI Integration:** (Briefly mention your AI logic here, e.g., "OpenAI API integration for drill generation").
* **Cloud Ready:** Fully configured for environment-based deployment on platforms like Railway.

---

## ⚙️ Environment Variables
To run this project locally or on the cloud, ensure the following variables are set:

| Key | Description |
| :--- | :--- |
| `SPRING_DATASOURCE_URL` | JDBC connection string for MySQL |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `APP_JWT_SECRET` | Secret key for signing tokens |
| `APP_JWT_EXPIRATION` | Token validity in milliseconds |

---

## 🚀 Local Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/keshavgarg070306-png/athletecoach-ai.git](https://github.com/keshavgarg070306-png/athletecoach-ai.git)
   cd athletecoach-ai
