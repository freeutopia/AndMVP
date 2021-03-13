package com.utopia.demo.response;

import com.utopia.demo.bean.Jokes;

import java.util.List;


public class JokesResponse {
    public String reason;
    public int error_code;
    public Result result;

    public static class Result{
        public List<Jokes> data;
    }
}
