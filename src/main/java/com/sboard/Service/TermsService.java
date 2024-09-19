package com.sboard.Service;

import com.sboard.dto.TermsDTO;
import com.sboard.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermsService {

    private final TermsRepository termsRepository;

    public List<TermsDTO> selectTerms() {
        return termsRepository.findAll().stream().map(entity -> entity.toDTO()).collect(Collectors.toList());
    }

}
