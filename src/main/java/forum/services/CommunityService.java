package forum.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import forum.beans.CommunityBean;
import forum.entities.CommunityEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommunityAlreadyExistsException;
import forum.exceptions.CommunityNotFoundException;
import forum.exceptions.IlegalCommunityArgumentsException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.CommunityRepository;

@Service
public class CommunityService {

    @Autowired
    SessionService sessionService;

    @Autowired
    CommunityRepository communityRepository;

    public Page<CommunityEntity> getAllCommunities(Pageable pageable) {
        return this.communityRepository.findAll(pageable);
    }

    public CommunityEntity getCommunity(Long id) throws CommunityNotFoundException {
        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        return this.communityRepository.findById(id).get();
    }

    public void createCommunity(CommunityBean communityBean)
            throws IlegalCommunityArgumentsException, InvalidSessionException, AccountNotFoundException,
            InsufficientPrivilegesException,
            CommunityAlreadyExistsException {
        if (communityBean == null || !communityBean.isValid()) {
            throw new IlegalCommunityArgumentsException();
        }

        if (!this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        if (this.communityRepository.existsByName(communityBean.getName())) {
            throw new CommunityAlreadyExistsException();
        }

        CommunityEntity communityEntity = communityBean.toEntity();
        communityEntity.setImage("/assets/community/images/default_000.png");
        communityEntity.setCreatedAt(LocalDateTime.now());

        this.communityRepository.save(communityEntity);
    }

    public void updateCommunity(Long id, CommunityBean communityBean)
            throws IlegalCommunityArgumentsException, InvalidSessionException, AccountNotFoundException,
            InsufficientPrivilegesException, CommunityNotFoundException, CommunityAlreadyExistsException {
        if (communityBean == null || !communityBean.isValid()) {
            throw new IlegalCommunityArgumentsException();
        }

        if (!this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        if (this.communityRepository.existsByName(communityBean.getName())) {
            throw new CommunityAlreadyExistsException();
        }

        CommunityEntity communityEntity = this.communityRepository.findById(id).get();
        communityEntity.setName(communityBean.getName());
        communityEntity.setDescription(communityBean.getDescription());
        communityEntity.setColor(communityBean.getColor());
        // Should update image too

        this.communityRepository.save(communityEntity);
    }

    public void deleteAccount(Long id)
            throws InvalidSessionException, AccountNotFoundException, CommunityNotFoundException,
            InsufficientPrivilegesException {
        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        if (!this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        this.communityRepository.deleteById(id);
    }
}
