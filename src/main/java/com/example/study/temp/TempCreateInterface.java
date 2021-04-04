package com.example.study.temp;

import com.example.study.model.network.Header;
import org.springframework.http.ResponseEntity;

public interface TempCreateInterface<Req, Res> {

    ResponseEntity<Header<Res>> create(Header<Req> request);

}
