package com.labourlink.service;

import com.labourlink.model.*;
import com.labourlink.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteWorkerRepository favoriteRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteWorkerRepository favoriteRepository,
                           UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    public FavoriteWorker addFavorite(Long contractorId, Long workerId) {
        if (favoriteRepository.existsByContractorIdAndWorkerId(contractorId, workerId)) {
            throw new RuntimeException("Worker is already in favorites");
        }

        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        FavoriteWorker fav = new FavoriteWorker();
        fav.setContractorId(contractorId);
        fav.setWorkerId(workerId);
        fav.setWorkerName(worker.getName());
        fav.setWorkerSkill(worker.getPrimarySkill());
        fav.setWorkerRating(worker.getAverageRating());

        return favoriteRepository.save(fav);
    }

    public void removeFavorite(Long contractorId, Long workerId) {
        FavoriteWorker fav = favoriteRepository.findByContractorIdAndWorkerId(contractorId, workerId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(fav);
    }

    public List<FavoriteWorker> getFavorites(Long contractorId) {
        return favoriteRepository.findByContractorId(contractorId);
    }

    public boolean isFavorite(Long contractorId, Long workerId) {
        return favoriteRepository.existsByContractorIdAndWorkerId(contractorId, workerId);
    }
}
