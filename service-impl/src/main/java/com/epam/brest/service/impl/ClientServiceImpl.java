package com.epam.brest.service.impl;

import com.epam.brest.dao.ClientDao;
import com.epam.brest.model.Beverage;
import com.epam.brest.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ClientServiceImpl implements ClientService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * When beverages is made,this method decreases ingredient's quantity
     */
    @Override
    public Integer updateIngredientQuantity(List<Beverage> selectedBeverages) {
        return clientDao.updateIngredientQuantity(selectedBeverages);
    }
}
