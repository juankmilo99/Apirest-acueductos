package projects.acueductosapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import projects.acueductosapi.entities.BlogPost;
import projects.acueductosapi.response.BlogResponseRest;

public interface BlogService {

    public ResponseEntity<BlogResponseRest> buscarBlogs();



    public ResponseEntity<BlogResponseRest> buscarBlogsPorId(Integer id);

    public ResponseEntity<BlogResponseRest> crear(BlogPost blogPost, MultipartFile imageFile);
}
