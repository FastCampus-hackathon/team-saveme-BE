package com.saveme.comparator.service;

import com.saveme.comparator.config.security.CustomUserDetails;
import com.saveme.comparator.domain.Recruition;
import com.saveme.comparator.domain.User;
import com.saveme.comparator.domain.Wish;
import com.saveme.comparator.dto.JobDataDto;
import com.saveme.comparator.repository.RecruitionRepository;
import com.saveme.comparator.repository.UserRepository;
import com.saveme.comparator.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.saveme.comparator.domain.Recruition.createRecruition;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final WishRepository wishRepository;
    private final RecruitionRepository recruitionRepository;

    @Transactional
    public void addWishJob(Authentication auth, JobDataDto jobDataDto) {

        User user = getUserByAuth(auth);

        if (!isAlreadySavedRecruition(jobDataDto)) {
            recruitionRepository.save(createRecruition(jobDataDto));
        }

        if (isUserAlreadyWishedRecruition(jobDataDto, user)) {
            wishRepository.deleteByUserAndRecruition_RecruitmentId(user, recruitionTypeConverted(jobDataDto));
        } else {
            Wish savedWish = wishRepository.save(Wish.createWish(user, getRecruitionById(jobDataDto)));
            savedWish.addWishListOfUser(user);
            savedWish.addWishListOfRecruition(getRecruitionById(jobDataDto));
        }
    }


    private long recruitionTypeConverted(JobDataDto jobDataDto) {
        return Long.parseLong(jobDataDto.getRecruitmentId());
    }

    private Recruition getRecruitionById(JobDataDto jobDataDto) {
        return recruitionRepository.findById(
                        recruitionTypeConverted(jobDataDto))
                .orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    private boolean isAlreadySavedRecruition(JobDataDto jobDataDto) {
        return recruitionRepository.existsByRecruitmentId(recruitionTypeConverted(jobDataDto));
    }

    private User getUserByAuth(Authentication auth) {
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        User user = customUserDetails.getUser();
        return user;
    }

    private boolean isUserAlreadyWishedRecruition(JobDataDto jobDataDto, User user) {
        return wishRepository.existsByUserAndRecruition_RecruitmentId(user, recruitionTypeConverted(jobDataDto));
    }


    public User createUser(User user) {

        final String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

}
