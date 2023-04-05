package hello.itemService.web.basic;

import hello.itemService.domain.item.Item;
import hello.itemService.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor    //주석처리 부분 전체 자동생성
public class basicItemController {

    private final ItemRepository itemRepository;
//    @Autowired
//    public basicItemController(ItemRepository repository) {
//        this.repository = repository;
//    }
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }



    /**
     * 테스트용 데이터추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10,10000));
        itemRepository.save(new Item("itemB", 20,20000));
    }
}
