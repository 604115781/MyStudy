package com.zjk.item.Service;

import java.time.Clock;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/9/10 11:33
 */
public class testImpl {

    public static void main(String[] args) {
        Itest fa = (int a, int b) -> {
            System.out.print(a + b);
        };
        fa.fa(1, 2);
    }
}
