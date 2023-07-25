package com.example.diary.repository;

import com.example.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary,Long> {
    List<Diary> findByPublished(boolean published);
    List<Diary> findByTitleContaining(String title);
}
