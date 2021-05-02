package np.com.roshanadhikary.blog.controller;

import np.com.roshanadhikary.blog.entity.*;
import np.com.roshanadhikary.blog.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/posts")
public class PostController {
	private final PostRepository postRepository;
	private final int PAGINATIONSIZE = 2;

	@Autowired
	public PostController(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@GetMapping("")
	public String getAllPosts() {
		return "redirect:/posts?page=0&size=" + PAGINATIONSIZE;
	}

	@GetMapping(params = {"page", "size"})
	public String getPaginatedPosts(@RequestParam int page, @RequestParam int size, Model model) {
		Pageable pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		Page<Post> postsPage = postRepository.findAll(pageRequest);
		List<Post> posts = postsPage.toList();

		long postCount = postRepository.count();
		int numOfPages = (int) Math.ceil((postCount * 1.0) / PAGINATIONSIZE);

		model.addAttribute("posts", posts);
		model.addAttribute("postCount", postCount);
		model.addAttribute("pageRequested", page);
		model.addAttribute("paginationSize", PAGINATIONSIZE);
		model.addAttribute("numOfPages", numOfPages);
		return "posts";
	}

	@GetMapping("/{id}")
	public String getPostById(@PathVariable long id, Model model) {
		Optional<Post> postOptional = postRepository.findById(id);

		if (postOptional.isPresent()) {
			model.addAttribute("post", postOptional.get());
		} else {
			model.addAttribute("error", "no-post");
		}
		return "post";
	}
}
