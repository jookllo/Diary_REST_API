package com.example.diary.controller;

import com.example.diary.model.Diary;
import com.example.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DiaryController {
    @Autowired
    DiaryRepository diaryRepository;

    @GetMapping("/diary_entry")
    public ResponseEntity<List<Diary>> getAllDiaryEntries(@RequestParam(required = false) String title){
        try {
            List<Diary> diaries = new ArrayList<Diary>();

            if (title == null) {
                diaries.addAll(diaryRepository.findAll());
            } else {
                diaries.addAll(diaryRepository.findByTitleContaining(title));
            }

            if (diaries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(diaries, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
        @GetMapping("/diary_entry/{id}")
        public ResponseEntity<Diary> getDiaryById(@PathVariable("id") long id){
            Optional<Diary> diaryData = diaryRepository.findById(id);

            return diaryData.map(diary -> new ResponseEntity<>(diary, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping("/diary_entry")
        public ResponseEntity<Diary> createDiaryEntry(@RequestBody Diary diary){
        try {
            Diary _diary = diaryRepository.save(new Diary(diary.getTitle(), diary.getDescription(), false));
            return new ResponseEntity<>(_diary,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        @PutMapping("diary_entry/{id}")
        public ResponseEntity<Diary> updateDiaryEntry(@PathVariable("id") long id,@RequestBody Diary diary){
               Optional<Diary> diaryData = diaryRepository.findById(id);
               if(diaryData.isPresent()){
                   Diary _diary = diaryData.get();
                   _diary.setTitle(diary.getTitle());
                   _diary.setDescription(diary.getDescription());
                   _diary.setPublished(diary.isPublished());
                   return new ResponseEntity<>(diaryRepository.save(_diary),HttpStatus.OK);
               }else{
                   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
               }
        }

        @DeleteMapping("/diary_entry/{id}")
        public ResponseEntity<HttpStatus> deleteDiaryEntry(@PathVariable("id") long id) {
            try {
                diaryRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @DeleteMapping("/diary_entry")
        public ResponseEntity<HttpStatus> deleteAllDiaryEntries() {
            try {
                diaryRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    @GetMapping("/diary_entry/published")
    public ResponseEntity<List<Diary>> findByPublished() {
        try {
            List<Diary> diaryentry = diaryRepository.findByPublished(true);

            if (diaryentry.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(diaryentry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        }


