package com.xuxue.dapp.red.packetes.service;

import com.xuxue.dapp.red.packetes.component.Account;
import com.xuxue.dapp.red.packetes.domain.TakeRecord;
import com.xuxue.dapp.red.packetes.domain.User;
import com.xuxue.dapp.red.packetes.model.Entry;
import com.xuxue.dapp.red.packetes.model.FailedEntry;
import com.xuxue.dapp.red.packetes.model.ObjectEntry;
import com.xuxue.dapp.red.packetes.repository.TakeRecordRepository;
import com.xuxue.dapp.red.packetes.repository.UserRepository;
import com.xuxue.dapp.red.packetes.util.Take;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedPacketsService {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private TakeRecordRepository takeRecordRepository;

    @Autowired
    private Account account;

    public Entry take(String address, String redAddress, String redId, String openId){
        try{
            User user = userRepository.findUserByOpenid(openId);
            if(user==null){
                return new FailedEntry(new Exception());
            }
            TakeRecord takeRecord = new TakeRecord();
            takeRecord.setRedAddress(redAddress);
            takeRecord.setRedId(redId);
            takeRecord.setOpenId(openId);
            takeRecordRepository.save(takeRecord);
            String hash = Take.take(account.getSecret(),redAddress,redId,address,takeRecord.getId()+"",account.getPassword());
            return new ObjectEntry(hash);
        }catch (Exception ex){
            return new FailedEntry(ex);
        }
    }



    public Entry message(String id){
        TakeRecord takeRecord = takeRecordRepository.findOne(Integer.parseInt(id));
        return new ObjectEntry(takeRecord);
    }

}
