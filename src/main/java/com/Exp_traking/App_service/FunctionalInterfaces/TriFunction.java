package com.Exp_traking.App_service.FunctionalInterfaces;

@FunctionalInterface
public interface TriFunction  <T, V, R> {
    R apply(T t, V v);
}
