package com.kurator.crawler.crawlerProcessor;

import com.kurator.library.model.Message;

public interface ICrawler {
    void crawl(Message msg);
}
