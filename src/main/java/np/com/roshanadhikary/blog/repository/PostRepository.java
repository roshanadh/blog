package np.com.roshanadhikary.blog.repository;

import np.com.roshanadhikary.blog.entity.*;
import org.springframework.data.repository.*;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
