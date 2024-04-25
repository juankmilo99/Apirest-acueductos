package projects.acueductosapi.response;

import projects.acueductosapi.entities.BlogPost;

import java.util.List;

public class BlogResponse {

    private List<BlogPost> blogPosts;

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }


}
