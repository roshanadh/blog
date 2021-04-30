package np.com.roshanadhikary.blog.entity;

import lombok.*;
import np.com.roshanadhikary.blog.util.*;

import javax.persistence.*;
import java.time.*;

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

	@Column(length = 5000)
	private String content;

	@Column(length = 150)
	private String synopsis;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@Column
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dateTime;

	public void setContent(String markdownContent) {
		MdToHtmlRenderer renderer = new MdToHtmlRenderer();

		this.content = renderer.render(markdownContent);
	}

	public void setSynopsis(String markdownContent) {
		MdToHtmlRenderer renderer = new MdToHtmlRenderer();
		String content = renderer.render(markdownContent);

		this.synopsis = content.length() <= 150 ? content : content.substring(0, 149);
	}
}
