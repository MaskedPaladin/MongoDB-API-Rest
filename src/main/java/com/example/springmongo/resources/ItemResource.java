package com.example.springmongo.resources;

import com.example.springmongo.controller.ItemController;
import com.example.springmongo.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ItemResource.Product_RESOURCE)
public class ItemResource {
    public final static String Product_RESOURCE = "/items";

    ItemController itemController;

    @Autowired
    public ItemResource(ItemController itemController) {
        this.itemController = itemController;
    }

    @GetMapping
    public List<Item> item(){
        return itemController.getAllItems();
    }

    @GetMapping("{id}")
    public Item item(@PathVariable("id") String id){
        return itemController.getItem(id);
    }

    @PostMapping
    public void addItem(@RequestBody Item item){
        itemController.add(item);
    }

    @DeleteMapping("{id}")
    public void deleteItem(@PathVariable("id") String id){
        itemController.deleteById(id);
    }

    @PutMapping("{id}")
    public void putItems(@PathVariable("id") String id, @RequestBody Item item){
        itemController.update(id, item);
    }

    @PatchMapping("{id}")
    public void patchItems(@PathVariable("id") String id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        itemController.patchItems(id,patch);
    }
}
