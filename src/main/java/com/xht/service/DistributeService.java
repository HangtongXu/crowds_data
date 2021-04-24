package com.xht.service;

import org.springframework.stereotype.Service;

@Service
public interface DistributeService {
    public void auctionDistribute();
    public void normalDistribute();
}
