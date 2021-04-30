package np.com.roshanadhikary.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.time.*;

@Data
@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String title;
	private String content;
	private Author author;
	private LocalDateTime dateTime;
}
