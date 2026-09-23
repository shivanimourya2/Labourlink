package com.labourlink.controller;

import com.labourlink.dto.ApiResponse;
import com.labourlink.model.FavoriteWorker;
import com.labourlink.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addFavorite(
            @RequestParam Long contractorId,
            @RequestParam Long workerId) {
        try {
            FavoriteWorker fav = favoriteService.addFavorite(contractorId, workerId);
            return ResponseEntity.ok(ApiResponse.ok("Worker added to favorites", fav));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> removeFavorite(
            @RequestParam Long contractorId,
            @RequestParam Long workerId) {
        try {
            favoriteService.removeFavorite(contractorId, workerId);
            return ResponseEntity.ok(ApiResponse.ok("Worker removed from favorites"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{contractorId}")
    public ResponseEntity<ApiResponse> getFavorites(@PathVariable Long contractorId) {
        try {
            List<FavoriteWorker> favorites = favoriteService.getFavorites(contractorId);
            return ResponseEntity.ok(ApiResponse.ok("Favorites found", favorites));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
