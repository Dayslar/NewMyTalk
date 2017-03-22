package com.dayslar.newmytalk.network.service.interfaces;

import com.dayslar.newmytalk.utils.entity.Organization;
import com.dayslar.newmytalk.network.calback.RetrofitCallback;

public interface OrganizationService {

    void loadOrganization(RetrofitCallback<Organization> callback);
}
