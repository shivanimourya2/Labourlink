package com.labourlink.service;

import com.labourlink.model.*;
import com.labourlink.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BroadcastService {

    private static final Logger log = LoggerFactory.getLogger(BroadcastService.class);

    private final UserRepository userRepository;
    private final WorkHistoryRepository workHistoryRepository;
    private final NotificationService notificationService;

    public BroadcastService(UserRepository userRepository,
                            WorkHistoryRepository workHistoryRepository,
                            NotificationService notificationService) {
        this.userRepository = userRepository;
        this.workHistoryRepository = workHistoryRepository;
        this.notificationService = notificationService;
    }

    /**
     * Broadcasts an urgent job to all relevant workers.
     * Prioritizes workers who have previously worked with this contractor.
     */
    public void broadcastUrgentJob(Job job) {
        log.info("🔔 BROADCASTING URGENT JOB: {} at {} for {}/day",
                job.getTitle(), job.getLocation(), job.getDailyRate());

        // Find all workers with matching skill
        List<User> matchingWorkers = userRepository.findByRoleAndPrimarySkill(
                Role.WORKER, job.getSkillRequired());

        // Also find workers in same city with matching skill
        List<User> cityWorkers = userRepository.findByRoleAndCityAndPrimarySkill(
                Role.WORKER, job.getLocation(), job.getSkillRequired());

        // Merge and deduplicate (city workers first = priority)
        Set<Long> seen = new HashSet<>();
        List<User> prioritized = new ArrayList<>();

        // Get workers who have previously worked with this contractor
        List<WorkHistory> pastWork = workHistoryRepository.findByContractorId(job.getContractorId());
        Set<Long> previousWorkers = pastWork.stream()
                .map(WorkHistory::getWorkerId)
                .collect(Collectors.toSet());

        // Priority 1: Previous workers in same city with matching skill
        for (User w : cityWorkers) {
            if (previousWorkers.contains(w.getId()) && seen.add(w.getId())) {
                prioritized.add(w);
            }
        }

        // Priority 2: Workers in same city with matching skill
        for (User w : cityWorkers) {
            if (seen.add(w.getId())) {
                prioritized.add(w);
            }
        }

        // Priority 3: Workers with matching skill (any city)
        for (User w : matchingWorkers) {
            if (seen.add(w.getId())) {
                prioritized.add(w);
            }
        }

        String skillName = job.getSkillRequired().getDisplayName();
        String message = String.format("🔥 Urgent: %s needed at %s for ₹%.0f/day. %s - Apply now!",
                skillName, job.getLocation(), job.getDailyRate(), job.getTitle());

        for (User worker : prioritized) {
            // Create DB notification
            notificationService.create(worker.getId(), message, "URGENT_JOB", job.getId());

            // Simulate SMS
            log.info("[SMS to +91{}]: {}", worker.getPhone(), message);
        }

        log.info("📤 Broadcast sent to {} workers ({} prioritized from past work)",
                prioritized.size(),
                prioritized.stream().filter(w -> previousWorkers.contains(w.getId())).count());
    }
}
