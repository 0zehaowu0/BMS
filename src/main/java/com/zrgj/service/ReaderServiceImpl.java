package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.dao.ReaderCardMapper;
import com.zrgj.po.ReaderCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderCardMapper readerCardMapper;



    @Override
    public PageInfo<ReaderCard> queryReaderListAll(ReaderCard readerCard, Integer page, Integer limit) {
        List<ReaderCard> readerCards = readerCardMapper.queryReaderListAll(readerCard);
        //PageHelper.startPage(page,limit);
        return new PageInfo<>(readerCards);
    }

    @Override
    public void addReaderSubmit(ReaderCard readerCard) {
        readerCardMapper.insertSelective(readerCard);
    }

    @Override
    public ReaderCard queryReaderById(Integer id) {
        ReaderCard readerCard = readerCardMapper.selectByPrimaryKey(id);
        return readerCard;
    }

    @Override
    public void updateReaderSumbit(ReaderCard readerCard) {
        readerCardMapper.updateByPrimaryKey(readerCard);
    }

    @Override
    public void deleteReader(List<String> list) {
        for (String id : list) {
            int id2 = Integer.valueOf(id);
            readerCardMapper.deleteByPrimaryKey(id2);
        }
    }

    @Override
    public ReaderCard queryUserInfoByNameAndPassword(String username, String password) {
        ReaderCard readerCard = readerCardMapper.queryUserInfoByNameAndPassword(username, password);
        return readerCard;
    }

    @Override
    public void updateReaderPwdSubmit(ReaderCard readerCard) {
        readerCardMapper.updateByPrimaryKeySelective(readerCard);
    }

}

