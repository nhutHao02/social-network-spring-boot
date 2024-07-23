package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.follow.FollowRequest;
import com.nhuthao02.social_network.dtos.responses.follow.FollowResponse;
import com.nhuthao02.social_network.entities.Follow;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.FollowMapper;
import com.nhuthao02.social_network.mapper.UserMapper;
import com.nhuthao02.social_network.repositories.FollowRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.IFollowService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowService implements IFollowService {
    @Autowired
    FollowRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowMapper mapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean add(FollowRequest request) {
        if (!userRepository.existsById(request.getUuidUserFollower())) throw new AppException(ErrorCode.USER_NOT_FOUND);
        if (!userRepository.existsById(request.getUuidUserFollowing()))
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        repository.save(mapper.requestToFollow(request));
        return true;
    }

    @Override
    public boolean delete(FollowRequest request) {
        if (!userRepository.existsById(request.getUuidUserFollower())) throw new AppException(ErrorCode.USER_NOT_FOUND);
        if (!userRepository.existsById(request.getUuidUserFollowing()))
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        Follow follow = repository.findFollowByUuidUserFollowerAndUuidUserFollowing(request.getUuidUserFollower(), request.getUuidUserFollowing()).orElseThrow(() -> new AppException(ErrorCode.UNFOLLOW_FAILURE));

        repository.delete(follow);
        return true;
    }

    @Override
    public List<FollowResponse> getFollower(String id, Integer page, Integer limit) {
        List<FollowResponse> listRs = new ArrayList<>();
        if (!userRepository.existsById(id)) throw new AppException(ErrorCode.USER_NOT_FOUND);
        Page<Follow> followers = repository.findAllByUuidUserFollowing(id, PageRequest.of(page, limit));

        if (followers.isEmpty()) throw new AppException(ErrorCode.FOLLOWER_NOT_FOUND);
        for (Follow follow :
                followers.getContent()) {
            String userId = follow.getUuidUserFollower();
            User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            listRs.add(userMapper.userToFollowResponse(user));
        }

        return listRs;
    }

    @Override
    public List<FollowResponse> getFollowing(String id, Integer page, Integer limit) {
        List<FollowResponse> listRs = new ArrayList<>();
        if (!userRepository.existsById(id)) throw new AppException(ErrorCode.USER_NOT_FOUND);
        Page<Follow> followers = repository.findAllByUuidUserFollower(id, PageRequest.of(page, limit));

        if (followers.isEmpty()) throw new AppException(ErrorCode.FOLLOWING_NOT_FOUND);
        for (Follow follow :
                followers.getContent()) {
            String userId = follow.getUuidUserFollowing();
            User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            listRs.add(userMapper.userToFollowResponse(user));
        }
        return listRs;
    }
}
