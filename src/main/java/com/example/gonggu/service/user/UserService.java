package com.example.gonggu.service.user;

import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createUser(Map<String, Object> info) {
        User user = new User();
        user.setUserId(info.get("userId").toString());
        user.setUserPw(bCryptPasswordEncoder.encode(info.get("userPw").toString()));
        user.setUserName(info.get("userName").toString());

        // signup 단계에서 계좌 정보를 받지 않음
//        if(info.get("userAccountBank") != null){
//            user.setAccounBank(info.get("userAccountBank").toString());
//            user.setAccountHolder(info.get("userAccountHolder").toString());
//            user.setAccountNum(info.get("userAccountNum").toString());
//        }

        userRepository.save(user);
    }

    public void userUpdate(Map<String, Object> info) {
        User user = userRepository.findByUserId(info.get("userId").toString());

        user.setUserName(info.get("userName").toString());
        user.setAccounBank(info.get("userAccountBank").toString());
        user.setAccountHolder(info.get("userAccountHolder").toString());
        user.setAccountNum(info.get("userAccountNum").toString());

        userRepository.save(user);
    }

    public void userSetPassword(Map<String,Object> info){
        User user = userRepository.findByUserId(info.get("userId").toString());
        user.setUserPw(bCryptPasswordEncoder.encode(info.get("userPw").toString()));

        userRepository.save(user);
    }


    public boolean loginUser(Map<String, Object> info) {
        User checkUser = userRepository.findByUserId(info.get("userId").toString());

        if (checkUser.getUserPw() == bCryptPasswordEncoder.encode(info.get("userPW").toString()))
            return true;
        else
            return false;
    }


    //info Map<String,Object>
    //return 타입 map<String,String>
    //학교 이매일 인증
    // User <- 토큰을 저장 User.getEmailAuth
    // /checkEmail/{id}/{EmailAuth}
    // User.getEmailAuth == parameter EmailAuth 일치?

}