package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemRepository {
    private final List<Post> posts = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger();

    public MemRepository() {
        Post post = Post.of("Продаю машину ладу 01.");
        post.setId(counter.incrementAndGet());
        Post post2 = Post.of("Продаю машину BMW X6.");
        post2.setId(counter.incrementAndGet());
        posts.add(post);
        posts.add(post2);
    }

    public List<Post> getAll() {
        return posts;
    }

    public void save(Post post) {
        if (!posts.contains(post)) {
            post.setId(counter.incrementAndGet());
            posts.add(post);
        } else {
            posts.set(post.getId() - 1, post);
        }
    }

    public Post findById(int postId) {
        return posts.get(postId - 1);
    }
}
