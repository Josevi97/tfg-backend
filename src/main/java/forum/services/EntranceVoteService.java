package forum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import forum.combinedIds.EntranceVoteId;
import forum.entities.EntranceVoteEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.EntranceNotFoundException;
import forum.exceptions.EntranceVoteAlreadyExistsException;
import forum.exceptions.EntranceVoteNotFoundException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.EntranceRepository;
import forum.repositories.EntranceVoteRepository;

@Service
public class EntranceVoteService {

    @Autowired
    SessionService sessionService;

    @Autowired
    private EntranceVoteRepository entranceVoteRepository;

    @Autowired
    private EntranceRepository entranceRepository;

    public void createVote(Long id, boolean vote)
            throws EntranceNotFoundException, AccountNotFoundException, InvalidSessionException,
            EntranceVoteAlreadyExistsException {
        if (!this.entranceRepository.existsById(id)) {
            throw new EntranceNotFoundException();
        }

        EntranceVoteId entranceVoteId = new EntranceVoteId(
                this.sessionService.getUser(),
                this.entranceRepository.findById(id).get());

        if (this.entranceVoteRepository.existsById(entranceVoteId)) {
            throw new EntranceVoteAlreadyExistsException();
        }

        EntranceVoteEntity entranceVoteEntity = new EntranceVoteEntity();
        entranceVoteEntity.setEntranceVoteId(entranceVoteId);
        entranceVoteEntity.setVote(vote);

        this.entranceVoteRepository.save(entranceVoteEntity);
    }

    public void deleteVote(Long id)
            throws EntranceNotFoundException, AccountNotFoundException, InvalidSessionException,
            EntranceVoteNotFoundException {
        if (!this.entranceRepository.existsById(id)) {
            throw new EntranceNotFoundException();
        }

        EntranceVoteId entranceVoteId = new EntranceVoteId(
                this.sessionService.getUser(),
                this.entranceRepository.findById(id).get());

        if (!this.entranceVoteRepository.existsById(entranceVoteId)) {
            throw new EntranceVoteNotFoundException();
        }

        this.entranceVoteRepository.deleteById(entranceVoteId);
    }
}
