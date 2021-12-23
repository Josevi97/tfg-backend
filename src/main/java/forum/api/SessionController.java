package forum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forum.beans.LoginBean;
import forum.entities.AccountEntity;
import forum.exceptions.IlegalLoginArgumentsException;
import forum.exceptions.InvalidSessionException;
import forum.helpers.ApiResponse;
import forum.services.SessionService;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @GetMapping
    public ResponseEntity<?> check() {
        try {
            return new ResponseEntity<AccountEntity>(
                    sessionService.getUser(),
                    HttpStatus.OK);
        } catch (InvalidSessionException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginBean loginBean) {
        ApiResponse response;

        try {
            sessionService.connect(loginBean);
            response = new ApiResponse("successful connection", HttpStatus.OK);
        } catch (IlegalLoginArgumentsException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> logout() {
        ApiResponse response;

        try {
            this.sessionService.disconnect();
            response = new ApiResponse("successful disconnection", HttpStatus.OK);
        } catch (InvalidSessionException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }
}
