package com.zjk.item.Service;

@FunctionalInterface
public interface Itest {
    void fa(int a,int b);
    default void fb(int a,int b){
         System.out.print(a+b);
    }
    static void fc(int a,int b){
        System.out.println(a+b);
    }
}
