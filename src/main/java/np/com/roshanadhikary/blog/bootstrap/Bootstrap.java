package np.com.roshanadhikary.blog.bootstrap;

import np.com.roshanadhikary.blog.entity.*;
import np.com.roshanadhikary.blog.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private PostRepository postRepository;

	@Value("classpath:posts/*")
	private Resource[] postFiles;

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

	public List<String> readMarkdownFileLines(String filename) {
		try {
			InputStream iStream = new ClassPathResource("/posts/" + filename)
					.getInputStream();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
			List<String> allLines = new ArrayList<>();
			String line = bReader.readLine();
			while (line != null) {
				allLines.add(line);
				line = bReader.readLine();
			}
			return allLines;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getTitleFromFileName(String filename) {
		String fileNameBeforeExtension = filename.split(".md")[0];
		String[] tokens = fileNameBeforeExtension.split("_");

		String[] titleTokens = Arrays.copyOfRange(tokens, 1, tokens.length);
		return String.join(" ", titleTokens);
	}

	public long getIdFromFileName(String filename) {
		String fileNameBeforeExtension = filename.split(".md")[0];
		return Long.parseLong(fileNameBeforeExtension.split("_")[0]);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		System.out.println("Bootstrapping posts...");

		Arrays.stream(postFiles).forEach(postFile -> {
			Optional<String> postFileNameOptional = Optional.ofNullable(postFile.getFilename());
			Post post = new Post();

			if (postFileNameOptional.isPresent()) {
				String postFileName = postFileNameOptional.get();
				String title = getTitleFromFileName(postFileName);
				List<String> lines = readMarkdownFileLines(postFileName);
				Author author = bootstrapAuthor();

				long id = getIdFromFileName(postFileName);

				Optional<Post> postOptional = postRepository.findById(id);
				if (postOptional.isEmpty()) {
					post.setTitle(title);
					post.setAuthor(author);
					post.setContent(lines);
					post.setSynopsis(lines);
					post.setDateTime(LocalDateTime.now());

					postRepository.save(post);
				}
			} else {
				System.out.println("postFileName is null, should not be null");
			}
		});
	}
}
