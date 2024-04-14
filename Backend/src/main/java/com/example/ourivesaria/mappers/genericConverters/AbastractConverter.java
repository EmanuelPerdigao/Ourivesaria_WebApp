package com.example.ourivesaria.mappers.genericConverters;

public interface AbastractConverter<T, S> {

    T convertToEntity(S dto);
    S convertToDto(T entity);
}
