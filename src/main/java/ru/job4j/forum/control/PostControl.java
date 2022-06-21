package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

@Controller
public class PostControl {
    private final PostService postService;

    public PostControl(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/addNewPost")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        return "edit";
    }

    @PostMapping("/savePost")
    public String save(@ModelAttribute("post") Post post) {
        postService.save(post);
        return "redirect:/";
    }

    @GetMapping("/updatePost")
    public String update(@RequestParam("postId") int postId, Model model) {
        model.addAttribute("post", postService.findById(postId));
        return "edit";
    }

    @GetMapping("/postInfo")
    public String postInfo(@RequestParam("postId") int postId, Model model) {
        model.addAttribute("post", postService.findById(postId));
        return "post";
    }
}
