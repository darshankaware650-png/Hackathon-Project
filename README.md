# ✦ CampusPulse

> **Your next opportunity starts with you.**
> One place for students to discover internships, scholarships, hackathons, competitions, courses, certifications and workshops, personalized to their profile.

🔗 **Live Demo:** [hackathon-project-ruby-one.vercel.app](https://hackathon-project-ruby-one.vercel.app/index.html)

Built for **FITFEST 2026** (GDG FIT Pune) as a *Student Opportunity Discovery Platform*.

---

## 📌 Problem Statement

Students find opportunities scattered across many websites, WhatsApp groups and social pages. Deadlines get missed, and finding something relevant is difficult.

## 💡 Our Solution

**CampusPulse** is a student-first platform. A student creates a profile (skills, interests, preferred opportunity types) and gets a category-wise opportunity board. Every opportunity has a details page, and when the student is ready to apply, they are redirected straight to the official opportunity website.

---

## 🖼️ Screenshots

### Explore by Category
<img width="959" height="599" alt="Screenshot 2026-09-28 110039" src="https://github.com/user-attachments/assets/f27d8ba8-eb3a-49ea-a887-edc590476444" />



### Opportunity Board (Search + Filter + Save)
<img width="959" height="599" alt="Screenshot 2026-09-28 101343" src="https://github.com/user-attachments/assets/2ad6471d-fd60-47fd-a701-84cfcb8043b8" />



---

## ✨ Features

- 👤 **Profile Setup**: full name, education, skills, interests and preferred opportunity types
- 🗂️ **Category-wise Sections** (7 categories)
  - 💼 Internships: work experience and career starts
  - ⚡ Hackathons: build, collaborate, compete
  - 🎓 Scholarships: funding for your education
  - 🏆 Competitions: showcase what you can do
  - 📚 Courses: learn a skill, level up
  - ✦ Certifications: validate your knowledge
  - 🧠 Workshops: hands-on learning sessions
- 🔍 **Search** by title, skills or keywords
- 🎛️ **Category Filter** via dropdown
- 🏷️ **Skill Tags & Deadlines** on every opportunity card
- ❤️ **Save / Bookmark** opportunities, with a live count in the sidebar
- ✨ **For You**: personalized feed based on the student's profile
- 📄 **View Details**, then redirect to the official site ↗
- 🏠 **Dashboard Overview**, **My Profile** and **Settings**

---

## 🔄 User Flow

```
Create Profile → Overview → Explore by Category
      → Opportunity Board (search / filter / save)
      → View Details → Redirect to Official Site → Apply
```

---

## 🛠️ Tech Stack

| Layer            | Technology                |
|------------------|---------------------------|
| Frontend         | HTML, CSS, JavaScript     |
| Backend          | Spring Boot (Java, Maven) |
| Database         | Firebase / Firestore      |
| Hosting Frontend | Vercel                    |
| Hosting Backend  | Render                    |

---

## 📁 Project Structure

```
HackathonProject/
├── frontend/          # HTML, CSS, JS pages (index, login, dashboard, ...)
└── backend/           # Spring Boot application
    └── src/main/java/
        ├── controller/
        ├── service/
        ├── repository/
        ├── model/
        └── config/
```

---

## 🚀 Getting Started

```bash
git clone https://github.com/darshankaware650-png/Hackathon-Project.git
cd Hackathon-Project/frontend
npx serve .
```

Backend (optional):

```bash
cd backend
mvn spring-boot:run    # http://localhost:8080
```

---

## 🔮 Future Scope

- AI-based opportunity recommendations
- Deadline reminders (email / notifications)
- Admin panel to add and verify opportunities
- College-wise and location-wise filters
- Automatic skill extraction from resumes

---

## 👨‍💻 Author

**Darshan Kaware**, Computer Engineering, VPKBIET Baramati

- GitHub: [darshankaware650-png](https://github.com/darshankaware650-png)
- LinkedIn: [darshan-kaware](https://linkedin.com/in/darshan-kaware-5262613a8)
