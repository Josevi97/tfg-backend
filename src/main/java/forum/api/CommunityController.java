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

import forum.beans.CommunityBean;
import forum.beans.EntranceBean;
import forum.entities.CommunityEntity;
import forum.entities.EntranceEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommunityAlreadyExistsException;
import forum.exceptions.CommunityAlreadyFollowedException;
import forum.exceptions.CommunityNotFollowedException;
import forum.exceptions.CommunityNotFoundException;
import forum.exceptions.IlegalCommunityArgumentsException;
import forum.exceptions.IlegalEntranceArgumentsException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.helpers.ApiResponse;
import forum.services.CommunityListService;
import forum.services.CommunityService;
import forum.services.EntranceService;
import forum.services.EntranceVoteService;

@RestController
@RequestMapping("/communities")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @Autowired
    CommunityListService communityListService;

    @Autowired
    EntranceService entranceService;

    @Autowired
    EntranceVoteService entranceVoteService;

    @GetMapping
    public ResponseEntity<?> getCommunities(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return new ResponseEntity<Page<CommunityEntity>>(
                this.communityService.getAllCommunities(pageable),
                HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> getCommunitiesByName(@PathVariable String name,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return new ResponseEntity<Page<CommunityEntity>>(
                this.communityService.getCommunitiesLikeName(name, pageable),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommunity(@PathVariable Long id) {
        try {
            return new ResponseEntity<CommunityEntity>(
                    this.communityService.getCommunity(id),
                    HttpStatus.OK);
        } catch (CommunityNotFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}/entrances")
    public ResponseEntity<?> getEntrancesByCommunityId(@PathVariable Long id,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        try {
            return new ResponseEntity<Page<EntranceEntity>>(
                    this.entranceVoteService.checkVoteOfSession(
                            this.entranceService.getEntrancesByCommunityId(id, pageable)),
                    HttpStatus.OK);
        } catch (CommunityNotFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCommunity(@RequestBody CommunityBean communityBean) {
        ApiResponse response;

        try {
            this.communityService.createCommunity(communityBean);
            response = new ApiResponse("community has been created", HttpStatus.OK);
        } catch (IlegalCommunityArgumentsException | InvalidSessionException | AccountNotFoundException
                | InsufficientPrivilegesException | CommunityAlreadyExistsException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<?> createFollow(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.communityListService.createFollow(id);
            response = new ApiResponse("community follow has been created", HttpStatus.OK);
        } catch (InvalidSessionException | AccountNotFoundException
                | CommunityNotFoundException | CommunityAlreadyFollowedException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PostMapping("/{id}/entrances")
    public ResponseEntity<?> createEntrance(@PathVariable Long id, @RequestBody EntranceBean entranceBean) {
        ApiResponse response;

        try {
            this.entranceService.createEntrance(id, entranceBean);
            response = new ApiResponse("entrance has been created", HttpStatus.OK);
        } catch (IlegalEntranceArgumentsException | InvalidSessionException | AccountNotFoundException
                | CommunityNotFoundException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommunity(@PathVariable Long id, @RequestBody CommunityBean communityBean) {
        ApiResponse response;

        try {
            this.communityService.updateCommunity(id, communityBean);
            response = new ApiResponse("community has been updated", HttpStatus.OK);
        } catch (IlegalCommunityArgumentsException | InvalidSessionException | AccountNotFoundException
                | InsufficientPrivilegesException | CommunityNotFoundException | CommunityAlreadyExistsException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommunity(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.communityService.deleteAccount(id);
            response = new ApiResponse("community has been deleted", HttpStatus.OK);
        } catch (InvalidSessionException | InsufficientPrivilegesException | AccountNotFoundException
                | CommunityNotFoundException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @DeleteMapping("/{id}/follow")
    public ResponseEntity<?> deleteFollow(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.communityListService.deleteFollow(id);
            response = new ApiResponse("community follow has been deleted", HttpStatus.OK);
        } catch (InvalidSessionException | AccountNotFoundException
                | CommunityNotFoundException | CommunityNotFollowedException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }
}
