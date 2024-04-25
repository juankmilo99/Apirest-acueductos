package projects.acueductosapi.response;

public class BlogResponseRest extends ResponseRest{

    private BlogResponse blogResponse = new BlogResponse();

    public BlogResponse getBlogResponse() {
        return blogResponse;
    }

    public void setBlogResponse(BlogResponse blogResponse) {
        this.blogResponse = blogResponse;
    }
}
