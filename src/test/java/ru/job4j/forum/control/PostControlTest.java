package ru.job4j.forum.control;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    public void shouldReturnEditViewWhenCreate() throws Exception {
        this.mockMvc.perform(get("/addNewPost"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditViewWhenUpdate() throws Exception {
        this.mockMvc.perform(get("/updatePost")
                        .param("postId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser
    public void shouldReturnPostView() throws Exception {
        this.mockMvc.perform(get("/postInfo")
                        .param("postId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"));
    }

    @Test
    @WithMockUser
    public void whenSavePostAndReturnOk() throws Exception {
        this.mockMvc.perform(post("/savePost")
                        .param("name", "Куплю ладу-гранту. Дорого.")
                        .param("description", "ОПисание машины"))
                .andDo(print())
                .andExpect(status().isFound());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(postService).save(argument.capture());
        MatcherAssert.assertThat(argument.getValue().getName(), is("Куплю ладу-гранту. Дорого."));
    }

    @Test
    @WithMockUser
    public void whenSaveAndThenUpdatePost() throws Exception {
        this.mockMvc.perform(post("/savePost")
                        .param("id", "1")
                        .param("name", "Куплю ноутбук")
                        .param("description", "Чтобы работал"))
                .andDo(print())
                .andExpect(status().isFound());
        this.mockMvc.perform(post("/savePost")
                        .param("id", "1")
                        .param("name", "Куплю ноутбук")
                        .param("description", "В хорошем состоянии"))
                .andDo(print())
                .andExpect(status().isFound());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(postService, times(2)).save(argument.capture());
        MatcherAssert.assertThat(argument.getValue().getId(), is(1));
        MatcherAssert.assertThat(argument.getValue().getDescription(), is("В хорошем состоянии"));
    }
}