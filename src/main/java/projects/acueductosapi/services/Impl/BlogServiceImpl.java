package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import projects.acueductosapi.entities.BlogPost;
import projects.acueductosapi.repository.BlogPostRepository;
import projects.acueductosapi.response.BlogResponseRest;
import projects.acueductosapi.services.BlogService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    private static final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<BlogResponseRest> buscarBlogs() {

        BlogResponseRest response = new BlogResponseRest();

        try {
            List<BlogPost> blogs = blogPostRepository.findAll();

            // Convert image bytes to Base64 for each blog post
            for (BlogPost blogPost : blogs) {
                byte[] imageBytes = blogPost.getImage();
                String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                blogPost.setImageBase64(imageBase64);
            }

            response.getBlogResponse().setBlogPosts(blogs);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar Blogs");
            log.error("error al consultar blogs: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<BlogResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<BlogResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<BlogResponseRest> buscarBlogsPorId(Integer id) {
        BlogResponseRest response = new BlogResponseRest();
        List<BlogPost> list = new ArrayList<>();

        try {
            Optional<BlogPost> blogPost = blogPostRepository.findById(id);

            if (blogPost.isPresent()) {
                // Convert image bytes to Base64
                byte[] imageBytes = blogPost.get().getImage();
                String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                blogPost.get().setImageBase64(imageBase64);

                list.add(blogPost.get());
                response.getBlogResponse().setBlogPosts(list);
            } else {
                log.error("Error en consultar BlogPost");
                response.setMetadata("Respuesta nok", "-1", "BlogPost no encontrado");
                return new ResponseEntity<BlogResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("Error en consultar BlogPost");
            response.setMetadata("Respuesta nok", "-1", "Error al consultar BlogPost");
            return new ResponseEntity<BlogResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        return new ResponseEntity<BlogResponseRest>(response, HttpStatus.OK); //devuelve 200
    }





    @Override
    @Transactional
    public ResponseEntity<BlogResponseRest> crear(BlogPost blogPost, MultipartFile imageFile) {
        log.info("Inicio metodo crear BlogPost");

        BlogResponseRest response = new BlogResponseRest();
        List<BlogPost> list = new ArrayList<>();

        try {

            if (imageFile != null && !imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                blogPost.setImage(imageBytes);
            }
            BlogPost blogPostGuardado = blogPostRepository.save(blogPost);

            if(blogPostGuardado != null) {
                list.add(blogPostGuardado);
                response.getBlogResponse().setBlogPosts(list);
            } else {
                log.error("Error en grabar BlogPost");
                response.setMetadata("Respuesta nok", "-1", "BlogPost no guardado");
                return new ResponseEntity<BlogResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch( Exception e) {
            log.error("Error en grabar BlogPost ");
            response.setMetadata("Respuesta nok", "-1", "Error al grabar BlogPost");
            return new ResponseEntity<BlogResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetadata("Respuesta ok", "00", "BlogPost creado");
        return new ResponseEntity<BlogResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
}
