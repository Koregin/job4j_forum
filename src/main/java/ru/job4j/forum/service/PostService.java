package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> getAll() {
        List<Post> postList = new ArrayList<>();
        repository.findAll().forEach(postList::add);
        return postList;
    }

    public void save(Post post) {
        repository.save(post);
    }

    public Post findById(int postId) {
        return repository.findById(postId).orElse(null);
    }
}
