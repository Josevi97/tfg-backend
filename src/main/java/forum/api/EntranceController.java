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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forum.beans.CommentBean;
import forum.beans.EntranceBean;
import forum.entities.CommentEntity;
import forum.entities.EntranceEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommentNotFoundException;
import forum.exceptions.EntranceNotFoundException;
import forum.exceptions.IlegalCommentArguments;
import forum.exceptions.IlegalEntranceArgumentsException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.helpers.ApiResponse;
import forum.services.CommentService;
import forum.services.EntranceService;

@RestController
@RequestMapping("/entrances")
public class EntranceController {

    @Autowired
    EntranceService entranceService;

    @Autowired
    CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getEntrances(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return new ResponseEntity<Page<EntranceEntity>>(
                this.entranceService.getAllEntrances(pageable),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntrances(@PathVariable Long id) {
        try {
            return new ResponseEntity<EntranceEntity>(
                    this.entranceService.getEntrance(id),
                    HttpStatus.OK);
        } catch (EntranceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentsByEntranceId(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        try {
            return new ResponseEntity<Page<CommentEntity>>(
                    this.commentService.getCommentsByEntranceId(id, pageable), HttpStatus.OK);
        } catch (EntranceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(e.getApiResponse(), e.getApiResponse().getStatus());
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> createComment(@PathVariable Long id, @RequestBody CommentBean commentBean) {
        ApiResponse response;

        try {
            this.commentService.createComment(id, commentBean);
            response = new ApiResponse("comment has been created", HttpStatus.OK);
        } catch (IlegalCommentArguments | EntranceNotFoundException | CommentNotFoundException
                | InvalidSessionException | AccountNotFoundException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntrance(@PathVariable Long id, @RequestBody EntranceBean entranceBean) {
        ApiResponse response;

        try {
            this.entranceService.updateEntrance(id, entranceBean);
            response = new ApiResponse("entrance has been updated", HttpStatus.OK);
        } catch (IlegalEntranceArgumentsException | EntranceNotFoundException | InvalidSessionException
                | AccountNotFoundException
                | InsufficientPrivilegesException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntrance(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.entranceService.deleteEntrance(id);
            response = new ApiResponse("entrance has been deleted", HttpStatus.OK);
        } catch (EntranceNotFoundException | InvalidSessionException | AccountNotFoundException
                | InsufficientPrivilegesException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }
}
