package forum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forum.services.EntranceService;

@RestController
@RequestMapping("/entrances")
public class EntranceController {

    @Autowired
    EntranceService entranceService;

    @GetMapping
    public ResponseEntity<?> test() {
        return new ResponseEntity<Long>(1L, HttpStatus.OK);
    }
}
