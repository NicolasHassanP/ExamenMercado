package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository dnaRecordRepository;
    private final MutantDetector mutantDetector;

    @Transactional
    public boolean isMutant(String[] dna) {
        String dnaHash = calculateSha256(dna);

        Optional<DnaRecord> cachedRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        if (cachedRecord.isPresent()) {
            return cachedRecord.get().isMutant();
        }

        boolean isMutant = mutantDetector.isMutant(dna);

        DnaRecord newRecord = DnaRecord.builder()
                .dnaHash(dnaHash)
                .isMutant(isMutant)
                .build();
        dnaRecordRepository.save(newRecord);

        return isMutant;
    }

    private String calculateSha256(String[] dna) {
        String concatenatedDna = Arrays.stream(dna).collect(Collectors.joining());
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(concatenatedDna.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("SHA-256 algorithm not available", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}