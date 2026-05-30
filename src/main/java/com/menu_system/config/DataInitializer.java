package com.menu_system.config;

import com.menu_system.model.MenuItem;
import com.menu_system.model.PrepStep;
import com.menu_system.model.PreparationStatus;
import com.menu_system.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Prevent duplicate seed inserts if restarting with persistent data
        if (menuItemRepository.count() > 0) {
            return;
        }

        // --- ITEM 1: ARTISAN BURGER ---
        MenuItem burger = new MenuItem();
        burger.setName("Artisan Smash Burger");
        burger.setDescription("Double beef patty, sharp cheddar, secret sauce, toasted brioche bun.");
        burger.setPrice(14.99);
        burger.setImageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd");
        burger.setCurrentStatus(PreparationStatus.COOKING); // In the cooking stage for demonstration

        PrepStep step1 = new PrepStep(null, 1, "Prep fresh toppings: Slice pickles, onions, and crisp lettuce.", 2);
        PrepStep step2 = new PrepStep(null, 2, "Smash beef patties onto high-heat flat top grill to lock in juices.", 4);
        PrepStep step3 = new PrepStep(null, 3, "Melt sharp cheddar over patties and lightly toast brioche buns.", 1);
        PrepStep step4 = new PrepStep(null, 4, "Assemble burger with house secret sauce and wrap meticulously.", 2);

        burger.setPreparationSteps(List.of(step1, step2, step3, step4));


        // --- ITEM 2: NEAPOLITAN PIZZA ---
        MenuItem pizza = new MenuItem();
        pizza.setName("Margherita Neapolitan Pizza");
        pizza.setDescription("San Marzano tomato base, fresh mozzarella, fresh basil, extra virgin olive oil.");
        pizza.setPrice(16.50);
        pizza.setImageUrl("https://images.unsplash.com/photo-1604382354936-07c5d9983bd3");
        pizza.setCurrentStatus(PreparationStatus.RECEIVED); // Just entered the system queue

        PrepStep pStep1 = new PrepStep(null, 1, "Hand-stretch fermented sourdough to 12 inches.", 3);
        PrepStep pStep2 = new PrepStep(null, 2, "Ladle crushed San Marzano tomatoes evenly across base.", 1);
        PrepStep pStep3 = new PrepStep(null, 3, "Top with torn fresh mozzarella chunks and fresh basil leaves.", 1);
        PrepStep pStep4 = new PrepStep(null, 4, "Fire in wood-burning oven at 900°F (480°C) for 90 seconds.", 2);

        pizza.setPreparationSteps(List.of(pStep1, pStep2, pStep3, pStep4));

        // Save everything to the H2 Database
        menuItemRepository.saveAll(List.of(burger, pizza));

        System.out.println(">> Database successfully seeded with 2 signature Menu Items!");
    }
}