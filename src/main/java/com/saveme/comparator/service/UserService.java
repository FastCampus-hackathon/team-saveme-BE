package com.saveme.comparator.service;

import com.saveme.comparator.config.security.CustomUserDetails;
import com.saveme.comparator.domain.*;
import com.saveme.comparator.dto.JobDataDto;
import com.saveme.comparator.dto.WishSetDto;
import com.saveme.comparator.dto.WishSetMemoDto;
import com.saveme.comparator.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;

import java.util.List;

import static com.saveme.comparator.domain.Recruition.createRecruition;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final WishRepository wishRepository;
    private final RecruitionRepository recruitionRepository;
    private final WishSetRepository wishSetRepository;
    private final WishSetMemoRepository wishSetMemoRepository;

    public List<JobDataDto> getWishList(Authentication auth) {
        User user = getUserByAuth(auth);
        List<Wish> wishList = wishRepository.findAllByUser_UserId(user.getUserId());
        List<JobDataDto> dataDtoList = new ArrayList<>();
        wishList.forEach(
                        w -> dataDtoList.add(JobDataDto.createJobDataDtoWithWish(w)
        ));
        return dataDtoList;
    }



    @Transactional
    public void addWishJob(Authentication auth, JobDataDto jobDataDto) {

        User user = getUserByAuth(auth);

        if (!isAlreadySavedRecruition(jobDataDto)) {
            recruitionRepository.save(createRecruition(jobDataDto));
        }

        if (isUserAlreadyWishedRecruition(jobDataDto, user)) {
            wishRepository.deleteByUserAndRecruition_RecruitmentId(user,
                    convertRecruitionType(jobDataDto.getRecruitmentId()));
        } else {
            Wish savedWish = wishRepository.save(Wish.createWish(user, getRecruitionById(jobDataDto)));
            savedWish.addWishListOfUser(user);
            savedWish.addWishListOfRecruition(getRecruitionById(jobDataDto));
        }

    }


    public static long convertRecruitionType(String recruitionId) {
        return Long.parseLong(recruitionId);
    }

    private Recruition getRecruitionById(JobDataDto jobDataDto) {
        return recruitionRepository.findById(
                        convertRecruitionType(jobDataDto.getRecruitmentId()))
                .orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    private boolean isAlreadySavedRecruition(JobDataDto jobDataDto) {
        return recruitionRepository.existsByRecruitmentId(
                convertRecruitionType(jobDataDto.getRecruitmentId()));
    }

    public User getUserByAuth(Authentication auth) {
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userRepository.findById(customUserDetails.getUser().getUserId()).orElseThrow(
                () -> new IllegalArgumentException("no such user")
        );
        return user;
    }

    private boolean isUserAlreadyWishedRecruition(JobDataDto jobDataDto, User user) {
        return wishRepository.existsByUserAndRecruition_RecruitmentId(
                user, convertRecruitionType(jobDataDto.getRecruitmentId()));
    }

    public User createUser(User user) {

        final String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public void createWishSet(Authentication auth ,WishSetDto wishSetDto) {
        WishSet wishSet = WishSet.builder().setTitle(wishSetDto.getSetTitle()).build();
        wishSet.setUser(getUserByAuth(auth));
        wishSet = wishSetRepository.save(wishSet);

        List<WishSetMemoDto> wishSetMemoDtos =  wishSetDto.getWishSetMemos();

        for(WishSetMemoDto wishSetMemoDto : wishSetMemoDtos) {
            wishSetMemoRepository.save(WishSetMemo.builder()
                    .wishSetMemoPK(new WishSetMemoPK(wishSet.getWishSetId(),wishSetMemoDto.getWishId()))
                    .wish( wishRepository.getById(wishSetMemoDto.getWishId()))
                    .wishSet(wishSet)
                    .memo(wishSetMemoDto.getMemo())
                    .build());
        }
    }
}
