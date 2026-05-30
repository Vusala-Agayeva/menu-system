package com.menu_system.controller;

import com.menu_system.model.MenuItem;
import com.menu_system.model.PreparationStatus;
import com.menu_system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<List<MenuItem>> getFullMenu() {
        List<MenuItem> items = menuService.getAllMenuItems();
        return ResponseEntity.ok(items);
    }

    /**
     * GET /api/v1/menu/{id}
     * Used when a user clicks on a single dish to check "How it's being prepared".
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuService.getMenuItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/menu
     * Used by restaurant managers or admins to append brand new dishes to the app.
     */
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem newItem) {
        MenuItem savedItem = menuService.createMenuItem(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    /**
     * PATCH /api/v1/menu/{id}/status
     * Used by the kitchen staff dashboard to update a dish's preparation tracking stage.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<MenuItem> updatePrepStatus(
            @PathVariable Long id,
            @RequestParam PreparationStatus status) {

        return menuService.updatePreparationStatus(id, status)
                .map(updatedItem -> {

                    messagingTemplate.convertAndSend("/topic/prep-updates", updatedItem);

                    return ResponseEntity.ok(updatedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
