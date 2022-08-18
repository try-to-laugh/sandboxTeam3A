package com.sandbox.mapper;

import org.springframework.stereotype.Component;

@Component
public interface MapperRest<D, A> {

    D toDto(A apiDto);

    A toApiDto(D dto);
}
