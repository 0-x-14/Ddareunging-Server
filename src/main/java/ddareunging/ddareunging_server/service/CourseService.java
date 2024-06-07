package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.Like;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.dto.*;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.LikeRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, LikeRepository likeRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FindCoursesResponseDTO getCoursesByTheme(Integer theme) {
        try {
            // 테마로 코스 조회

            List<Course> courses = courseRepository.findCoursesByTheme(theme);

            if (courses.isEmpty()) {
                // 테마를 찾을 수 없는 경우
                return FindCoursesResponseDTO.builder()
                        .theme(null)
                        .courses(new ArrayList<>())
                        .message("존재하지 않는 테마입니다").build();
            }
            // 현재 서비스에 등록된 테마는 두 가지
            // 추후 테마가 추가되어도 서비스에서 자체적으로 제공하는 테마별 추천 코스가 있다고 가정하고 courses가 null인 경우 존재하지 않는 테마입니다라는 메세지를 반환하도록 함

            List<CourseDTO> coursesDTO = courses.stream()
                    .map(course -> {
                        String userNickname = course.getUser().getNickname();
                        return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                    })
                    .collect(Collectors.toList());

            return FindCoursesResponseDTO.builder()
                    .theme(theme)
                    .courses(coursesDTO)
                    .message("테마별 코스가 정상적으로 조회되었습니다.").build();
        } catch (Exception e) {
            // 예외 처리
            return FindCoursesResponseDTO.builder()
                    .theme(theme)
                    .courses(Collections.emptyList()) // 비어 있는 리스트 반환
                    .message("테마별 코스 조회 중 오류가 발생했습니다.: " + e.getMessage()).build();
        }
    }

    @Transactional
    public FindMyCoursesResponseDTO getCoursesByUser(Long userId) {
        try {
            // 나만의 코스 조회

            User user = userRepository.findUserByUserId(userId);

            if (user == null) {
                // 사용자를 찾을 수 없는 경우
                return FindMyCoursesResponseDTO.builder()
                        .userId(null)
                        .courses(new ArrayList<>())
                        .message("조회되지 않는 사용자입니다").build();
            }

            List<Course> courses = courseRepository.findCoursesByUserUserId(userId);

            if (courses.isEmpty()) {
                // 비어 있는 경우, 응답 DTO에 메시지만 설정하여 반환
                return FindMyCoursesResponseDTO.builder()
                        .userId(userId)
                        .courses(new ArrayList<>()) // 비어있는 list 반환
                        .message("나만의 코스 만들기").build();
            } // 조회된 코스가 없는 경우

            List<CourseDTO> myCoursesDTO = courses.stream()
                    .map(course -> {
                        String userNickname = course.getUser().getNickname();
                        return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                    })
                    .collect(Collectors.toList());

            return FindMyCoursesResponseDTO.builder()
                    .userId(userId)
                    .courses(myCoursesDTO)
                    .message("나만의 코스가 정상적으로 조회되었습니다.").build();
        } catch (Exception e) {
            // 예외 처리
            return FindMyCoursesResponseDTO.builder()
                    .userId(userId)
                    .courses(new ArrayList<>()) // 비어있는 list로 초기화
                    .message("나만의 코스 조회 중 오류가 발생했습니다.: " + e.getMessage()).build();
        }
    }

    @Transactional
    public FindMyLikedCoursesResponseDTO getLikedCoursesByUser(Long userId) {
        try {
            // 내가 찜한 코스 조회

            User user = userRepository.findUserByUserId(userId);

            if (user == null) {
                // 사용자를 찾을 수 없는 경우
                return FindMyLikedCoursesResponseDTO.builder()
                        .userId(null)
                        .courses(new ArrayList<>())
                        .message("존재하지 않는 사용자입니다").build();
            }

            List<Like> likes = likeRepository.findLikeByUserId(userId);

            if (likes.isEmpty()) {
                // 비어 있는 경우, 응답 DTO에 메시지만 설정하여 반환
                return FindMyLikedCoursesResponseDTO.builder()
                        .userId(userId)
                        .courses(new ArrayList<>()) // 비어있는 list 반환
                        .message("찜한 코스가 없습니다.").build();
            } // 조회된 코스가 없는 경우

            List<CourseDTO> likedCoursesDTO = likes.stream()
                    .map(like -> {
                        Course course = like.getCourse();
                        String userNickname = course.getUser().getNickname(); // 해당 course가 참조하는 user의 nickname, 즉 코스를 만든 사람의 닉네임을 가져옴
                        return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                    })
                    .collect(Collectors.toList());

            return FindMyLikedCoursesResponseDTO.builder()
                    .userId(userId)
                    .courses(likedCoursesDTO)
                    .message("찜한 코스가 정상적으로 조회되었습니다.").build();
        } catch (Exception e) {
            // 예외 처리
            return FindMyLikedCoursesResponseDTO.builder()
                    .userId(userId)
                    .courses(new ArrayList<>()) // 비어있는 list로 초기화
                    .message("찜한 코스 조회 중 오류가 발생했습니다.: " + e.getMessage()).build();
        }
    }
}