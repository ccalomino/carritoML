package com.example.carritoWeb.controller;

public class Counter
{
    private int count;

    public Counter()
    {
        count = 0;
    }

    public Counter(int init)
    {
        count = init;
    }

    public int get()
    {
        return count;
    }

    public int clear()
    {
        count = 0;
        return count;
    }

    public int incrementAndGet()
    {
        return ++count;
    }
    
    public int decrementAndGet()
    {
        return --count;
    }

    public String toString()
    {
        return ""+count;
    }
}