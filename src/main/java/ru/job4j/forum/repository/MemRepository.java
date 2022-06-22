package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemRepository {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(2);

    public MemRepository() {
        posts.put(1, Post.of(1, "Продаю машину ладу 01."));
        posts.put(2, Post.of(2, "Продаю машину BMW X6."));
    }

    public List<Post> getAll() {
        return new ArrayList<>(posts.values());
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(counter.incrementAndGet());
            posts.put(post.getId(), post);
        } else {
            posts.computeIfPresent(post.getId(), (id, oldPost) -> post);
        }
    }

    public Post findById(int postId) {
        return posts.get(postId);
    }
}
