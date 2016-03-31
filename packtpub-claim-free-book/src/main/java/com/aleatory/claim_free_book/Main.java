package com.aleatory.claim_free_book;

import com.aleatory.claim_free_book.utils.httpConn.HttpUtils;

/**
 * Created by fspolti on 3/30/16.
 */
public class Main {

    private static HttpUtils httpUtils = new HttpUtils();

    public static void main(String[] args) {

        //Claiming the free book
        httpUtils.claimFreeBook();

    }
}