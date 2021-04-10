package com.xht.demo;

import ch.hsr.geohash.GeoHash;

public class TestDemo {

    public static void main(String[] args) {
        GeoHash geoHash=GeoHash.withCharacterPrecision(39.92123032, 116.51171875,5);
        for(GeoHash geo : geoHash.getAdjacent()){
            System.out.println(geo.toBase32());
        }
    }
    public void test(){

    }
}
