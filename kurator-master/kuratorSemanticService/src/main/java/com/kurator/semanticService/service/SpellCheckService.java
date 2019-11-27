package com.kurator.semanticService.service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class SpellCheckService {


    @Value("classpath:dictionary.txt")
    private Resource resourceDictionary;

    //directory creation
    public String spellCheck(String input) throws IOException {
//  Load the dictionary.txt file and store in directory
        Directory directory = new SimpleFSDirectory(new File("classpath:dictionary.txt"));

//  Create object of SpellChecker
        final SpellChecker sp = new SpellChecker(directory);

//  Take one line at
        sp.indexDictionary(new PlainTextDictionary(resourceDictionary.getInputStream()));


        //your 'wrong' search
        String search = "";


        //number of suggestions
        final int suggestionNumber = 1;


        //get the suggested words
        boolean correct = true;

        //get the suggested words
        ArrayList<String> brokenWords = CreateListOfWord(input);
        for (String words : brokenWords) {
            System.out.println("Inside loop " + words);
            String[] suggestions = sp.suggestSimilar(words, suggestionNumber);
//            System.out.println("if you are right, then"+ suggestions[0]);
            System.out.println(Arrays.toString(suggestions));
            if (suggestions == null || suggestions.length == 0) {
                correct = false;
                search += words + "" + " ";
            }
            for (String word : suggestions) {
                search += word + " ";
            }

        }


            return search;
    }
    public ArrayList<String> CreateListOfWord(String input)
    {
        String[] words=input.split(" ");
//        System.out.println(words);
        List<String > listOfWord= Arrays.asList(words);
        ArrayList<String> arrayListOfWords=new ArrayList<>(listOfWord);
        return arrayListOfWords;
    }
}
