package com.menu_system.service;

import com.menu_system.model.MenuItem;
import com.menu_system.model.PreparationStatus;
import com.menu_system.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Transactional(readOnly = true)
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    @Transactional
    public MenuItem createMenuItem(MenuItem item) {
        return menuItemRepository.save(item);
    }

    @Transactional
    public Optional<MenuItem> updatePreparationStatus(Long id, PreparationStatus newStatus) {
        return menuItemRepository.findById(id).map(item -> {
            item.setCurrentStatus(newStatus);
            return menuItemRepository.save(item); // Updates the status in the DB
        });
    }
}
