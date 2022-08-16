package com.phoenix.rabbitauthorization.service.impl;

import com.phoenix.rabbitauthorization.entity.SysAuthorization;
import com.phoenix.rabbitauthorization.mapper.SysAuthorizationMapper;
import com.phoenix.rabbitauthorization.service.SysAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysAuthorizationServiceImpl implements SysAuthorizationService {

    @Autowired
    private SysAuthorizationMapper sysAuthorizationMapper;

    @Override
    public void insert(SysAuthorization sysAuthorization) {
        sysAuthorizationMapper.insert(sysAuthorization);
    }
}
