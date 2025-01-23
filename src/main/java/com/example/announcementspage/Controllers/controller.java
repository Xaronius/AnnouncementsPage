package com.example.announcementspage.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class controller {

    @GetMapping("/")
    public String homePage(Model model) {
        return "LoginPage.html";
    }

    @GetMapping("/LoginPage")
    public String loginPage() {
        return "LoginPage.html";
    }

    @GetMapping("/RegisterPage")
    public String registerPage() {
        return "RegisterPage.html";
    }

    @GetMapping("/announcements")
    public String announcementsPage() {
        return "announcements.html"; // You may also return a JSON response if needed
    }

    @GetMapping("/CreateNew")
    public String CreateNewPage() {
        return "createNew.html"; // You may also return a JSON response if needed
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard.html";
    }

    @GetMapping("/Profile")
    public String ProfilePage() {
        return "Profile.html"; // You may also return a JSON response if needed
    }

    @GetMapping("/MyAnnouncements")
    public String MyAnnouncementsPage() {
        return "MyAnnouncements.html";
    }


/*
    @PostMapping("/announcement")
    public String showAnnouncement(@RequestParam("title") String title, @RequestParam("text") String text, Model model) {
        // Add data to the model to pass it to the view

        model.addAttribute("title", title);
        model.addAttribute("text", text);

        // Return the name of the template to display
        return "announcementPage.html"; // Points to announcementPage.html
    }
    */

    @GetMapping("/announcementPage")
    public String showAnnouncement(@RequestParam("adId") long adId, Model model) {
        model.addAttribute("adId", adId);
        return "announcementPage"; // This refers to announcementPage.html in the templates folder
    }

    @GetMapping("/UpdateAnnouncement")
    public String updateAnnouncementPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("announcementId", id); // Pass the announcement ID to the page
        return "UpdateAnnouncement"; // Points to UpdateAnnouncement.html in the templates folder
    }

//    @GetMapping("/mainpage/announcement")
//    public String showAnnouncement(@RequestParam("title") String title,
//                                   @RequestParam("text") String text) {
//        return "redirect:/announcementPage.html?title=" + title + "&text=" + text;
//    }

    private boolean checkIfLoggedIn() {

        return true;
    }
}
