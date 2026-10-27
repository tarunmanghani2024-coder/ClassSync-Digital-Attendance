# ClassSync – Digital Attendance System

A lightweight attendance management web app for classrooms. Teachers mark attendance, track every student and prepare monthly parent reports. Students check in with a short code and see their own attendance. The frontend is a single HTML file, and a small **Java backend with no external libraries** serves it and stores the data.

![Java](https://img.shields.io/badge/Java-11%2B-orange)
![Frontend](https://img.shields.io/badge/Frontend-HTML%20%7C%20CSS%20%7C%20JavaScript-blue)
![Dependencies](https://img.shields.io/badge/Dependencies-none-brightgreen)

<!-- Add screenshots here, e.g. ![Dashboard](docs/dashboard.png) -->

## Features

**Teacher**
- Dashboard with total students, present today, class average and students below the limit
- Mark attendance as Present, Absent, Late or Excused, with an "All present" shortcut
- Attendance calendar with class-wide percentages for each day
- Add, edit and delete students, with photo upload and bulk CSV import
- Monthly reports with a parent message preview, sent by email or WhatsApp link, plus CSV export
- Settings for minimum attendance %, counting Late as Present, and holidays
- Audit log of every change

**Student**
- Personal calendar, monthly stats and 6-month trend
- Shortage calculator: how many classes you can skip, or must attend, to stay above the limit
- Self check-in with a 6-digit code from the teacher (valid for 2 minutes; entering it after the first 10 minutes marks Late)

**General**
- Teacher and student roles with password login (passwords are hashed with SHA-256 in the browser)
- Light and dark theme, responsive layout for phones and desktops
- Works offline: if the server is not running, data is saved in the browser

## Tech stack

| Layer | Technology |
|---|---|
| Frontend | HTML, CSS, vanilla JavaScript (single file) |
| Backend | Java 11+ using the built-in `com.sun.net.httpserver` |
| Storage | JSON file on the server (`data/classsync.json`) |

## Project structure

```
classsync/
├── Server.java        # Java backend: static files + data API
├── public/
│   └── index.html     # Frontend app
├── data/              # Created at runtime; holds classsync.json
└── README.md
```

## Getting started

**Requirements:** Java 11 or newer.

```bash
git clone https://github.com/<your-username>/classsync.git
cd classsync
java Server.java
```

Open **http://localhost:8080** in your browser.

To use it from phones on the same Wi-Fi, open `http://<your-computer-ip>:8080`. Student check-in then works across devices.

### Demo accounts

| Role | Name | Password |
|---|---|---|
| Teacher | Prof. Meera Joshi | `teacher123` |
| Student | Aman Sharma | `student123` |

Registering a new teacher needs the staff key `STAFF2026`. Demo data can be reset from **Settings → Reset everything**.

## API

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/data` | Returns the saved app data as JSON (`204` if nothing saved yet) |
| `PUT` | `/api/data` | Saves the app data (JSON object, up to 5 MB) |

The browser keeps the logged-in user and theme to itself, so each device has its own login while sharing the same class data. The page also refreshes from the server every 5 seconds.

## Known limitations

This is a basic-level project meant for learning and small demos.

- Login and the staff key are checked in the browser, so they are not secure for real use.
- The whole data file is saved at once, so with simultaneous edits the last save wins.
- Parent reports open a prefilled email or WhatsApp message. The app does not send them itself.

## Roadmap

- Move login and sessions to the server (password hashing, tokens)
- Separate endpoints for students and attendance
- Database storage (SQLite or MySQL) using JDBC
- Automatic email delivery of monthly reports
- Unit tests for the backend


-------------------------------------------------------------------------------------------------------------------------------------------------------------------
Declaration of Ai use :-
1. Claude Ai ( use to adding detail  and finding the feature what i can add in this program )
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
