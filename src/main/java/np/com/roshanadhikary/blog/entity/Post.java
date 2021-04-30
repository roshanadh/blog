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
	@Column
	private String content;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
	@Column
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dateTime;
}
