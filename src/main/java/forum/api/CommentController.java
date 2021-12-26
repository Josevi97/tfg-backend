package forum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forum.entities.CommentEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommentNotFoundException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.helpers.ApiResponse;
import forum.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/{id}/responses")
    public ResponseEntity<?> getCommentsByCommentId(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        try {
            return new ResponseEntity<Page<CommentEntity>>(
                    this.commentService.getCommentsByCommentId(id, pageable),
                    HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        try {
            return new ResponseEntity<CommentEntity>(
                    this.commentService.getComment(id),
                    HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.commentService.deleteComment(id);
            response = new ApiResponse("comment has been deleted", HttpStatus.OK);
        } catch (CommentNotFoundException | InvalidSessionException | AccountNotFoundException
                | InsufficientPrivilegesException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }
}
