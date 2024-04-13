package com.example.fashioncommuni.member.service;

import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.UserDto;
import com.example.fashioncommuni.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserDto userDto) {
        // UserDto에서 비밀번호를 가져와서 암호화합니다.
        String encodedPassword = passwordEncoder.encode(userDto.password());

        // 암호화된 비밀번호를 가진 새로운 UserDto 인스턴스를 생성합니다.
        // 여기서는 UserDto가 record이므로, 직접 수정이 불가능하고 새 인스턴스를 생성해야 합니다.
        UserDto encryptedUserDto = new UserDto(
                userDto.userId(),
                userDto.loginId(),
                encodedPassword,
                userDto.username(),
                userDto.email(),
                userDto.age(),
                userDto.gender(),
                userDto.roleType(),
                userDto.status()
        );

        // 암호화된 비밀번호를 포함하는 UserDto를 기반으로 User 엔티티 인스턴스를 생성합니다.
        User user = User.of(encryptedUserDto);

        // UserRepository를 통해 데이터베이스에 저장합니다.
        userRepository.save(user);
    }
}
