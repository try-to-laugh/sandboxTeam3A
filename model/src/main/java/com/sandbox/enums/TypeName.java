package com.sandbox.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeName {
    INCOME("INCOME"),
    EXPENSE("EXPENSE");

    private final String name;
}
