package com.cg.service.withdraws;

import com.cg.model.Withdraw;
import com.cg.repository.IWithdrawsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WithdrawService implements IWithdrawService {
    @Autowired
    private IWithdrawsRepository withdrawsRepository;

    @Override
    public Iterable<Withdraw> findAll() {
        return withdrawsRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withdrawsRepository.findById(id);
    }

    @Override
    public Withdraw getById(Long id) {
        return null;
    }

    @Override
    public void save(Withdraw withdraw) {
        withdrawsRepository.save(withdraw);
    }

    @Override
    public void remove(Long id) {
        withdrawsRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return withdrawsRepository.existsById(id);
    }
}
