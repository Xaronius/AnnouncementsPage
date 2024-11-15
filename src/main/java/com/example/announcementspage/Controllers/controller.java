package com.example.announcementspage.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class controller {

    @GetMapping("/")
    public String homePage() {
        return "index.html"; // This maps to index.html in the templates folder
    }

    @GetMapping("/announcements")
    public String announcementsPage() {
        System.out.println("Success");
        return "announcements.html"; // You may also return a JSON response if needed
    }

    @GetMapping("/CreateNew")
    public String CreateNewPage() {
        System.out.println("Success");
        return "createNew.html"; // You may also return a JSON response if needed
    }

    @GetMapping("/Profile")
    public String ProfilePage() {
        System.out.println("Success");
        return "Profile.html"; // You may also return a JSON response if needed
    }

}
