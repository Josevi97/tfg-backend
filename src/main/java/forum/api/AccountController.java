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

import forum.beans.AccountBean;
import forum.beans.AccountUpdateBean;
import forum.beans.ResetPasswordBean;
import forum.entities.AccountEntity;
import forum.entities.AccountFollowEntity;
import forum.entities.CommentEntity;
import forum.entities.CommunityListEntity;
import forum.entities.EntranceEntity;
import forum.exceptions.AccountAlreadyExistsException;
import forum.exceptions.AccountFollowAlreadyExistsException;
import forum.exceptions.AccountFollowNotFoundException;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.ApiException;
import forum.exceptions.IlegalAccountArgumentsException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.exceptions.SelfAccountFollowAttemptException;
import forum.helpers.ApiResponse;
import forum.services.AccountFollowService;
import forum.services.AccountService;
import forum.services.CommentService;
import forum.services.CommentVoteService;
import forum.services.CommunityListService;
import forum.services.EntranceService;
import forum.services.EntranceVoteService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    CommunityListService communityListService;

    @Autowired
    EntranceService entranceService;

    @Autowired
    CommentService commentService;

    @Autowired
    AccountFollowService accountFollowService;

    @Autowired
    EntranceVoteService entranceVoteService;

    @Autowired
    CommentVoteService commentVoteService;

    @GetMapping
    public ResponseEntity<?> getAccounts(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return new ResponseEntity<Page<AccountEntity>>(
                this.accountService.getAllAccounts(pageable),
                HttpStatus.OK);
    }

    @GetMapping("/communities/entrances")
    public ResponseEntity<?> getEntrancesBySessionCommunities(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        try {
            return new ResponseEntity<Page<EntranceEntity>>(
                    this.entranceVoteService.checkVoteOfSession(
                            this.entranceService.getEntrancesByCommunities(
                                    this.communityListService.getCommunitiesBySession(), pageable)),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/following/entrances")
    public ResponseEntity<?> getEntrancesBySessionFollowTo(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        try {
            return new ResponseEntity<Page<EntranceEntity>>(
                    this.entranceVoteService.checkVoteOfSession(
                            this.entranceService.getEntrancesByAccounts(
                                    this.accountFollowService.getFollowingBySession(), pageable)),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        try {
            return new ResponseEntity<AccountEntity>(
                    this.accountFollowService.checkFollowOfSession(
                            this.accountService.getAccount(id)),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}/communities")
    public ResponseEntity<?> getCommunities(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {

        try {
            return new ResponseEntity<Page<CommunityListEntity>>(
                    this.communityListService.getCommunitiesByUserId(id, pageable),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}/entrances")
    public ResponseEntity<?> getEntrancesByAccountId(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {

        try {
            return new ResponseEntity<Page<EntranceEntity>>(
                    this.entranceVoteService.checkVoteOfSession(
                            this.entranceService.getEntrancesByAccountId(id, pageable)),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentsByAccountId(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {

        try {
            return new ResponseEntity<Page<CommentEntity>>(
                    this.commentVoteService.checkVoteOfSession(
                            this.commentService.getCommentsByAccountId(id, pageable)),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<?> getFollowingByAccountId(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {

        try {
            return new ResponseEntity<Page<AccountFollowEntity>>(
                    this.accountFollowService.checkFollowOfSessionToFollows(
                            this.accountFollowService.getFollowingByAccountId(id, pageable)),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getFollowersByAccountId(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {

        try {
            return new ResponseEntity<Page<AccountFollowEntity>>(
                    this.accountFollowService.checkFollowOfSessionToFollows(
                            this.accountFollowService.getFollowersByAccountId(id, pageable)),
                    HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountBean accountBean) {
        ApiResponse response;

        try {
            this.accountService.createAccount(accountBean);
            response = new ApiResponse("account has been created", HttpStatus.OK);
        } catch (ApiException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<?> createAccountFollow(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.accountFollowService.createFollow(id);
            response = new ApiResponse("follow has been created", HttpStatus.OK);
        } catch (ApiException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @DeleteMapping("/{id}/follow")
    public ResponseEntity<?> deleteAccountFollow(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.accountFollowService.deleteFollow(id);
            response = new ApiResponse("follow has been deleted", HttpStatus.OK);
        } catch (ApiException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateBean accountBean) {
        ApiResponse response;

        try {
            this.accountService.updateAccount(id, accountBean);
            response = new ApiResponse("account has been updated", HttpStatus.OK);
        } catch (ApiException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestBody ResetPasswordBean resetPasswordBean) {
        ApiResponse response;

        try {
            this.accountService.resetPassword(id, resetPasswordBean);
            response = new ApiResponse("account password has been reseted", HttpStatus.OK);
        } catch (ApiException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.accountService.deleteAccount(id);
            response = new ApiResponse("account has been deleted", HttpStatus.OK);
        } catch (ApiException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }
}
