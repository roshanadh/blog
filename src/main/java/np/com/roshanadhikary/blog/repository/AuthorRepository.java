package np.com.roshanadhikary.blog.repository;

import np.com.roshanadhikary.blog.entity.*;
import org.springframework.data.repository.*;


public interface AuthorRepository extends CrudRepository<Author, Long> {
}
