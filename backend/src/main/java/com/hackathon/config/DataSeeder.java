package com.hackathon.config;

import com.hackathon.model.Opportunity;
import com.hackathon.service.OpportunityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final OpportunityService opportunityService;

    public DataSeeder(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @Override
    public void run(String... args) {
        if (!opportunityService.isEmpty()) {
            return;
        }

        seed("Smart India Hackathon 2026", "Hackathon",
                "National-level hackathon for student teams to solve real-world problem statements.",
                "https://sih.gov.in", "2026-11-15", List.of("java", "problem solving", "teamwork", "software"));

        seed("Google Summer of Code 2027", "Internship",
                "Remote open-source internship program for students, working with mentors on real projects.",
                "https://summerofcode.withgoogle.com", "2027-03-20", List.of("open source", "coding", "java", "python", "git"));

        seed("AICTE Merit Scholarship", "Scholarship",
                "Merit-based scholarship for engineering students with strong academic performance.",
                "https://aicte-india.org", "2026-12-01", List.of("academics", "financial aid"));

        seed("AWS Cloud Practitioner Certification", "Certification",
                "Foundational certification covering core AWS cloud concepts and services.",
                "https://aws.amazon.com/certification", "rolling", List.of("cloud", "aws", "devops"));

        seed("Smart India Hackathon Internal Round", "Competition",
                "College-level qualifier round to select teams for the national hackathon.",
                "#", "2026-10-10", List.of("java", "teamwork", "software"));

        seed("DSA Bootcamp", "Course",
                "8-week structured course covering data structures and algorithms with weekly assignments.",
                "#", "rolling", List.of("dsa", "java", "problem solving"));

        seed("Web Development Workshop", "Workshop",
                "Hands-on weekend workshop covering HTML, CSS, JavaScript and deployment basics.",
                "#", "2026-10-05", List.of("html", "css", "javascript", "web"));

        seed("Firebase & Cloud Fundamentals Workshop", "Workshop",
                "Introductory workshop on Firebase, Firestore, and deploying apps to the cloud.",
                "#", "2026-10-18", List.of("firebase", "cloud", "backend"));

        seed("GDG DevFest Pune 2026", "Competition",
                "Community tech conference with talks, workshops, and a student solution showcase.",
                "#", "2026-11-01", List.of("community", "web", "android", "cloud"));

        seed("TCS CodeVita Season 14", "Competition",
                "Global coding competition open to students, testing algorithmic problem-solving skills.",
                "https://codevita.tcs.com", "2026-11-20", List.of("java", "dsa", "problem solving"));

        seed("Coursera Java Programming Specialization", "Course",
                "Online specialization covering Java fundamentals through object-oriented design.",
                "https://coursera.org", "rolling", List.of("java", "oop"));

        seed("Microsoft Learn Student Ambassadors", "Internship",
                "Community leadership and learning program for students interested in Microsoft technologies.",
                "https://studentambassadors.microsoft.com", "rolling", List.of("community", "cloud", "leadership"));

        seed("National Robotics Competition", "Competition",
                "Inter-college robotics competition with design and coding challenges.",
                "#", "2026-12-10", List.of("robotics", "hardware", "problem solving"));

        seed("Spring Boot & Firestore Certification", "Certification",
                "Self-paced certification validating backend development skills with Spring Boot and Firestore.",
                "#", "rolling", List.of("java", "spring boot", "firebase", "backend"));

        seed("HackWithInfy 2026", "Hackathon",
                "Infosys' national coding and hackathon event for engineering students.",
                "https://hackwithinfy.infosys.com", "2026-11-25", List.of("java", "coding", "dsa"));

        seed("UI/UX Design Fundamentals Course", "Course",
                "Beginner-friendly course on design principles, wireframing, and prototyping tools.",
                "#", "rolling", List.of("design", "ui", "ux", "css"));
    }

    private void seed(String title, String category, String description, String link, String deadline, List<String> tags) {
        Opportunity o = new Opportunity();
        o.setTitle(title);
        o.setCategory(category);
        o.setDescription(description);
        o.setLink(link);
        o.setDeadline(deadline);
        o.setTags(tags);
        opportunityService.create(o);
    }
}
