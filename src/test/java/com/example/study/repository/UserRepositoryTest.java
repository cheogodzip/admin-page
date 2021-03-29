package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests{

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {

        String account = "Test01";
        String password = "Test01";
        String status = "REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-2222";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);

        User newUser = userRepository.save(user);

        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        if (user != null){
            user.getOrderGroupList().stream().forEach(orderGroup -> {

                System.out.println("----------------주문묶음----------------");
                System.out.println("수령인 : " + orderGroup.getRevName());
                System.out.println("수령지 : " + orderGroup.getRevAddress());
                System.out.println("총금액 : " + orderGroup.getTotalPrice());
                System.out.println("총수량 : " + orderGroup.getTotalQuantity());
                
                System.out.println("----------------주문상세----------------");
                orderGroup.getOrderDetailList().stream().forEach(orderDetail -> {
                    System.out.println("주문의 상태 : " + orderDetail.getStatus());
                    System.out.println("도착예정일자 : " + orderDetail.getArrivalDate());
                });

            });
        }
    }

    @Test
    @Transactional
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(seletUser -> {
            seletUser.setAccount("pppp");
            seletUser.setUpdatedAt(LocalDateTime.now());
            seletUser.setUpdatedBy("update method()");

            userRepository.save(seletUser);
        });
    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(3L);

        Assertions.assertTrue(user.isPresent());

        user.ifPresent(seletUser -> {
            userRepository.delete(seletUser);
        });

        Optional<User> deleteUser = userRepository.findById(3L);

        Assertions.assertFalse(deleteUser.isPresent());
    }
}
