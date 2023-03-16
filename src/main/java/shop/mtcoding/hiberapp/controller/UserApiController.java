package shop.mtcoding.hiberapp.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.hiberapp.model.User;
import shop.mtcoding.hiberapp.model.UserJpaRepository;
import shop.mtcoding.hiberapp.model.UserRepository;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    // private final UserRepository userRepository;
    private final UserJpaRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(User user) {
        User userPS = userRepository.save(user);
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, User user) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        userPS.update(user.getPassword(), user.getEmail());
        User updateUserPS = userRepository.save(userPS);
        return new ResponseEntity<>(updateUserPS, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(userPS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findUsers(@RequestParam(defaultValue = "0") int page) {

        Page<User> userListPS = userRepository.findAll(PageRequest.of(page, 2));
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserOne(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }
}
