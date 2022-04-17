package com.cg.repository;

import com.cg.model.Transfer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransferRepository extends PagingAndSortingRepository<Transfer,Long> {
}
