package com.xuxue.dapp.red.packetes.repository;

import com.xuxue.dapp.red.packetes.domain.TakeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TakeRecordRepository extends JpaRepository<TakeRecord,Integer>,JpaSpecificationExecutor<TakeRecord> {
}
