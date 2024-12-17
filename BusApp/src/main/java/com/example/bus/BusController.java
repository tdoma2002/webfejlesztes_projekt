package com.example.bus;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BusController {
    private static final Logger logger = LoggerFactory.getLogger(BusController.class);

    @Autowired
    private BusRepository busRepository;

    @GetMapping("/feltoltes")
    public String showForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "feltoltes";
    }

    @PostMapping("/buszaink")
    public String submitForm(@ModelAttribute Bus bus, Model model) {
        busRepository.save(bus);
        model.addAttribute("bus", bus);
        return "buszaink";
    }

    @GetMapping("/bus")
    public String getAllBuses(Model model) {
        model.addAttribute("buses", busRepository.findAll());
        return "busList";
    }

    @PostMapping("/bus")
    public String submitForm1(@ModelAttribute Bus bus, Model model) {
        logger.info("Bus objektum: {}", bus);
        busRepository.save(bus);
        model.addAttribute("message", "A busz adatai sikeresen elmentve!");
        model.addAttribute("bus", new Bus());
        return "feltoltes";
    }

    @GetMapping("/form")
    public String showFeltoltesPage(Model model) {
        List<Bus> buses = busRepository.findAll();
        model.addAttribute("buses", buses);
        model.addAttribute("bus", new Bus());
        return "feltoltes";
    }

    // Törlés
    @GetMapping("/bus/delete/{id}")
    public String deleteBus(@PathVariable Long id, Model model) {
        Optional<Bus> bus = busRepository.findById(id);
        if (bus.isPresent()) {
            busRepository.delete(bus.get());
            model.addAttribute("message", "A busz sikeresen törölve lett!");
        } else {
            model.addAttribute("message", "A megadott busz nem található!");
        }
        return "redirect:/bus"; // Visszairányítás a buszok listájához
    }

    // Módosítás (űrlap megjelenítése)
    @GetMapping("/bus/edit/{id}")
    public String editBusForm(@PathVariable Long id, Model model) {
        Optional<Bus> bus = busRepository.findById(id);
        if (bus.isPresent()) {
            model.addAttribute("bus", bus.get());
            return "editBus"; // Szerkesztési oldal
        } else {
            model.addAttribute("message", "A megadott busz nem található!");
            return "redirect:/bus";
        }
    }

    // Módosítás (adatok mentése)
    @PostMapping("/bus/edit")
    public String updateBus(@ModelAttribute Bus bus, Model model) {
        busRepository.save(bus); // Frissítés
        model.addAttribute("message", "A busz adatai sikeresen frissítve lettek!");
        return "redirect:/bus";
    }

    // Új végpont a buszaink oldalhoz
    @GetMapping("/buszaink")
    public String showBuszaink(Model model) {
        List<Bus> buses = busRepository.findAll(); // Az összes busz lekérése
        model.addAttribute("buses", buses); // Átadjuk a Thymeleaf sablon számára
        return "buszaink"; // A buszaink.html sablonhoz irányít
    }

        // Busz foglalása
        @GetMapping("/bus/book/{id}")
        public String bookBus(@PathVariable Long id, Model model) {
            Optional<Bus> bus = busRepository.findById(id);
            if (bus.isPresent()) {
                Bus b = bus.get();
                b.setBooked(true); // A busz foglalása
                busRepository.save(b); // Adatok mentése
                model.addAttribute("message", "A busz sikeresen le lett foglalva!");
            } else {
                model.addAttribute("message", "A megadott busz nem található!");
            }
            return "redirect:/buszaink"; // Visszairányítás a buszaink oldalra
        }

        @GetMapping("/bus/cancel/{id}")
        public String cancelBooking(@PathVariable Long id, Model model) {
            Optional<Bus> bus = busRepository.findById(id);
            if (bus.isPresent()) {
                Bus b = bus.get();
                b.setBooked(false); // Visszamondás: a busz foglaltságát töröljük
                busRepository.save(b); // Mentsük el az adatokat az adatbázisba
                model.addAttribute("message", "A busz foglalása sikeresen visszamondva!");
            } else {
                model.addAttribute("message", "A megadott busz nem található!");
            }
            return "redirect:/buszaink"; // Visszairányítunk a buszok listájához
        }

    }



