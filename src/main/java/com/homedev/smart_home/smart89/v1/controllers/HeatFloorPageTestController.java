package com.homedev.smart_home.smart89.v1.controllers;

import com.homedev.smart_home.smart89.v1.database.domain.HeatFloorDatabaseModel;
import com.homedev.smart_home.smart89.v1.database.repository.HeatFloorDatabaseModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAutoConfiguration
public class HeatFloorPageTestController {

    private static final Logger log = LoggerFactory.getLogger(
            HeatFloorPageTestController.class);

    private static int counter = 1;

    private HeatFloorDatabaseModelRepository heatFloorRepository;

    @Autowired
    public HeatFloorPageTestController(HeatFloorDatabaseModelRepository heatFloorRepository) {
        this.heatFloorRepository = heatFloorRepository;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String readersBooks(Model model) {

        HeatFloorDatabaseModel heatFloorDatabaseModel = heatFloorRepository.findHeatFloorByRoomName("hello");

        System.out.println(heatFloorDatabaseModel);

        if (heatFloorDatabaseModel != null) {
            System.out.println(heatFloorDatabaseModel.getRoomName());
        }

/*        List<Book> readingList =
                readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }*/
        return "test";
    }

    @RequestMapping(value = "/put")
    public String addToReadingList() {

        HeatFloorDatabaseModel heatFloorDatabaseModel = new HeatFloorDatabaseModel();
        heatFloorDatabaseModel.setRoomName(counter++ + "hello");

        heatFloorRepository.save(heatFloorDatabaseModel);

        return "redirect:/get";
    }

    @RequestMapping(value = "/save")
    public String save() {

        HeatFloorDatabaseModel heatFloorDatabaseModel = new HeatFloorDatabaseModel();
        heatFloorDatabaseModel.setRoomName("hello");
        heatFloorRepository.save(heatFloorDatabaseModel);
        return "redirect:/get";
    }
}
