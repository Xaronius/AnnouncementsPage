package com.example.announcementspage.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class controller {

    @GetMapping("/")
    public String homePage() {
        return "index.html"; // This maps to index.html in the templates folder
    }

    @GetMapping("/announcements")
    public String announcementsPage() {
        return "announcements.html"; // You may also return a JSON response if needed
    }

    @GetMapping("/CreateNew")
    public String CreateNewPage() {
        return "createNew.html"; // You may also return a JSON response if needed
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
    @GetMapping("/announcement")
    public String showAnnouncement(@RequestParam("title") String title,
                                   @RequestParam("text") String text) {
        return "redirect:/announcementPage.html?title=" + title + "&text=" + text;
    }
}
