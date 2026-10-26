# ClassSync-Digital-Attendance
A single-file digital attendance system with teacher and student dashboards, self check-in codes, shortage calculator and monthly parent reports. Built with vanilla JS.
Discribtion 
# ClassSync – Digital Attendance

A lightweight, single-file attendance management web app for classrooms. Built with plain HTML, CSS and JavaScript, with no frameworks, no build step and no dependencies. Just open `index.html`.

## Features

- **Role-based login:** Teacher and Student accounts with SHA-256 hashed passwords
- **Fast attendance marking:** Present, Absent, Late and Excused, with an "All present" shortcut
- **Student self check-in:** Teachers generate a 6-digit code that expires in 2 minutes
- **Live dashboard:** Daily trends, class average and students below the minimum attendance
- **Shortage calculator:** Shows how many classes a student can miss, or must attend, to stay above the threshold
- **Monthly parent reports:** Preview and send by Email or WhatsApp, plus CSV export
- **Student management:** Add, edit, delete, photo upload and bulk CSV import
- **Calendar view:** Class-wide attendance by day, with holiday support
- **Settings and audit log:** Custom threshold, late-counts-as-present rule and a change history
- **Light and dark theme:** Responsive layout that works on mobile

## Demo Accounts

| Role | Name | Password |
|---------|-------------------|--------------|
| Teacher | Prof. Meera Joshi | `teacher123` |
| Student | Aman Sharma | `student123` |

Teacher registration staff key: `STAFF2026`

## Tech Stack

HTML5 · CSS3 (CSS variables, Grid, Flexbox) · Vanilla JavaScript · Web Crypto API · `localStorage`

## Note

This is a front-end demo. Data is stored in the browser's `localStorage`, and the staff key and self check-in code are not server-verified. A production version would need a backend for authentication, cross-device check-in and real email delivery.
---

**Short one-line version** (for the repo "About" field):
> A single-file digital attendance system with teacher and student dashboards, self check-in codes, shortage calculator and monthly parent reports. Built with vanilla JS.

**Suggested topics:** `attendance-system` `vanilla-javascript` `html-css-js` `classroom-management` `student-dashboard` `localstorage`
