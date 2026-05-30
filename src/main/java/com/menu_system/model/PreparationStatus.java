package com.menu_system.model;

public enum PreparationStatus {
    RECEIVED,    // Order accepted by kitchen
    PREPPING,    // Ingredients are being gathered/chopped
    COOKING,     // On the stove/grill/oven
    PLATING,     // Final garnishes and staging
    READY        // Ready for service/delivery
}
