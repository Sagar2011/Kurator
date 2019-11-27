package com.kurator.semanticService.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class BadWordsFilter {

    @Autowired
    private ResourceLoader resourceLoader;




    //load the file for the removal of bad words provided by the list from google itself
    public boolean filterText(String input) {
        try {

            Resource res = resourceLoader.getResource("classpath:badwords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));

            List<String> badWords = new ArrayList<String>();
            badWords = reader.lines().collect(Collectors.toList());
            ArrayList<String> allWords = Stream.of(input.split(" "))
                    .collect(Collectors.toCollection(ArrayList<String>::new));
            int inputSize = allWords.size();
            allWords.removeAll(badWords);
//			String result = allWords.stream().collect(Collectors.joining(" "));
            if (allWords.size() != inputSize) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
