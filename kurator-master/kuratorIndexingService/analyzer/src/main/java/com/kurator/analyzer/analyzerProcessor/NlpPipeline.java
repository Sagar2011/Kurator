package com.kurator.analyzer.analyzerProcessor;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class NlpPipeline {
    private static Properties properties;
    private static String propertiesName= "tokenize, ssplit, pos, lemma";
    private static StanfordCoreNLP stanfordCoreNLP;
    private NlpPipeline()
    {
    }
    static {
        properties= new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    public static StanfordCoreNLP getPipeline() {
        if(stanfordCoreNLP== null) {
            stanfordCoreNLP= new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}
