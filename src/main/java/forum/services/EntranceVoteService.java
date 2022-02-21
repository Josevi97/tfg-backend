package forum.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import forum.combinedIds.EntranceVoteId;
import forum.entities.EntranceEntity;
import forum.entities.EntranceVoteEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.EntranceNotFoundException;
import forum.exceptions.EntranceVoteAlreadyExistsException;
import forum.exceptions.EntranceVoteNotFoundException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;
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

    @Transactional
    public void createVote(Long id, boolean vote)
            throws EntranceNotFoundException, AccountNotFoundException, InvalidSessionException,
            EntranceVoteAlreadyExistsException {

        EntranceVoteId entranceVoteId = new EntranceVoteId(
                this.sessionService.getUser(),
                this.entranceRepository.findById(id).orElseThrow(() -> new EntranceNotFoundException()));

        if (this.entranceVoteRepository.existsById(entranceVoteId)) {
            EntranceVoteEntity entranceVoteEntity = this.entranceVoteRepository.findById(entranceVoteId).get();

            if (entranceVoteEntity.getVote() == vote) {
                this.entranceVoteRepository.deleteById(entranceVoteId);
            } else {
                entranceVoteEntity.setVote(vote);
                this.entranceVoteRepository.save(entranceVoteEntity);
            }
        } else {
            EntranceVoteEntity entranceVoteEntity = new EntranceVoteEntity();
            entranceVoteEntity.setEntranceVoteId(entranceVoteId);
            entranceVoteEntity.setVote(vote);

            this.entranceVoteRepository.save(entranceVoteEntity);
        }
    }

    public EntranceEntity checkVoteOfSession(EntranceEntity entranceEntity) {
        int value = -1;

        if (this.entranceRepository.existsById(entranceEntity.getId())) {
            try {
                EntranceVoteId entranceVoteId = new EntranceVoteId(
                        this.sessionService.getUser(),
                        entranceEntity);

                if (this.entranceVoteRepository.existsById(entranceVoteId)) {
                    value = this.entranceVoteRepository.findById(entranceVoteId).get().getVote() ? 1 : 0;
                }
            } catch (InvalidSessionException | AccountNotFoundException e) {
            }
        }
        entranceEntity.setSessionVoted(value);
        return entranceEntity;
    }

    public Page<EntranceEntity> checkVoteOfSession(Page<EntranceEntity> entrances) {
        entrances.forEach(entrance -> this.checkVoteOfSession(entrance));
        return entrances;
    }
}
