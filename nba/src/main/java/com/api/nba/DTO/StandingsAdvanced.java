package com.api.nba.DTO;

public record StandingsAdvanced(String seed, String season, String team, String overall,
                                String home, String road, String E, String W, String A, String C, String SE,
                                String NW, String P, String SW, String pre, String post, String winLoseRecordLessThanEqualTo3,
                                String winLoseRecordGreaterOrEqualTo10, String oct, String nov, String dec, String jan,
                                String feb, String mar, String apr) {}
