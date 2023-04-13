package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.saveCheck;
import hello.itemservice.domain.item.updateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }

//    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,result},null);
            }
        }
        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v4/addForm";
        }

        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV2(@Validated(value = saveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,result},null);
            }
        }
        //검증에 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){ //에러가 있다면
            log.info("errors = {}",bindingResult);
            return "validation/v4/addForm";
        }

        //검증에 성공시 아래 로직실행
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

//    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute Item item,BindingResult bindingResult) {

        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,result},null);
            }
        }
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v4/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @Validated(value = updateCheck.class) @ModelAttribute Item item,BindingResult bindingResult) {

        if (item.getPrice() != null && item.getQuantity() != null){
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,result},null);
            }
        }
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v4/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v4/items/{itemId}";
    }

}

