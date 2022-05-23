package com.example.springmongo.controller;

import com.example.springmongo.model.Item;
import com.example.springmongo.repositories.ItemDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ItemController {
    ItemDAO itemDAO;


    @Autowired
    public ItemController(ItemDAO animalDAO) {
        this.itemDAO = animalDAO;
    }

    public List<Item> getAllItems() {
        return itemDAO.findAll();
    }

    public void addAllItems(List<Item> cars) {
        itemDAO.saveAll(cars);
    }

    public void add(Item item){
        if (!itemDAO.existsById(item.getId())){
            itemDAO.save(item);
        }
    }

    public Item getItem(String id){
        return itemDAO.findById(new ObjectId(id)).get();
    }

    public void deleteById(String id){
        itemDAO.deleteById(new ObjectId(id));
    }

    public void update(String id, Item item) {
        Item finish = getItem(id);

        finish.setName(item.getName());
        finish.setDescription(item.getDescription());
        //real.setQuantity(animal.getQuantity());

        itemDAO.save(finish);
    }

    public void updateAll(List<Item> items){
        for (Item c : getAllItems()){
            for (Item i: items){
                if (c.getId() == i.getId()){
                    c.setName(i.getName());
                    c.setDescription(i.getDescription());
                    //p.setQuantity(pp.getQuantity());
                    itemDAO.save(c);
                }
            }
        }
    }

    public void patchItems(String id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Item item = getItem(id);
        Item itemPatch = applyPatch(patch, item);

        itemDAO.save(itemPatch);

    }

    private Item applyPatch(JsonPatch patch, Item item) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched = patch.apply(objectMapper.convertValue(item, JsonNode.class));
        return objectMapper.treeToValue(patched, Item.class);
    }

}
