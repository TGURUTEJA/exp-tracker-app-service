package com.Exp_traking.App_service.FunctionalInterfaces;

@FunctionalInterface
public interface BiFunction <T, U, R> {
    R apply(T t, U u);
}
