package com.example.study.controller.api;

import com.example.study.model.entity.Settlement;
import com.example.study.model.entity.User;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.SettlementRepository;
import com.example.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/settlement")
public class SettlementApiController {

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    // 조회
    @GetMapping("/{id}")
    public Settlement settlementCheck(@PathVariable Long id){
        if (userRepository.findById(id).isPresent()){
            Settlement settlement = Settlement.builder()
                    .userId(id)
                    .price(settlementRepository.getOne(id).getPrice())
                    .build();
            return settlement;
        } else {
            Settlement settlement = Settlement.builder()
                    .userId(null)
                    .price(null)
                    .build();
            return settlement;
        }
    }


    // 전체 유저에 대해 누적 사용 금액을 업데이트하는 메소드
    @GetMapping("/allUpdate")
    public void allUpdate(){
        List<User> userList = userRepository.findAll();
        userList.stream()
                .map(user -> {

                    if (settlementRepository.findById(user.getId()).isPresent()) {
                        settlementRepository.save(settlementRepository.getOne(user.getId()).setPrice(BigDecimal.valueOf(0)));
                    } else {
                        Settlement settlement = Settlement.builder()
                                .userId(user.getId())
                                .price(BigDecimal.valueOf(0))
                                .build();
                        settlementRepository.save(settlement);
                    }

                    user.getOrderGroupList().stream()
                            .map(orderGroup -> {
                                BigDecimal newPrice = settlementRepository
                                        .getOne(user.getId())
                                        .getPrice()
                                        .add(orderGroup.getTotalPrice());

                                Settlement settlement = Settlement.builder()
                                        .userId(user.getId())
                                        .price(newPrice)
                                        .build();
                                settlementRepository.save(settlement);
                                return null;
                            })
                            .collect(Collectors.toList());
                    return null;
                })
                .collect(Collectors.toList());
    }
}
