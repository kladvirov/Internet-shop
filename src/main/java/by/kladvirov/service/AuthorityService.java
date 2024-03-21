package by.kladvirov.service;

import by.kladvirov.dto.AuthorityCreationDto;
import by.kladvirov.dto.AuthorityDto;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.AuthorityMapper;
import by.kladvirov.model.Authority;
import by.kladvirov.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    @Transactional(readOnly = true)
    public AuthorityDto findById(Long id) {
        Authority authority = authorityRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such authority", HttpStatus.NOT_FOUND));
        return authorityMapper.toDto(authority);
    }

    @Transactional(readOnly = true)
    public List<AuthorityDto> findAll(Pageable pageable) {
        try {
            return authorityMapper.toDto(authorityRepository.findAll(pageable).toList());
        } catch (Exception ex) {
            throw new ServiceException("Error during finding all authorities", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public AuthorityDto save(AuthorityCreationDto authorityCreationDto) {
        try {
            Authority entity = authorityMapper.toEntity(authorityCreationDto);
            return authorityMapper.toDto(authorityRepository.save(entity));
        } catch (Exception ex) {
            throw new ServiceException("Error during saving authority", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(Long id, AuthorityCreationDto authorityCreationDto) {
        try {
            Authority authority = authorityRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such authority", HttpStatus.NOT_FOUND));
            Authority mappedAuthority = authorityMapper.toEntity(authorityCreationDto);
            updateAuthority(authority, mappedAuthority);
            authorityRepository.save(authority);
        } catch (Exception ex) {
            throw new ServiceException("Error during updating authority", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            authorityRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ServiceException("Error during deleting authority", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateAuthority(Authority authority, Authority mappedAuthority) {
        authority.setName(mappedAuthority.getName());
        authority.setRoles(mappedAuthority.getRoles());
    }

}
