package np.com.roshanadhikary.blog.bootstrap;

import np.com.roshanadhikary.blog.entity.*;
import np.com.roshanadhikary.blog.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PostRepository postRepository;

	public Author bootstrapAuthor() {
		Optional<Author> authorOptional = authorRepository.findById(1L);
		if (authorOptional.isPresent()) {
			return authorOptional.get();
		} else {
			Author roshanAuthor = new Author();
			roshanAuthor.setName("Roshan Adhikari");
			roshanAuthor.setEmail("nahsorad@gmail.com");
			roshanAuthor.setUrl("roshanadhikari.name.np");

			authorRepository.save(roshanAuthor);
			return roshanAuthor;
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		System.out.println("Bootstrapping posts...");

		Optional<Post> postOptional = postRepository.findById(1L);
		if (postOptional.isEmpty()) {
			Post helloWorldPost = new Post();
			String helloWorldPostContent = """
			Hello World! *This is my first post*!
			""";
			helloWorldPost.setTitle("Hello World!");
			helloWorldPost.setAuthor(bootstrapAuthor());
			helloWorldPost.setContent(helloWorldPostContent);
			helloWorldPost.setSynopsis(helloWorldPostContent);
			helloWorldPost.setDateTime(LocalDateTime.now());

			Post introPost = new Post();
			String introPostContent = """
			**This is my second post in this blog!**
			""";
			introPost.setTitle("Intro to my blog!");
			introPost.setAuthor(bootstrapAuthor());
			introPost.setContent(introPostContent);
			introPost.setSynopsis(introPostContent);
			introPost.setDateTime(LocalDateTime.now());

			postRepository.save(helloWorldPost);
			postRepository.save(introPost);
		}
	}
}
