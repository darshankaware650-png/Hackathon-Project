# ✦ CampusPulse

> **Your next opportunity starts with you.**
> Ek hi jagah par students ke liye internships, scholarships, hackathons, competitions, courses, certifications aur workshops — profile ke hisaab se personalized.

🔗 **Live Demo:** [hackathon-project-ruby-one.vercel.app](https://hackathon-project-ruby-one.vercel.app/index.html)

Built for **FITFEST 2026** (GDG FIT Pune) — *Student Opportunity Discovery Platform*.

---

## 📌 Problem Statement

Students ko opportunities alag-alag websites, WhatsApp groups aur social pages par bikhri hui milti hain. Isse deadlines miss ho jaate hain aur relevant opportunity dhundhna mushkil hota hai.

## 💡 Our Solution

**CampusPulse** ek student-first platform hai. Student apna profile banata hai (skills, interests, preferred opportunity types), aur uske baad use category-wise organized opportunity board milta hai. Har opportunity ka details page hota hai, aur apply karne ke liye student seedha official opportunity website par redirect ho jaata hai.

---

## 🖼️ Screenshots

### Explore by Category
![Categories](screenshots/categories.png)

### Opportunity Board (Search + Filter + Save)
![Explore opportunities](screenshots/explore.png)

---

## ✨ Features

- 👤 **Profile Setup** — full name, education, skills, interests aur pasandida opportunity types
- 🗂️ **Category-wise Sections** (7 categories)
  - 💼 Internships — work experience and career starts
  - ⚡ Hackathons — build, collaborate, compete
  - 🎓 Scholarships — funding for your education
  - 🏆 Competitions — showcase what you can do
  - 📚 Courses — learn a skill, level up
  - ✦ Certifications — validate your knowledge
  - 🧠 Workshops — hands-on learning sessions
- 🔍 **Search** — title, skills ya keywords se opportunities dhundho
- 🎛️ **Category Filter** — dropdown se type select karo
- 🏷️ **Skill Tags & Deadlines** — har card par tags aur last date
- ❤️ **Save / Bookmark** — pasandida opportunities save karo, sidebar mein count dikhta hai
- ✨ **For You** — profile ke basis par personalized feed
- 📄 **View Details** — full details dekho, phir official site par redirect ↗
- 🏠 **Dashboard Overview**, **My Profile** aur **Settings**

---

## 🔄 User Flow

```
Create Profile → Overview → Explore by Category
      → Opportunity Board (search / filter / save)
      → View Details → Redirect to Official Site → Apply
```

---

## 🛠️ Tech Stack

| Layer    | Technology                  |
|----------|-----------------------------|
| Frontend | HTML, CSS, JavaScript       |
| Backend  | Spring Boot (Java, Maven)   |
| Database | Firebase / Firestore        |
| Hosting  | Vercel                      |

> Agar final build mein kuch alag use kiya hai to yeh table update kar lena.

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
git clone https://github.com/darshankaware650-png/<repo-name>.git
cd <repo-name>/frontend
npx serve .            # ya index.html seedha browser mein kholo
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
- Admin panel se opportunities add aur verify karna
- College-wise aur location-wise filters
- Resume se automatic skill extraction

---

## 👨‍💻 Author

**Darshan Kaware** — Computer Engineering, VPKBIET Baramati

- GitHub: [darshankaware650-png](https://github.com/darshankaware650-png)
- LinkedIn: [darshan-kaware](https://linkedin.com/in/darshan-kaware-5262613a8)

---

⭐ Project pasand aaye to star zaroor dena!