package com.example.popularmoviesapp.popularmoviesapp.repository;

public class RepoFactory {
    public static DataRepository getDataRepository() {
        return AppDataRepository.getInstance();
    }
}
