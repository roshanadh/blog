package np.com.roshanadhikary.blog.controller;

import np.com.roshanadhikary.blog.entity.*;
import np.com.roshanadhikary.blog.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/posts")
public class PostController {
	private final PostRepository postRepository;

	@Autowired
	public PostController(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@GetMapping("/")
	public String getAllPosts(Model model) {
		model.addAttribute("posts", postRepository.findAll());
		return "posts";
	}

	@GetMapping("/{id}")
	public String getPostById(@PathVariable long id, Model model) {
		System.out.println("REQUEST GET /posts/{id}");
		Optional<Post> postOptional = postRepository.findById(id);
		if (postOptional.isPresent()) {
			model.addAttribute("post", postOptional.get());
			System.out.println("TITLE IS: " + postOptional.get().getTitle());
		} else {
			model.addAttribute("error", "no-post");
		}
		return "post";


	}
}
