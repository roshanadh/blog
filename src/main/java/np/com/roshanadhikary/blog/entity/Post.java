package np.com.roshanadhikary.blog.entity;

import lombok.*;
import np.com.roshanadhikary.blog.util.*;
import org.jsoup.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Data
@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private long id;

	@Column
	private String title;

	@Column(columnDefinition = "TEXT", length = 50000)
	private String content;

	@Column(length = 150)
	private String synopsis;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@Column
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dateTime;

	public void setContent(List<String> markdownLines) {
		MdToHtmlRenderer renderer = new MdToHtmlRenderer();

		this.content = renderer.render(markdownLines);
	}

	public void setSynopsis(List<String> markdownLines) {
		MdToHtmlRenderer renderer = new MdToHtmlRenderer();
		String renderedHtml = renderer.render(markdownLines);
		String content = Jsoup.parse(renderedHtml).text();

		this.synopsis = content.length() <= 150 ? content : content.substring(0, 149);
	}
}
