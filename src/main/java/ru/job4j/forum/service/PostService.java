package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.MemRepository;

import java.util.List;

@Service
public class PostService {

    private final MemRepository memRepository;

    public PostService(MemRepository memRepository) {
        this.memRepository = memRepository;
    }

    public List<Post> getAll() {
        return memRepository.getAll();
    }

    public void save(Post post) {
        memRepository.save(post);
    }

    public Post findById(int postId) {
        return memRepository.findById(postId);
    }
}
