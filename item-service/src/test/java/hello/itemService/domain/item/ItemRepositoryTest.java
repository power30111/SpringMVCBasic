package hello.itemService.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }
    @Test
    void save(){
        //given
        Item item = new Item("itemA",10,10000);
        //when
        Item saveItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }
    @Test
    void findAll(){
        //given
        Item item1 = new Item("itemB",10,10000);
        Item item2 = new Item("itemC",20,20000);
        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);
    }
    @Test
    void updateItem(){
        //given
        Item item = new Item("itemA",10,10000);
        Item savedItem = itemRepository.save(item);
        long itemId = savedItem.getId();
        //when
        Item itemB = new Item("itemB", 20, 20000);
        itemRepository.update(itemId,itemB);
        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(itemB.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(itemB.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(itemB.getQuantity());

    }

}
