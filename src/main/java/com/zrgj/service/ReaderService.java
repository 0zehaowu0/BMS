package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.ReaderCard;

import java.util.List;

public interface ReaderService {

    PageInfo<ReaderCard> queryReaderListAll(ReaderCard readerCard, Integer page, Integer limit);

    void addReaderSubmit(ReaderCard readerCard);

    ReaderCard queryReaderById(Integer id);

    void updateReaderSumbit(ReaderCard readerCard);

    void deleteReader(List<String> list);

    ReaderCard queryUserInfoByNameAndPassword(String username, String password);

    void updateReaderPwdSubmit(ReaderCard readerCard);

}
