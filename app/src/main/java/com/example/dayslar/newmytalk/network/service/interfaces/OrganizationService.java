package com.example.dayslar.newmytalk.network.service.interfaces;

import com.example.dayslar.newmytalk.utils.entity.Organization;
import com.example.dayslar.newmytalk.network.calback.RetrofitCallback;

public interface OrganizationService {

    void loadOrganization(RetrofitCallback<Organization> callback);
}
