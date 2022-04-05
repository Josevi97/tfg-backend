package forum.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import forum.beans.CommunityBean;
import forum.constants.FileConstants;
import forum.entities.CommunityEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommunityAlreadyExistsException;
import forum.exceptions.CommunityNotFoundException;
import forum.exceptions.IlegalCommunityArgumentsException;
import forum.exceptions.IlegalFileExtensionException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.CommunityRepository;

@Service
public class CommunityService {

    @Autowired
    SessionService sessionService;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    FileService fileService;

    public Page<CommunityEntity> getAllCommunities(Pageable pageable, String filter) {
        if (filter == null) {
            return this.communityRepository.findAll(pageable);
        }

        return this.communityRepository.filtered(filter, pageable);
    }

    public Page<CommunityEntity> getRandomCommunities(List<Long> blackList, Pageable pageable) {
        if (blackList == null) {
            blackList = new ArrayList<Long>();
            blackList.add(0L);
        }

        return this.communityRepository.random(blackList, pageable);
    }

    public Page<CommunityEntity> getCommunitiesLikeName(String title, Pageable pageable) {
        return this.communityRepository.findByNameContaining(title, pageable);
    }

    public CommunityEntity getCommunity(Long id) throws CommunityNotFoundException {
        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        return this.communityRepository.findById(id).get();
    }

    public void createCommunity(CommunityBean communityBean)
            throws IlegalCommunityArgumentsException, InvalidSessionException, AccountNotFoundException,
            InsufficientPrivilegesException, CommunityAlreadyExistsException {
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
        communityEntity.setImage(null);
        communityEntity.setCreatedAt(LocalDateTime.now());

        this.communityRepository.save(communityEntity);
    }

    public void updateCommunity(Long id, CommunityBean communityBean, MultipartFile file)
            throws IlegalCommunityArgumentsException, InvalidSessionException, AccountNotFoundException,
            InsufficientPrivilegesException, CommunityNotFoundException, CommunityAlreadyExistsException,
            IlegalFileExtensionException {
        if (communityBean == null || !communityBean.isValid()) {
            throw new IlegalCommunityArgumentsException();
        }

        if (!this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        if (this.communityRepository.existsByName(communityBean.getName())
                && this.communityRepository.findByName(communityBean.getName()).getId() != id) {
            throw new CommunityAlreadyExistsException();
        }

        CommunityEntity communityEntity = this.communityRepository.findById(id).get();

        String newImage = file != null ? this.fileService.generateName(id, FileConstants.COMMUNITY_FILE_ID) : null;
        String oldImage = communityEntity.getImage();

        communityEntity.setName(communityBean.getName());
        communityEntity.setDescription(communityBean.getDescription());
        communityEntity.setImage(newImage);

        CommunityEntity response = this.communityRepository.save(communityEntity);

        this.fileService.toImage(file, newImage, communityBean.getChangeImage());
        this.fileService.removeFile(oldImage);
    }

    public void deleteAccount(Long id) throws InvalidSessionException, AccountNotFoundException,
            CommunityNotFoundException, InsufficientPrivilegesException {
        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        if (!this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        String imageName = this.communityRepository.findById(id).get().getImage();

        this.communityRepository.deleteById(id);
        this.fileService.removeFile(imageName);
    }
}
