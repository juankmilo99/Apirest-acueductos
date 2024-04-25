package projects.acueductosapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projects.acueductosapi.entities.BlogPost;
import projects.acueductosapi.response.BlogResponseRest;
import projects.acueductosapi.services.BlogService;
import projects.acueductosapi.services.ProductoService;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    public BlogService blogService;

    @GetMapping("")
    public ResponseEntity<BlogResponseRest> consultarBlogs() {
        ResponseEntity<BlogResponseRest> response = blogService.buscarBlogs();
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseRest> consultarBlogPorId(@PathVariable Integer id) {
        ResponseEntity<BlogResponseRest> response = blogService.buscarBlogsPorId(id);
        return response;
    }

    @PostMapping("")
    public ResponseEntity<BlogResponseRest> crearBlogPost(@RequestPart("blogPost") String blogPostStr, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BlogPost blogPost = objectMapper.readValue(blogPostStr, BlogPost.class);
        ResponseEntity<BlogResponseRest> response = blogService.crear(blogPost, imageFile);
        return response;
    }



}
