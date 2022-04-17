package com.cg.repository;

import com.cg.model.Withdraw;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWithdrawsRepository extends PagingAndSortingRepository<Withdraw,Long> {
}
