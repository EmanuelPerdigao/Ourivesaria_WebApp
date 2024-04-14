package com.example.ourivesaria.mappers.genericConverters;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConverterImpl<S, T> implements AbastractConverter<S, T>{

    public List<T> convertToDTOList(List<S> listToConvert) {

        List<T> newList = new ArrayList<>();

        for (S element : listToConvert) {
            newList.add(convertToDto(element));
        }
        return newList;
    }

    public List<S> convertToEntityList(List<T> listToConvert) {

        List<S> newList = new ArrayList<>();

        for (T element : listToConvert) {
            newList.add(convertToEntity(element));
        }
        return newList;
    }

}
