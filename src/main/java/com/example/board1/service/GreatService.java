package com.example.board1.service;

import com.example.board1.dto.GreatForPostAndUserDto;
import com.example.board1.entity.Great;
import com.example.board1.entity.Post;
import com.example.board1.entity.User;
import com.example.board1.repository.GreatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class GreatService {

    private final GreatRepository greatRepository;
    private final PostService postService;
    private final UserService userService;


    // 좋아요 : true  , 삭제 : false
    @Transactional
    public Boolean likePost(Long postId, String userId) {
        Post post = postService.findPostByPostId(postId);
        User user = userService.findUserByUserId(userId);
//        Great great = greatRepository.findByPostIdAndUserId(postId, userId);

        // 사용자의 좋아요 목록에서 해당 포스트의 좋아요를 찾기
        Great great = user.getGreats().stream()
                .filter(g -> g.getPost().getId().equals(postId))
                .findFirst()
                .orElse(null);

        if(great == null){
//            Great newGreat = new Great(postId, userId);
            Great newGreat = new Great();
            newGreat.setPost(post);
            newGreat.setUser(user);
            user.getGreats().add(newGreat);
            greatRepository.save(newGreat);
            greatRepository.flush();  // 변경사항 즉시 반영
            return true;
        } else{
            user.getGreats().remove(great);  // 사용자 목록에서 제거
            greatRepository.delete(great);
            greatRepository.flush();  // 변경사항 즉시 반영
            return false;
        }
    }

    // 좋아요 개수
    public Long countGreatByPostId(Long postId) {
        greatRepository.flush();  // 변경사항 즉시 반영
        return greatRepository.countGreatByPostId(postId);
    }


    // 게시물 별 좋아요 수 상위 N개
    public List<GreatForPostAndUserDto> getTopPosts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = greatRepository.findTopPosts(pageable);

        return results.stream()
                .map(result -> GreatForPostAndUserDto.toDto((Long) result[0], (String) result[1], (Long) result[2]))
                .collect(Collectors.toList());
    }

    // 유저 별 좋아요 수 상위 N개
    public List<User> getTopUsers(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = greatRepository.findTopUsers(pageable);

        return results.stream()
                .map(result -> (User) result[0])
                .collect(Collectors.toList());
    }

    

}
