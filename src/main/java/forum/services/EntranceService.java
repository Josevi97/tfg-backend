package forum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import forum.repositories.EntranceRepository;

@Service
public class EntranceService {

    @Autowired
    EntranceRepository entranceRepository;
}
